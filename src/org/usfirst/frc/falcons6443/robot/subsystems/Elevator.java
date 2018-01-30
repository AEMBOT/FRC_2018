package org.usfirst.frc.falcons6443.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.falcons6443.robot.RobotMap;
import org.usfirst.frc.falcons6443.robot.hardware.ElevatorEncoder;
import org.usfirst.frc.falcons6443.robot.utilities.ElevatorEnums;
import org.usfirst.frc.falcons6443.robot.utilities.PID;

public class Elevator extends Subsystem {

    public static final double TransferHeight = 0; //inches
    public static final double SwitchHeight = 0; //inches
    public static final double ScaleHeight = 0; //inches
    public static final double buffer = 1; //inches
    public static final double diameter = 2; //inches

    public static final double P = 0;
    public static final double I = 0;
    public static final double D = 0;
    public static final double Eps = 0; //weakest applied power

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
        if (touchSensor.get()) {
            return true;
        } else {
            return false;
        }
    }

    public ElevatorEnums currentElevatorState(){
        return state;
    }

    public void setToHeight(ElevatorEnums elevatorState){
        switch(elevatorState){
            case Transfer:
                pid.setDesiredValue(convert(false, TransferHeight));
                break;
            case Switch:
                pid.setDesiredValue(convert(false, SwitchHeight));
                break;
            case Scale:
                pid.setDesiredValue(convert (false, ScaleHeight));
                break;
        }
    }

    //put in periodic function
    public void update(){
        while (!isAtHeight()){
            double power = pid.calcPID(getHeight()); //ticks
            motor.set(power);
        }
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
        return encoder.getTicks();
    }

    public double getHeightInches(){
        return convert(true, encoder.getTicks());
    }

    /**
     * Converts ticks to inches and inches to ticks
     *
     * @param toInches  true if ticks to inches, false if inches to ticks
     * @param input the value you wish to convert in inches or ticks
     */
    public double convert(boolean toInches, double input){
        // Encoder clicks per rotation = 1024
        if (toInches){
            return input * diameter * Math.PI / 1024.0; // In inches
        } else {
            return input / diameter / Math.PI * 1024.0; // In ticks
        }
    }
}

//measure predetermined heights
//know where to go (buttons, auto code, etc) AKA: integrate with rest of code
//PID to smooth motion

//Done
//know if it has cube
//know current position
//know predetermined height vals