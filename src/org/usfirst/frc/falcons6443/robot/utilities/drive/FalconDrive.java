package org.usfirst.frc.falcons6443.robot.utilities.drive;

import edu.wpi.first.wpilibj.drive.Vector2d;
import org.usfirst.frc.falcons6443.robot.hardware.joysticks.Xbox;

/**
 * Drive specific code should be separate from other code, as it
 * is a unique system that shouldn't be affected by external systems.
 * @author Aleks Vidmantas
 */
public class FalconDrive implements WCDProfile {

    private double turnAxis;
    private double forwardAxis;
    private double revereseAxis;

    private Vector2d power;
    private Xbox Controller;

    /**
     * @param controller the controller that the class uses to calculate the power vector
     * */
    public FalconDrive(Xbox controller){
        this.turnAxis = 0;
        this.forwardAxis = 0;
        this.revereseAxis = 0;

        this.power = new Vector2d(0,0);
        this.Controller = controller;
    }

    public Vector2d calculate(){
        return power;
    }
}
