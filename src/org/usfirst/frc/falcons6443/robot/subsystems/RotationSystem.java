package org.usfirst.frc.falcons6443.robot.subsystems;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.falcons6443.robot.RobotMap;
import org.usfirst.frc.falcons6443.robot.hardware.Encoders;
//import org.usfirst.frc.falcons6443.robot.utilities.Logger;
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
    private final double downSpeed = -.45;
    private final int upEncVal = -40;
    private final int downEncVal = -700;
    private final int midEncVal = -270;
    private final int buffer = 20; //ticks

    public RotationSystem(){
        rotateMotor = new Spark(RobotMap.RotationMotor);
        encoder = new Encoders(RobotMap.RotationEncoderA, RobotMap.RotationEncoderB);
        timer = new Timer();
        rotateMotor.setInverted(false);
    }

    public void startTimer(){ timer.start(); }
    public void stopTimer(){ timer.stop(); }
    public double getTime(){ return timer.get(); }

    public double getEncoderVal(){
        return encoder.getDistance();
    }
    public void resetEncoder(){ encoder.reset(); }

    public void stop(){
        //rotateMotor.set(0.17);
        rotateMotor.set(0);
    }

    public void middle(){
        if(encoder.getDistance() > (midEncVal + buffer)){
            rotateMotor.set(downSpeed);
        } else if(encoder.getDistance() < (midEncVal - buffer)){
            rotateMotor.set(upSpeed);
        } else {
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
            speed = 0.1; //0? less strain on the motor
        }
        rotateMotor.set(speed);
        System.out.println("Encoder: " + encoder.getDistance());
    }

    public void down(){
        double speed = downSpeed;
        if (encoder.getDistance() < downEncVal) {
            speed = 0.1;
        }
        rotateMotor.set(speed);
        System.out.println("Encoder: " + encoder.getDistance());
    }

    public void autoMoveIntake(){
        //Look at logs of flywheel flipping back up. does it say at half pos?
        // going up? going down? something else crazy?
        switch (currentPosition){
            case IntakeUpPosition:
                stop();
//                Logger.log(LoggerSystems.Flywheel,"Flywheel at up pos");
                break;
            case IntakeHalfPosition:
            /*  if(encoder.getDistance() > (midEncVal + buffer)){
                    rotateMotor.setSpeed(downSpeed);
                    Logger.log(LoggerSystems.Flywheel,"Flywheel", "going down to half pos");
                } else if(encoder.getDistance() < (midEncVal - buffer)){
                    rotateMotor.setSpeed(upSpeed);
                    Logger.log(LoggerSystems.Flywheel,"Flywheel", "going up to half pos");
                } else {
                    stop();
                    Logger.log(LoggerSystems.Flywheel,"Flywheel", "at half pos");
                }*/
                if(getTime() > 2.5){
                    rotateMotor.set(0);
                } else {
                    rotateMotor.set(downSpeed);
                }
                break;
            case IntakeDownPosition:
              //if (encoder.getDistance() < (downEncVal + buffer)) {
                    stop();
 //                   Logger.log(LoggerSystems.Flywheel,"Flywheel at down pos");
              //} else {
              //    rotateMotor.setSpeed(downSpeed);
              //    Logger.log(LoggerSystems.Flywheel,"Flywheel", "going to down pos");
              //}
                break;
        }
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
