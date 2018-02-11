package org.usfirst.frc.falcons6443.robot.commands;

/**
 *
 * */
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

    //Performs
    private void choose(){

    }

}
