package org.usfirst.frc.falcons6443.robot.commands;

import org.usfirst.frc.falcons6443.robot.Robot;
import org.usfirst.frc.falcons6443.robot.hardware.joysticks.Xbox;

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
    private boolean first = true;

    private Xbox primary;           //Drive and flywheel/output
    private Xbox secondary;         //Secondary functions
    private List<Boolean> runOnceSavedData = new ArrayList<>();
    private List<Boolean> isManualLessThanBuffer = new ArrayList<>();
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

    //A list of all manual controls of the robot, excluding drive
    //Used for manual controls. Can only have one ManualControls per manual axis (NOT per subsystem!)
    public enum ManualControls {
        Elevator, Rotate
    }

    @Override
    public void initialize() {
        primary = Robot.oi.getXbox(true);
        secondary = Robot.oi.getXbox(false);
        //driveProfile = new FalconDrive(primary);

        //adding manual getters and setters to their array
        while(isManualGetter.size() < ManualControls.values().length) isManualGetter.add(null); //ensures that array is at least size of ManualControls enum
        while(isManualSetter.size() < ManualControls.values().length) isManualSetter.add(null);
        addIsManualGetterSetter(ManualControls.Elevator, () -> elevator.getManual(),
                (Boolean set) -> elevator.setManual(set));
        addIsManualGetterSetter(ManualControls.Rotate, () -> rotation.getManual(),
                (Boolean set) -> rotation.setManual(set));
    }

    @Override
    public void execute() {

        //drive
        driveTrain.falconDrive(primary.leftStickX(), primary.leftTrigger(), primary.rightTrigger());
        // driveTrain.tankDrive(driveProfile.calculate()); TODO: TEST this cause profiles are cool

        //shifting
        press(primary.rightBumper(), () -> driveTrain.upShift());
        press(primary.leftBumper(), () -> driveTrain.downShift());

        //elevator
//        press(ManualControls.Elevator, secondary.A(), () -> elevator.setToHeight(ElevatorPosition.Exchange));
//        press(ManualControls.Elevator, secondary.B(), () -> elevator.setToHeight(ElevatorPosition.Switch));
//        press(ManualControls.Elevator, secondary.X(), () -> elevator.setToHeight(ElevatorPosition.Stop));
//        press(ManualControls.Elevator, secondary.Y(), () -> elevator.setToHeight(ElevatorPosition.Scale));
        manual(ManualControls.Elevator, secondary.leftStickY(), () -> elevator.manual(-secondary.leftStickY()));

        //flywheels
        press(primary.A(), () -> flywheel.intake());
        press(primary.B(), () -> flywheel.output());
        press(primary.Y(), () -> flywheel.slowOutput());
        runOncePerPress(secondary.back(), () -> flywheel.toggleKill(), false); //toggles slow spin while off

        //rotation
            press(ManualControls.Rotate, secondary.leftBumper(), () -> rotation.up());
            press(ManualControls.Rotate, secondary.rightBumper(), () -> rotation.down());
        //    press(ManualControls.Rotate, secondary.B(), () -> rotation.middle());
        //    press(ManualControls.Rotate, secondary.start(), () -> rotation.resetEncoder());
        //manual(ManualControls.Rotate, secondary.rightStickY(), () -> rotation.manual(-secondary.rightStickY()));

        //off functions
        off(() -> elevator.stop(), ManualControls.Elevator);
        off(() -> flywheel.stop(), primary.A(), primary.B(), primary.Y());
        off(() -> rotation.stop(), ManualControls.Rotate, secondary.rightBumper(),
                secondary.leftBumper(), secondary.B());

        //general periodic functions
        //elevator.moveToHeight(false);
        periodicEnd();
    }

    //adding manual getters and setters to Lists using params:
    // ManualControls.manualEnum, () -> function(), (Boolean set) -> function(set)
    //Example: addIsManualGetter(TeleopStructure.ManualControls.Elevator, () -> elevator.getManual(),
    //                      (Boolean set) -> elevator.setManual(set));
    private void addIsManualGetterSetter(ManualControls manual, Callable<Boolean> callable,
                                         Consumer<Boolean> consumer) {
        isManualGetter.add(manual.ordinal(), callable);
        isManualSetter.add(manual.ordinal(), consumer);
        isManualLessThanBuffer.add(manual.ordinal(), true);  //included to ensure equal numbers of getters/setters to buffer checkers
    }

    //Pairs an action with a button
    private void press(boolean button, Runnable action){
        if(button) action.run();
    }

    //Pairs an action with a button, compatible with manual()
    // ie: this function can be used with manual() to control the same component
    // eg: button control and (backup) manual control of the same component
    private void press(ManualControls manual, boolean button, Runnable action){
        if(button) {
            isManualSetter.get(manual.ordinal()).accept(false); //turn manual off if nonmanual button pressed
            action.run();
        }
    }

    //Pairs an action with a manual input (joystick, trigger, etc)
    private void manual(ManualControls manualNumber, double input, Runnable action){
        if(Math.abs(input) > 0.2){
            isManualSetter.get(manualNumber.ordinal()).accept(true);
            isManualLessThanBuffer.set(manualNumber.ordinal(), false);
            action.run();
        } else {
            isManualLessThanBuffer.set(manualNumber.ordinal(), true);
        }
    }

    //Runs an action when a set of buttons is not pressed
    private void off(Runnable off, boolean ... button){
        if(areAllFalse(button)) off.run();
    }

    //Runs an action when manual is less than buffer
    private void off(Runnable off, ManualControls manualNumber) {
        if(isManualLessThanBuffer.get(manualNumber.ordinal())) off.run();
    }

    //Runs an action when a set of buttons is not pressed and manual is less than buffer
    private void off(Runnable off, ManualControls manualNumber, boolean ... button){
        try {
            if(areAllFalse(button) && !isManualGetter.get(manualNumber.ordinal()).call()) off.run();
            else if((areAllFalse(button) && isManualGetter.get(manualNumber.ordinal()).call()
                    && isManualLessThanBuffer.get(manualNumber.ordinal()))) off.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Pairs an action with a button, activated only once unpressed (true) or once pressed (false)
    //This action will only run once, unlike press() which runs periodically until unpressed
    private void runOncePerPress(boolean button, Runnable function, boolean unpressedMode){
        if(first) runOnceSavedData.add(unpressedID, false);
        if(button){
            if(!unpressedMode && !runOnceSavedData.get(unpressedID)){
                function.run();
            }
            runOnceSavedData.set(unpressedID, true);
        } else {
            if(unpressedMode && runOnceSavedData.get(unpressedID)){
                function.run();
            }
            runOnceSavedData.set(unpressedID, false);
        }
        unpressedID++;
    }

    //clears the unpressedID
    private void periodicEnd(){
        first = false;
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
