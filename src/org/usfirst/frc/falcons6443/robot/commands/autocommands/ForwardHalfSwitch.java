package org.usfirst.frc.falcons6443.robot.commands.autocommands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.falcons6443.robot.commands.SimpleCommand;
import org.usfirst.frc.falcons6443.robot.commands.subcommands.Delay;
import org.usfirst.frc.falcons6443.robot.commands.subcommands.DriveToDistance;
import org.usfirst.frc.falcons6443.robot.commands.subcommands.MoveElevator;
import org.usfirst.frc.falcons6443.robot.commands.subcommands.MoveIntake;
import org.usfirst.frc.falcons6443.robot.communication.FieldData;
import org.usfirst.frc.falcons6443.robot.communication.NetTables;
import org.usfirst.frc.falcons6443.robot.utilities.Enums.ElevatorPosition;
import org.usfirst.frc.falcons6443.robot.utilities.Enums.IntakePosition;

public class ForwardHalfSwitch extends CommandGroup {
    public ForwardHalfSwitch(){
        if((FieldData.getChar(FieldData.Object.SWITCH) == 'R') && NetTables.getEntry("right").getBoolean(true)){

           // addSequential(new MoveIntake(IntakePosition.IntakeDownPosition, false, false));
            addSequential(new Delay(.5));
            addSequential(new MoveElevator(ElevatorPosition.Switch));

            addSequential(new DriveToDistance(140));

           // addSequential(new MoveIntake(IntakePosition.IntakeDownPosition, true, false));
            addSequential(new Delay(4));
           // addSequential(new MoveIntake(IntakePosition.IntakeDownPosition, false, true));
        }else if((FieldData.getChar(FieldData.Object.SWITCH) == 'L') && NetTables.getEntry("left").getBoolean(true)){
            //addSequential(new MoveIntake(IntakePosition.IntakeDownPosition, false, false));
            addSequential(new Delay(.5));
            addSequential(new MoveElevator(ElevatorPosition.Switch));

            addSequential(new DriveToDistance(140));

         //   addSequential(new MoveIntake(IntakePosition.IntakeDownPosition, true, false));
            addSequential(new Delay(4));
       //     addSequential(new MoveIntake(IntakePosition.IntakeDownPosition, false, true));
        }else{
            addSequential(new DriveToDistance(140));
        }

    }

}
