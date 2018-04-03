package org.usfirst.frc.falcons6443.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.falcons6443.robot.RobotMap;
import org.usfirst.frc.falcons6443.robot.hardware.ElevatorEncoder;
import org.usfirst.frc.falcons6443.robot.hardware.ElevatorMotor;
import org.usfirst.frc.falcons6443.robot.utilities.Logger;
import org.usfirst.frc.falcons6443.robot.utilities.enums.*;

public class ElevatorSystem extends Subsystem {

    private ElevatorMotor motor;
    private DigitalInput scaleLimit;
    private DigitalInput switchLimit;
    private DigitalInput bottomLimit;
    private ElevatorEncoder encoder = null;
    private Timer timer;

    private ElevatorPosition desiredState = ElevatorPosition.Exchange;
    private ElevatorPosition previousLimit = ElevatorPosition.UnderSwitch;

    private final double upSpeed = 1;
    private final double downSpeed = -1;
    private final double switchHeight = 600; //set in ticks
    private final double scaleHeight = 10000; //set in ticks
    private final double autoTimeOneMotor = 5;
    private final double autoTimeRedlines = 5; //set
    private double autoTime;

    public ElevatorSystem(){
        if(RobotMap.Redline){
            encoder = new ElevatorEncoder();
            autoTime = autoTimeRedlines;
        } else {
            autoTime = autoTimeOneMotor;
        }
        motor = new ElevatorMotor();
        scaleLimit = new DigitalInput(RobotMap.ElevatorScaleLimit);
        switchLimit = new DigitalInput(RobotMap.ElevatorSwitchLimit);
        bottomLimit = new DigitalInput(RobotMap.ElevatorBottomLimit);
        timer = new Timer();
    }

    @Override
    public void initDefaultCommand() { }

    public void startTimer(){ timer.start(); }
    public void stopTimer(){ timer.stop(); }
    public double getTime(){ return timer.get(); }

    private void updatePreviousLimit(){
        if (!scaleLimit.get() || encoder.getDistance() > switchHeight){
            previousLimit = ElevatorPosition.OverSwitch;
        } else if(!bottomLimit.get() || encoder.getDistance() < switchHeight){
            previousLimit = ElevatorPosition.UnderSwitch;
        }
    }

    public void setToHeight (ElevatorPosition elevatorState){
        Logger.log(LoggerSystems.Elevator,"Set elevator position", elevatorState.getValue());
        switch (elevatorState){
            case Exchange:
                desiredState = ElevatorPosition.Exchange;
                break;
            case Switch:
                desiredState = ElevatorPosition.Switch;
                break;
            case Scale:
                desiredState = ElevatorPosition.Scale;
                break;
            case Stop:
                desiredState = ElevatorPosition.Stop;
                break;
        }
    }

    private boolean getSwitchHeight(){
        if(!switchLimit.get()){
            return true;
        } else if (encoder.getDistance() > switchHeight){
            return true;
        } else if (encoder.getDistance() < switchHeight){
            return false;
        }
        return !switchLimit.get();
    }
    private boolean getBottomHeight(){
        return !bottomLimit.get();
    }
    private boolean getScaleHeight(){
        if(!scaleLimit.get()){
            return true;
        } else if (encoder.getDistance() > scaleHeight){
            return true;
        } else if (encoder.getDistance() < scaleHeight){
            return false;
        }
        return !scaleLimit.get();
    }

    //put in periodic function
    public void moveToHeight(boolean auto){
        double power = 0;
        updatePreviousLimit();

        if(auto && getTime() > autoTime){
            desiredState = ElevatorPosition.Stop;
            Logger.log(LoggerSystems.Auto, "Elevator ran overtime", "stopped elevator");
            Logger.log(LoggerSystems.Elevator, "Ran overtime in auto", "stopped elevator");
        } else if (!auto){
            stopTimer();
        }

        switch (desiredState) {
            case Exchange:
                if (getBottomHeight()){
                    power = 0;
                    encoder.reset();
                } else {
                    power = downSpeed;
                }
                Logger.log(LoggerSystems.Elevator,"Bottom limit", Boolean.toString(!bottomLimit.get()));
                break;
            case Scale:
                if (getScaleHeight()){
                    power = 0;
                } else {
                    power = upSpeed;
               }
               Logger.log(LoggerSystems.Elevator,"Scale limit", Boolean.toString(!scaleLimit.get()));
               break;
            case Switch:
                if(auto){
                    if(getSwitchHeight() || getScaleHeight() || getTime() > autoTime){
                        power = 0;
                    } else if (getTime() < autoTime)  {
                        power = upSpeed;
                    }
                } else {
                    if (getSwitchHeight()) {
                        power = 0;
                    } else {
                        if (previousLimit == ElevatorPosition.UnderSwitch) {
                            power = upSpeed;
                        } else {
                            power = downSpeed;
                        }
                    }
                }
                Logger.log(LoggerSystems.Elevator,"Switch limit", Boolean.toString(!switchLimit.get()));
                break;
            case Stop:
                Logger.log(LoggerSystems.Elevator,"move to height", "Stop enum");
                power = 0;
                break;
        }
        motor.set(power);
    }

    public void up (){
        motor.set(upSpeed);
    }

    public void down (){
        motor.set(downSpeed);
    }

    public void stop () {
        motor.set(0);
    }

    public void manual(double x){
        motor.set(x);
    }
}