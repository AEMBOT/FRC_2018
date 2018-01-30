package org.usfirst.frc.falcons6443.robot.subsystems;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.falcons6443.robot.RobotMap;

/**
 * Subsystem for the flywheels that push the block out.
 *
 * @author Aleksandras Vidmantas
 */
public class FlywheelSystem extends Subsystem {

    private Spark motor;
    private Spark secMotor;
    private int direction;
    boolean intakeFlag;
    boolean outtakeFlag;

    public FlywheelSystem(int left, int right){
        intakeFlag = false;
        outtakeFlag = false;
        motor = new Spark(left);
        secMotor = new Spark(right);
        direction = 1; //-1 is reversed
    }

    public void setPower(float power){
        if(intakeFlag || outtakeFlag){
            secMotor.set(power);
            motor.set(power);
        }else{
            motor.set(0);
            secMotor.set(0);
        }

    }


    @Override
    protected void initDefaultCommand() {

    }

    public void setForward(float power){
        if(power != 0){
            intakeFlag = true;
        }else{
            intakeFlag = false;
        }
        setPower(power);
    }

    public void setReverse(float power){
        if(power != 0){
            outtakeFlag = true;
        }else{
            outtakeFlag = false;
        }
        setPower(power*-1);
    }
}
