package org.usfirst.frc.falcons6443.robot.commands;

import org.usfirst.frc.falcons6443.robot.Robot;
import org.usfirst.frc.falcons6443.robot.hardware.Xbox;
import org.usfirst.frc.falcons6443.robot.utilities.enums.Controls;
import org.usfirst.frc.falcons6443.robot.utilities.enums.ElevatorPosition;
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

    private Xbox primary;           //Drive and intake/output
    private Xbox secondary;         //Secondary functions
    private int number = 0;
    private List<Boolean> on = new ArrayList<>();
    private List<Callable<Boolean>> isManualGetter = new ArrayList<>(); //add control manual getters
    private List<Consumer<Boolean>> isManualSetter = new ArrayList<>(); //add control manual setters

    private boolean isFirst = true;
    //private WCDProfile driveProfile;//Profile used to calculate robot drive power

    public TeleopMode() {
        super("Teleop Command");
        requires(driveTrain);
        requires(intake);
        requires(elevator);
    }

    @Override
    public void initialize() {
        primary = Robot.oi.getXbox(true);
        secondary = Robot.oi.getXbox(false);
        //driveProfile = new FalconDrive(primary);
        isManualGetter.add(Controls.Elevator.getValue(), () -> elevator.getManualBool());
        isManualSetter.add(Controls.Elevator.getValue(), (Boolean set) -> elevator.setManualBool(set));
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
        press((Boolean set) -> elevator.setManualBool(set), secondary.A(), () -> elevator.setToHeight(ElevatorPosition.Exchange));
        press((Boolean set) -> elevator.setManualBool(set), secondary.B(), () -> elevator.setToHeight(ElevatorPosition.Switch));
        press((Boolean set) -> elevator.setManualBool(set), secondary.X(), () -> elevator.setToHeight(ElevatorPosition.Stop));
        press((Boolean set) -> elevator.setManualBool(set), secondary.Y(), () -> elevator.setToHeight(ElevatorPosition.Scale));
        manual(Controls.Elevator, secondary.leftStickY(), () -> elevator.manual(secondary.leftStickY()));

        //flywheels
        press(primary.A(), () -> intake.intake());
        press(primary.B(), () -> intake.output());
        press(primary.Y(), () -> intake.slowOutput());
        depress(secondary.seven(), () -> intake.toggleKill()); //toggles slow spin while off

        press(secondary.rightBumper(), () -> intake.moveIntake(true));
        press(secondary.leftBumper(), () -> intake.moveIntake(false));

        off(() -> intake.stop(), primary.A(), primary.B(), primary.Y());
        off(() -> intake.rotateStop(), secondary.rightBumper(), secondary.leftBumper());
        elevator.moveToHeight(false);
        periodicEnd();
    }

    //Use if you want an action on a button
    private void press(boolean button, Runnable action){
        if(button) action.run();
    }

    private void press(Consumer<Boolean> setManual, boolean button, Runnable action){
        if(button) {
            setManual.accept(false);
            action.run();
        }
    }

    //Use if you want an action on a manual input (joystick, trigger)
    private void manual(Controls manualNumber, double input, Runnable action){
        if(input > 0.2){
            isManualSetter.get(manualNumber.getValue()).accept(true);
            action.run();
        }
    }

    //Use if you want an action to run when a set of buttons is not pressed
    //Put all off functions at the end of execute
    private void off(Runnable off, boolean ... button){
        if(areAllFalse(button)) off.run();
    }

    private void off(Controls manualNumber, Runnable off, boolean ... button){
        try {
            if(areAllFalse(button) && !isManualGetter.get(manualNumber.getValue()).call()) off.run();
        } catch (Exception e) {
        } finally {
        }
    }
    //Use if you want an action on a button to only be activated when depressed
    private void depress(boolean button, Runnable function){
        if(isFirst) on.add(number, false);

        if(button){
            if(!on.get(number)){
                function.run();
                on.set(number, true);
            }
        } else {
            on.set(number, false);
        }
        number++;
    }

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