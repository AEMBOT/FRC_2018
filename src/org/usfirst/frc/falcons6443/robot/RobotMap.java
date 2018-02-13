package org.usfirst.frc.falcons6443.robot;

/**
 * RobotMap contains a multitude [Billions and Billions] of constants that define port numbers
 * of various hardware components.
 *
 * @author Christopher Medlin
 */
public class RobotMap {

    //any -1s are not being used and are yet to be specified

	/* Ok, here's the deal. You're gonna forget to change them back to their appropriate values.
     * You will, trust me. So DON'T FORGET TO SET YOUR PORTS PROPERLY. */

    //drive train motors
    public static final int FrontRightMotor = 2;
    public static final int FrontLeftMotor = 1;
    public static final int BackRightMotor = 3;
    public static final int BackLeftMotor = 0;

    //elevator motors
    public static final int ElevatorMotor = 7;

    //elevator sensors
    public static final int ElevatorEncoderA = 1;
    public static final int ElevatorEncoderB = 2;
    public static final int ElevatorTopLimit = 4;
    public static final int ElevatorBottomLimit = 3;

}