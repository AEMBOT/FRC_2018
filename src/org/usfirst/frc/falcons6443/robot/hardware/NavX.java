package org.usfirst.frc.falcons6443.robot.hardware;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SPI;

/**
 * Singleton wrapper class for the robot's NavX sensor.
 *
 * @author Shivashriganesh Mahato
 */
public class NavX {

    public static NavX instance;
    // Attitude and Heading Reference System of the NavX
    private AHRS ahrs;

    private NavX() {
        try {
            // Communicate with NavX via the MXP SPI Bus
            ahrs = new AHRS(SPI.Port.kMXP);
        } catch (RuntimeException ex) {
        }
    }

    /**
     * @return the one instance of this class.
     */
    public static NavX get() {
        if (instance == null) {
            instance = new NavX();
        }
        return instance;
    }

    /**
     * @return the AHRS object contained by this class.
     */
    public AHRS ahrs() {
        return ahrs;
    }

    /**
     * @return the yaw read from the NavX.
     */
    public float getYaw() {
        return ahrs.getYaw();
    }

    public void reset() {
        ahrs().reset();
    }

    public boolean isMoving() {
        return ahrs().isMoving();
    }

    /**
     * @return the x displacement of the NavX.
     */
    public float getDisplacementX() {
        return ahrs().getDisplacementX();
    }

    /**
     * @return the y displacement of the NavX.
     */
    public float getDisplacementY() {
        return ahrs().getDisplacementY();
    }

    /**
     * Resets the displacement of the NavX.
     */
    public void resetDisplacement() {
        ahrs().resetDisplacement();
    }

}
