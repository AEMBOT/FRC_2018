package org.usfirst.frc.falcons6443.robot.commands;

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

        switch (position){

            case LEFT:
                break;

            case CENTER:
                break;

            case RIGHT:
                break;
        }

    }

}
