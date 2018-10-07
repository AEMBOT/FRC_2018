package org.usfirst.frc.falcons6443.robot.hardware.Joysticks;

import edu.wpi.first.wpilibj.Joystick;

//NOTE: no rumble
public class Logitech {

    public Joystick controller;

    public Logitech(Joystick controller) {
        this.controller = controller;
    }

    /**
     * @return The value of the X axis of the left stick.
     */
    public double leftStickX() {
        return controller.getRawAxis(0);
    }

    /**
     * @return The value of the Y axis of the left stick.
     */
    public double leftStickY() {
        return controller.getRawAxis(1);
    }

    /**
     * @return The value of the X axis of the right stick.
     */
    public double rightStickX() {
        return controller.getRawAxis(4);
    }

    /**
     * @return The value of the Y axis of the right stick.
     */
    public double rightStickY() {
        return controller.getRawAxis(5);
    }

    /**
     * @return The value of the axis for the left trigger.
     */
    public boolean leftTrigger() {
        return controller.getRawButton(2);
    }

    /**
     * @return The value of the axis for the right trigger.
     */
    public boolean rightTrigger() {
        return controller.getRawButton(3);
    }

    /**
     * @return the value of the left bumper.
     */
    public boolean leftBumper() {
        return controller.getRawButton(5);
    }

    /**
     * @return the value of the right bumper.
     */
    public boolean rightBumper() {
        return controller.getRawButton(6);
    }

    /**
     * @return the value of the left joystick button.
     */
    public boolean leftStickButton() {
        return controller.getRawButton(9);
    }

    /**
     * @return the value of the right joystick button.
     */
    public boolean rightStickButton() {
        return controller.getRawButton(10);
    }

    /**
     * @return the value of the A button.
     */
    public boolean A() {
        return controller.getRawButton(1);
    }

    /**
     * @return the value of the B button.
     */
    public boolean B() {
        return controller.getRawButton(2);
    }

    /**
     * @return the value of the X button.
     */
    public boolean X() {
        return controller.getRawButton(3);
    }

    /**
     * @return the value of the Y button.
     */
    public boolean Y() {
        return controller.getRawButton(4);
    }

    /**
     * @return the value of the back button.
     */
    public boolean back() {
        return controller.getRawButton(7);
    }

    /**
     * @return the value of the start button.
     */
    public boolean start() { return controller.getRawButton(8); }
}
