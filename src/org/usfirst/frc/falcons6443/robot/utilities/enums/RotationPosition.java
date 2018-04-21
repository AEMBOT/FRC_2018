package org.usfirst.frc.falcons6443.robot.utilities.enums;

public enum RotationPosition {
    IntakeUpPosition("Up position"), IntakeDownPosition("Down position"), IntakeHalfPosition("Half position");
    private String name;

    RotationPosition(String Name) {
        this.name = Name;
    }

    public String getName() {
        return name;
    }
}
