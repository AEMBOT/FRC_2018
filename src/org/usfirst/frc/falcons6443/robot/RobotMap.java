package org.usfirst.frc.falcons6443.robot;

/**
 * RobotMap contains a multitude [Billions and Billions] of constants that define port numbers
 * of various hardware components.
 *
 * @author Christopher Medlin
 */
public class RobotMap {
    //any -1s are not being used and are yet to be specified

    //2018 drive train motors
    public static final int FrontRightMotor = 2;
    public static final int FrontLeftMotor = 0;
    public static final int BackRightMotor = 3;
    public static final int BackLeftMotor = 1;

    //drive train encoders
    public static final int LeftEncoderA = 0;
    public static final int LeftEncoderB = 1;
    public static final int RightEncoderA = 2;
    public static final int RightEncoderB = 3;

    //elevator motors
    public static final int ElevatorMotor = 7;

    //elevator sensors
    public static final int ElevatorTopLimit = -1;
    public static final int ElevatorScaleLimit = 8;
    public static final int ElevatorSwitchLimit = -1;
    public static final int ElevatorBottomLimit = 9;

    //intake motors
    public static final int IntakeLeftMotor = 4;
    public static final int IntakeRightMotor = 5;
    public static final int IntakeRotateMotor = 6;

    //intake sensors
    public static final int IntakeTouchSensor = -1;
}