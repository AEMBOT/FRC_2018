package org.usfirst.frc.falcons6443.robot.commands;

import org.usfirst.frc.falcons6443.robot.Robot;
import org.usfirst.frc.falcons6443.robot.utilities.ElevatorEnums;

public class TestElevator extends SimpleCommand {
    int maxEncoderRevolutions;

    public TestElevator(int maxEncoderRevolutions) {
        super("Teleop Command");
        requires(elevator);

        this.maxEncoderRevolutions = maxEncoderRevolutions;
    }

    @Override
    public void initialize() {
        //elevator.moveToHeight();
    }

    public boolean isFinished() {
        return elevator.isAtHeight();
    }
}
