package org.usfirst.frc.falcons6443.robot.hardware.Joysticks;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import org.usfirst.frc.falcons6443.robot.utilities.enums.XboxRumble;

/**
 * Wrapper for an Xbox 360 Xbox. Provides clearer interface with button and axis inputs.
 *
 * @author Patrick Higgins
 */
public class Xbox {

    public XboxController controller;

    /**
     * Constructor for Xbox.
     *
     * @param controller the xbox's joystick.
     */
    public Xbox(XboxController controller) {
        this.controller = controller;
    }

    public XboxController getJoystick(XboxController xbox) {
        return xbox;
    }

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
    public double leftTrigger() {
        return controller.getRawAxis(2);
    }

    /**
     * @return The value of the axis for the right trigger.
     */
    public double rightTrigger() {
        return controller.getRawAxis(3);
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
     * @return the value of the dpad up arrow. TODO: Add real value
     */
    public boolean DpadUp() { return controller.getRawButton(-1); }

    /**
     * @return the value of the dpad down arrow. TODO: Add real value
     */
    public boolean DpadDown() { return controller.getRawButton(-1); }

    /**
     * @return the value of the dpad left arrow. TODO: Add real value
     */
    public boolean DpadLeft() { return controller.getRawButton(-1); }

    /**
     * @return the value of the dpad right arrow. TODO: Add real value
     */
    public boolean DpadRight() { return controller.getRawButton(-1); }

    //PLEASE FIND THE ACTUAL NAME FOR THESE
    public boolean seven() {
        return controller.getRawButton(7);
    }
    public boolean eight() {
        return controller.getRawButton(8);
    }


    public void setRumble(XboxRumble rumble, double value){
        switch (rumble){
            case RumbleLeft:
                controller.setRumble(GenericHID.RumbleType.kLeftRumble, value);
                break;
            case RumbleRight:
                controller.setRumble(GenericHID.RumbleType.kRightRumble, value);
                break;
            case RumbleBoth:
                controller.setRumble(GenericHID.RumbleType.kLeftRumble, value);
                controller.setRumble(GenericHID.RumbleType.kRightRumble, value);
                break;
        }
    }
}
