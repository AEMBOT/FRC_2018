package org.usfirst.frc.falcons6443.robot.autonomous;

import org.usfirst.frc.falcons6443.robot.communication.FieldData;
import org.usfirst.frc.falcons6443.robot.subsystems.ElevatorSystem;
import org.usfirst.frc.falcons6443.robot.subsystems.FlywheelSystem;
import org.usfirst.frc.falcons6443.robot.subsystems.RotationSystem;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/*
 * The main auto class holding the logic to select the auto path and helps pass instances of
 * subsystems and autoDrive to the autoPaths class.
 *
 * Use runAutoPath() in Robot.java, function AutonomousInit()
 */
public class AutoMain {

    private AutoPaths autoPaths;
    private static SendableChooser sendable;

    public AutoMain(AutoDrive autoDrive, ElevatorSystem elevator, FlywheelSystem flywheel, RotationSystem rotation) {
        autoPaths = new AutoPaths(autoDrive, elevator, flywheel, rotation);
        autoChooser();
    }

    //enums for auto selection off the dashboard
    public enum Position {
        LEFT, CENTER, RIGHT, DEFAULT
    }

    //sets up the dashboard for auto path choices
    //Test and see where these should be created!!
    private void autoChooser() {
        sendable = new SendableChooser();
        sendable.addObject("Left", Position.LEFT);
        sendable.addObject("Center", Position.CENTER);
        sendable.addObject("Right", Position.RIGHT);
        sendable.addDefault("Line", Position.DEFAULT);
        SmartDashboard.putData("Auto Path", sendable);
    }

    //runs the auto path selected in the dashboard
    public void runAutoPath() {
        Position position = (Position) sendable.getSelected();

        switch (position) {
            //handles which code to run depending on result of the specified switch/scale
            case LEFT:
                //enter left auto path
                break;

            case CENTER:
                if (FieldData.getChar(FieldData.Object.SCALE) == 'L')
                    autoPaths.centerToLeftSwitch();
                else
                    autoPaths.centerToRightSwitch();
                break;

            case RIGHT:
                //enter right auto path
                break;

            case DEFAULT:
                autoPaths.driveToLine();
                break;
        }
    }
}