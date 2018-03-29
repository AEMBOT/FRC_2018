package org.usfirst.frc.falcons6443.robot.commands.autocommands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.falcons6443.robot.RobotMap;
import org.usfirst.frc.falcons6443.robot.commands.subcommands.*;

public class RotateToAngle extends CommandGroup {
    public RotateToAngle(double angle){
        if(RobotMap.BackUpAngle){
            addSequential(new RotateToAngleSadBackUp(angle));
            addSequential(new StopDrive());
        } else {
            addSequential(new RotateToAngleSad(angle));
            addSequential(new StopDrive());
        }
    }
}
