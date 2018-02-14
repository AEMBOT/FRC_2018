package org.usfirst.frc.falcons6443.robot.commands.autocommands;

import edu.wpi.first.wpilibj.command.CommandGroup;

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
