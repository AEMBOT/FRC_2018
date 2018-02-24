package org.usfirst.frc.falcons6443.robot.commands.subcommands;

import org.usfirst.frc.falcons6443.robot.commands.SimpleCommand;

public class StopDrive extends SimpleCommand {

    private boolean off;

    public StopDrive(){
        super("stop");
        requires(driveTrain);
        off = false;
    }
    @Override
    public void initialize() {
        driveTrain.tankDrive(0, 0);
    }

    @Override
    public void execute() {
        driveTrain.tankDrive(0, 0);
        off = true;
    }

    @Override
    public boolean isFinished() {
        return off;
    }
}