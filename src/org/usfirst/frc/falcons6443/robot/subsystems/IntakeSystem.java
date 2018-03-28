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

    private final double intakeSpeed = -.75;
    private final double outputSpeed = .75;
    private final double upSpeed = -0.7;
    private final double downSpeed = 0.5;
    private final int buffer = 20; //ticks
    private final int upEncVal = 0;
    private final int downEncVal = -550;
    private final int midEncVal = -230;

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
                speed = 0;
            }
        } else {
            speed = downSpeed;
            if (encoder.getDistance() < downEncVal) {
                speed = 0;
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
                if (encoder.getDistance() < midEncVal + buffer) {
                    rotateStop();
                    Logger.log(LoggerSystems.Intake,"Intake", "at half pos");
                } else {
                    rotateMotor.set(downSpeed);
                    Logger.log(LoggerSystems.Intake,"Intake", "going to half pos");
                }
                break;
            case IntakeDownPosition:
                if (encoder.getDistance() < downEncVal + buffer) {
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
        rotateMotor.set(0);
    }

    public void manual(double power){
        rotateMotor.set(power);
        Logger.log(LoggerSystems.Intake,"Intake", "manual");
    }

    public void rotateMid(){
        if(encoder.getDistance()> -210){
            rotateMotor.set(downSpeed);
        } else if(encoder.getDistance() < -250){
            rotateMotor.set(upSpeed);
        } else {
            rotateStop();
        }
    }

    public void reset(){ encoder.reset(); }
}