package org.usfirst.frc.falcons6443.robot.utilities.enums;

public enum LoggerSystems {
    RobotInit(0), Auto(1), Teleop(2), Intake(3), Elevator(4), PDP(5);

    private int value;

    LoggerSystems(int Value) {
        this.value = Value;
    }

    public int getValue() {
        return value;
    }
}