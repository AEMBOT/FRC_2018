package org.usfirst.frc.falcons6443.robot.commands.autocommands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.falcons6443.robot.commands.subcommands.DriveToDistance;
import org.usfirst.frc.falcons6443.robot.commands.subcommands.RotateToAngle;

/**
 * Command to get over the Autoline from center position
 *
 * @author Owen Engbretson
 */
public class CenterToLine extends CommandGroup {
      public CenterToLine(){
            // To add delay, add second parameter to addSequential in seconds
            addSequential(new RotateToAngle(45));
            addSequential(new DriveToDistance(170));

        }
}
