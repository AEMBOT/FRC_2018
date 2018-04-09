package org.usfirst.frc.falcons6443.robot.hardware.Encoders;

import edu.wpi.first.wpilibj.Encoder;
import org.usfirst.frc.falcons6443.robot.RobotMap;
import org.usfirst.frc.falcons6443.robot.utilities.Logger;
import org.usfirst.frc.falcons6443.robot.utilities.enums.LoggerSystems;

public class ElevatorEncoder {
    private Encoder encoder;

    public ElevatorEncoder(){
        encoder = new Encoder(RobotMap.ElevatorEncoderA, RobotMap.ElevatorEncoderB);
    }

    public double getDistance(){
        Logger.log(LoggerSystems.Elevator,"Encoder", Double.toString(encoder.getRaw()));
        return encoder.getRaw();
    }

    public void reset(){
        encoder.reset();
        Logger.log(LoggerSystems.Elevator,"Encoder", "reset");
    }
}
