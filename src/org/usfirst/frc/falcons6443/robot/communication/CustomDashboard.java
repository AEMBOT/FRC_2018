package org.usfirst.frc.falcons6443.robot.communication;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.falcons6443.robot.Robot;
import org.usfirst.frc.falcons6443.robot.commands.AutoChooser;
import org.usfirst.frc.falcons6443.robot.commands.subcommands.DriveToDistance;
import org.usfirst.frc.falcons6443.robot.hardware.DriveEncoders;
import org.usfirst.frc.falcons6443.robot.subsystems.DriveTrainSystem;


/**
 * Adds the sendable chooser options to the smart dashboard.
 *
 * @author Goirick Saha
 */

public class CustomDashboard {

    // autoChoice is a multiple choice component that will
    // be used to determine which auto routine to run by
    // passing its selected value into AutoChooser
    private SendableChooser autoChoice;
    private SmartDashboard smartDashboard;
    boolean center = false,left = false,right = false;

    public CustomDashboard() {
        smartDashboard = new SmartDashboard();
        autoChoice = new SendableChooser();
        smartDashboard.putBoolean("Center",center);
        smartDashboard.putBoolean("left", left);
        smartDashboard.putBoolean("right",right);


        // additions to smart dashboard
        // smartDashboard.putBoolean("Elevator Upper Limit", ); //Upper elevator switch
        // smartDashboard.putBoolean("Elevator Lower Limit", ); //Lower elevator switch
        // smartDashboard.putBoolean("Has Cube", ); //Cube capture identification method in flywheel. Data via sensor
        // smartDashboard.putBoolean("Intake raised", ); //Encoder data from intake motor
        // smartDashboard.putBoolean("Elevator Raising", ) //Whether or not the elevator is raising. Data from elevator motor.
        smartDashboard.putString("Match time: ", Double.toString(Timer.getMatchTime()));
        smartDashboard.putBoolean("Robot reversed", Robot.DriveTrain.isReversed());
        smartDashboard.putData("Autonomous mode chooser", autoChoice);

        DriveEncoders encoders = new DriveEncoders();
        smartDashboard.putNumber("Right Encoder Value", encoders.getRightDistance());
        smartDashboard.putNumber("Left Encoder Value", encoders.getLeftDistance());
        smartDashboard.putNumber("Absolute Encoder Distance", encoders.getLinearDistance());

        //relays current command
        smartDashboard.putData(Scheduler.getInstance());
    }

    public AutoChooser.Position getSelectedPos() {
        if(right = true)return AutoChooser.Position.RIGHT;
        else if(left = true)return AutoChooser.Position.LEFT;
        else if(center = true)return AutoChooser.Position.CENTER;
        else return AutoChooser.Position.UNKNOWN;
    }


}
