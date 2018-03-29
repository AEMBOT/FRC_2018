package org.usfirst.frc.falcons6443.robot.commands.autocommands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.falcons6443.robot.commands.subcommands.*;

public class DriveToDistanceStop extends CommandGroup {
    public DriveToDistanceStop(int distance, boolean backUp){
        if(backUp){
            addSequential(new DriveToDistanceBackUp(distance));
            addSequential(new StopDrive());
        } else {
            addSequential(new DriveToDistance(distance));
            addSequential(new StopDrive());
        }

    }
}
