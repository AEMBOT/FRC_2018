package org.usfirst.frc.falcons6443.robot.commands.subcommands;

import org.usfirst.frc.falcons6443.robot.commands.SimpleCommand;

public class StopDrive extends SimpleCommand {

    public StopDrive(){
        super("Stop DriveTrain");
        requires(driveTrain);
    }
    @Override
    public void initialize() {
        driveTrain.tankDrive(0, 0);
    }

    @Override
    public void execute() {
        driveTrain.tankDrive(0, 0);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
