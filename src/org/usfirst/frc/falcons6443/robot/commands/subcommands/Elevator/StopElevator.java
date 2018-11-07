package org.usfirst.frc.falcons6443.robot.commands.subcommands.Elevator;

import org.usfirst.frc.falcons6443.robot.commands.SimpleCommand;

public class StopElevator extends SimpleCommand {

    public StopElevator() {
        super("Stop Elevator");
        requires(elevator);
    }

    @Override
    public void initialize() {
        elevator.stop();
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}
