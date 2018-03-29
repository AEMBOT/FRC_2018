package org.usfirst.frc.falcons6443.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.falcons6443.robot.commands.autocommands.*;
import org.usfirst.frc.falcons6443.robot.commands.autocommands.unused.*;
import org.usfirst.frc.falcons6443.robot.commands.complete.LaneToLine;
import org.usfirst.frc.falcons6443.robot.communication.FieldData;
import org.usfirst.frc.falcons6443.robot.communication.NetTables;

/**
 * This class handles will choose and autonomous mode
 * based on the starting position then from there, instantiate
 * a command group based on FMS data once the game starts.
 *
 *@author Aleks Vidmantas
 */

public class AutoChooser {
    //represents the three starting robot positions
    public enum Position {
        LEFT, CENTER, RIGHT, UNKNOWN
    }

    //auto class will be created, must be CommandGroup
    CommandGroup finalAuto;
    private Position position;


    //pass in a Position enum from Robot.java
    public AutoChooser(Position position){
        this.position = position;
        choose();
    }

    //performs selection process by using a switch for which two
    //commands then choose command once fms data is received.
    private void choose(){

        if(NetTables.getEntry("left").getBoolean(false)){
            this.position = Position.LEFT;
        }else if(NetTables.getEntry("center").getBoolean(false)){
            this.position = Position.CENTER;
        }else if(NetTables.getEntry("right").getBoolean( false)){
            this.position = Position.RIGHT;
        }else{
            this.position = Position.UNKNOWN;
        }

        switch (position){

            //handles which code to run depending on result of the specified switch/scale
            case LEFT:
                if(FieldData.getChar(FieldData.Object.SCALE) == 'L')
                    finalAuto = new LeftToLeftScale();
                else
                    finalAuto = new LeftToRightScale();
                break;

            case CENTER:
                if(FieldData.getChar(FieldData.Object.SWITCH) == 'L')
                    finalAuto = new CenterToLeftSwitch();
                else
                    finalAuto = new CenterToRightSwitch();
                break;

            case RIGHT:
                if(FieldData.getChar(FieldData.Object.SCALE) == 'R')
                    finalAuto = new RightToRightScale();
                else
                    finalAuto = new RightToLeftScale();
                break;

            case UNKNOWN:  //position is UNKNOWN if dashboard fails or user fails to enter choice
                finalAuto = new LaneToLine();
                break;
        }
        finalAuto = new LaneToLine();

    }

    public void cancel(){
        if(!(finalAuto == null)){
            finalAuto.cancel();
        }
    }

    public CommandGroup getFinalAuto(){return finalAuto;}




}
