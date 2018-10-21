package org.usfirst.frc.falcons6443.robot;

/**
 * RobotMap contains a multitude [Billions and Billions] of constants that define port numbers
 * of various hardware components.
 *
 * @author Christopher Medlin
 */
public class RobotMap {
    //any -1s are not being used and are yet to be specified

    //drive train motors
    public static final int FrontRightMotor = 2;
    public static final int FrontLeftMotor = 0;
    public static final int BackRightMotor = 3;
    public static final int BackLeftMotor = 1;

    //drive train encoders
    public static final int LeftEncoderA = 0;
    public static final int LeftEncoderB = 1;
    public static final int RightEncoderA = 12;//2
    public static final int RightEncoderB = 13;//3

    //elevator motors
    public static final int ElevatorSingleMotor = 7;
    public static final int ElevatorRedLineMotor1 = 7;
    public static final int ElevatorRedLineMotor2 = 8;
    public static final int ElevatorRedLineMotor3 = 9;
    public static final int ElevatorRedLineMotor4 = 10;

    //elevator sensors
    public static final int ElevatorScaleLimit = 7;
    public static final int ElevatorSwitchLimit = 4;
    public static final int ElevatorBottomLimit = 6;
    public static final int ElevatorEncoderA = 2;//12
    public static final int ElevatorEncoderB = 3;//11

    //flywheel motors
    public static final int FlywheelLeftMotor = 4;
    public static final int FlywheelRightMotor = 5;

    //rotation motor
    public static final int RotationMotor = 6;

    //rotation sensors
    public static final int RotationEncoderA = 8;
    public static final int RotationEncoderB = 9;

    //code settings
    public static final boolean RedLine = true; //sets whether the elevator is one SIM or 4 redLines
    public static final boolean Logger = true; //turns the logger on and off
}