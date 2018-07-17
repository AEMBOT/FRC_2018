package org.usfirst.frc.falcons6443.robot.commands.complete;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.falcons6443.robot.commands.autocommands.CenterToLeftSwitch;
import org.usfirst.frc.falcons6443.robot.commands.autocommands.CenterToRightSwitch;
import org.usfirst.frc.falcons6443.robot.communication.FieldData;

public class CenterToSwitch extends CommandGroup{
    public CenterToSwitch() {
//        if ((FieldData.getChar(FieldData.Object.SWITCH) == 'R') ) {
            addSequential(new CenterToRightSwitch());
//        } else if ((FieldData.getChar(FieldData.Object.SWITCH) == 'L')) {
//            addSequential(new CenterToLeftSwitch());
//        }
    }
}