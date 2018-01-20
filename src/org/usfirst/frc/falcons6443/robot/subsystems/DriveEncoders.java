package org.usfirst.frc.falcons6443.robot.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import org.usfirst.frc.falcons6443.robot.Robot;
import org.usfirst.frc.falcons6443.robot.RobotMap;

public class DriveEncoders {

    private Encoder left;
    private Encoder right;

    public DriveEncoders(){
        left = new Encoder(RobotMap.LeftEncoderA, RobotMap.LeftEncoderB);
        right = new Encoder(RobotMap.RightEncoderA, RobotMap.RightEncoderB);
        right.setReverseDirection(true);
    }

    public double getLeftDistance(){
        return left.getDistance();
    }

    public double getRightDistance(){
        return right.getDistance();
    }

    public double getLinearDistance(){
        return (getLeftDistance() + getRightDistance()) / 2;
    }

    public double getLeftSpeed(){
        return left.getRate();
    }

    public double getRightSpeed(){
        return right.getRate();
    }

    public void reset(){
        left.reset();
        right.reset();
    }

}
