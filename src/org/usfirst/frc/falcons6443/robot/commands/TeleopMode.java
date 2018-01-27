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
    private boolean reversed;

    public TeleopMode() {
        super("Teleop Command");
        requires(driveTrain);
        requires(elevator);
    }

    @Override
    public void initialize() {
        gamepad = Robot.oi.getGamepad();
        reversed = false;
    }

    @Override
    public void execute() {

        //The A button will update the elevator to the switch height
        if (gamepad.A()){
            elevator.setToHeight(ElevatorEnums.Switch);
        }

        // the Start button will toggle the drive train to reverse mode
        if (gamepad.Start()) {
            // safeguard for if the driver holds down the Y button.
            if (!reversed) {
                driveTrain.reverse();
                reversed = true;
            }
        } else {
            reversed = false;
        }

        elevator.update();
    }

    public boolean isFinished() {
        return false;
    }
}
