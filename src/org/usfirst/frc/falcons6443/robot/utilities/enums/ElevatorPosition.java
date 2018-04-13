package org.usfirst.frc.falcons6443.robot.utilities.enums;

public enum ElevatorPosition {
    Exchange("Exchange"), Switch("Switch"), Scale("Scale"), UnderSwitch("Under Switch"), OverSwitch("Over Switch"), Stop("Stop");
    private String name;

    ElevatorPosition(String Name) {
        this.name = Name;
    }

    public String getName() {
        return name;
    }
}
