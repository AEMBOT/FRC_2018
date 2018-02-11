package org.usfirst.frc.falcons6443.robot.commands.autocommands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.falcons6443.robot.commands.subcommands.DriveToDistance;

//Temporary until one is written
public class LeftToElevator extends CommandGroup {

    public LeftToElevator(){

        addSequential(new DriveToDistance(10));

    }

}
