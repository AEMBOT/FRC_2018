package org.usfirst.frc.falcons6443.robot.commands.autocommands.unused;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.falcons6443.robot.commands.autocommands.RotateToAngle;
import org.usfirst.frc.falcons6443.robot.commands.subcommands.*;
import org.usfirst.frc.falcons6443.robot.utilities.enums.*;

/**
 * Command to get from second starting position to leftside scale
 *
 * @author Owen Engbretson, Goirick Saha
 */
public class RightToRightScale extends CommandGroup {

    public RightToRightScale() {
        addSequential(new MoveIntake(RotationPosition.IntakeHalfPosition, false, false, true));
        addSequential(new Delay(0.5));
        addSequential(new MoveElevator(ElevatorPosition.Scale));

        addSequential(new DriveToDistance(271)); //Short faster method of travel
        addSequential(new RotateToAngle(270));

        addSequential(new MoveIntake(RotationPosition.IntakeDownPosition, false, false, false));
        addSequential(new Delay(2));
        addSequential(new MoveIntake(RotationPosition.IntakeDownPosition, true, false, false));
        addSequential(new Delay(2));
        addSequential(new MoveIntake(RotationPosition.IntakeDownPosition, false, true, false));
    }
}
//        addSequential(new MoveIntake(RotationPosition.IntakeDownPosition, false, false, true));
//        addSequential(new Delay(1.5));
//        addSequential(new MoveElevator(ElevatorPosition.Scale));
//
//        addSequential(new DriveToDistance(228));
//        addSequential(new RotateToAngle(20));
//        addSequential(new DriveToDistance(102));
//        addSequential(new RotateToAngle(250));//-110
//        addSequential(new DriveToDistance(35));
//
//        //elevator.setToHeight(enums.Scale);
//
//        addSequential(new MoveIntake(RotationPosition.IntakeDownPosition, true, false, false));
//        addSequential(new Delay(4));
//        addSequential(new MoveIntake(RotationPosition.IntakeDownPosition, false, true, false));
//    }

