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
 * Command to move to the left switch from the right starting position and place block
 *
 * @author Goirick Saha
 */

public class CenterToLeftSwitch extends CommandGroup {

    //private Delay delay;


    public CenterToLeftSwitch() {
        addSequential(new MoveElevator(ElevatorPosition.Switch));

        addSequential(new DriveToDistance(45));
        addSequential(new RotateToAngle(90)); //Turns 90 degrees left.
        addSequential(new DriveToDistance(110));
        addSequential(new RotateToAngle(270));
        addSequential(new DriveToDistance(56));

        //elevator.setToHeight(ElevatorPosition.Switch);

        addSequential(new MoveIntake(IntakePosition.IntakeDownPosition, false, false));
        addSequential(new Delay(2));
        addSequential(new MoveIntake(IntakePosition.IntakeDownPosition, true, false));
        addSequential(new Delay(4));
        addSequential(new MoveIntake(IntakePosition.IntakeDownPosition, false, true));

    }
}
