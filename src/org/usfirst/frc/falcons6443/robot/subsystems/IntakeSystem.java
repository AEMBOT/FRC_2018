package org.usfirst.frc.falcons6443.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.falcons6443.robot.RobotMap;
import edu.wpi.first.wpilibj.Timer;
import org.usfirst.frc.falcons6443.robot.utilities.Enums.IntakePosition;

/**
 * Subsystem for the flywheels that push the block out.
 *
 * @author Aleksandras Vidmantas
 */
public class IntakeSystem extends Subsystem {

    private Spark leftMotor;
    private Spark rightMotor;
    private Spark rotateMotor;
    private DigitalInput touchSensor;
    private Timer timer;
    private IntakePosition currentPosition;

    private final double intakeSpeed = .75;
    private final double outputSpeed = .75;

    public IntakeSystem(){
        leftMotor = new Spark(RobotMap.IntakeLeftMotor);
        rightMotor = new Spark(RobotMap.IntakeRightMotor);
        rotateMotor = new Spark(RobotMap.IntakeRotateMotor);
        timer = new Timer();
        //touchSensor = new DigitalInput(RobotMap.IntakeTouchSensor);
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

    public void rotateIntake(IntakePosition position){
        double power = 0;
        double time = 0;
        timer.reset();
        if (position == IntakePosition.IntakeUpPosition && currentPosition != IntakePosition.IntakeUpPosition){
            power = 0.7;
            time = 0.5;
            currentPosition = IntakePosition.IntakeUpPosition;
        }
        if (position == IntakePosition.IntakeDownPosition && currentPosition != IntakePosition.IntakeDownPosition){
            power = -0.5;
            time = 0.4;
            currentPosition = IntakePosition.IntakeDownPosition;
        }
        timer.start();
        while (timer.get() < time){
            rotateMotor.set(power);
        }
        rotateStop();
        timer.stop();
    }

    public void rotateStop(){
        rotateMotor.set(0);
    }

    public void manual(double power){
        rotateMotor.set(power);
    }

}
