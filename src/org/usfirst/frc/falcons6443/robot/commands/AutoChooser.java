package org.usfirst.frc.falcons6443.robot.commands;
import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.falcons6443.robot.commands.autocommands.LeftToElevator;
import org.usfirst.frc.falcons6443.robot.commands.autocommands.RightToElevator;
import org.usfirst.frc.falcons6443.robot.utilities.FieldData;


/**
 * This class handles the logic by receiving a selected position
 * by the drive team.  It will call a final CommandGroup.
 *
 *@author Aleks Vidmantas
 */
public class AutoChooser {

    public enum Position {
        LEFT, CENTER, RIGHT
    }

    Position position;

    //Pass in the chosen position via dashboard/sendable chooser
    public AutoChooser(Position position){
        this.position = position;
        choose();
    }

    //Performs selection process
    private void choose(){
        CommandGroup finalAuto;
        switch (position){

            //Handles which code to run depending on result of the specified char
            case LEFT:
                if(FieldData.getCharScale() == 'L')
                    finalAuto = new LeftToElevator();
                else
                    finalAuto = new RightToElevator();
                break;

            case CENTER:
                break;

            case RIGHT:
                break;
        }

    }

}
