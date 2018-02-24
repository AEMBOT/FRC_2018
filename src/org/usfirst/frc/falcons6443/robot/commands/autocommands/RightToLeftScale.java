package org.usfirst.frc.falcons6443.robot.commands.autocommands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.falcons6443.robot.commands.subcommands.DriveToDistance;
import org.usfirst.frc.falcons6443.robot.commands.subcommands.RotateToAngleSad;
import org.usfirst.frc.falcons6443.robot.subsystems.FlywheelSystem;

/**
 * Command to get to leftside scale from second position
 *
 * @author Owen Engbretson
 */
public class RightToLeftScale extends CommandGroup {

    private FlywheelSystem flywheelSystem;

        public RightToLeftScale(){
            addSequential(new DriveToDistance(228));
            addSequential(new RotateToAngleSad(-90));
            addSequential(new DriveToDistance(232));
            addSequential(new RotateToAngleSad(90));
            addSequential(new DriveToDistance(96));
            addSequential(new RotateToAngleSad(90));
            addSequential(new DriveToDistance(54));

            //add elevator code

            flywheelSystem.output();


        }

}
