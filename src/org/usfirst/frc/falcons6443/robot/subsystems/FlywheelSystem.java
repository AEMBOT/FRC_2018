package org.usfirst.frc.falcons6443.robot.subsystems;

import edu.wpi.first.wpilibj.Spark;
import org.usfirst.frc.falcons6443.robot.RobotMap;

/**
 * Subsystem for the flywheels that push the block out.
 *
 * @author Aleksandras Vidmantas
 */
public class FlywheelSystem{

    private Spark leftMotor;
    private Spark rightMotor;

    private final double intakeSpeed = 1;
    private final double outputSpeed = 0.75;
    private final double outputSlowSpeed = 0.5;
    private boolean kill; //kill stops the constant slow speed

    public FlywheelSystem(){
        leftMotor = new Spark(RobotMap.FlywheelLeftMotor);
        rightMotor = new Spark(RobotMap.FlywheelRightMotor);
        leftMotor.setInverted(true);
        kill = false;
    }

    public void toggleKill(){
        kill =! kill;
    }

    public void intake(){
        rightMotor.set(intakeSpeed);
        leftMotor.set(intakeSpeed);
    }

    public void output(){
        rightMotor.set(-outputSpeed);
        leftMotor.set(-outputSpeed);
    }

    public void slowOutput(){
        rightMotor.set(-outputSlowSpeed);
        leftMotor.set(-outputSlowSpeed);
    }

    public void stop(){
        if(kill){
            rightMotor.set(0);
            leftMotor.set(0);
        } else {
            rightMotor.set(0.22);
            leftMotor.set(0.26); //because the left motor doesn't spin as well
        }
    }
}