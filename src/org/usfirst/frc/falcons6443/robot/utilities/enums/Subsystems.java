package org.usfirst.frc.falcons6443.robot.utilities.enums;

public enum Subsystems {
    Drive(0), Elevator(1), Flywheels(2), Rotate(3);
    private int value;

    Subsystems(int Value){
        value = Value;
    }

    public int getValue(){
        return value;
    }
}
