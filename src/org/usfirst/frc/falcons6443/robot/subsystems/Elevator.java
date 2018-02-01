package org.usfirst.frc.falcons6443.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.falcons6443.robot.RobotMap;
import org.usfirst.frc.falcons6443.robot.hardware.ElevatorEncoder;
import org.usfirst.frc.falcons6443.robot.utilities.ElevatorEnums;
import org.usfirst.frc.falcons6443.robot.utilities.PID;

public class Elevator extends Subsystem {

    private static final double TransferHeight = 0; //inches
    private static final double SwitchHeight = 0; //inches
    private static final double ScaleHeight = 0; //inches
    private static final double buffer = 1; //inches
    private static final double diameter = 2; //inches

    private static final double P = 0;
    private static final double I = 0;
    private static final double D = 0;
    private static final double Eps = 0; //weakest applied power

    private Spark motor;

    private DigitalInput touchSensor;
    private ElevatorEncoder encoder;
    private ElevatorEnums state;
    
    private PID pid;

    public Elevator (){
        motor = new Spark (RobotMap.ElevatorMotor);
        touchSensor = new DigitalInput (RobotMap.ElevatorTouchSensor);
        encoder = new ElevatorEncoder();
        pid = new PID(P, I, D, Eps);
        pid.setMaxOutput(.5);
        pid.setMinDoneCycles(5);
        pid.setDoneRange(buffer);
    }

    @Override
    public void initDefaultCommand() {
    }

    public boolean hasCube(){
        return touchSensor.get();
    }

    public ElevatorEnums currentElevatorState(){
        return state;
    }

    public void setToHeight(ElevatorEnums elevatorState){
        switch(elevatorState){
            case Transfer:
                pid.setDesiredValue(TransferHeight);
                break;
            case Switch:
                pid.setDesiredValue(SwitchHeight);
                break;
            case Scale:
                pid.setDesiredValue(ScaleHeight);
                break;
        }
    }

    //put in periodic function
    public void moveToHeight(){
        double power = pid.calcPID(getHeight());
        motor.set(power);
    }

    public void stop(){
        motor.set(0);
    }

    public boolean isAtHeight(){
        return pid.isDone();
        /*if((getHeight() + buffer) > height && (getHeight() - buffer) < height){
            return true;
        } else {
            return false;
        }*/
    }

    public double getHeight(){
        return encoder.getTicks() * diameter * Math.PI / 1024.0;
    } //inches
}

//measure predetermined heights
//know where to go (buttons, auto code, etc) AKA: integrate with rest of code
//PID to smooth motion
//tune PID

//Done
//know if it has cube
//know current position
//know predetermined height vals