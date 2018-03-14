package org.usfirst.frc.falcons6443.robot.commands.autocommands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.falcons6443.robot.commands.subcommands.RotateToAngleSad;
import org.usfirst.frc.falcons6443.robot.commands.subcommands.StopDrive;

public class RotateToAngle extends CommandGroup {
    public RotateToAngle(double angle){
        addSequential(new RotateToAngleSad(angle));
        addSequential(new StopDrive());
    }
}
