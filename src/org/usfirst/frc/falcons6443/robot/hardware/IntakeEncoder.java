package org.usfirst.frc.falcons6443.robot.hardware;

import edu.wpi.first.wpilibj.Encoder;

public class IntakeEncoder {
    private Encoder encoder;

    public IntakeEncoder(){
        //encoder = new Encoder(RobotMap.IntakeEncoderA, RobotMap.IntakeEncoderB);
    }

    public double getDistance(){
        return encoder.getRaw();
    }

    public void reset(){
        encoder.reset();
    }
}
