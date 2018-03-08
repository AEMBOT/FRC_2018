package org.usfirst.frc.falcons6443.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.falcons6443.robot.RobotMap;
import edu.wpi.first.wpilibj.Timer;
import org.usfirst.frc.falcons6443.robot.hardware.IntakeEncoder;
import org.usfirst.frc.falcons6443.robot.utilities.Enums.IntakePosition;

/**
 * Subsystem for the flywheels that push the block out.
 *
 * @author Aleksandras Vidmantas
 */
public class FlywheelSystem extends Subsystem {

    private Spark leftMotor;
    private Spark rightMotor;
    private Spark rotateMotor;
    private DigitalInput touchSensor;
    private IntakeEncoder encoder;
    private IntakePosition currentPosition;

    private final double intakeSpeed = -.75;
    private final double outputSpeed = .75;
    private final double upSpeed = .5;
    private final double downSpeed = -.5;
    private final int buffer = 20; //ticks
    private final int upEncVal = 1;
    private final int downEncVal = -580;

    private int desiredEncVal = 0;
    private boolean moveDown = false;

    public FlywheelSystem(){
        leftMotor = new Spark(RobotMap.IntakeLeftMotor);
        rightMotor = new Spark(RobotMap.IntakeRightMotor);
        rotateMotor = new Spark(RobotMap.IntakeRotateMotor);
        //touchSensor = new DigitalInput(RobotMap.IntakeTouchSensor);
        encoder = new IntakeEncoder();
        leftMotor.setInverted(true);
        rotateMotor.setInverted(true);
    }

    @Override
    protected void initDefaultCommand() {    }

    public boolean hasBlock(){
        return !touchSensor.get();
    }

    public void intake(){
        rightMotor.set(intakeSpeed);
        leftMotor.set(intakeSpeed);
    }

    public void output(){
        if (currentPosition == IntakePosition.IntakeUpPosition){
            stop();
        } else {
            rightMotor.set(outputSpeed);
            leftMotor.set(outputSpeed);
        }
    }

    public void stop(){
        rightMotor.set(0);
        leftMotor.set(0);
    }

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

    public void setIntakePosition(IntakePosition position){
        if (position == IntakePosition.IntakeDownPosition && currentPosition != IntakePosition.IntakeDownPosition){
            moveDown = true;
        }
    }

    public void moveIntake(boolean up){
        double speed;
        if (up){
            speed = upSpeed;
        } else {
            speed = downSpeed;
        }

        if(encoder.getDistance() < downEncVal && speed < 0){
            speed = 0;
        }

        if(encoder.getDistance() > upEncVal && speed > 0){
            speed = 0;
        }

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

}
