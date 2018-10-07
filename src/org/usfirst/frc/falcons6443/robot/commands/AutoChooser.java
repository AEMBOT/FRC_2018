package org.usfirst.frc.falcons6443.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import javafx.geometry.Pos;
import org.usfirst.frc.falcons6443.robot.commands.autocommands.*;
import org.usfirst.frc.falcons6443.robot.commands.autocommands.unused.*;
import org.usfirst.frc.falcons6443.robot.commands.complete.LaneToLine;
import org.usfirst.frc.falcons6443.robot.communication.FieldData;
import org.usfirst.frc.falcons6443.robot.Robot;
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
        LEFT, CENTER, RIGHT, LINE
    }

    //auto class will be created, must be CommandGroup
    CommandGroup finalAuto;
    //private Position position;


    //pass in a Position enum from Robot.java
    public AutoChooser(){//Position position){
        //this.position = position;
        choose();
    }

    //performs selection process by using a switch for which two
    //commands then choose command once fms data is received.
    private void choose(){

        Position position = (Position) Robot.sendable1.getSelected();

        switch (position){
            //handles which code to run depending on result of the specified switch/scale
            case LEFT:
                /*if(FieldData.getChar(FieldData.Object.SCALE) == 'L')
                    finalAuto = new LeftToLeftScale();
                else
                    finalAuto = new LeftToRightScale();*/
                finalAuto = new CenterToRightSwitch();
                break;

            case CENTER:
                /*if(FieldData.getChar(FieldData.Object.SWITCH) == 'L')
                    finalAuto = new CenterToLeftSwitch();
                else
                    finalAuto = new CenterToRightSwitch();*/
                finalAuto = new CenterToLeftSwitch();
                break;

            case RIGHT:
              /*  if(FieldData.getChar(FieldData.Object.SCALE) == 'R')
                    finalAuto = new RightToRightScale();
                else
                    finalAuto = new RightToLeftScale();*/
                break;

            case LINE:  //position is LINE if dashboard fails or user fails to enter choice
                System.out.println("At Switch Statement");
                finalAuto = new LaneToLine();
                break;
        }
        //finalAuto = new LaneToLine();
    }

    public void cancel(){
        if(!(finalAuto == null)){
            finalAuto.cancel();
        }
    }

    public CommandGroup getFinalAuto(){return finalAuto;}
}
