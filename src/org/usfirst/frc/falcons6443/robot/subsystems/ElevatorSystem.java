package org.usfirst.frc.falcons6443.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.falcons6443.robot.RobotMap;
import org.usfirst.frc.falcons6443.robot.commands.subcommands.MoveElevator;
import org.usfirst.frc.falcons6443.robot.utilities.Logger;
import org.usfirst.frc.falcons6443.robot.utilities.enums.*;

public class ElevatorSystem extends Subsystem {

    private Spark motor;
    private DigitalInput scaleLimit;
    private DigitalInput switchLimit;
    private DigitalInput bottomLimit;
    private Timer timer;

    private ElevatorPosition desiredState = ElevatorPosition.Exchange;
    private ElevatorPosition previousLimit = ElevatorPosition.UnderSwitch;

    private boolean manual = false;
    private final double upSpeed = 1;
    private final double downSpeed = -1;

    public ElevatorSystem(){
        motor = new Spark (RobotMap.ElevatorMotor);
        scaleLimit = new DigitalInput (RobotMap.ElevatorScaleLimit);
        switchLimit = new DigitalInput (RobotMap.ElevatorSwitchLimit);
        bottomLimit = new DigitalInput (RobotMap.ElevatorBottomLimit);
        timer = new Timer();
        motor.setInverted(true);
    }

    @Override
    public void initDefaultCommand() { }

    public void startTimer(){ timer.start(); }
    public void stopTimer(){ timer.stop(); }
    public double getTime(){ return timer.get(); }

    public boolean getManual(){ return manual; }

    private void updatePreviousLimit(){
        if (!scaleLimit.get()){
            previousLimit = ElevatorPosition.OverSwitch;
        } else if(!bottomLimit.get()){
            previousLimit = ElevatorPosition.UnderSwitch;
        }
    }

    public void setToHeight (ElevatorPosition elevatorState){
        Logger.log(LoggerSystems.Elevator,"Set elevator position", elevatorState.getValue());
        manual = false;
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

    public boolean getSwitchLimit(){
        return !switchLimit.get();
    }
    public boolean getBottomLimit(){
        return !bottomLimit.get();
    }
    public boolean getScaleLimit(){
        return !scaleLimit.get();
    }

    //put in periodic function
    public void moveToHeight(boolean auto){
        double power = 0;
        updatePreviousLimit();

        if(auto && getTime() > 3){
            desiredState = ElevatorPosition.Stop;
            Logger.log(LoggerSystems.Auto, "Elevator ran overtime", "stopped elevator");
            Logger.log(LoggerSystems.Elevator, "Ran overtime in auto", "stopped elevator");

        } else if (!auto){
            stopTimer();
        }

        switch (desiredState) {
            case Exchange:
                if (!bottomLimit.get()){
                    power = 0;
                } else {
                    power = downSpeed;
                }
                Logger.log(LoggerSystems.Elevator,"Bottom limit", Boolean.toString(!bottomLimit.get()));
                break;
            case Scale:
                if (!scaleLimit.get()){
                    power = 0;
                } else {
                    power = upSpeed;
               }
               Logger.log(LoggerSystems.Elevator,"Scale limit", Boolean.toString(!scaleLimit.get()));
               break;
            case Switch:
                 if (!switchLimit.get()){
                    power = 0;
                } else {
                    if (previousLimit == ElevatorPosition.UnderSwitch){
                        power = upSpeed;
                    } else {
                        power = downSpeed;
                    }
                }
                Logger.log(LoggerSystems.Elevator,"Switch limit", Boolean.toString(!switchLimit.get()));
                break;
            case Stop:
                Logger.log(LoggerSystems.Elevator,"move to height", "Stop enum");
                power = 0;
                break;
        }
        if (!manual) { motor.set(power);} else {
            Logger.log(LoggerSystems.Elevator,"manual", "on");
        }
    }

    public void up (){
            motor.set(upSpeed);
            manual = true;
    }

    public void down (){
            motor.set(downSpeed);
            manual = true;
    }

    public void stop () {
        motor.set(0);
    }

    public void manual(double x) {motor.set(x);}
}