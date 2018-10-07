package org.usfirst.frc.falcons6443.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
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

    //Used for manual controls
    public enum Subsystems {
        Drive, Elevator, Flywheels, Rotate;
    }

    @Override
    public void initialize() {
        primary = Robot.oi.getXbox(true);
        secondary = Robot.oi.getXbox(false);
        //driveProfile = new FalconDrive(primary);

        //adding manual getters and setters to their array using Subsystems.subsystemEnum.ordinal()
        // (to indicate which subsystem),
        // () -> function() or (Boolean set) -> function() (depending on required params)
        while(isManualGetter.size() < numOfSubsystems) isManualGetter.add(null);
        while(isManualSetter.size() < numOfSubsystems) isManualSetter.add(null);
        isManualGetter.add(Subsystems.Elevator.ordinal(), () -> elevator.getManual());
        isManualSetter.add(Subsystems.Elevator.ordinal(), (Boolean set) -> elevator.setManual(set));
        isManualGetter.add(Subsystems.Rotate.ordinal(), () -> rotation.getManual());
        isManualSetter.add(Subsystems.Rotate.ordinal(), (Boolean set) -> rotation.setManual(set));
    }

    @Override
    public void execute() {

        //drive
        driveTrain.falconDrive(primary.leftStickX(), primary.rightTrigger(), primary.leftTrigger()); //left, right
        // driveTrain.tankDrive(driveProfile.calculate()); TODO: TEST this cause profiles are cool

        //shifting
        press(primary.rightBumper(), () -> driveTrain.upShift());
        press(primary.leftBumper(), () -> driveTrain.downShift());

        //elevator
//        press(Subsystems.Elevator, secondary.A(), () -> elevator.setToHeight(ElevatorPosition.Exchange));
//        press(Subsystems.Elevator, secondary.B(), () -> elevator.setToHeight(ElevatorPosition.Switch));
//        press(Subsystems.Elevator, secondary.X(), () -> elevator.setToHeight(ElevatorPosition.Stop));
//        press(Subsystems.Elevator, secondary.Y(), () -> elevator.setToHeight(ElevatorPosition.Scale));
        manual(Subsystems.Elevator, secondary.leftStickY(), () -> elevator.manual(-secondary.leftStickY()));

        //flywheels
        press(primary.A(), () -> flywheel.intake());
        press(primary.B(), () -> flywheel.output());
        press(primary.Y(), () -> flywheel.slowOutput());
        unpressed(secondary.seven(), () -> flywheel.toggleKill(), false); //toggles slow spin while off

        //rotation
        press(Subsystems.Rotate, secondary.leftBumper(), () -> rotation.up());
        press(Subsystems.Rotate, secondary.rightBumper(), () -> rotation.down());
        press(Subsystems.Rotate, secondary.B(), () -> rotation.middle());
        press(Subsystems.Rotate, secondary.eight(), () -> rotation.resetEncoder());
        manual(Subsystems.Rotate, secondary.rightStickY(), () -> rotation.manual(-secondary.rightStickY()));

        //off functions
        off(() -> elevator.stop(), Subsystems.Elevator);
        off(() -> flywheel.stop(), primary.A(), primary.B(), primary.Y());
        off(() -> rotation.stop(), Subsystems.Rotate, secondary.rightBumper(),
                secondary.leftBumper(), secondary.B());

        //general periodic functions
        //elevator.moveToHeight(false);
        periodicEnd();
    }

    //Pairs an action with a button
    private void press(boolean button, Runnable action){
        if(button) action.run();
    }

    //Pairs an action with a button, compatible with manual()
    // ie: this function can be used with manual() to control the same component
    // eg: button control and (backup) manual control of the same component
    private void press(Subsystems manualNumber, boolean button, Runnable action){
        if(button) {
            isManualSetter.get(manualNumber.ordinal()).accept(false);
            action.run();
        }
    }

    //Pairs an action with a manual input (joystick, trigger, etc)
    private void manual(Subsystems manualNumber, double input, Runnable action){
        if(Math.abs(input) > 0.2){
            isManualSetter.get(manualNumber.ordinal()).accept(true);
            isManualLessThanBuffer[manualNumber.ordinal()] = false;
            action.run();
        } else {
            isManualLessThanBuffer[manualNumber.ordinal()] = true;
        }
    }

    //Runs an action when a set of buttons is not pressed
    private void off(Runnable off, boolean ... button){
        if(areAllFalse(button)) off.run();
    }

    //Runs an action when manual is less than buffer
    private void off(Runnable off, Subsystems manualNumber) {
        if(isManualLessThanBuffer[manualNumber.ordinal()]) off.run();
    }

    //Runs an action when a set of buttons is not pressed and manual is less than buffer
    private void off(Runnable off, Subsystems manualNumber, boolean ... button){
        try {
            if(areAllFalse(button) && !isManualGetter.get(manualNumber.ordinal()).call()) off.run();
            else if((areAllFalse(button) && isManualGetter.get(manualNumber.ordinal()).call()
                    && isManualLessThanBuffer[manualNumber.ordinal()])) off.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Pairs an action with a button, activated only once unpressed (true) or once pressed (false)
    //THis action will only run once, unlike press() which runs periodically until unpressed
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

    //clears the unpressedID
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