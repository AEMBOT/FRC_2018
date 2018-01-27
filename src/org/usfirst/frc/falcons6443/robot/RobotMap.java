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
    public static final int FrontRightVictor = 2; //old bot: 2 new bot : 2
    public static final int FrontLeftVictor = 1; //old bot: 1 new bot : 0
    public static final int BackRightVictor = 3; //old bot: 3 new bot : 3
    public static final int BackLeftVictor = 0; //old bot: 0 new bot : 1

    //drive train encoders
    public static final int LeftEncoderA = -1;
    public static final int LeftEncoderB = -1;
    public static final int RightEncoderA = -1;
    public static final int RightEncoderB = -1;

}