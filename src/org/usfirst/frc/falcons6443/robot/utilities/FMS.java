package org.usfirst.frc.falcons6443.robot.utilities;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.falcons6443.robot.commands.CenterToLine;
import org.usfirst.frc.falcons6443.robot.commands.LaneToLine;

/**
 * Utility to choose autonomous program based on start position and FMS info
 * @author Owen Engbretson
 */
public class FMS {

    public enum startPos {left,center,right}

    public Command autoChooser(startPos startPos, String autoMessage){
        if(startPos == FMS.startPos.left){
            if(autoMessage.charAt(1) == 'L') return new LaneToLine();
            else if(autoMessage.charAt(1) == 'R') return new LaneToLine();
        }
        else if(startPos == FMS.startPos.center){
            if(autoMessage.charAt(0) == 'L') return new CenterToLine();
            else if(autoMessage.charAt(0) == 'R') return new CenterToLine();
        }
        else if(startPos == FMS.startPos.right){
            if(autoMessage.charAt(1) == 'L') return new LaneToLine();
            else if(autoMessage.charAt(1) == 'R') return new LaneToLine();
        }

    }
}
