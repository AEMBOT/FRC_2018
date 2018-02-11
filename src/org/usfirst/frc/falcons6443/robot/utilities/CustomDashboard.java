package org.usfirst.frc.falcons6443.robot.utilities;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.falcons6443.robot.Robot;
import org.usfirst.frc.falcons6443.robot.commands.AutoChooser;
import org.usfirst.frc.falcons6443.robot.commands.subcommands.DriveToDistance;
import org.usfirst.frc.falcons6443.robot.subsystems.DriveTrainSystem;


/**
 * Adds the sendable chooser options to the smart dashboard.
 *
 * @author Goirick Saha
 */

public class CustomDashboard {
    private SendableChooser autoChoice;
    private SmartDashboard smartDashboard;


    public CustomDashboard() {
       autoChoice = new SendableChooser();
       autoChoice.addDefault("Unknown", AutoChooser.Position.UNKNOWN);
       autoChoice.addObject("Left", AutoChooser.Position.LEFT);
       autoChoice.addObject("Center", AutoChooser.Position.CENTER);
       autoChoice.addObject("Right", AutoChooser.Position.RIGHT);

      // smartDashboard.putBoolean("Elevator Upper Limit", ); //Upper elevator switch
      // smartDashboard.putBoolean("Elevator Lower Limit", ); //Lower elevator switch
      // smartDashboard.putBoolean("Has Cube", ); //Cube capture identification method in flywheel. Data via sensor
      // smartDashboard.putBoolean("Intake raised", ); //Encoder data from intake motor
      // smartDashboard.putBoolean("Elevator Raising", ) //Whether or not the elevator is raising. Data from elevator motor.
       smartDashboard.putString("Match time: ", Double.toString(Timer.getMatchTime()));
       smartDashboard.putBoolean("Robot reversed", Robot.DriveTrain.isReversed());

       smartDashboard.putData("Autonomous mode chooser", autoChoice);
    }

    public AutoChooser.Position getSelectedPos() {
        return (AutoChooser.Position)autoChoice.getSelected();
    }

}
