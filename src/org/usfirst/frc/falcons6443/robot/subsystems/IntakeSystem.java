package org.usfirst.frc.falcons6443.robot.subsystems;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.falcons6443.robot.RobotMap;
import org.usfirst.frc.falcons6443.robot.hardware.IntakeEncoder;
import org.usfirst.frc.falcons6443.robot.utilities.Logger;
import org.usfirst.frc.falcons6443.robot.utilities.enums.IntakePosition;
import org.usfirst.frc.falcons6443.robot.utilities.enums.LoggerSystems;

/**
 * Subsystem for the flywheels that push the block out.
 *
 * @author Aleksandras Vidmantas
 */
public class IntakeSystem extends Subsystem {

    private Spark leftMotor;
    private Spark rightMotor;
    private Spark rotateMotor;
    private IntakeEncoder encoder;
    private IntakePosition currentPosition = IntakePosition.IntakeUpPosition;

    private final double intakeSpeed = 0.75;
    private final double outputSpeed = 0.75;
    private final double upSpeed = .75;
    private final double downSpeed = -.3;
    private final int upEncVal = 0;
    private final int downEncVal = -700;
    private final int midEncVal = -230;
    private final int buffer = 20; //ticks

    public IntakeSystem(){
        leftMotor = new Spark(RobotMap.IntakeLeftMotor);
        rightMotor = new Spark(RobotMap.IntakeRightMotor);
        rotateMotor = new Spark(RobotMap.IntakeRotateMotor);
        encoder = new IntakeEncoder();
        leftMotor.setInverted(true);
        rotateMotor.setInverted(true);
    }

    @Override
    protected void initDefaultCommand() {    }

    public void intake(){
        rightMotor.set(intakeSpeed);
        leftMotor.set(intakeSpeed);
    }

    public void output(){
        rightMotor.set(outputSpeed);
        leftMotor.set(outputSpeed);
    }

    public void stop(){
        rightMotor.set(0);
        leftMotor.set(0);
    }

    public void setIntakePosition(IntakePosition intakeState){
        Logger.log(LoggerSystems.Intake,"Set intake position", intakeState.getValue());
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
        switch (currentPosition){
            case IntakeUpPosition:
                rotateStop();
                Logger.log(LoggerSystems.Intake,"Intake", "at up pos");
                break;
            case IntakeHalfPosition:
                if(encoder.getDistance() > (midEncVal + buffer)){
                    rotateMotor.set(downSpeed);
                    Logger.log(LoggerSystems.Intake,"Intake", "going down to half pos");
                } else if(encoder.getDistance() < (midEncVal - buffer)){
                    rotateMotor.set(upSpeed);
                    Logger.log(LoggerSystems.Intake,"Intake", "going up to half pos");
                } else {
                    rotateStop();
                    Logger.log(LoggerSystems.Intake,"Intake", "at half pos");
                }
                break;
            case IntakeDownPosition:
                if (encoder.getDistance() < (downEncVal + buffer)) {
                    rotateStop();
                    Logger.log(LoggerSystems.Intake,"Intake", "at down pos");
                } else {
                    rotateMotor.set(downSpeed);
                    Logger.log(LoggerSystems.Intake,"Intake", "going to down pos");
                }
            break;
        }
    }

    public void readjust() {
        rightMotor.set(intakeSpeed/2);
        leftMotor.set(intakeSpeed);
        Logger.log(LoggerSystems.Intake,"Intake", "readjust");
    }

    public void rotateStop(){
        rotateMotor.set(0.1);
    }

    public void manual(double power){
        rotateMotor.set(power);
        Logger.log(LoggerSystems.Intake,"Intake", "manual");
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

    public void reset(){ encoder.reset(); }
}