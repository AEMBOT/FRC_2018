package org.usfirst.frc.falcons6443.robot.subsystems;

import org.usfirst.frc.falcons6443.robot.RobotMap;
import edu.wpi.first.wpilibj.Encoder;

public class ElevatorEncoder {

    private Encoder encoder;

    public ElevatorEncoder(){
        encoder = new Encoder(RobotMap.ElevatorEncoderA, RobotMap.ElevatorEncoderB);
    }

    public double getDistance(){
        return encoder.getDistance();
    }

    public double getSpeed(){
        return encoder.getRate();
    }

    public void reset(){
        encoder.reset();
    }
}
