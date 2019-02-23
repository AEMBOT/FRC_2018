package org.usfirst.frc.falcons6443.robot.hardware.joysticks;

import edu.wpi.first.wpilibj.Joystick;

/**
 * Wrapper class for a virtual joystick controlled by mixer
 */
public class vJoy {
    public Joystick controller;

    public vJoy(Joystick controller){this.controller = controller;}

    /**
     * Get the value of the X axis on the joystick
     * @return X axis on virtual joystick for turning
     */
    public double getX() {return controller.getRawAxis(0);}

    /**
     * Get the value of the Y axis on the joystick
     * @return Y axis on virtual joystick for driving
     */
    public double getY() {return controller.getRawAxis(1);}
}
