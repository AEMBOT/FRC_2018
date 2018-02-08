package org.usfirst.frc.falcons6443.robot.commands;

import org.usfirst.frc.falcons6443.robot.Robot;
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
    }

    @Override
    public void initialize() {
        xbox = Robot.oi.getXbox();
        reversed = false;
    }

    @Override
    public void execute() {
        double leftDrive = xbox.leftStickY();
        double rightDrive = xbox.rightStickY();

        // set the driveTrain power.
        driveTrain.tankDrive(leftDrive, rightDrive);

        System.out.println("Left: " + driveTrain.getLeftDistance());
        System.out.println("Right: " + driveTrain.getRightDistance());

        // the Y button will toggle the drive train to reverse mode
        /*if (xbox.Y()) {
            // safeguard for if the driver holds down the Y button.
            if (!reversed) {
                driveTrain.reverse();
                reversed = true;
            }
        } else {
            reversed = false;
        }*/

        if(xbox.Y()){
            driveTrain.reset();
        }
        //intake button
        if (xbox.leftBumper()) {
            //if (flywheel.hasBlock()) {
              //  flywheel.stop();
            //} else {
                flywheel.intake();
            //}
        }

        //output button
        if (xbox.rightBumper()) {
            flywheel.output();
        }

        //stop
        if (!xbox.leftBumper() && !xbox.rightBumper()){
            flywheel.stop();
        }

        if (xbox.X()){
            flywheel.stop();
        }
    }

    public boolean isFinished() {
        return false;
    }
}
