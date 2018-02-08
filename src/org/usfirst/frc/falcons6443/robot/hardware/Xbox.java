package org.usfirst.frc.falcons6443.robot.hardware;

import edu.wpi.first.wpilibj.Joystick;

/**
 * Wrapper for an Xbox 360 Xbox. Provides clearer interface with button and axis inputs.
 *
 * @author Patrick Higgins
 */
public class Xbox {

    private Joystick xbox;

    /**
     * Constructor for Xbox.
     *
     * @param xbox the xbox's joystick.
     */
    public Xbox(Joystick xbox) { //TODO: xbox.getIsXbox is now longer a method and needs to be replace
        this.xbox = xbox;
        //ideally, make it rumble
    }

    /**
     * @return the Xbox's joystick in the form of a Joystick object.
     */
    public Joystick getJoystick() {
        return this.xbox;
    }

    /**
     * @return The value of the X axis of the left stick.
     */
    public double leftStickX() {
        return xbox.getRawAxis(0);
    }

    /**
     * @return The value of the Y axis of the left stick.
     */
    public double leftStickY() {
        return xbox.getRawAxis(1);
    }

    /**
     * @return The value of the X axis of the right stick.
     */
    public double rightStickX() {
        return xbox.getRawAxis(4);
    }

    /**
     * @return The value of the Y axis of the right stick.
     */
    public double rightStickY() {
        return xbox.getRawAxis(5);
    }

    /**
     * @return The value of the axis for the left trigger.
     */
    public double leftTrigger() {
        return xbox.getRawAxis(2);
    }

    /**
     * @return The value of the axis for the right trigger.
     */
    public double rightTrigger() {
        return xbox.getRawAxis(3);
    }

    /**
     * @return the value of the left bumper.
     */
    public boolean leftBumper() {
        return xbox.getRawButton(5);
    }

    /**
     * @return the value of the right bumper.
     */
    public boolean rightBumper() {
        return xbox.getRawButton(6);
    }

    /**
     * @return the value of the left joystick button.
     */
    public boolean leftStickButton() {
        return xbox.getRawButton(9);
    }

    /**
     * @return the value of the right joystick button.
     */
    public boolean rightStickButton() {
        return xbox.getRawButton(10);
    }

    /**
     * @return the value of the A button.
     */
    public boolean A() {
        return xbox.getRawButton(1);
    }

    /**
     * @return the value of the B button.
     */
    public boolean B() {
        return xbox.getRawButton(2);
    }

    /**
     * @return the value of the X button.
     */
    public boolean X() {
        return xbox.getRawButton(3);
    }

    /**
     * @return the value of the Y button.
     */
    public boolean Y() {
        return xbox.getRawButton(4);
    }
}
