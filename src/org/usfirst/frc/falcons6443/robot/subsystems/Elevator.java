package org.usfirst.frc.falcons6443.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Victor;
import org.usfirst.frc.falcons6443.robot.RobotMap;
import org.usfirst.frc.falcons6443.robot.subsystems.ElevatorEnums;

public class Elevator {

    public static final double transferHeight = 0;
    public static final double switchHeight = 0;
    public static final double scaleHeight = 0;

    public double buffer = .5; // inches
    private double diameter = 2; // inches

    private Victor motor;
    private DigitalInput touchSensor;
    private ElevatorEncoder encoder;
    private ElevatorEnums states;

    public Elevator (){
        motor = new Victor (RobotMap.ElevatorVictor);
        touchSensor = new DigitalInput (RobotMap.ElevatorTouchSensor);
        encoder = new ElevatorEncoder();
    }

    public boolean hasCube(){
        if (touchSensor.get()) {
            return true;
        } else {
            return false;
        }
    }

    public void moveToHeight(ElevatorEnums elevatorState){
        switch(elevatorState){
            case Transfer:
                if(!isAtHeight(transferHeight)){
                    move(transferHeight, .4);
                }
                break;
            case Switch:
                if(isAtHeight(switchHeight)){
                    move(switchHeight, .4);
                }
                break;
            case Scale:
                if(isAtHeight(scaleHeight)){
                    move(scaleHeight, .4);
                }
                break;
        }
    }

    public void move(double height, double power){
        while (!isAtHeight(height)){
            motor.set(power);
            //wait???
        }
        motor.set(0);
    }

    public boolean isAtHeight(double height){
        if((getDistance() + buffer) > height && (getDistance() - buffer) < height){
            return true;
        } else {
            return false;
        }
    }

    public double getDistance(){
        // Encoder clicks per rotation = 1024
        return encoder.getDistance() * diameter * Math.PI / 1024.0; // In inches
    }
}
//know predetermined height vals
//know where to go (buttons, auto code, etc) AKA: integrate with rest of code
// poss. PID to smooth motion

//Done
//know if it has cube
//know current position
