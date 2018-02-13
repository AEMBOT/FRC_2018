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
        //driveTrain.tankDrive(leftDrive, rightDrive);

        //intake button
        if (xbox.leftBumper(xbox.primary)) {
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
        }

        //elevator.moveToHeight();
        //manual rotation
        flywheel.manual(xbox.rightStickY(xbox.primary));
        System.out.println(xbox.rightStickY(xbox.primary));

        //stop all
        if (xbox.X(xbox.primary)){
            flywheel.stop();
            flywheel.manual(0);
        }
    }

    public boolean isFinished() {
        return false;
    }
}
