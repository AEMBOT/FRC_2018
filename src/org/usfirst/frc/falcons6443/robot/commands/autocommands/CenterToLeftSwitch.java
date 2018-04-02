package org.usfirst.frc.falcons6443.robot.commands.autocommands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.falcons6443.robot.RobotMap;
import org.usfirst.frc.falcons6443.robot.commands.subcommands.*;
import org.usfirst.frc.falcons6443.robot.utilities.Logger;
import org.usfirst.frc.falcons6443.robot.utilities.enums.*;

/**
 * Command to move to the left switch from the right starting position and place block
 *
 * @author Goirick Saha
 */
public class CenterToLeftSwitch extends CommandGroup {

    public CenterToLeftSwitch() {
        Logger.log(LoggerSystems.Auto,"Start auto path", "Center to left switch");
        addSequential(new MoveElevator(ElevatorPosition.Switch));

        addSequential(new DriveToDistanceBackUp(35, true));
        addSequential(new RotateToAngle(270)); //Turns 90 degrees left.
        addSequential(new DriveToDistanceBackUp(110, true));
        addSequential(new RotateToAngle(90));
        addSequential(new MoveIntake(IntakePosition.IntakeHalfPosition, false, false, true));
        addSequential(new DriveToDistanceBackUp(55, true)); //56
        addSequential(new MoveElevator(ElevatorPosition.Stop));
        addSequential(new Crawl(true));

        //try intakeStopPosition??
        //addSequential(new Delay(2)); //delay??
        addSequential(new MoveIntake(IntakePosition.IntakeHalfPosition, true, false, false));
        addSequential(new Delay(4));
        addSequential(new MoveIntake(IntakePosition.IntakeHalfPosition, false, true, false));
    }
}
