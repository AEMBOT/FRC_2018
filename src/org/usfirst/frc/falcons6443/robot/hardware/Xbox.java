package org.usfirst.frc.falcons6443.robot.hardware;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;

/**
 * Wrapper for an Xbox 360 Xbox. Provides clearer interface with button and axis inputs.
 *
 * @author Patrick Higgins
 */
public class Xbox {

    public XboxController primary;
    public XboxController secondary;

    /**
     * Constructor for Xbox.
     *
     * @param primary the xbox's joystick.
     */
    public Xbox(XboxController primary, XboxController secondary) {
        this.primary = primary;
        this.secondary = secondary;
        //ideally, make it rumble
    }

    public XboxController getJoystick(XboxController xbox) {
        return xbox;
    }

    public double leftStickX(XboxController xbox) {
        return xbox.getRawAxis(0);
    }

    /**
     * @return The value of the Y axis of the left stick.
     */
    public double leftStickY(XboxController xbox) {
        return xbox.getRawAxis(1);
    }

    /**
     * @return The value of the X axis of the right stick.
     */
    public double rightStickX(XboxController xbox) {
        return xbox.getRawAxis(4);
    }

    /**
     * @return The value of the Y axis of the right stick.
     */
    public double rightStickY(XboxController xbox) {
        return xbox.getRawAxis(5);
    }

    /**
     * @return The value of the axis for the left trigger.
     */
    public double leftTrigger(XboxController xbox) {
        return xbox.getRawAxis(2);
    }

    /**
     * @return The value of the axis for the right trigger.
     */
    public double rightTrigger(XboxController xbox) {
        return xbox.getRawAxis(3);
    }

    /**
     * @return the value of the left bumper.
     */
    public boolean leftBumper(XboxController xbox) {
        return xbox.getRawButton(5);
    }

    /**
     * @return the value of the right bumper.
     */
    public boolean rightBumper(XboxController xbox) {
        return xbox.getRawButton(6);
    }

    /**
     * @return the value of the left joystick button.
     */
    public boolean leftStickButton(XboxController xbox) {
        return xbox.getRawButton(9);
    }

    /**
     * @return the value of the right joystick button.
     */
    public boolean rightStickButton(XboxController xbox) {
        return xbox.getRawButton(10);
    }

    /**
     * @return the value of the A button.
     */
    public boolean A(XboxController xbox) {
        return xbox.getRawButton(1);
    }

    /**
     * @return the value of the B button.
     */
    public boolean B(XboxController xbox) {
        return xbox.getRawButton(2);
    }

    /**
     * @return the value of the X button.
     */
    public boolean X(XboxController xbox) {
        return xbox.getRawButton(3);
    }

    /**
     * @return the value of the Y button.
     */
    public boolean Y(XboxController xbox) {
        return xbox.getRawButton(4);
    }
}
