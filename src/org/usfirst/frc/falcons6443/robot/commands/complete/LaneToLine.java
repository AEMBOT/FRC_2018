package org.usfirst.frc.falcons6443.robot.commands.complete;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.falcons6443.robot.commands.subcommands.DriveToDistance;
import org.usfirst.frc.falcons6443.robot.utilities.Logger;
import org.usfirst.frc.falcons6443.robot.utilities.enums.LoggerSystems;

/**
 * Command to get over AutoLine from left or right starting position
 *
 * @author Owen Engbretson
 */
public class LaneToLine extends CommandGroup {
     public LaneToLine(){
         Logger.log("Start auto path", "Lane to line");
         addSequential(new DriveToDistance(120));
    }
}
