package org.usfirst.frc.falcons6443.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.falcons6443.robot.RobotMap;
import org.usfirst.frc.falcons6443.robot.hardware.ElevatorEncoder;
import org.usfirst.frc.falcons6443.robot.utilities.ElevatorEnums;

public class Elevator extends Subsystem {

    public static final double TransferHeight = 0;
    public static final double SwitchHeight = 0;
    public static final double ScaleHeight = 0;

    public double buffer = .5; // inches
    private double diameter = 2; // inches

    private Victor motor;

    private Victor scoringMotor; //MOTOR??? PISTON??? SERVO??? SOMETHING ELSE?????????
    private DigitalInput touchSensor;
    private ElevatorEncoder encoder;
    private ElevatorEnums state;
    private Timer timer;
    private double timerDelay = 1; //seconds

    public Elevator (){
        motor = new Victor (RobotMap.ElevatorVictor);
        scoringMotor = new Victor(RobotMap.ElevatorVictor2);
        touchSensor = new DigitalInput (RobotMap.ElevatorTouchSensor);
        encoder = new ElevatorEncoder();
        timer = new Timer();
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

    public void moveToHeight(ElevatorEnums elevatorState){
        switch(elevatorState){
            case Transfer:
                if(!isAtHeight(TransferHeight)){
                    move(TransferHeight, .4);
                }
                state = ElevatorEnums.Transfer;
                break;
            case Switch:
                if(isAtHeight(SwitchHeight)){
                    move(SwitchHeight, .4);
                }
                state = ElevatorEnums.Switch;
                break;
            case Scale:
                if(isAtHeight(ScaleHeight)){
                    move(ScaleHeight, .4);
                }
                state = ElevatorEnums.Scale;
                break;
        }
    }

    public void move(double height, double power){
        while (!isAtHeight(height)){
            int direction = getDistance() < height ? 1 : -1;
            motor.set(power * direction);
            timer.delay(timerDelay);
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

    //MOTOR?? SOMETHING ELSE??????????
    public void scoreCube(){
        scoringMotor.set(-.75);
        timer.delay(3);
        scoringMotor.set(0);
    }

    //MOTOR?? SOMETHING ELSE??? IF NOT, DO WE NEED A SEPARATE RESET FUNCTION?????????
    public void resetScoringMotor(){
        scoringMotor.set(.75);
        timer.delay(3);
        scoringMotor.set(0);
    }
}

//measure predetermined heights
//know where to go (buttons, auto code, etc) AKA: integrate with rest of code
// poss. PID to smooth motion

//Done
//know if it has cube
//know current position
//know predetermined height vals