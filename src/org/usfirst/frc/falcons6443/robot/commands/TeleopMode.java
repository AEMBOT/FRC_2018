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

        
        //intake button
        if (gamepad.A()) {
            //if (flywheel.hasBlock()) {
              //  flywheel.stop();
            //} else {
                flywheel.intake();
            //}
        }

        //output button
        if (gamepad.B()) {
            flywheel.output();
        }

        if (gamepad.X()){
            flywheel.stop();
        }
    }

    public boolean isFinished() {
        return false;
    }
}
