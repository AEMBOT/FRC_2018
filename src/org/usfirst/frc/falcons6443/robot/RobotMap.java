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
    public static final int FrontRightMotor = 2; //Yep
    public static final int FrontLeftMotor = 0; //Yep
    public static final int BackRightMotor = 3; //Yep
    public static final int BackLeftMotor = 1; //Yep

    //drive train encoders
    public static final int LeftEncoderA = 0; //No
    public static final int LeftEncoderB = 1; //No
    public static final int RightEncoderA = 12;//Yep...
    public static final int RightEncoderB = 13;//Yep...

    //elevator motors
    public static final int ElevatorSingleMotor = 7; //...
    public static final int ElevatorRedLineMotor1 = 7; //Yep
    public static final int ElevatorRedLineMotor2 = 8; //Yep
    public static final int ElevatorRedLineMotor3 = 9; //Yep
    public static final int ElevatorRedLineMotor4 = 10; //Yep

    //elevator sensors
    public static final int ElevatorScaleLimit = 7; //Yep
    public static final int ElevatorSwitchLimit = 4; //Yep
    public static final int ElevatorBottomLimit = 6; //Yep
    public static final int ElevatorEncoderA = 15; //Yep
    public static final int ElevatorEncoderB = 14; //Yep

    //flywheel motors
    public static final int FlywheelLeftMotor = 4; //Yep
    public static final int FlywheelRightMotor = 5; //Yep

    //rotation motor
    public static final int RotationMotor = 6; //Yep

    //rotation sensors
    public static final int RotationEncoderA = 8; // Yep
    public static final int RotationEncoderB = 9; // Yep

    //code settings
    public static final boolean BackUpDistance = false;
    public static final boolean BackUpAngle = false;
    public static final boolean RedLine = true;
    public static final boolean Logger = true;
}