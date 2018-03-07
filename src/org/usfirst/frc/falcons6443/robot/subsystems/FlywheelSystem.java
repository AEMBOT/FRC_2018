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

    private final double intakeSpeed = .75;
    private final double outputSpeed = .75;
    private final double upSpeed = .5;
    private final double downSpeed = .5;
    private final int buffer = 20; //ticks
    private final int upEncVal = 500;
    private final int downEncVal = 150;

    private int desiredEncVal = 0;

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
        rightMotor.set(-intakeSpeed);
        leftMotor.set(-intakeSpeed);
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

    public void setIntakePosition(IntakePosition position){
        if (position == IntakePosition.IntakeUpPosition && currentPosition != IntakePosition.IntakeUpPosition){
            desiredEncVal = upEncVal;
            currentPosition = IntakePosition.IntakeUpPosition;
        }
        if (position == IntakePosition.IntakeDownPosition && currentPosition != IntakePosition.IntakeDownPosition){
            desiredEncVal = downEncVal;
            currentPosition = IntakePosition.IntakeDownPosition;
        }
    }

    public void moveIntake(){
        double power = 0;
        if (desiredEncVal == upEncVal) {
            power = upSpeed;
        } else if (desiredEncVal == downEncVal){
            power = downSpeed;
        }
        if ((encoder.getDistance() + buffer) < desiredEncVal && (encoder.getDistance() - buffer) > desiredEncVal){
            rotateMotor.set(power);
        } else {
            rotateStop();
        }
    }

    public void rotateStop(){
        rotateMotor.set(0);
    }

    public void manual(double power){
        rotateMotor.set(power);
    }

}
