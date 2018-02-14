package org.usfirst.frc.falcons6443.robot.commands.autocommands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.falcons6443.robot.subsystems.FlywheelSystem;
import org.usfirst.frc.falcons6443.robot.commands.subcommands.*;

/**
 * Command to move to the right scale from the left starting position and place block
 *
 * @author Goirick Saha
 */



public class LeftToRightScale extends CommandGroup {

    private FlywheelSystem flywheelSystem;

    public LeftToRightScale() {

        addSequential(new DriveToDistance(228));
        addSequential(new RotateToAngle(90));
        addSequential(new DriveToDistance(232));
        addSequential(new RotateToAngle(180));
        addSequential(new DriveToDistance(96));
        addSequential(new RotateToAngle(-90));
        addSequential(new DriveToDistance(54));

            // elevator code.

            flywheelSystem.output();


        }
}
