package org.usfirst.frc.falcons6443.robot.commands.autocommands.unused;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.falcons6443.robot.RobotMap;
import org.usfirst.frc.falcons6443.robot.commands.autocommands.RotateToAngle;
import org.usfirst.frc.falcons6443.robot.commands.subcommands.*;
import org.usfirst.frc.falcons6443.robot.utilities.enums.*;

/**
 * Command to move to the right switch from the second position on the right.
 *
 * @author Goirick Saha
 */
public class RightToRightScaleLong extends CommandGroup {

    public RightToRightScaleLong(){
        addSequential(new MoveIntake(IntakePosition.IntakeHalfPosition, false, false, true));
        addSequential(new Delay(1.5));
        addSequential(new MoveElevator(ElevatorPosition.Scale));

        addSequential(new DriveToDistance(242));
        addSequential(new RotateToAngle(270));
        addSequential(new DriveToDistance(12));
        addSequential(new RotateToAngle(90));
        addSequential(new DriveToDistance(32));
        addSequential(new RotateToAngle(90));
        addSequential(new DriveToDistance(53));

        addSequential(new MoveIntake(IntakePosition.IntakeDownPosition, true, false, false));
        addSequential(new Delay(4));
        addSequential(new MoveIntake(IntakePosition.IntakeDownPosition, false, true, false));
    }
}
