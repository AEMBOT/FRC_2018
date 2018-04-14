package org.usfirst.frc.falcons6443.robot.subsystems;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.falcons6443.robot.RobotMap;
import org.usfirst.frc.falcons6443.robot.hardware.Encoders;
import org.usfirst.frc.falcons6443.robot.utilities.Logger;
import org.usfirst.frc.falcons6443.robot.utilities.enums.*;

/**
 * Subsystem for the flywheels that push the block out.
 *
 * @author Aleksandras Vidmantas
 */
public class IntakeSystem extends Subsystem {

    private Spark leftMotor;
    private Spark rightMotor;
    private Spark rotateMotor;
    private Encoders encoder;
    private IntakePosition currentPosition = IntakePosition.IntakeUpPosition;
    private Timer timer;

    private final double intakeSpeed = 0.9;
    private final double outputSpeed = 0.75;
    private final double outputSlowSpeed = 0.5;
    private final double upSpeed = 1;
    private final double downSpeed = -.5;
    private final int upEncVal = -40;
    private final int downEncVal = -700;
    private final int midEncVal = -270;
    private final int buffer = 20; //ticks
    private boolean kill; //kill stops the constant slow speed
    public boolean m_manual;

    public IntakeSystem(){
        leftMotor = new Spark(RobotMap.IntakeLeftMotor);
        rightMotor = new Spark(RobotMap.IntakeRightMotor);
        rotateMotor = new Spark(RobotMap.IntakeRotateMotor);
        encoder = new Encoders(RobotMap.IntakeEncoderA, RobotMap.IntakeEncoderB);
        timer = new Timer();
        leftMotor.setInverted(true);
        rotateMotor.setInverted(true);
        kill = false;
    }

    @Override
    protected void initDefaultCommand() {    }

    public void startTimer(){ timer.start(); }
    public void stopTimer(){ timer.stop(); }
    public double getTime(){ return timer.get(); }

    public void toggleKill(){
        kill =! kill;
    }

    public void teleop(double manual, boolean[] buttons){
        if(buttons[0]){
            m_manual = false;
            moveIntake(true);
        } else if(buttons[1]){
            m_manual = false;
            moveIntake(false);
        } else if(buttons[2]){
            m_manual = false;
            rotateMid();
        } else if (!m_manual){
            rotateStop();
        }

        if(Math.abs(manual) > 0.2){
            m_manual = true;
            rotateMotor.set(manual);
        } else if(m_manual){
            rotateStop();
        }

        if(buttons[3]){
            intake();
        } else if(buttons[4]){
            output();
        } else {
            stop();
        }
    }

    public void intake(){
        rightMotor.set(intakeSpeed);
        leftMotor.set(intakeSpeed);
    }

    public double getIntekeEnc(){
        return encoder.getDistance();
    }

    public void output(){
        rightMotor.set(-outputSpeed);
        leftMotor.set(-outputSpeed);
    }

    public void slowOutput(){
        rightMotor.set(-outputSlowSpeed);
        leftMotor.set(-outputSlowSpeed);
    }

    public void stop(){
        if(kill){
            rightMotor.set(0);
            leftMotor.set(0);
        } else {
        rightMotor.set(0.22);
        leftMotor.set(0.26); //because the left motor doesn't spin as well
        }
    }

    public void setIntakePosition(IntakePosition intakeState){
        Logger.log(LoggerSystems.Intake,"Set intake position: " + intakeState.getName());
        switch (intakeState){
            case IntakeUpPosition:
                currentPosition = IntakePosition.IntakeUpPosition;
                break;
            case IntakeDownPosition:
                currentPosition = IntakePosition.IntakeDownPosition;
                break;
            case IntakeHalfPosition:
                currentPosition = IntakePosition.IntakeHalfPosition;
                break;
        }
    }

    public void moveIntake(boolean up) {
        double speed;
        if (up) {
            speed = upSpeed;
            if (encoder.getDistance() > upEncVal) {
                speed = 0.1; //0? less strain on the motor

            }
        } else {
            speed = downSpeed;
            if (encoder.getDistance() < downEncVal) {
                speed = 0.1;
            }
        }
        rotateMotor.set(speed);
    }

    public void autoMoveIntake(){
        //Look at logs of intake flipping back up. does it say at half pos?
        // going up? going down? something else crazy?
        switch (currentPosition){
            case IntakeUpPosition:
                rotateStop();
                Logger.log(LoggerSystems.Intake,"Intake at up pos");
                break;
            case IntakeHalfPosition:
              /*  if(encoder.getDistance() > (midEncVal + buffer)){
                    rotateMotor.setSpeed(downSpeed);
                    Logger.log(LoggerSystems.Intake,"Intake", "going down to half pos");
               } else if(encoder.getDistance() < (midEncVal - buffer)){
                    rotateMotor.setSpeed(upSpeed);
                    Logger.log(LoggerSystems.Intake,"Intake", "going up to half pos");
                } else {
                    rotateStop();
                    Logger.log(LoggerSystems.Intake,"Intake", "at half pos");
                }*/

                if(getTime() > 2.5){
                    rotateMotor.set(0);
                } else {
                    rotateMotor.set(downSpeed);
                }
                break;
            case IntakeDownPosition:
                //if (encoder.getDistance() < (downEncVal + buffer)) {
                    rotateStop();
                    Logger.log(LoggerSystems.Intake,"Intake at down pos");
                //} else {
                //    rotateMotor.setSpeed(downSpeed);
                //    Logger.log(LoggerSystems.Intake,"Intake", "going to down pos");
                //}
            break;
        }
    }

    public void rotateStop(){
        rotateMotor.set(0.1);
    }

    public void rotateMid(){
        if(encoder.getDistance() > (midEncVal + buffer)){
            rotateMotor.set(downSpeed);
        } else if(encoder.getDistance() < (midEncVal - buffer)){
            rotateMotor.set(upSpeed);
        } else {
            rotateStop();
        }
    }

    public void resetEnc(){
        encoder.reset();
    }
}