package org.usfirst.frc.falcons6443.robot.commands;

import org.usfirst.frc.falcons6443.robot.Robot;
import org.usfirst.frc.falcons6443.robot.hardware.NavX;
import org.usfirst.frc.falcons6443.robot.utilities.ElevatorEnums;
import org.usfirst.frc.falcons6443.robot.hardware.Xbox;

/**
 * Teleoperated mode for the robot.
 * The execute method of this class handles all possible inputs from the driver during the game.
 *
 * @author Ivan Kenevich, Christopher Medlin, Shivashriganesh Mahato
 */
public class TeleopMode extends SimpleCommand {

    private Xbox xbox;         //Drive and intake/output
    private Xbox auxXbox;      //Secondary functions
    private boolean reversed;

    public TeleopMode() {
        super("Teleop Command");

        requires(driveTrain);
        requires(flywheel);
        requires(elevator);
        requires(navigation);
    }

    @Override
    public void initialize() {
        xbox = Robot.oi.getXbox();
        reversed = false;
    }

    @Override
    public void execute() {
        //for testing
        //elevator.manual(xbox.rightStickY(xbox.primary));
        //manual rotation
        //flywheel.manual(xbox.leftStickY(xbox.primary));

        //System.out.println("intake: " + xbox.leftStickY(xbox.primary));
        //System.out.println("lift: " + xbox.rightStickY(xbox.primary));

        //testing
        if(xbox.X()){
            elevator.up(true);
        }

        if(xbox.Y()){
            elevator.down(true);
        }

        if (!xbox.X() && !xbox.Y()) {
            elevator.stop();
        }

        //elevator.limitTest();

        // set the driveTrain power.
        driveTrain.tankDrive(xbox.leftStickY(), xbox.rightStickY());

        //System.out.println("Left: " + (driveTrain.getLeftDistance()));
        //System.out.println("Right: " + (driveTrain.getRightDistance()));
        //System.out.println("left: " + xbox.leftStickY(xbox.primary));
        //System.out.println("right: " + xbox.rightStickY(xbox.primary));

        /*if (elevator.lowerLimit()){
            System.out.println("limit on");
        } else {
            System.out.println("OFF");
        }*/

        //elevator.
        //System.out.println("yaw: " + navigation.getYaw());

        //testing -- resets encoders
        if(xbox.Y()){
            driveTrain.reset();
        }

        //intake button
        /*if (xbox.leftBumper(xbox.primary)) {
            //if (flywheel.hasBlock()) {
              //  flywheel.stop();
            //} else {
                flywheel.intake();
            //}
        }

        //output button
        if (xbox.rightBumper(xbox.primary)) {
            flywheel.output();
        }

        //stop
        if (!xbox.leftBumper(xbox.primary) && !xbox.rightBumper(xbox.primary)){
            flywheel.stop();
        }*/

        //elevator.moveToHeight();
    }

    public boolean isFinished() {
        return false;
    }
}
