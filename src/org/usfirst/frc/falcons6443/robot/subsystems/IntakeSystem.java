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

    private int desiredEncVal = 0;
    private boolean moveDown = false;

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
        Logger.log(LoggerSystems.Intake, "Intake", "intake");
    }

    public void output(){
        rightMotor.set(outputSpeed);
        leftMotor.set(outputSpeed);
        Logger.log(LoggerSystems.Intake, "Intake", "output");
    }

    public void stop(){
        rightMotor.set(0);
        leftMotor.set(0);
    }

    public void setIntakePosition(IntakePosition position){
        if (position == IntakePosition.IntakeDownPosition && currentPosition != IntakePosition.IntakeDownPosition){
            moveDown = true;
            currentPosition = IntakePosition.IntakeDownPosition;
        }
    }

    //needs to be tested
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
        Logger.log(LoggerSystems.Intake, "Intake encoder", Double.toString(encoder.getDistance()));
        rotateMotor.set(speed);
    }

    public void autoMoveIntake(){
        if (moveDown){
            if (encoder.getDistance() < downEncVal + buffer) {
                rotateStop();
            } else {
                rotateMotor.set(downSpeed);
            }
        } else {
            rotateStop();
        }
    }

    public void readjust() {
        rightMotor.set(intakeSpeed/2);
        leftMotor.set(intakeSpeed);
    }

    public void rotateStop(){
        rotateMotor.set(0);
    }

    public void manual(double power){
        rotateMotor.set(power);
    }

    public void reset(){ encoder.reset(); }
}
   /* public void moveUp(){
        double speed = -0.7;
       if(encoder.getDistance() > upEncVal && speed < 0){
           speed = 0;
        }
        rotateMotor.set(speed);
        System.out.println(encoder.getDistance());
    }

    public void moveDown(){
        double speed = 0.5;
        if(encoder.getDistance() < downEncVal && speed > 0){
            speed = 0;
        }
        rotateMotor.set(speed);
        //System.out.println(encoder.getDistance());
    }*/
   /*public void setIntakePosition(IntakePosition position){
        if (position == IntakePosition.IntakeUpPosition && currentPosition != IntakePosition.IntakeUpPosition){
            desiredEncVal = upEncVal;
            currentPosition = IntakePosition.IntakeUpPosition;
        }
        if (position == IntakePosition.IntakeDownPosition && currentPosition != IntakePosition.IntakeDownPosition){
            desiredEncVal = downEncVal;
            currentPosition = IntakePosition.IntakeDownPosition;
        }
    }*/
