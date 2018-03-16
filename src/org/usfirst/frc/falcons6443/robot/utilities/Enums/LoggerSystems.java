package org.usfirst.frc.falcons6443.robot.utilities.Enums;

public enum LoggerSystems {
    Drive(0), Auto(1), Intake(2), Elevator(3), PDP(4);

    private int value;

    LoggerSystems(int Value) {
        this.value = Value;
    }

    public int getValue() {
        return value;
    }
}