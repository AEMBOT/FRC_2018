package org.usfirst.frc.falcons6443.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.falcons6443.robot.RobotMap;
import org.usfirst.frc.falcons6443.robot.hardware.IntakeEncoder;
import org.usfirst.frc.falcons6443.robot.utilities.PID;

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

    private PID pid;

    private final double intakeSpeed = .75;
    private final double outputSpeed = .75;

    private final double P = 0;
    private final double I = 0;
    private final double D = 0;
    private final double Eps = 0;

    private final int storagePosition = 0; //ticks
    private final int intakePosition = 0; //ticks

    public FlywheelSystem(){
        leftMotor = new Spark(RobotMap.IntakeLeftMotor);
        rightMotor = new Spark(RobotMap.IntakeRightMotor);
        rotateMotor = new Spark(RobotMap.IntakeRotateMotor);
        //touchSensor = new DigitalInput(RobotMap.IntakeTouchSensor);
        //encoder = new IntakeEncoder();
        pid = new PID (P, I, D, Eps);
        pid.setMaxOutput(1);
        pid.setDoneRange(20); //ticks
        pid.setMinDoneCycles(5);
        rotateMotor.setInverted(true);
    }

    public boolean hasBlock(){
        return touchSensor.get();
    }

    @Override
    protected void initDefaultCommand() {

    }

    public void intake(){
        rightMotor.set(-intakeSpeed);
        leftMotor.set(intakeSpeed);
    }

    public void output(){
        rightMotor.set(outputSpeed);
        leftMotor.set(-outputSpeed);
    }

    public void stop(){
        rightMotor.set(0);
        leftMotor.set(0);
    }

    //Add encoder to know angle/how far to drive motor

    public void setStoragePosition(){
        pid.setDesiredValue(storagePosition);
    }

    public void setIntakePosition(){
        pid.setDesiredValue(intakePosition);
    }

    public void rotateIntake(){
        double power = pid.calcPID(encoder.getDistance());
        if (isAtHeight()){
            power = 0;
        }
        rotateMotor.set(power);
    }

    public void rotateStop(){
        rotateMotor.set(0);
    }

    public void manual(double power){
        rotateMotor.set(power);
    }

    public boolean isAtHeight(){ return pid.isDone(); }

    public void reset(){
        //encoder.reset();
    }
}
