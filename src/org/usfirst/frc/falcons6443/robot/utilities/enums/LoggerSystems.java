package org.usfirst.frc.falcons6443.robot.utilities.enums;

public enum LoggerSystems {
    Master(0), Drive(1), Elevator(2), Intake(3), Gyro(4), Auto(5);

    private int value;

    LoggerSystems(int Value) {
        this.value = Value;
    }

    public int getValue() {
        return value;
    }
}