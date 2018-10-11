package org.usfirst.frc.falcons6443.robot.Autonomous;

import org.usfirst.frc.falcons6443.robot.communication.FieldData;
import org.usfirst.frc.falcons6443.robot.subsystems.ElevatorSystem;
import org.usfirst.frc.falcons6443.robot.subsystems.FlywheelSystem;
import org.usfirst.frc.falcons6443.robot.subsystems.RotationSystem;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

public class AutoMain {

    private AutoPath autoPath;
    private static SendableChooser sendable;

    public AutoMain(AutoDrive autoDrive, ElevatorSystem elevator, FlywheelSystem flywheel, RotationSystem rotation) {
        autoPath = new AutoPath(autoDrive, elevator, flywheel, rotation);
        autoChooser();
    }

    public enum Position {
        LEFT, CENTER, RIGHT, DEFAULT
    }

    private void autoChooser() {
        sendable = new SendableChooser();
        sendable.addObject("Left", Position.LEFT);
        sendable.addObject("Center", Position.CENTER);
        sendable.addObject("Right", Position.RIGHT);
        sendable.addDefault("Default", Position.DEFAULT);
    }

    public void runAutoPath() {
        Position position = (Position) sendable.getSelected();

        switch (position) {
            //handles which code to run depending on result of the specified switch/scale
            case LEFT:
                //finalAuto = new auto command;
                break;

            case CENTER:
                if (FieldData.getChar(FieldData.Object.SCALE) == 'L')
                    autoPath.centerToLeftSwitch();
                else
                    autoPath.centerToRightSwitch();
                break;

            case RIGHT:
                //finalAuto = new auto command;
                break;

            case DEFAULT:
                //finalAuto = new auto command;
                break;
        }
    }
}