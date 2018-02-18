package org.usfirst.frc.falcons6443.robot.commands;

import org.usfirst.frc.falcons6443.robot.Robot;
import org.usfirst.frc.falcons6443.robot.utilities.ElevatorEnums;
import org.usfirst.frc.falcons6443.robot.hardware.Xbox;

/**
 * Teleoperated mode for the robot.
 * The execute method of this class handles all possible inputs from the driver during the game.
 *
 * @author Ivan Kenevich, Christopher Medlin, Shivashriganesh Mahato
 */
public class TeleopMode extends SimpleCommand {

    private Xbox xbox;
    private boolean reversed;

    public TeleopMode() {
        super("Teleop Command");

        requires(driveTrain);
        requires(flywheel);
        requires(elevator);
    }

    @Override
    public void initialize() {
        xbox = Robot.oi.getXbox();
        reversed = false;
    }

    @Override
    public void execute() {
        //for testing
        elevator.manual(xbox.rightStickY(xbox.primary));
        //manual rotation
        //flywheel.manual(xbox.leftStickY(xbox.primary));

        //System.out.println("intake: " + xbox.leftStickY(xbox.primary));
        System.out.println("lift: " + xbox.rightStickY(xbox.primary));

        //testing
        if(xbox.X(xbox.primary)){
            elevator.up(true);
        }

        if(xbox.Y(xbox.primary)){
            elevator.down(true);
        }

        if (!xbox.X(xbox.primary) && !xbox.Y(xbox.primary)) {
            elevator.stop();
        }

        // set the driveTrain power.
        //driveTrain.tankDrive(xbox.leftStickY(xbox.primary), xbox.rightStickY(xbox.primary));

        //System.out.println("Left: " + driveTrain.getLeftDistance());
        //System.out.println("Right: " + driveTrain.getRightDistance());

        //testing -- resets encoders
        if(xbox.Y(xbox.primary)){
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
