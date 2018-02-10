package org.usfirst.frc.falcons6443.robot.commands;

import org.usfirst.frc.falcons6443.robot.Robot;
import org.usfirst.frc.falcons6443.robot.hardware.Gamepad;
import org.usfirst.frc.falcons6443.robot.utilities.ElevatorEnums;

/**
 * Teleoperated mode for the robot.
 * The execute method of this class handles all possible inputs from the driver during the game.
 *
 * @author Ivan Kenevich, Christopher Medlin, Shivashriganesh Mahato
 */
public class TeleopMode extends SimpleCommand {

    private Gamepad gamepad;

    public TeleopMode() {
        super("Teleop Command");
        requires(driveTrain);
        requires(elevator);
    }

    @Override
    public void initialize() {
        gamepad = Robot.oi.getGamepad();
    }

    @Override
    public void execute() {
        //for testing
        elevator.manual(gamepad.leftStickY());

        //elevator.moveToHeight();
    }

    public boolean isFinished() {
        return false;
    }
}
