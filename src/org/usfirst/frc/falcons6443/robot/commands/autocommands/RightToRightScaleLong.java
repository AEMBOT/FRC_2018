package org.usfirst.frc.falcons6443.robot.commands.autocommands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.falcons6443.robot.commands.subcommands.DriveToDistance;
import org.usfirst.frc.falcons6443.robot.subsystems.FlywheelSystem;
import org.usfirst.frc.falcons6443.robot.utilities.Enums.Enums;


/**
 * Command to move to the right switch from the second position on the right.
 *
 * @author Goirick Saha
 */

public class RightToRightScaleLong extends CommandGroup {

    private FlywheelSystem flywheelSystem;

    public RightToRightScaleLong(){

        addSequential(new DriveToDistance(242));
        addSequential(new RotateToAngle(270));
        addSequential(new DriveToDistance(12));
        addSequential(new RotateToAngle(90));
        addSequential(new DriveToDistance(32));
        addSequential(new RotateToAngle(90));
        addSequential(new DriveToDistance(53));

        //elevator.setToHeight(Enums.Scale);

        flywheelSystem.rotateIntake(Enums.IntakeDownPosition);
        flywheelSystem.output();

    }
}
