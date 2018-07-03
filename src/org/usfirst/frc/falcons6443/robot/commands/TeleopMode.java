package org.usfirst.frc.falcons6443.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.falcons6443.robot.Robot;
import org.usfirst.frc.falcons6443.robot.hardware.Joysticks.Xbox;
import org.usfirst.frc.falcons6443.robot.utilities.Logger;
import org.usfirst.frc.falcons6443.robot.utilities.enums.*;

import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.function.Consumer;

/**
 * Teleoperated mode for the robot.
 * The execute method of this class handles all possible inputs from the driver during the game.
 *
 * @author Ivan Kenevich, Christopher Medlin, Shivashriganesh Mahato
 */
public class TeleopMode extends SimpleCommand {

    private int unpressedID = 0;
    private int numOfSubsystems = 4;

    private Xbox primary;           //Drive and flywheel/output
    private Xbox secondary;         //Secondary functions
    private boolean[] unpressed = new boolean[numOfSubsystems];
    private boolean[] isManualLessThanBuffer = new boolean[numOfSubsystems];
    private List<Callable<Boolean>> isManualGetter = new ArrayList<>(); //add control manual getters
    private List<Consumer<Boolean>> isManualSetter = new ArrayList<>(); //add control manual setters
    //private WCDProfile driveProfile;//Profile used to calculate robot drive power

    public TeleopMode() {
        super("Teleop Command");
        requires(driveTrain);
        requires(flywheel);
        requires(elevator);
        requires(rotation);
    }

    @Override
    public void initialize() {
        primary = Robot.oi.getXbox(true);
        secondary = Robot.oi.getXbox(false);
        //driveProfile = new FalconDrive(primary);

        //adding manual getters and setters to their array using Subsystems.subsystemEnum.getValue() (to indicate which subsystem),
        // () -> function() or (Boolean set) -> function() (depending on required params)
        while(isManualGetter.size() < numOfSubsystems) isManualGetter.add(null);
        while(isManualSetter.size() < numOfSubsystems) isManualSetter.add(null);
        isManualGetter.add(Subsystems.Elevator.getValue(), () -> elevator.getManual());
        isManualSetter.add(Subsystems.Elevator.getValue(), (Boolean set) -> elevator.setManual(set));
        isManualGetter.add(Subsystems.Rotate.getValue(), () -> rotation.getManual());
        isManualSetter.add(Subsystems.Rotate.getValue(), (Boolean set) -> rotation.setManual(set));

        SmartDashboard.putNumber("Number", 1);
    }

    @Override
    public void execute() {
        Logger.log(LoggerSystems.Drive, "LOGS!!");

        //drive
        driveTrain.falconDrive(primary.leftStickX(), primary.leftTrigger(), primary.rightTrigger());
        // driveTrain.tankDrive(driveProfile.calculate()); TODO: TEST this cause profiles are cool

        //shifting
        press(primary.rightBumper(), () -> driveTrain.upShift());
        press(primary.leftBumper(), () -> driveTrain.downShift());

        //elevator
//        press((Boolean set) -> elevator.setManual(set), secondary.A(), () -> elevator.setToHeight(ElevatorPosition.Exchange));
//        press((Boolean set) -> elevator.setManual(set), secondary.B(), () -> elevator.setToHeight(ElevatorPosition.Switch));
//        press((Boolean set) -> elevator.setManual(set), secondary.X(), () -> elevator.setToHeight(ElevatorPosition.Stop));
//        press((Boolean set) -> elevator.setManual(set), secondary.Y(), () -> elevator.setToHeight(ElevatorPosition.Scale));
        manual(Subsystems.Elevator, secondary.leftStickY(), () -> elevator.manual(-secondary.leftStickY()));

        //flywheels
        press(primary.A(), () -> flywheel.intake());
        press(primary.B(), () -> flywheel.output());
        press(primary.Y(), () -> flywheel.slowOutput());
        unpressed(secondary.seven(), () -> flywheel.toggleKill(), true); //toggles slow spin while off

        //rotation
        press((Boolean set) -> rotation.setManual(set), secondary.rightBumper(), () -> rotation.up());
        press((Boolean set) -> rotation.setManual(set), secondary.leftBumper(), () -> rotation.down());
        manual(Subsystems.Rotate, secondary.rightStickY(), () -> rotation.manual(-secondary.rightStickY()));

        //off functions
        off(() -> elevator.stop(), Subsystems.Elevator);
        off(() -> flywheel.stop(), primary.A(), primary.B(), primary.Y());
        off(() -> rotation.stop(), Subsystems.Rotate, secondary.rightBumper(), secondary.leftBumper());

        //general periodic functions
        //elevator.moveToHeight(false);
        periodicEnd();
    }

    //Use if you want an action with a button
    private void press(boolean button, Runnable action){
        if(button) action.run();
    }

    //Use if you want an action with a button (compatible with backup action with a manual input)
    private void press(Consumer<Boolean> setManual, boolean button, Runnable action){
        if(button) {
            setManual.accept(false);
            action.run();
        }
    }

    //Use if you want an action with a manual input (joystick, trigger, etc)
    private void manual(Subsystems manualNumber, double input, Runnable action){
        if(Math.abs(input) > 0.2){
            isManualSetter.get(manualNumber.getValue()).accept(true);
            isManualLessThanBuffer[manualNumber.getValue()] = false;
            action.run();
        } else {
            isManualLessThanBuffer[manualNumber.getValue()] = true;
        }
    }

    //Use if you want an action to run when a set of buttons is not pressed
    private void off(Runnable off, boolean ... button){
        if(areAllFalse(button)) off.run();
    }

    //Use if you want an action to run when manual is less than buffer
    private void off(Runnable off, Subsystems manualNumber) {
        if(isManualLessThanBuffer[manualNumber.getValue()]) off.run();
    }

    //Use if you want an action to run when a set of buttons is not pressed and manual is less than buffer
    private void off(Runnable off, Subsystems manualNumber, boolean ... button){
        try {
            if(areAllFalse(button) && !isManualGetter.get(manualNumber.getValue()).call()) off.run();
            else if((areAllFalse(button) && isManualGetter.get(manualNumber.getValue()).call()
                    && isManualLessThanBuffer[manualNumber.getValue()])) off.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Use if you want an action with a button to only be activated once unpressed (true) or once pressed (false)
    private void unpressed(boolean button, Runnable function, boolean unpressedMode){
        if(button){
            if(!unpressedMode && !unpressed[unpressedID]){
                function.run();
            }
            unpressed[unpressedID] = true;
        } else {
            if(unpressedMode && unpressed[unpressedID]){
                function.run();
            }
            unpressed[unpressedID] = false;
        }
        unpressedID++;
    }

    //clears variables in unpressed()
    private void periodicEnd(){
        unpressedID = 0;
    }

    private boolean areAllFalse(boolean[] array) {
        for(boolean b : array) if(b) return false;
        return true;
    }

    private static boolean areAllZero(double buffer, double[] array) {
        for(double d : array) if(d > buffer) return false;
        return true;
    }

    public void reset(){
        //driveProfile = null;
    }

    public boolean isFinished() {
        return false;
    }
}