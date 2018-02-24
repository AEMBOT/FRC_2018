package org.usfirst.frc.falcons6443.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.falcons6443.robot.RobotMap;
import edu.wpi.first.wpilibj.Timer;
import org.usfirst.frc.falcons6443.robot.utilities.Enums;

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
    private Timer timer;
    private Enums currentPosition;

    private final double intakeSpeed = .75;
    private final double outputSpeed = .75;

    public FlywheelSystem(){
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
        if (currentPosition == Enums.IntakeUpPosition){
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

    public void rotateIntake(Enums position){
        double power = 0;
        double time = 0;
        timer.reset();
        if (position == Enums.IntakeUpPosition && currentPosition != Enums.IntakeUpPosition){
            power = 0.7;
            time = 0.5;
            currentPosition = Enums.IntakeUpPosition;
        }
        if (position == Enums.IntakeDownPosition && currentPosition != Enums.IntakeDownPosition){
            power = -0.5;
            time = 0.4;
            currentPosition = Enums.IntakeDownPosition;
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
