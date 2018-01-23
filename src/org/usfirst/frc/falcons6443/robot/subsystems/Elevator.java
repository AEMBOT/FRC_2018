package org.usfirst.frc.falcons6443.robot.subsystems;

import edu.wpi.first.wpilibj.Victor;
import org.usfirst.frc.falcons6443.robot.RobotMap;

public class Elevator {

    private static final double transferHeight = 0;
    private static final double switchHeight = 0;
    private static final double scaleHeight = 0;

    private Victor motor;
    private TouchSensor touchSensor;

    public Elevator (){
        motor = new Victor (RobotMap.ElevatorVictor);
        touchSensor = new TouchSensor (RobotMap.ElevatorToughSensor);
    }

    public boolean hasCube(){
        if (touchSensor) {
            return true;
        } else {
            return false;
        }
    }
}
//know predetermined height vals
//know current position
//know if it has cube
//know where to go (buttons, auto code, etc)
// poss. PID to smooth motion
