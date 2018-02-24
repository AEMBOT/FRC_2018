package org.usfirst.frc.falcons6443.robot.commands.autocommands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.falcons6443.robot.commands.subcommands.Delay;
import org.usfirst.frc.falcons6443.robot.commands.subcommands.DriveToDistance;
import org.usfirst.frc.falcons6443.robot.commands.subcommands.RotateToAngleSad;
import org.usfirst.frc.falcons6443.robot.subsystems.FlywheelSystem;
import org.usfirst.frc.falcons6443.robot.utilities.Enums;

/**
 * Command to get from second starting position to leftside scale
 *
 * @author Owen Engbretson
 */
public class RightToRightScale extends CommandGroup{

    private FlywheelSystem flywheelSystem;

    public RightToRightScale(){
        addSequential(new DriveToDistance(228));
        addSequential(new RotateToAngleSad(20));
        addSequential(new DriveToDistance(102));
        addSequential(new RotateToAngleSad(-110));
        addSequential(new DriveToDistance(35));

        //elevator.setToHeight(Enums.SCALE)

        flywheelSystem.rotateIntake(Enums.IntakeDownPosition);
        flywheelSystem.output();
        addSequential(new Delay(2));
        flywheelSystem.stop();




    }
}
