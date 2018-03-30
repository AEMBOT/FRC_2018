package org.usfirst.frc.falcons6443.robot.subsystems;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.drive.Vector2d;
import org.usfirst.frc.falcons6443.robot.RobotMap;
import org.usfirst.frc.falcons6443.robot.hardware.*;
import org.usfirst.frc.falcons6443.robot.utilities.Logger;
import org.usfirst.frc.falcons6443.robot.utilities.enums.LoggerSystems;

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

    private SpeedControllerGroup leftMotors;
    private SpeedControllerGroup rightMotors;

    private Encoders leftEncoder;
    private Encoders rightEncoder;

    private boolean reversed;
    private static final double WheelDiameter = 6;

    // A [nice] class in the wpilib that provides numerous driving capabilities.
    // Use it whenever you want your robot to move.
    private DifferentialDrive drive;

    /**
     * Constructor for DriveTrainSystem.
     */
    public DriveTrainSystem() {
        leftMotors = new SpeedControllerGroup(new Spark(RobotMap.FrontLeftMotor),
                new Spark(RobotMap.BackLeftMotor));
        rightMotors = new SpeedControllerGroup(new Spark(RobotMap.FrontRightMotor),
                new Spark(RobotMap.BackRightMotor));
        drive = new DifferentialDrive(leftMotors, rightMotors);
        leftMotors.setInverted(true);
        leftEncoder = new Encoders(RobotMap.LeftEncoderA, RobotMap.LeftEncoderB);
        rightEncoder = new Encoders(RobotMap.RightEncoderA, RobotMap.RightEncoderB);
        // the driver station will complain for some reason if this isn't set so it's pretty necessary.
        // [FOR SCIENCE!]
        drive.setSafetyEnabled(false);
        reversed = false;
        drive.setMaxOutput(1);
    }

    @Override
    public void initDefaultCommand() { }

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
     * Toggles the motors to go in reverse.
     */
    public void reverse() {
        reversed = !reversed;
    }

    public boolean isReversed() {
        return reversed;
    }

    public double getLeftDistance(){
        // Encoders clicks per rotation = 850
        Logger.log(LoggerSystems.Drive, "left distance: " + Double.toString(leftEncoder.getDistance() * WheelDiameter * Math.PI / 850));
        return leftEncoder.getDistance() * WheelDiameter * Math.PI / 850; // In inches
    }

    public double getRightDistance(){
        Logger.log(LoggerSystems.Drive, "right distance: " + Double.toString(rightEncoder.getDistance() * WheelDiameter * Math.PI / 850));
        return rightEncoder.getDistance() * WheelDiameter * Math.PI / 850; // In inches
    }

    public double getLinearDistance(){
        Logger.log(LoggerSystems.Drive, "linear distance: " + Double.toString((getLeftDistance() + getRightDistance()) / 2));
        return (getLeftDistance() + getRightDistance()) / 2;
    }

    public void reset(){
        leftEncoder.reset();
        rightEncoder.reset();
        Logger.log(LoggerSystems.Drive, "reset drive encoders");
    }

    //Not sure if good format, but these values are only used for this method
    Vector2d vector = new Vector2d(0,0);
    double differential = 0;
    public void falconDrive(double rotateAxis, double reverseThrottle, double forwardThrottle) {

        //safety settings, to prevent rogue robots
        //vector represents the values to be assigned to the drive train
        vector.x = 0;
        vector.y = 0;
        differential = 0;

        // The Math.abs stuff eliminates erroneous values when the joystick springs back close to 0 when it should be 0
        if (Math.abs(rotateAxis) < .15) {
            differential = 0;
        } else {
            differential = Math.signum(-1 * rotateAxis) * Math.pow(rotateAxis, 2) / 1.8;
        }
        if (forwardThrottle > 0) {//forward code settings TODO modify so user can quickly drive backwards, TODO increase speed cap
            vector.x = forwardThrottle * .5 * (forwardThrottle * .7 + .44f) + (differential + .2 * forwardThrottle);//x is right
            vector.y = forwardThrottle * .5 * (forwardThrottle * .7 + .44f) - (differential - .2 * forwardThrottle);//y is left
        } else if (reverseThrottle > 0) { //reverse
            vector.x = reverseThrottle * -.1 * (reverseThrottle * .7 + .44f) + .8 * (differential + reverseThrottle);//x is right
            vector.y = reverseThrottle * -.1 * (reverseThrottle * .7 + .44f) - .8 * (differential - reverseThrottle);//y is left
            vector.x *= -1;
            vector.y *= -1;
        } else { //code when none of the triggers are pressed, stationary rotation
            if(Math.abs(rotateAxis) > .2){
                vector.x = -rotateAxis/1.68-(.1*Math.signum(rotateAxis));
                vector.y = rotateAxis/1.68+(.1*Math.signum(rotateAxis));
            }
        }

        //set power
        tankDrive(vector.y, vector.x);
    }
}