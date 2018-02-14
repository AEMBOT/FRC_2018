package org.usfirst.frc.falcons6443.robot.commands.autocommands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.falcons6443.robot.commands.subcommands.DriveToDistance;
import org.usfirst.frc.falcons6443.robot.subsystems.FlywheelSystem;


/**
 * Command to move forward and place cube on right switch from center position
 *
 * @author Goirick Saha
 */

//@TODO add elevator code to lift intake


public class CenterToRightSwitch extends CommandGroup {

    private FlywheelSystem flywheelSystem;

    public CenterToRightSwitch() {

        addSequential(new DriveToDistance(101));

        //Elevator code to go here

        flywheelSystem = new FlywheelSystem();
        flywheelSystem.output();
    }
}
