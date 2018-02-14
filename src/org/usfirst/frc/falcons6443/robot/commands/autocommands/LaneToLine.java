package org.usfirst.frc.falcons6443.robot.commands.autocommands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.falcons6443.robot.commands.subcommands.*;

/**
 * Command to get over AutoLine from left or right starting position
 *
 * @author Owen Engbretson
 */
public class LaneToLine extends CommandGroup {
     public LaneToLine(){
        addSequential(new DriveToDistance(120));
    }
}
