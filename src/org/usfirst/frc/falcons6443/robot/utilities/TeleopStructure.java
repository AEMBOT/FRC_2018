package org.usfirst.frc.falcons6443.robot.utilities;

import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.function.Consumer;


/*
 * A class containing all of the logic for teleop, including pairing a button with an action 
 * running continuously or once per press), manual controls with and without backup buttons, 
 * and functions that run when a set of buttons are not pressed.
 *
 * Use this class in Robot.java, function TeleopInit() and TeleopPeriodic()
 */
public class TeleopStructure {

    private int unpressedID;
    private int numOfSubsystems = Subsystems.values().length; 

    private boolean[] runOnceSavedData = new boolean[numOfSubsystems]; //should it be numOfSubs???
    private boolean[] isManualLessThanBuffer = new boolean[numOfSubsystems];
    private List<Callable<Boolean>> isManualGetter = new ArrayList<>(); //add control manual getters
    private List<Consumer<Boolean>> isManualSetter = new ArrayList<>(); //add control manual setters

    public TeleopStructure(){
        //While loops to fill Lists with nulls (allows us to change them later)
        while(isManualGetter.size() <= numOfSubsystems) isManualGetter.add(null);
        while(isManualSetter.size() <= numOfSubsystems) isManualSetter.add(null);
    }

    //A list of al subsystems. KEEP THIS UPDATED PLEASE!
    public enum Subsystems {
        Drive, Elevator, Flywheels, Rotate
    }

    //adding manual getters to List using params Subsystems.subsystemEnum, () -> function()
    //Example: addIsManualGetter(TeleopStructure.Subsystems.Elevator, () -> elevator.getManual());
    public void addIsManualGetter(Subsystems system, Callable<Boolean> call) {
        isManualGetter.add(system.ordinal(), call);
    }

    //adding manual setters to List using params Subsystems.subsystemEnum, (Boolean set) -> function(set)
    //Example: addIsManualSetter(TeleopStructure.Subsystems.Elevator, (Boolean set) -> elevator.setManual(set));
    public void addIsManualSetter(Subsystems system, Consumer<Boolean> consumer) {
        isManualSetter.add(system.ordinal(), consumer);
    }

    //Pairs an action with a button
    public void press(boolean button, Runnable action){
        if(button) action.run();
    }

    //Pairs an action with a button, compatible with manual()
    // ie: this function can be used with manual() to control the same component
    // eg: button control and (backup) manual control of the same component
    public void press(Consumer<Boolean> setManual, boolean button, Runnable action){
        if(button) {
            setManual.accept(false);
            action.run();
        }
    }

    //Pairs an action with a manual input (joystick, trigger, etc)
    public void manual(Subsystems manualNumber, double input, Runnable action){
        if(Math.abs(input) > 0.2){
            isManualSetter.get(manualNumber.ordinal()).accept(true);
            isManualLessThanBuffer[manualNumber.ordinal()] = false;
            action.run();
        } else {
            isManualLessThanBuffer[manualNumber.ordinal()] = true;
        }
    }

    //Runs an action when a set of buttons is not pressed
    public void off(Runnable off, boolean ... button){
        if(areAllFalse(button)) off.run();
    }

    //Runs an action when manual is less than buffer
    public void off(Runnable off, Subsystems manualNumber) {
        if(isManualLessThanBuffer[manualNumber.ordinal()]) off.run();
    }

    //Runs an action when a set of buttons is not pressed and manual is less than buffer
    public void off(Runnable off, Subsystems manualNumber, boolean ... button){
        try {
            if(areAllFalse(button) && !isManualGetter.get(manualNumber.ordinal()).call()) off.run();
            else if((areAllFalse(button) && isManualGetter.get(manualNumber.ordinal()).call()
                    && isManualLessThanBuffer[manualNumber.ordinal()])) off.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Pairs an action with a button, activated only once unpressed (true) or once pressed (false)
    public void runOncePerPress(boolean button, Runnable function, boolean unpressedMode){
        if(button){
            if(!unpressedMode && !runOnceSavedData[unpressedID]){
                function.run();
            }
            runOnceSavedData[unpressedID] = true;
        } else {
            if(unpressedMode && runOnceSavedData[unpressedID]){
                function.run();
            }
            runOnceSavedData[unpressedID] = false;
        }
        unpressedID++;
    }

    //clears the unpressedID
    public void periodicEnd(){
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
}
