package org.usfirst.frc.falcons6443.robot.commands.autocommands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.falcons6443.robot.commands.subcommands.DriveToDistance;
import org.usfirst.frc.falcons6443.robot.subsystems.Elevator;
import org.usfirst.frc.falcons6443.robot.subsystems.FlywheelSystem;
import org.usfirst.frc.falcons6443.robot.utilities.ElevatorEnums;



/**
 * Command to move forward and place cube on right switch from center position
 *
 * @author Goirick Saha
 */

//@TODO add elevator code to lift intake


public class CenterToRightSwitch extends CommandGroup {

    private FlywheelSystem flywheelSystem;
    private Elevator elevator;

    public CenterToRightSwitch() {

        addSequential(new DriveToDistance(101));

        //elevator.setToHeight(ElevatorEnums.Switch); setToHeight needs to be tested in elevator class.

        flywheelSystem = new FlywheelSystem();
        flywheelSystem.output();
    }
}
