package org.usfirst.frc.falcons6443.robot.commands.autocommands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.falcons6443.robot.commands.subcommands.Delay;
import org.usfirst.frc.falcons6443.robot.commands.subcommands.DriveToDistance;
import org.usfirst.frc.falcons6443.robot.commands.subcommands.MoveElevator;
import org.usfirst.frc.falcons6443.robot.commands.subcommands.MoveIntake;
import org.usfirst.frc.falcons6443.robot.utilities.Enums.IntakePosition;
import org.usfirst.frc.falcons6443.robot.utilities.Enums.ElevatorPosition;


/**
 * Command to move to the right switch from the left starting position and place block
 *
 * @author Goirick Saha
 */



public class LeftToLeftScale extends CommandGroup {


    public LeftToLeftScale() {
        addSequential(new MoveIntake(IntakePosition.IntakeDownPosition, false, false));
        addSequential(new Delay(1.5));
        addSequential(new MoveElevator(ElevatorPosition.Scale));


        addSequential(new DriveToDistance(242));
        addSequential(new RotateToAngle(90));
        addSequential(new DriveToDistance(12));
        addSequential(new RotateToAngle(270));
        addSequential(new DriveToDistance(32));
        addSequential(new RotateToAngle(270));
        addSequential(new DriveToDistance(53));


        addSequential(new MoveIntake(IntakePosition.IntakeDownPosition, true, false));
        addSequential(new Delay(4));
        addSequential(new MoveIntake(IntakePosition.IntakeDownPosition, false, true));
    }
}
