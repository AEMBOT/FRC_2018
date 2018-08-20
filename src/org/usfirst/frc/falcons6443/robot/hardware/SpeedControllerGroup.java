package org.usfirst.frc.falcons6443.robot.hardware;

import edu.wpi.first.wpilibj.SpeedController;

/**
 * SpeedControllerGroup serves as a wrapper to an array SpeedController objects, enabling
 * easy passing of methods to a group of SpeedControllers.
 *
 * @author Patrick Higgins
 */

public class SpeedControllerGroup implements SpeedController {
    private SpeedController[] controllers;

    /**
     * Constructor for SpeedControllerGroup.
     *
     * @param controllers any number of speed controllers.
     */
    public SpeedControllerGroup(SpeedController ... controllers) {
        this.controllers = controllers;
    }

    @Override
    public void pidWrite(double arg0) {
        for (SpeedController controller : controllers) {
            controller.pidWrite(arg0);
        }
    }

    @Override
    public void disable() {
        for (SpeedController controller : controllers) {
            controller.disable();
        }
    }

    //returns the current setSpeed speed of the controller
    @Override
    public double get() {
        return controllers[0].get();
    }

    @Override
    public boolean getInverted() {
        return controllers[0].getInverted();
    }

    @Override
    public void set(double arg0) {
        for (SpeedController controller : controllers) {
            controller.set(arg0);
        }
    }

    @Override
    public void setInverted(boolean arg0) {
        for (SpeedController controller : controllers) {
            controller.setInverted(arg0);
        }
    }

    @Override
    public void stopMotor() {
        for (SpeedController controller : controllers) {
            controller.stopMotor();
        }
    }

    /**
     * Toggles whether the speed controllers in this group are inverted.
     */
    public void toggleInverted() {
        for (int i = 0; i < controllers.length; i++) {
            SpeedController controller = controllers[i];
            controller.setInverted(!controller.getInverted());
        }
    }
}