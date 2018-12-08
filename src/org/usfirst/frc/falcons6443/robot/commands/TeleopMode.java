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

        //Decide which driveMode we are using... Defaults to tank raw
        /* Since we cant just return the enum (and strings are annoying) I used Int's the following are the number and the corresponding mode
         * 0 = Raw Input Tank Drive
         * 1 = Smooth Input Tank Drive
         * 2 = Arcade Like Drive Mode
         */

        //Sets the drive mode at robot init
        int driveModeInt = 0;
        driveTrain.setDriveMode(driveModeInt);
    }

    @Override
    public void execute() {
        Logger.log(LoggerSystems.Drive, "LOGS!!");

        //Same Table as above
        /* Since we cant just return the enum (and strings are annoying) I used Int's the following are the number and the corresponding mode
         * 0 = Raw Input Tank Drive
         * 1 = Smooth Input Tank Drive
         * 2 = Arcade Like Drive Mode
         */

        //TODO: Add dropdown to smart dashboard for switching drive mode from dashboard

        //Sets drive mode based off selected
        switch (driveTrain.getSelectedDriveMode()){
            case 0:
                driveTrain.TankDrive(primary.leftStickY(), primary.rightStickY());
                // driveTrain.tankDrive(driveProfile.calculate()); TODO: TEST this cause profiles are cool
                break;
            case 1:
                driveTrain.SmoothTankDrive(primary.leftStickY(), primary.rightStickY());
                break;
            case 2:
                driveTrain.ArcadeDrive(primary.rightTrigger(), primary.leftTrigger(), primary.leftStickX());
                break;

        }


        //Kept For Demo in case I forget how it works
        //press(primary.A(), () -> flywheel.intake());


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
    private void press(Consumer<Boolean> setManual, boolean button, Runnable action){
        if(button) {
            setManual.accept(false);
            action.run();
        }
    }

    //Pairs an action with a manual input (joystick, trigger, etc)
    private void manual(Subsystems manualNumber, double input, Runnable action){
        if(Math.abs(input) > 0.2){
            isManualSetter.get(manualNumber.getValue()).accept(true);
            isManualLessThanBuffer[manualNumber.getValue()] = false;
            action.run();
        } else {
            isManualLessThanBuffer[manualNumber.getValue()] = true;
        }
    }

    //Runs an action when a set of buttons is not pressed
    private void off(Runnable off, boolean ... button){
        if(areAllFalse(button)) off.run();
    }

    //Runs an action when manual is less than buffer
    private void off(Runnable off, Subsystems manualNumber) {
        if(isManualLessThanBuffer[manualNumber.getValue()]) off.run();
    }

    //Runs an action when a set of buttons is not pressed and manual is less than buffer
    private void off(Runnable off, Subsystems manualNumber, boolean ... button){
        try {
            if(areAllFalse(button) && !isManualGetter.get(manualNumber.getValue()).call()) off.run();
            else if((areAllFalse(button) && isManualGetter.get(manualNumber.getValue()).call()
                    && isManualLessThanBuffer[manualNumber.getValue()])) off.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Pairs an action with a button, activated only once unpressed (true) or once pressed (false)
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