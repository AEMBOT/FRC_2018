package org.usfirst.frc.falcons6443.robot.commands;

import org.usfirst.frc.falcons6443.robot.Robot;
import org.usfirst.frc.falcons6443.robot.hardware.Joysticks.Xbox;
import org.usfirst.frc.falcons6443.robot.utilities.Logger;
import org.usfirst.frc.falcons6443.robot.utilities.enums.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.function.Consumer;

/**
 * Teleoperated mode for the robot.
 * The execute method of this class handles all possible inputs from the driver during the game.
 *
 * @author Ivan Kenevich, Christopher Medlin, Shivashriganesh Mahato
 */
public class TeleopMode extends SimpleCommand {

    private Xbox primary;           //Drive and flywheel/output
    private Xbox secondary;         //Secondary functions
    private int number = 0;
    private int numOfSubsystems = 4;
    private List<Boolean> depress = new ArrayList<>();
    private boolean[] isManualLessThanBuffer = new boolean[numOfSubsystems];
    private List<Callable<Boolean>> isManualGetter = new ArrayList<>(); //add control manual getters
    private List<Consumer<Boolean>> isManualSetter = new ArrayList<>(); //add control manual setters

    private boolean isFirst = true;
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

        //adding manual getters and setters using Subsystems.subsystemEnum.getValue() (to indicate which subsystem),
        // () -> function() or (Boolean set) -> function() (depending on required params)
        isManualGetter.add(null);
        isManualSetter.add(null);
        isManualGetter.add(Subsystems.Elevator.getValue(), () -> elevator.getManual());
        isManualSetter.add(Subsystems.Elevator.getValue(), (Boolean set) -> elevator.setManual(set));
        isManualGetter.add(null);
        isManualSetter.add(null);
        isManualGetter.add(Subsystems.Rotate.getValue(), () -> rotation.getManual());
        isManualSetter.add(Subsystems.Rotate.getValue(), (Boolean set) -> rotation.setManual(set));
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
        depress(secondary.seven(), () -> flywheel.toggleKill()); //toggles slow spin while off

        //rotation
        press((Boolean set) -> rotation.setManual(set), secondary.rightBumper(), () -> rotation.up());
        press((Boolean set) -> rotation.setManual(set), secondary.leftBumper(), () -> rotation.down());
        manual(Subsystems.Rotate, secondary.rightStickY(), () -> rotation.manual(-secondary.rightStickY()));

        //off functions
        off(Subsystems.Elevator, () -> elevator.stop());
        off(() -> flywheel.stop(), primary.A(), primary.B(), primary.Y());
        off(Subsystems.Rotate, () -> rotation.stop(), secondary.rightBumper(), secondary.leftBumper());

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
    //Put all off functions at the end of execute
    private void off(Runnable off, boolean ... button){
        if(areAllFalse(button)) off.run();
    }

    //Use if you want an action to run when manual is less than buffer
    private void off(Subsystems manualNumber, Runnable off) {
        if(isManualLessThanBuffer[manualNumber.getValue()]) off.run();
    }

    //Use if you want an action to run when a set of buttons is not pressed and manual is less than buffer
    private void off(Subsystems manualNumber, Runnable off, boolean ... button){
        try {
            if(areAllFalse(button) && !isManualGetter.get(manualNumber.getValue()).call()) off.run();
            else if((areAllFalse(button) && isManualGetter.get(manualNumber.getValue()).call()
                    && isManualLessThanBuffer[manualNumber.getValue()])) off.run();
        } catch (Exception e) {
        } finally {
        }
    }

    //Use if you want an action with a button to only be activated when depressed
    private void depress(boolean button, Runnable function){
        if(isFirst) depress.add(number, false);

        if(button){
            Logger.log(LoggerSystems.Flywheel, "Depress button pushed");
            if(!depress.get(number)){
                function.run();
                Logger.log(LoggerSystems.Flywheel, "Run depress function");
                depress.set(number, true);
            }
        } else {
            Logger.log(LoggerSystems.Flywheel, "Depress button not pushed");
            depress.set(number, false);
        }
        number++;
    }

    //clears variables in depress()
    private void periodicEnd(){
        number = 0;
        isFirst = false;
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