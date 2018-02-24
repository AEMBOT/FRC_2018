package org.usfirst.frc.falcons6443.robot.commands;

import org.usfirst.frc.falcons6443.robot.Robot;
import org.usfirst.frc.falcons6443.robot.hardware.Xbox;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;

/**
 * Teleoperated mode for the robot.
 * The execute method of this class handles all possible inputs from the driver during the game.
 *
 * @author Ivan Kenevich, Christopher Medlin, Shivashriganesh Mahato
 */
public class TeleopMode extends SimpleCommand {

    private Xbox primary;         //Drive and intake/output
    private Xbox secondary;      //Secondary functions
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
        primary = Robot.oi.getXbox(true);
        secondary = Robot.oi.getXbox(false);
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
        if(primary.X()){
            elevator.up(true);
        }

        if(primary.Y()){
            elevator.down(true);
        }

        if (!primary.X() && !primary.Y()) {
            elevator.stop();
        }

        //elevator.limitTest();

        // set the driveTrain power.
        driveTrain.tankDrive(primary.leftStickY(), primary.rightStickY());

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
        if(primary.Y()){
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

        if (flywheel.hasBlock()){
            primary.controller.setRumble(RumbleType.kLeftRumble, 1);
        }

        //elevator.moveToHeight();
    }

    public boolean isFinished() {
        return false;
    }
}
