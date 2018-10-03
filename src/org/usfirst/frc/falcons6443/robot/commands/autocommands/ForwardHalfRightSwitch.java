package org.usfirst.frc.falcons6443.robot.commands.autocommands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.falcons6443.robot.commands.subcommands.*;
import org.usfirst.frc.falcons6443.robot.communication.FieldData;
import org.usfirst.frc.falcons6443.robot.utilities.enums.*;

public class ForwardHalfRightSwitch extends CommandGroup {
    public ForwardHalfRightSwitch(){
//        if(FieldData.getChar(FieldData.Object.SWITCH) == 'R'){
            addSequential(new MoveIntake(RotationPosition.IntakeHalfPosition, false, false, true));
            addSequential(new MoveElevator(ElevatorPosition.Switch));

            addSequential(new DriveToDistance(104));
            addSequential(new Crawl(true));

            addSequential(new MoveIntake(RotationPosition.IntakeHalfPosition, true, false, false));
            addSequential(new Delay(4));
            addSequential(new MoveIntake(RotationPosition.IntakeHalfPosition, false, true, false));
//        }else{
//            addSequential(new DriveToDistance(140));
//        }
    }
}
