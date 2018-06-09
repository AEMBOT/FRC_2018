package org.usfirst.frc.falcons6443.robot.commands.complete;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.falcons6443.robot.commands.subcommands.Delay;
import org.usfirst.frc.falcons6443.robot.commands.subcommands.DriveToDistance;
//import org.usfirst.frc.falcons6443.robot.utilities.Logger;
import org.usfirst.frc.falcons6443.robot.utilities.enums.LoggerSystems;

/**
 * Use in matches with BearMetal
 **/
public class LaneToLineWait extends CommandGroup {
    public LaneToLineWait(){
//        Logger.log(LoggerSystems.Auto,"Start auto path: Lane to line wait");
        addSequential(new Delay(9));
        addSequential(new DriveToDistance(120));
    }
}