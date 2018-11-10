package org.usfirst.frc.falcons6443.robot.subsystems;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.falcons6443.robot.RobotMap;
import org.usfirst.frc.falcons6443.robot.hardware.Encoders;
import org.usfirst.frc.falcons6443.robot.utilities.Logger;
import org.usfirst.frc.falcons6443.robot.utilities.enums.RotationPosition;
import org.usfirst.frc.falcons6443.robot.utilities.enums.LoggerSystems;

public class RotationSystem extends Subsystem {
    public Spark rotateMotor;
    public Encoders encoder;
    public Timer timer;
    public boolean isManual;

    public RotationPosition currentPosition = RotationPosition.IntakeUpPosition;

    public final double upSpeed = 1;
    public final double downSpeed = -.55;
    public final int upEncVal = -40;
    public final int downEncVal = -650;
    public final int midEncVal = -270;
    public final int buffer = 20; //ticks
    public boolean constantPower = false;

    public RotationSystem(){
        rotateMotor = new Spark(RobotMap.RotationMotor);
        encoder = new Encoders(RobotMap.RotationEncoderA, RobotMap.RotationEncoderB);
        timer = new Timer();
        rotateMotor.setInverted(false);
    }

    public void startTimer(){ timer.start(); }
    public void stopTimer(){ timer.stop(); }
    public double getTime(){ return timer.get(); }

    public double getEncoderVal(){ return encoder.getDistance(); }
    public void resetEncoder(){ encoder.reset(); }

    public void manual(double input){ rotateMotor.set(input); }

    public void setManual(boolean on){
        isManual = on;
        Logger.log(LoggerSystems.Rotation, "Set manual " + on);
    }

    public boolean getManual(){
        return isManual;
    }

    @Override
    protected void initDefaultCommand() {    }
}
