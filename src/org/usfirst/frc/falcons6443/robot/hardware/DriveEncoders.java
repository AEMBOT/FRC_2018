package org.usfirst.frc.falcons6443.robot.hardware;

import edu.wpi.first.wpilibj.Encoder;
import org.usfirst.frc.falcons6443.robot.Robot;
import org.usfirst.frc.falcons6443.robot.RobotMap;

public class DriveEncoders {

    private Encoder left;
    private Encoder right;

    public DriveEncoders(){
        //left = new Encoder(RobotMap.LeftEncoderA, RobotMap.LeftEncoderB);
        //right = new Encoder(RobotMap.RightEncoderA, RobotMap.RightEncoderB);
        //right.setReverseDirection(true); 
    }

    public double getLeftDistance(){
        return left.getRaw();
    }

    public double getRightDistance(){
        return right.getRaw();
    }

    public double getLinearDistance(){
        return (getLeftDistance() + getRightDistance()) / 2;
    }

    public void reset(){
        left.reset();
        right.reset();
    }

}
