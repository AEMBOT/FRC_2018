package org.usfirst.frc.falcons6443.robot.commands.autocommands;

import edu.wpi.first.wpilibj.command.CommandGroup;

import org.usfirst.frc.falcons6443.robot.commands.subcommands.Delay;
import org.usfirst.frc.falcons6443.robot.commands.subcommands.DriveToDistance;
import org.usfirst.frc.falcons6443.robot.commands.subcommands.RotateToAngleSad;
import org.usfirst.frc.falcons6443.robot.subsystems.Elevator;
import org.usfirst.frc.falcons6443.robot.subsystems.FlywheelSystem;
import org.usfirst.frc.falcons6443.robot.utilities.Enums;

/**
 * Command to move to the left switch from the right starting position and place block
 *
 * @author Goirick Saha
 */

public class CenterToLeftSwitch extends CommandGroup {

    private FlywheelSystem flywheelSystem;
    private Elevator elevator;


    public CenterToLeftSwitch() {

        addSequential(new DriveToDistance(45));
        addSequential(new RotateToAngle(270)); //Turns 90 degrees left. Since 90 is to the right 270 would be 90 to the left.
        addSequential(new DriveToDistance(110));
        addSequential(new RotateToAngle(90));
        addSequential(new DriveToDistance(56));

        //elevator.setToHeight(Enums.SWITCH)

        flywheelSystem.rotateIntake(Enums.IntakeDownPosition);
        flywheelSystem.output();
        addSequential(new Delay(2));
        flywheelSystem.stop();
       // Commented these out since the flywheel can be kept on.

    }
}
