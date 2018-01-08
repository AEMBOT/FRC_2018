package org.usfirst.frc.falcons6443.robot.hardware;

import edu.wpi.first.wpilibj.Joystick;

/**
 * Wrapper for an Xbox 360 Gamepad. Provides clearer interface with button and axis inputs.
 *
 * @author Patrick Higgins
 */
public class Gamepad {

    private Joystick gamepad;

    /**
     * Constructor for Gamepad.
     *
     * @param gamepad the gamepad's joystick.
     */
    public Gamepad(Joystick gamepad) {
        assert gamepad.getIsXbox();
        this.gamepad = gamepad;
        //ideally, make it rumble
    }

    /**
     * @return the Gamepad's joystick in the form of a Joystick object.
     */
    public Joystick getJoystick() {
        return this.gamepad;
    }

    /**
     * @return The value of the X axis of the left stick.
     */
    public double leftStickX() {
        return gamepad.getRawAxis(0);
    }

    /**
     * @return The value of the Y axis of the left stick.
     */
    public double leftStickY() {
        return gamepad.getRawAxis(1);
    }

    /**
     * @return The value of the X axis of the right stick.
     */
    public double rightStickX() {
        return gamepad.getRawAxis(4);
    }

    /**
     * @return The value of the Y axis of the right stick.
     */
    public double rightStickY() {
        return gamepad.getRawAxis(5);
    }

    /**
     * @return The value of the axis for the left trigger.
     */
    public double leftTrigger() {
        return gamepad.getRawAxis(2);
    }

    /**
     * @return The value of the axis for the right trigger.
     */
    public double rightTrigger() {
        return gamepad.getRawAxis(3);
    }

    /**
     * @return the value of the left bumper.
     */
    public boolean leftBumper() {
        return gamepad.getRawButton(5);
    }

    /**
     * @return the value of the right bumper.
     */
    public boolean rightBumper() {
        return gamepad.getRawButton(6);
    }

    /**
     * @return the value of the left joystick button.
     */
    public boolean leftStickButton() {
        return gamepad.getRawButton(9);
    }

    /**
     * @return the value of the right joystick button.
     */
    public boolean rightStickButton() {
        return gamepad.getRawButton(10);
    }

    /**
     * @return the value of the A button.
     */
    public boolean A() {
        return gamepad.getRawButton(1);
    }

    /**
     * @return the value of the B button.
     */
    public boolean B() {
        return gamepad.getRawButton(2);
    }

    /**
     * @return the value of the X button.
     */
    public boolean X() {
        return gamepad.getRawButton(3);
    }

    /**
     * @return the value of the Y button.
     */
    public boolean Y() {
        return gamepad.getRawButton(4);
    }
}
