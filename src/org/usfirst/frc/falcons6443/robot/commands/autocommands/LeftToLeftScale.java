package org.usfirst.frc.falcons6443.robot.commands.autocommands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.falcons6443.robot.commands.subcommands.DriveToDistance;
import org.usfirst.frc.falcons6443.robot.subsystems.FlywheelSystem;


/**
 * Command to move to the right switch from the left starting position and place block
 *
 * @author Goirick Saha
 */



public class LeftToLeftScale extends CommandGroup {

    private FlywheelSystem flywheelSystem;

    public LeftToLeftScale() {
        flywheelSystem = new FlywheelSystem();

        addSequential(new DriveToDistance(242));
        addSequential(new RotateToAngle(270));
        addSequential(new DriveToDistance(12));
        addSequential(new RotateToAngle(180));
        addSequential(new DriveToDistance(32));
        addSequential(new RotateToAngle(90));
        addSequential(new DriveToDistance(53));

        //Elevator code here.

        flywheelSystem.output();
    }
}
