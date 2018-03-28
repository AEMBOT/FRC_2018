package org.usfirst.frc.falcons6443.robot.utilities.enums;

public enum LoggerSystems {
    All(0, "All"), Drive(1, "Drive"), Elevator(2, "Elevator"), Intake(3, "Intake"),
    Gyro(4, "Gyro"), Auto(5, "Auto");

    private int value;
    private String name;

    LoggerSystems(int Value, String Name) {
        this.value = Value;
        this.name = Name;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}