package org.usfirst.frc.falcons6443.robot.hardware;

import edu.wpi.first.wpilibj.Encoder;
import org.usfirst.frc.falcons6443.robot.RobotMap;

public class DriveEncoders {

    private Encoder left;
    private Encoder right;

    public DriveEncoders(){
        left = new Encoder(RobotMap.LeftEncoderA, RobotMap.LeftEncoderB);
        right = new Encoder(RobotMap.RightEncoderA, RobotMap.RightEncoderB);
        right.setReverseDirection(true);
        left.setReverseDirection(true);
    }

    public int getLeftDistance(){
        return left.getRaw();
    }

    public int getRightDistance(){
        return right.getRaw();
    }

    public void reset(){
        left.reset();
        right.reset();
    }

}
