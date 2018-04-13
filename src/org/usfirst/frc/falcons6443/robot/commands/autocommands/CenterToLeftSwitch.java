package org.usfirst.frc.falcons6443.robot.commands.autocommands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Scheduler;
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
        Logger.log(LoggerSystems.Auto,"Start auto path: Center to left switch");

        Scheduler.getInstance().removeAll();
        addSequential(new MoveIntake(IntakePosition.IntakeUpPosition, false, false, false));
       // addSequential(new MoveElevator(ElevatorPosition.Exchange));

        addSequential(new DriveToDistanceBackUp(25, true, false));//35
        addSequential(new RotateToAngle(285)); //Turns 90 degrees left.
        addSequential(new DriveToDistanceBackUp(100, true, false));//110
        addSequential(new MoveElevator(ElevatorPosition.Switch));
        addSequential(new RotateToAngle(80));
        addSequential(new MoveIntake(IntakePosition.IntakeHalfPosition, false, false, true));
        //addSequential(new Delay(.7));
        addSequential(new DriveToDistanceBackUp(40, true, true)); //55
        //addSequential(new MoveElevator(ElevatorPosition.Stop));
        addSequential(new Crawl(true));

        //addSequential(new Delay(2)); //delay??
        addSequential(new MoveIntake(IntakePosition.IntakeHalfPosition, true, false, false));
        addSequential(new Delay(2));
        addSequential(new MoveIntake(IntakePosition.IntakeUpPosition, false, true, false));
    }
}
