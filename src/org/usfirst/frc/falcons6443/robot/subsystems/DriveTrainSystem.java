package org.usfirst.frc.falcons6443.robot.subsystems;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.falcons6443.robot.RobotMap;
import org.usfirst.frc.falcons6443.robot.hardware.SpeedControllerGroup;

/**
 * Subsystem for the robot's drive train.
 * <p>
 * Contains 2 SpeedControllerGroups which are controlled by an instance of RobotDrive.
 * This class is meant to fix some of the shortcomings of the original DriveTrainSystem
 * class as well as make it more simple and readable.
 *
 * @author Christopher Medlin, Ivan Kenevich, Shivashriganesh Mahato
 */
public class DriveTrainSystem extends Subsystem {

    // The constant that determines the maximum curvature at which the robot can move.
    // It is determined by the formula c = e^(-r/w), where
    // r is the radius of the turn and w is the wheelbase (distance between the wheels) of the robot
    // more info in the describtion of drive() method in RobotDrive
    private static final double MAXIMUM_CURVE = 0.36787944; //UPDATE!

    private SpeedControllerGroup leftMotors;
    private SpeedControllerGroup rightMotors;

    public DriveEncoders encoders;

    private boolean reversed;
    private double wheelDiameter = 6; //UPDATE!

    // A [nice] class in the wpilib that provides numerous driving capabilities.
    // Use it whenever you want your robot to move.
    private RobotDrive drive;

    // PID: proportional–integral–derivative controller
    // more info at https://en.wikipedia.org/wiki/PID_controller
    public static final double KP = 0.04;  //.04
    public static final double KI = 0.001; //.001
    public static final double KD = 0.00;  //.00
    public static final double KF = 0.00;

    /**
     * Constructor for DriveTrainSystem.
     */
    public DriveTrainSystem() {
        leftMotors = new SpeedControllerGroup(new VictorSP(RobotMap.FrontLeftVictor),
                new VictorSP(RobotMap.BackLeftVictor));

        rightMotors = new SpeedControllerGroup(new VictorSP(RobotMap.FrontRightVictor),
                new VictorSP(RobotMap.BackRightVictor));

        drive = new RobotDrive(leftMotors, rightMotors);
        encoders = new DriveEncoders();
        // the driver station will complain for some reason if this isn't set so it's pretty necessary.
        // [FOR SCIENCE!]
        drive.setSafetyEnabled(false);

        reversed = false;

        drive.setMaxOutput(1);
    }

    @Override
    public void initDefaultCommand() {
    }

    /**
     * Allows for custom setting of motor power level.
     *
     * @param left  the power for the left motors.
     * @param right the power for the right motors.
     */
    public void tankDrive(double left, double right) {
        if (reversed) {
            drive.tankDrive(-left, -right);
        } else {
            drive.tankDrive(left, right);
        }
    }

    /**
     * Spins the robot.
     * <p>
     * A negative speed spins the robot clockwise and a positive speed
     * spins it counter-clockwise.
     * [I know that some of you math nerds will be annoyed by this choice of sign]
     *
     * @param speed the speed at which the robot spins.
     */
    public void spin(double speed) {
        drive.tankDrive(speed, -speed);
    }

    /**
     * Toggles the motors to go in reverse.
     */
    public void reverse() {
        reversed = !reversed;
    }

    /**
     * @return whether the robot is reversed
     */
    public boolean isReversed() {
        return reversed;
    }

    /**
     * Moves the robot at a specified speed and curvature.
     * <p>
     * @param speed the desired speed.
     * @param curve the desired curvature.
     */
    public void drive(double speed, double curve) {
        if (!reversed) {
            drive.drive(speed, curve * MAXIMUM_CURVE);
        } else {
            drive.drive(-speed, -curve * MAXIMUM_CURVE);
        }
    }


    public double getLeftDistance(){
        // Encoder clicks per rotation = 1024
        return -encoders.getLeftDistance() * wheelDiameter * Math.PI / 1024.0; // In inches
    }

    public double getRightDistance(){
        return encoders.getRightDistance() * wheelDiameter * Math.PI / 1024.0; // In inches
    }
}