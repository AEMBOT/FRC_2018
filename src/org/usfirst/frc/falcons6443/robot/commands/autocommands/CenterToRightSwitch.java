package org.usfirst.frc.falcons6443.robot.commands.autocommands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.falcons6443.robot.commands.subcommands.*;
import org.usfirst.frc.falcons6443.robot.utilities.enums.ElevatorPosition;
import org.usfirst.frc.falcons6443.robot.utilities.enums.IntakePosition;


/**
 * Command to move forward and place cube on right switch from center position
 *
 * @author Goirick Saha
 */

//@TODO add elevator code to lift intake


public class CenterToRightSwitch extends CommandGroup {

    public CenterToRightSwitch() {
        addSequential(new MoveIntake(IntakePosition.IntakeDownPosition, false, false, true));
        addSequential(new Delay(0.3));
        addSequential(new MoveElevator(ElevatorPosition.Switch));

        addSequential(new DriveToDistance(106)); //101
        addSequential(new Crawl(true));

        addSequential(new MoveIntake(IntakePosition.IntakeDownPosition, true, false, false));
        addSequential(new Delay(2));
        addSequential(new MoveIntake(IntakePosition.IntakeDownPosition, false, true, false));
    }
}
