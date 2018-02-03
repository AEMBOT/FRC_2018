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

    private Gamepad gamepad;
    private boolean reversed;

    public TeleopMode() {
        super("Teleop Command");

        requires(driveTrain);
        requires(flywheel);
    }

    @Override
    public void initialize() {
        gamepad = Robot.oi.getGamepad();
        reversed = false;
    }

    @Override
    public void execute() {
        double leftDrive = gamepad.leftStickY();
        double rightDrive = gamepad.rightStickY();

        driveTrain.tankDrive(leftDrive, rightDrive);

        //intake button
        if (gamepad.leftBumper()) {
            //if (flywheel.hasBlock()) {
              //  flywheel.stop();
            //} else {
                flywheel.intake();
            //}
        }

        //output button
        if (gamepad.rightBumper()) {
            flywheel.output();
        }

        //stop
        if (!gamepad.leftBumper() && !gamepad.rightBumper()){
            flywheel.stop();
        }

        if (gamepad.X()){
            flywheel.stop();
        }
    }

    public boolean isFinished() {
        return false;
    }
}
