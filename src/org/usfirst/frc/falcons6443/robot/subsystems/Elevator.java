package org.usfirst.frc.falcons6443.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.falcons6443.robot.RobotMap;
import org.usfirst.frc.falcons6443.robot.hardware.ElevatorEncoder;
import org.usfirst.frc.falcons6443.robot.utilities.ElevatorEnums;
import org.usfirst.frc.falcons6443.robot.utilities.PID;

public class Elevator extends Subsystem {

    private static final double TransferHeight = 0; //inches -- just leave in ticks???
    private static final double SwitchHeight = 0; //inches
    private static final double ScaleHeight = 0; //inches
    private static final double TopLimitHeight = 0; //inches
    private static final double BottomLimitHeight = 0; //inches
    private static final double Buffer = 1; //inches
    private static final double Diameter = 2; //inches -- do we want??

    private static final double P = 0;
    private static final double I = 0;
    private static final double D = 0;
    private static final double Eps = 0; //weakest applied power

    private Spark motor;

    private DigitalInput topLimit;
    private DigitalInput bottomLimit;
    private ElevatorEncoder encoder;

    private ElevatorEnums state;
    
    private PID pid;

    public Elevator (){
        motor = new Spark (RobotMap.ElevatorMotor);
        topLimit = new DigitalInput (RobotMap.ElevatorTopLimit);
        bottomLimit = new DigitalInput (RobotMap.ElevatorBottomLimit);
        encoder = new ElevatorEncoder();
        pid = new PID(P, I, D, Eps);
        pid.setMaxOutput(.5);
        pid.setMinDoneCycles(5);
        pid.setDoneRange(Buffer);
    }

    @Override
    public void initDefaultCommand() {
    }

    public ElevatorEnums currentElevatorState(){
        return state;
    }

    public void setToHeight(ElevatorEnums elevatorState){
        switch(elevatorState){
            case Transfer:
                pid.setDesiredValue(TransferHeight);
                state = ElevatorEnums.Transfer;
                break;
            case Switch:
                pid.setDesiredValue(SwitchHeight);
                state = ElevatorEnums.Switch;
                break;
            case Scale:
                pid.setDesiredValue(ScaleHeight);
                state = ElevatorEnums.Scale;
                break;
            case BottomLimit:
                pid.setDesiredValue(BottomLimitHeight);
                state = ElevatorEnums.BottomLimit;
                break;
            case TopLimit:
                pid.setDesiredValue(TopLimitHeight);
                state = ElevatorEnums.TopLimit;
                break;
        }
    }

    //put in periodic function
    public void moveToHeight(){
        double power = pid.calcPID(getHeight());
        if(bottomLimit.get()){
            encoder.reset();
            setToHeight(ElevatorEnums.BottomLimit);
            power = Math.abs(power);
        } else if (topLimit.get()){
            setToHeight(ElevatorEnums.TopLimit);
            power = -Math.abs(power);
        }

        if (isAtHeight()) {
            stop();
        } else {
            motor.set(power);
        }
    }

    public void stop(){
        motor.set(0);
    }

    public void manual(double power){
        if (power < .1){
            power = 0;
        }
        motor.set(power);
    }

    public boolean isAtHeight(){
        return pid.isDone();
        /*if((getHeight() + Buffer) > height && (getHeight() - Buffer) < height){
            return true;
        } else {
            return false;
        }*/
    }

    public double getHeight(){
        return encoder.getTicks() * Diameter * Math.PI / 1024.0;
    } //inches
}

//To Do:
//measure predetermined heights
//know where to go (buttons, auto code, etc) AKA: integrate with rest of code
//tune PID

//Done:
//know if it has cube
//know current position
//know predetermined height vals
//add limit switches
// PID to smooth motion
