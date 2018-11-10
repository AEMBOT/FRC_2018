package org.usfirst.frc.falcons6443.robot.commands.autocommands.unused;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.falcons6443.robot.commands.autocommands.RotateToAngle;
import org.usfirst.frc.falcons6443.robot.commands.subcommands.*;
import org.usfirst.frc.falcons6443.robot.commands.subcommands.Elevator.AutoLiftElevator;
import org.usfirst.frc.falcons6443.robot.commands.subcommands.Elevator.SetElevatorPosition;
import org.usfirst.frc.falcons6443.robot.commands.subcommands.Intake.OutputCube;
import org.usfirst.frc.falcons6443.robot.commands.subcommands.Intake.ResetRotateEncoder;
import org.usfirst.frc.falcons6443.robot.commands.subcommands.Intake.RotateIntake;
import org.usfirst.frc.falcons6443.robot.commands.subcommands.Intake.SetRotationPosition;
import org.usfirst.frc.falcons6443.robot.utilities.enums.*;

/**
 * Command to move to the right switch from the left starting position and place block
 *
 * @author Goirick Saha
 */
public class LeftToLeftScale extends CommandGroup {
    public LeftToLeftScale() {
        addSequential(new ResetRotateEncoder());
        addSequential(new SetElevatorPosition(ElevatorPosition.Scale));
        addSequential(new SetRotationPosition(RotationPosition.IntakeHalfPosition));

        addSequential(new DriveToDistance(271)); //Short faster method of travel
        addSequential(new RotateToAngle(90));

        addSequential(new AutoLiftElevator());
        addSequential(new RotateIntake(true));
        addSequential(new OutputCube(false));
    }
//    public LeftToLeftScale() {
 //       addSequential(new MoveIntake(RotationPosition.IntakeDownPosition, false, false, true));
//        addSequential(new Delay(1.5));
//        addSequential(new MoveElevator(ElevatorPosition.Scale));
//
//
//        addSequential(new DriveToDistance(242));
//        addSequential(new RotateToAngle(90));
//        addSequential(new DriveToDistance(12));
//        addSequential(new RotateToAngle(270));
//        addSequential(new DriveToDistance(32));
//        addSequential(new RotateToAngle(270));
//        addSequential(new DriveToDistance(53));
//
//
//        addSequential(new MoveIntake(RotationPosition.IntakeDownPosition, true, false, false));
//        addSequential(new Delay(4));
//        addSequential(new MoveIntake(RotationPosition.IntakeDownPosition, false, true, false));
//    }
}
