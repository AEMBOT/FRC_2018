package org.usfirst.frc.falcons6443.robot.utilities.drive;

import edu.wpi.first.wpilibj.drive.Vector2d;

/**
 * All profiles will implement this interface.
 * The only requirement is that it returns a calculate vector.
 * This class can be used as long as the robot maintains a WCD.
 * @author Aleks Vidmantas
 */
public interface WCDProfile {

    public Vector2d calculate();
}
