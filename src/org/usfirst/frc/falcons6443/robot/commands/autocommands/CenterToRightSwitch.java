package org.usfirst.frc.falcons6443.robot.commands.autocommands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.falcons6443.robot.commands.subcommands.*;
//import org.usfirst.frc.falcons6443.robot.utilities.Logger;
import org.usfirst.frc.falcons6443.robot.commands.subcommands.Elevator.AutoLiftElevator;
import org.usfirst.frc.falcons6443.robot.commands.subcommands.Elevator.SetElevatorPosition;
import org.usfirst.frc.falcons6443.robot.commands.subcommands.Elevator.StopElevator;
import org.usfirst.frc.falcons6443.robot.commands.subcommands.Intake.*;
import org.usfirst.frc.falcons6443.robot.utilities.enums.*;

/**
 * Command to move forward and place cube on right switch from center position
 *
 * @author Goirick Saha
 */
public class CenterToRightSwitch extends CommandGroup {

    public CenterToRightSwitch() {
  //      Logger.log(LoggerSystems.Auto,"Start auto path: Center to right switch");
        addSequential(new ResetRotateEncoder());
        addSequential(new SetElevatorPosition(ElevatorPosition.Switch));
        addSequential(new SetRotationPosition(RotationPosition.IntakeHalfPosition));
        addParallel(new AutoLiftElevator());
        addParallel(new RotateIntake(true));
        addParallel(new DriveToDistance(75)); //101  //Stop

        addSequential(new StopElevator());
        addSequential(new Crawl(true));

        addSequential(new Delay(0.6)); //add delay??
        addSequential(new IntakeCube());
        addSequential(new Delay(4));
        addSequential(new OutputCube(false));
    }
}
