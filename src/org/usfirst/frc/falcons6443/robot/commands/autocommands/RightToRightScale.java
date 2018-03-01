package org.usfirst.frc.falcons6443.robot.commands.autocommands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.falcons6443.robot.commands.subcommands.DriveToDistance;
import org.usfirst.frc.falcons6443.robot.subsystems.IntakeSystem;
import org.usfirst.frc.falcons6443.robot.utilities.Enums.IntakePosition;

/**
 * Command to get from second starting position to leftside scale
 *
 * @author Owen Engbretson
 */
public class RightToRightScale extends CommandGroup{

    private IntakeSystem intakeSystem;

    public RightToRightScale(){
        intakeSystem = new IntakeSystem();

        addSequential(new DriveToDistance(228));
        addSequential(new RotateToAngle(20));
        addSequential(new DriveToDistance(102));
        addSequential(new RotateToAngle(250));//-110
        addSequential(new DriveToDistance(35));

        //elevator.setToHeight(Enums.Scale);

        intakeSystem.rotateIntake(IntakePosition.IntakeDownPosition);
        intakeSystem.output();




    }
}
