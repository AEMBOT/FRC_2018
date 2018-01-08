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

    //drive train victors
    public static final int FrontRightVictor = 2;
    public static final int FrontLeftVictor = 1;
    public static final int BackRightVictor = 3;
    public static final int BackLeftVictor = 0;

    //solenoid
    public static final int GearHolderSolenoidOpen = 0;
    public static final int GearHolderSolenoidClose = 1;

    //ultrasonics
    public static final int FrontUltrasonic = -1;
    public static final int LeftUltrasonic = -1;
    public static final int RightUltrasonic = -1;
    public static final int BackUltrasonic = -1;

    //rope climber
    public static final int RopeClimberSpark = 4;

}