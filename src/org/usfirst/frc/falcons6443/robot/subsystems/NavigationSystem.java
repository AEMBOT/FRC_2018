package org.usfirst.frc.falcons6443.robot.subsystems;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.falcons6443.robot.hardware.NavX;
import org.usfirst.frc.falcons6443.robot.hardware.UltrasonicSensor;

/**
 * Subsystem containing the NavX sensor as well as ultrasonic sensors.
 *
 * @author Shivashriganesh Mahato, Christopher Medlin, Ivan Kenevich
 */
public class NavigationSystem extends Subsystem {
    public final double PingTimeDelay = 0.05;

    public NavX navx;
    private PIDController pid;
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

    /**
     * Initializes a PID controller with the NavX as it's PIDSource.
     *
     * @param output the PIDOutput for the PIDController to write to.
     */
    public void initPIDController(PIDOutput output) {
        isPIDInitialized = true;
        pid = new PIDController(DriveTrainSystem.KP,
                DriveTrainSystem.KI,
                DriveTrainSystem.KD,
                DriveTrainSystem.KF,
                navx.ahrs(), output);
        pid.setInputRange(-180.0f, 180.0f);
        pid.setOutputRange(-0.04, 0.04);
        pid.setAbsoluteTolerance(2.0f);
        pid.setContinuous(true);
        pid.enable();
    }

    public boolean onTarget() {
        return pid.onTarget();
    }

    public void pidSetEnabled(boolean enabled) {
        if (enabled) {
            pid.enable();
        } else {
            pid.disable();
        }
    }

    /**
     * Sets the setpoint of the PID controller.
     *
     * @param setpoint the desired setpoint.
     */
    public void pidSetPoint(float setpoint) {
        if (isPIDInitialized) {
            pid.setSetpoint(setpoint);
        }
    }

    public void freePID() {
        pid.disable();
        pid.free();
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
