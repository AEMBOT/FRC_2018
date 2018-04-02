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

    // PID: proportional–integral–derivative controller
    // more info at https://en.wikipedia.org/wiki/PID_controller
    public static final double KP = 0.04;  //.04
    public static final double KI = 0.001; //.001
    public static final double KD = 0.00;  //.00
    public static final double KF = 0.00;

    private SpeedControllerGroup leftMotors;
    private SpeedControllerGroup rightMotors;

    private DriveEncoders encoders;

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

    public boolean isReversed() {
        return reversed;
    }

    public double getLeftDistance(){
        // Encoder clicks per rotation = 850
        Logger.log(LoggerSystems.Drive, "left distance", Double.toString(encoders.getLeftDistance() * WheelDiameter * Math.PI / 850));
        return encoders.getLeftDistance() * WheelDiameter * Math.PI / 850; // In inches
    }

    public double getRightDistance(){
        Logger.log(LoggerSystems.Drive, "right distance", Double.toString(encoders.getRightDistance() * WheelDiameter * Math.PI / 850));
        return encoders.getRightDistance() * WheelDiameter * Math.PI / 850; // In inches
    }

    public double getLinearDistance(){
        Logger.log(LoggerSystems.Drive, "linear distance", Double.toString((getLeftDistance() + getRightDistance()) / 2));
        return (getLeftDistance() + getRightDistance()) / 2;
    }

    public void reset(){
        encoders.reset();
        Logger.log(LoggerSystems.Drive, "drive encoders", "reset");
    }

    //Not sure if good format, but these values are only used for this method
    Vector2d vector = new Vector2d(0,0);
    double differential = 0;
    public void falconDrive(double leftStickX, double rightTrigger, double leftTrigger) {
        Vector2d vector = new Vector2d(0,0);

        vector.x = 0;
        vector.y = 0;
        double differential = 0;

        if (Math.abs(leftStickX) < .15) {
            differential = 0;
        } else {
            differential = Math.abs(leftStickX);
        }

        if (rightTrigger > 0) {//forward
            vector.x = rightTrigger*.75+.1 - Math.pow(Math.E,-rightTrigger)*.5*differential*Math.signum(leftStickX)*-1;
            vector.y = rightTrigger*.75+.1 - Math.pow(Math.E,-rightTrigger)*.5*differential*Math.signum(leftStickX);
            vector.x *= -1;
            vector.y *= -1;
        } else if (leftTrigger > 0) { //reverse
            vector.x = leftTrigger*.65+.1 - Math.pow(Math.E,-leftTrigger)*.5*differential*Math.signum(leftStickX);
            vector.y = leftTrigger*.65+.1 - Math.pow(Math.E,-leftTrigger)*.5*differential*Math.signum(leftStickX)*-1;
            //vector.x *= -1;
            //vector.y *= -1;ghtTrigger() * 1.2 * (primary.rightTrigger() * .7 + .44f) + (differential + .71 * primary.rightTrigger());//x is right
            //            //drive.y = primary.ri
        } else { //no trigger values, stationary rotation
            //  drive.x = primary.rightTrigger() * 1.2 * (primary.rightTrigger() * .7 + .44f) - (differential - .71 * primary.rightTrigger());//y is left
            // drive.x = 2*differential;
            //drive.y = -2*differential;
            if(Math.abs(leftStickX) > .2){
                vector.x = -leftStickX/1.28-(.1*Math.signum(leftStickX));
                vector.y = leftStickX/1.28+(.1*Math.signum(leftStickX));
            }
        }

        tankDrive(vector.y, vector.x);
    }
}