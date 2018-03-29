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
        Logger.log(LoggerSystems.Auto,"Start auto path", "Center to right switch");
        addSequential(new MoveIntake(IntakePosition.IntakeHalfPosition, false, false, true));
      //  addSequential(new MoveElevator(ElevatorPosition.Switch));

        addSequential(new DriveToDistanceStop(76)); //101
        addSequential(new Crawl(true));

        addSequential(new MoveIntake(IntakePosition.IntakeHalfPosition, true, false, false));
        addSequential(new Delay(4));
        addSequential(new MoveIntake(IntakePosition.IntakeHalfPosition, false, true, false));
    }
}
