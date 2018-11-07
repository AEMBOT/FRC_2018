package org.usfirst.frc.falcons6443.robot.commands.subcommands.Intake;

import org.usfirst.frc.falcons6443.robot.commands.SimpleCommand;

public class ResetRotateEncoder extends SimpleCommand {

    public ResetRotateEncoder() {
        super("Reset rotation encoder");
        requires(rotation);
    }

    @Override
    public void initialize() {
        rotation.resetEncoder();
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}
