package org.usfirst.frc.falcons6443.robot.commands;

import org.usfirst.frc.falcons6443.robot.Robot;
import org.usfirst.frc.falcons6443.robot.hardware.Gamepad;

/**
 * Teleoperated mode for the robot.
 * The execute method of this class handles all possible inputs from the driver during the game.
 *
 * @author Ivan Kenevich, Christopher Medlin, Shivashriganesh Mahato
 */
public class TeleopMode extends SimpleCommand {

   // private Gamepad gamepad;
    private boolean reversed;

    public TeleopMode() {
        super("Teleop Command");

        requires(driveTrain);
    }

    @Override
    public void initialize() {
        //gamepad = Robot.oi.getGamepad();
        reversed = false;
    }

    @Override
    public void execute() {
        //double leftDrive = gamepad.leftStickY();
        //double rightDrive = gamepad.rightStickY();

        // set the driveTrain power.
        driveTrain.tankDrive(.6, .6);

        // the Y button will toggle the drive train to reverse mode
       /* if (gamepad.Y()) {
            // safeguard for if the driver holds down the Y button.
            if (!reversed) {
                driveTrain.reverse();
                reversed = true;
            }
        } else {
            reversed = false;
        }*/
    }

    public boolean isFinished() {
        return false;
    }
}
