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
    private Spark rotateMotor;
    private Encoders encoder;
    private Timer timer;
    private boolean isManual;

    private RotationPosition currentPosition = RotationPosition.IntakeUpPosition;

    private final double upSpeed = 1;
    private final double downSpeed = -.55;
    private final int upEncVal = -40;
    private final int downEncVal = -650;
    private final int midEncVal = -270;
    private final int buffer = 20; //ticks
    private boolean constantPower = false;

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

    public void stop(){
        if(constantPower)
            rotateMotor.set(0.17);
        else rotateMotor.set(0);
    }

    public void middle(){
        if(encoder.getDistance() > (midEncVal + buffer)){
            rotateMotor.set(downSpeed);
        } else if(encoder.getDistance() < (midEncVal - buffer)){
            rotateMotor.set(upSpeed);
        } else {
            constantPower = true;
            stop();
        }
    }

    public void setIntakePosition(RotationPosition intakeState){
 //       Logger.log(LoggerSystems.Rotation,"Set flywheel position: " + intakeState.getName());
        switch (intakeState){
        case IntakeUpPosition:
            currentPosition = RotationPosition.IntakeUpPosition;
            break;
        case IntakeDownPosition:
            currentPosition = RotationPosition.IntakeDownPosition;
            break;
        case IntakeHalfPosition:
            currentPosition = RotationPosition.IntakeHalfPosition;
            break;
        }
    }

    public void up() {
        double speed = upSpeed;
        if (encoder.getDistance() > upEncVal) {
              speed = 0;
              constantPower = false;
        } else constantPower = true;
        rotateMotor.set(speed);
        System.out.println("Encoder: " + encoder.getDistance());
    }

    public void down(){
        double speed = downSpeed;
        if (encoder.getDistance() < downEncVal) {
            speed = 0;
            constantPower = false;
        } else constantPower = true;
        rotateMotor.set(speed);
        System.out.println("Encoder: " + encoder.getDistance());
    }

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
