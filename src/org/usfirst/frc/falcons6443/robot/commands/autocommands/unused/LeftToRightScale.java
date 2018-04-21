package org.usfirst.frc.falcons6443.robot.commands.autocommands.unused;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.falcons6443.robot.commands.autocommands.RotateToAngle;
import org.usfirst.frc.falcons6443.robot.commands.subcommands.*;
import org.usfirst.frc.falcons6443.robot.utilities.enums.*;

/**
 * Command to move to the right scale from the left starting position and place block
 *
 * @author Goirick Saha
 */
public class LeftToRightScale extends CommandGroup {
    public LeftToRightScale() {
        addSequential(new MoveIntake(RotationPosition.IntakeHalfPosition, false, false, true));
        addSequential(new Delay(0.5));
        addSequential(new MoveElevator(ElevatorPosition.Scale));

        addSequential(new DriveToDistance(228));
        addSequential(new RotateToAngle(90));
        addSequential(new DriveToDistance(232));
        addSequential(new RotateToAngle(270));
        addSequential(new DriveToDistance(96));
        addSequential(new RotateToAngle(270));
        addSequential(new DriveToDistance(54));


        addSequential(new MoveIntake(RotationPosition.IntakeDownPosition, false, false, false));
        addSequential(new Delay(2));
        addSequential(new MoveIntake(RotationPosition.IntakeDownPosition, true, false, false));
        addSequential(new Delay(2));
        addSequential(new MoveIntake(RotationPosition.IntakeDownPosition, false, true, false));
        }
}
