package org.usfirst.frc.falcons6443.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.falcons6443.robot.RobotMap;

/**
 * Subsystem for the flywheels that push the block out.
 *
 * @author Aleksandras Vidmantas
 */
public class FlywheelSystem extends Subsystem {

    private Spark leftMotor;
    private Spark rightMotor;
    private DigitalInput touchSensor;

    private double intakeSpeed = .4;
    private double outputSpeed = .3;

    public FlywheelSystem(){
        leftMotor = new Spark(RobotMap.IntakeMotorLeft);
        rightMotor = new Spark(RobotMap.IntakeMotorRight);
        touchSensor = new DigitalInput(RobotMap.IntakeTouchSensor);
    }

    public boolean hasBlock(){
        return touchSensor.get();
    }

    @Override
    protected void initDefaultCommand() {

    }

    public void intake(){
        rightMotor.set(intakeSpeed);
        leftMotor.set(intakeSpeed);
    }

    public void output(){
        rightMotor.set(outputSpeed*-1);
        leftMotor.set(outputSpeed*-1);
    }

    public void stop(){
        rightMotor.set(0);
        leftMotor.set(0);
    }
}
