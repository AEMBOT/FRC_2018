package org.usfirst.frc.falcons6443.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.falcons6443.robot.RobotMap;
import org.usfirst.frc.falcons6443.robot.Utilities.PID;

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

    private PID pid;

    private double intakeSpeed = .75;
    private double outputSpeed = .75;

    public FlywheelSystem(){
        leftMotor = new Spark(RobotMap.IntakeLeftMotor);
        rightMotor = new Spark(RobotMap.IntakeRightMotor);
        rotateMotor = new Spark(RobotMap.IntakeRotateMotor);
        //touchSensor = new DigitalInput(RobotMap.IntakeTouchSensor);
        pid = new PID (0, 0, 0, 0);
    }

    public boolean hasBlock(){
        return touchSensor.get();
    }

    @Override
    protected void initDefaultCommand() {

    }

    public void intake(){
        rightMotor.set(intakeSpeed*-1);
        leftMotor.set(intakeSpeed);
    }

    public void output(){
        rightMotor.set(outputSpeed);
        leftMotor.set(outputSpeed*-1);
    }

    public void stop(){
        rightMotor.set(0);
        leftMotor.set(0);
    }

    //Add encoder to know angle/how far to drive motor

    public void rotateIntakeUp(){
        rotateMotor.set(.3);
    }

    public void rotateIntakeDown(){
        rotateMotor.set(-.3);
    }

    public void rotateStop(){
        rotateMotor.set(0);
    }

    public void reset(){
        //encoder.reset();
    }
}
