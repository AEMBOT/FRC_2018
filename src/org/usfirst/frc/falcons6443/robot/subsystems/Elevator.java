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
                if(isAtHeight(transferHeight)){
                    break;
                } else {

                }
            case Switch:
                if(isAtHeight(switchHeight)){
                    break;
                } else {

                }
            case Scale:
                if(isAtHeight(scaleHeight)){
                    break;
                } else {

                }
        }
    }

    public void move(double height, double power){
        if (!isAtHeight(height)){

        }
    }

    public boolean isAtHeight(double height){
        if((encoder.getVal() + buffer) > height && (encoder.getVal() - buffer) < height){
            return true;
        } else {
            return false;
        }
    }
}
//know predetermined height vals
//know current position
//know where to go (buttons, auto code, etc)
// poss. PID to smooth motion

//Done
//know if it has cube
