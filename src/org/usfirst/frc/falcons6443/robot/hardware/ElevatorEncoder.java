package org.usfirst.frc.falcons6443.robot.hardware;

import org.usfirst.frc.falcons6443.robot.RobotMap;
import edu.wpi.first.wpilibj.Encoder;

public class ElevatorEncoder {

    private Encoder encoder;

    //public ElevatorEncoder(){
   //     encoder = new Encoder(RobotMap.ElevatorEncoderA, RobotMap.ElevatorEncoderB);
    //}

    public int getTicks(){
        return encoder.getRaw();
    }

    public void reset(){
        encoder.reset();
    }
}
