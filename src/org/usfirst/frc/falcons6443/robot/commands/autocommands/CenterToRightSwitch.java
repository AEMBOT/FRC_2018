package org.usfirst.frc.falcons6443.robot.commands.autocommands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.falcons6443.robot.commands.subcommands.Delay;
import org.usfirst.frc.falcons6443.robot.commands.subcommands.DriveToDistance;
import org.usfirst.frc.falcons6443.robot.subsystems.Elevator;
import org.usfirst.frc.falcons6443.robot.subsystems.FlywheelSystem;
import org.usfirst.frc.falcons6443.robot.utilities.Enums.IntakePosition;


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
        flywheelSystem = new FlywheelSystem();
        addSequential(new DriveToDistance(101));

        //elevator.setToHeight(ElevatorPosition.Switch); //setToHeight needs to be tested in elevator class.
        flywheelSystem.setIntakePosition(IntakePosition.IntakeDownPosition);
        addSequential(new Delay(2));
        flywheelSystem.output();
    }
}
