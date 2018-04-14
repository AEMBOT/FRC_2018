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
        // the driver station will complain for some reason if this isn't setSpeed so it's pretty necessary.
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
        Logger.log(LoggerSystems.Drive, "* {" + left + "}[" + right + "]" );
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

    //FIND A BETTER WAY!!!
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
    //Vector2d vector = new Vector2d(0,0);
    //double differential = 0;
    boolean shifted = false;
    public void upShift(){ shifted = true;}
    public void downShift(){shifted = false;}

    public void falconDrive(double leftStickX, double rightTrigger, double leftTrigger) {
        Vector2d vector = new Vector2d(0,0);
        vector.x = 0;
        vector.y = 0;
        double differential;
        double power = 1;

        if (Math.abs(leftStickX) < .15) {
            differential = 0;
        } else {
            differential = Math.abs(leftStickX);
        }
        if(!shifted){ power = .7; }

        if (rightTrigger > 0) {//forward
            vector.x = rightTrigger*power+.1 - Math.pow(Math.E,-rightTrigger)*.5*differential*Math.signum(leftStickX)*-1;
            vector.y = rightTrigger*power+.1 - Math.pow(Math.E,-rightTrigger)*.5*differential*Math.signum(leftStickX);
            vector.x *= -1;
            vector.y *= -1;
        } else if (leftTrigger > 0) { //reverse
            vector.x = leftTrigger*power+.1 - Math.pow(Math.E,-leftTrigger)*.5*differential*Math.signum(leftStickX);
            vector.y = leftTrigger*power+.1 - Math.pow(Math.E,-leftTrigger)*.5*differential*Math.signum(leftStickX)*-1;
          //vector.x *= -1;
          //vector.y *= -1;ghtTrigger() * 1.2 * (primary.rightTrigger() * .7 + .44f) + (differential + .71 * primary.rightTrigger());//x is right
          //drive.y = primary.ri
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