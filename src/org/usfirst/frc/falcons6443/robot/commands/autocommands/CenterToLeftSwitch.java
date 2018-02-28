package org.usfirst.frc.falcons6443.robot.commands.autocommands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.falcons6443.robot.commands.subcommands.DriveToDistance;
import org.usfirst.frc.falcons6443.robot.commands.subcommands.RotateToAngleSad;
import org.usfirst.frc.falcons6443.robot.subsystems.FlywheelSystem;
import org.usfirst.frc.falcons6443.robot.utilities.Enums;

/**
 * Command to move to the left switch from the right starting position and place block
 *
 * @author Goirick Saha
 */

public class CenterToLeftSwitch extends CommandGroup {

    private FlywheelSystem flywheelSystem;
    //private Delay delay;


    public CenterToLeftSwitch() {
        flywheelSystem = new FlywheelSystem();

        addSequential(new DriveToDistance(45));
        addSequential(new RotateToAngle(90)); //Turns 90 degrees left.
        addSequential(new DriveToDistance(110));
        addSequential(new RotateToAngle(270));
        addSequential(new DriveToDistance(56));

        //Elevator code to go here

        flywheelSystem.rotateIntake(Enums.IntakeDownPosition);
        flywheelSystem.output();

    }
}
