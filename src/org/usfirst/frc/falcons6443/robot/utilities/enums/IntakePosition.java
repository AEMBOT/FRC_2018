package org.usfirst.frc.falcons6443.robot.utilities.enums;

public enum IntakePosition {
    IntakeUpPosition("Up position"), IntakeDownPosition("Down position"), IntakeHalfPosition("Half position");
    private String value;

    IntakePosition(String Value) {
        this.value = Value;
    }

    public String getValue() {
        return value;
    }
}
