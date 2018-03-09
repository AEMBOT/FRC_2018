package org.usfirst.frc.falcons6443.robot.commands.autocommands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.falcons6443.robot.commands.subcommands.Delay;
import org.usfirst.frc.falcons6443.robot.commands.subcommands.DriveToDistance;
import org.usfirst.frc.falcons6443.robot.commands.subcommands.MoveElevator;
import org.usfirst.frc.falcons6443.robot.commands.subcommands.MoveIntake;
import org.usfirst.frc.falcons6443.robot.subsystems.FlywheelSystem;
import org.usfirst.frc.falcons6443.robot.utilities.Enums.ElevatorPosition;
import org.usfirst.frc.falcons6443.robot.utilities.Enums.IntakePosition;

/**
 * Command to get from second starting position to leftside scale
 *
 * @author Owen Engbretson
 */
public class RightToRightScale extends CommandGroup{

    public RightToRightScale(){
        addSequential(new MoveIntake(IntakePosition.IntakeDownPosition, false, false));
        addSequential(new Delay(1.5));
        addSequential(new MoveElevator(ElevatorPosition.Scale));

        addSequential(new DriveToDistance(228));
        addSequential(new RotateToAngle(20));
        addSequential(new DriveToDistance(102));
        addSequential(new RotateToAngle(250));//-110
        addSequential(new DriveToDistance(35));

        //elevator.setToHeight(Enums.Scale);

        addSequential(new MoveIntake(IntakePosition.IntakeDownPosition, true, false));
        addSequential(new Delay(4));
        addSequential(new MoveIntake(IntakePosition.IntakeDownPosition, false, true));
    }
}
