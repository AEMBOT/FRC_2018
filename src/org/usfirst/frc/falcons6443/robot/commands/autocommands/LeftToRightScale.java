package org.usfirst.frc.falcons6443.robot.commands.autocommands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.falcons6443.robot.commands.subcommands.DriveToDistance;
import org.usfirst.frc.falcons6443.robot.subsystems.IntakeSystem;
import org.usfirst.frc.falcons6443.robot.utilities.Enums.IntakePosition;


/**
 * Command to move to the right scale from the left starting position and place block
 *
 * @author Goirick Saha
 */



public class LeftToRightScale extends CommandGroup {

    private IntakeSystem intakeSystem;

    public LeftToRightScale() {
        intakeSystem = new IntakeSystem();

        addSequential(new DriveToDistance(228));
        addSequential(new RotateToAngle(270));
        addSequential(new DriveToDistance(232));
        addSequential(new RotateToAngle(90));
        addSequential(new DriveToDistance(96));
        addSequential(new RotateToAngle(90));
        addSequential(new DriveToDistance(54));

        //elevator.setToHeight(ElevatorPosition.Scale);

        intakeSystem.rotateIntake(IntakePosition.IntakeDownPosition);
        intakeSystem.output();


        }
}
