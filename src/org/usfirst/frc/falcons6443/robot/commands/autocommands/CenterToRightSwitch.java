package org.usfirst.frc.falcons6443.robot.commands.autocommands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.falcons6443.robot.commands.subcommands.*;
import org.usfirst.frc.falcons6443.robot.utilities.Logger;
import org.usfirst.frc.falcons6443.robot.utilities.enums.*;

/**
 * Command to move forward and place cube on right switch from center position
 *
 * @author Goirick Saha
 */
public class CenterToRightSwitch extends CommandGroup {

    public CenterToRightSwitch() {
        Logger.log(LoggerSystems.Auto,"Start auto path: Center to right switch");
        addSequential(new MoveElevator(ElevatorPosition.Switch));
        addSequential(new MoveIntake(RotationPosition.IntakeHalfPosition, false, false, true));

        //addSequential(new Delay(2));
        addSequential(new DriveToDistanceBackUp(75, false, true)); //101  //Stop
        addSequential(new MoveElevator(ElevatorPosition.Stop));
        addSequential(new Crawl(true));

        addSequential(new Delay(0.6)); //add delay??
        addSequential(new MoveIntake(RotationPosition.IntakeHalfPosition, true, false, false));
        addSequential(new Delay(4));
        addSequential(new MoveIntake(RotationPosition.IntakeUpPosition, false, true, false));
    }
}
