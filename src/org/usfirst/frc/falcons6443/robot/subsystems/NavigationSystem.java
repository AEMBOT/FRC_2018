package org.usfirst.frc.falcons6443.robot.subsystems;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.falcons6443.robot.hardware.*;

/**
 * Subsystem containing the NavX sensor as well as ultrasonic sensors.
 *
 * @author Shivashriganesh Mahato, Christopher Medlin, Ivan Kenevich
 */
public class NavigationSystem extends Subsystem {
    public final double PingTimeDelay = 0.05;

    public NavX navx;
   // private PIDController pid;
    private boolean isPIDInitialized;
    private UltrasonicSensor uSensor;

    /**
     * Constructor for NavigationSystem.
     */
    public NavigationSystem() {
        navx = NavX.get();
        uSensor = new UltrasonicSensor(116);
    }

    @Override
    public void initDefaultCommand() {
    }

    public void reset() {
        navx.ahrs().reset();
    }

    public boolean isMoving() {
        return navx.ahrs().isMoving();
    }

    /**
     * @return the x displacement of the NavX.
     */
    public float getDisplacementX() {
        return navx.ahrs().getDisplacementX();
    }

    /**
     * @return the y displacement of the NavX.
     */
    public float getDisplacementY() {
        return navx.ahrs().getDisplacementY();
    }

    /**
     * Resets the displacement of the NavX.
     */
    public void resetDisplacement() {
        navx.ahrs().resetDisplacement();
    }

    /**
     * @return the yaw of the robot.
     */
    public float getYaw() {
        return navx.getYaw();
    }

    public double read() {
        uSensor.ping();
        return uSensor.read();
    }

    public double readInches() {
        uSensor.ping();
        return uSensor.readInches();
    }
}
