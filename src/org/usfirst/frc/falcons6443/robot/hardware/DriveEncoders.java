package org.usfirst.frc.falcons6443.robot.hardware;

import edu.wpi.first.wpilibj.Encoder;
import org.usfirst.frc.falcons6443.robot.RobotMap;
import org.usfirst.frc.falcons6443.robot.utilities.enums.LoggerSystems;
import org.usfirst.frc.falcons6443.robot.utilities.Logger;

public class DriveEncoders {

    private Encoder left;
    private Encoder right;

    public DriveEncoders(){
        left = new Encoder(RobotMap.LeftEncoderA, RobotMap.LeftEncoderB);
        right = new Encoder(RobotMap.RightEncoderA, RobotMap.RightEncoderB);
        right.setReverseDirection(true);
        left.setReverseDirection(false);
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
