package org.usfirst.frc.falcons6443.robot.subsystems;

import edu.wpi.first.wpilibj.CounterBase;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.falcons6443.robot.RobotMap;
import org.usfirst.frc.falcons6443.robot.hardware.Encoders;
import org.usfirst.frc.falcons6443.robot.hardware.ElevatorMotor;
import org.usfirst.frc.falcons6443.robot.hardware.LimitSwitch;
import org.usfirst.frc.falcons6443.robot.utilities.Logger;
import org.usfirst.frc.falcons6443.robot.utilities.enums.*;

public class ElevatorSystem extends Subsystem {

    private ElevatorMotor motor;
    private LimitSwitch scaleLimit;
    private LimitSwitch bottomLimit;
    private Encoder encoder;
    private Timer timer;

    private ElevatorPosition desiredState = ElevatorPosition.Exchange;
    private ElevatorPosition previousLimit = ElevatorPosition.UnderSwitch;

    private final double upSpeed = 0.9;
    private final double downSpeed = -0.6;
    private final double constantSpeed = 0.2;
    private final double switchHeight = 600000; //setSpeed in ticks//630000
    private final double scaleHeight = 1230000; //setSpeed in ticks

    private final double autoTimeOneMotor = 5;
    private final double autoTimeRedlines = 1; //setSpeed //.8
    private double autoTime;
    private boolean isManual;

    public ElevatorSystem(){
        if(RobotMap.RedLine){
            encoder = new Encoder(RobotMap.ElevatorEncoderA, RobotMap.ElevatorEncoderB, false, Encoder.EncodingType.k4X);
            autoTime = autoTimeRedlines;
        } else {
            autoTime = autoTimeOneMotor;
        }
        motor = new ElevatorMotor();
        scaleLimit = new LimitSwitch(RobotMap.ElevatorScaleLimit);
        bottomLimit = new LimitSwitch(RobotMap.ElevatorBottomLimit);
        timer = new Timer();
        isManual = false;
    }

    @Override
    public void initDefaultCommand() { }

    public void startTimer(){ timer.start(); }
    public void stopTimer(){ timer.stop(); }
    public double getTime(){ return timer.get(); }

    public double getEncoderDistance(){
        //return encoder.getDistance();
        return 100;}
    public double getEncoderDistanceAuto(){
        //return -encoder.getDistance();
        return 100;
    }

    public void resetEncoder() {
        //encoder.reset();
        }

    private void updatePreviousLimit(){
        if (scaleLimit.get() || encoder.getDistance() > switchHeight){
            previousLimit = ElevatorPosition.OverSwitch;
        } else if(bottomLimit.get() || encoder.getDistance() < switchHeight){
            previousLimit = ElevatorPosition.UnderSwitch;
        }
    }

    public void setToHeight (ElevatorPosition elevatorState){
//        Logger.log(LoggerSystems.Elevator,"Set elevator position: " + elevatorState.getName());
        switch (elevatorState){
            case Exchange:
                desiredState = ElevatorPosition.Exchange;
                setManual(false);
                break;
            case Switch:
                desiredState = ElevatorPosition.Switch;
                setManual(false);
                break;
            case Scale:
                desiredState = ElevatorPosition.Scale;
                setManual(false);
                break;
            case Stop:
                desiredState = ElevatorPosition.Stop;
                break;
        }
    }

    private boolean getSwitchHeight(){
        return getEncoderDistanceAuto() > switchHeight;
    }
    private boolean getBottomHeight(){
        return bottomLimit.get();
    }
    private boolean getScaleHeight(){
        if(scaleLimit.get()){
            return true;
        } else if (encoder.getDistance() > scaleHeight){
            return true;
        } else if (encoder.getDistance() < scaleHeight){
            return false;
        }
        return scaleLimit.get();
    }

    //put in periodic function
    public void moveToHeight(boolean auto){
        double power = 0;
        updatePreviousLimit();

        if(auto && getTime() > autoTime){
            desiredState = ElevatorPosition.Stop;
//            Logger.log(LoggerSystems.Auto, "Elevator ran overtime: stopped elevator");
//            Logger.log(LoggerSystems.Elevator, "Ran overtime in auto: stopped elevator");
        } else if (!auto){
            stopTimer();
        }

        switch (desiredState) {
            case Exchange:
                if (getBottomHeight()){
                    power = 0;
//                    resetEncoder();
                } else {
                    power = downSpeed;
                }
//                Logger.log(LoggerSystems.Elevator,"Bottom limit: " + Boolean.toString(bottomLimit.get()));
                break;
            case Scale:
                if (getScaleHeight()){
                    power = 0;
                } else {
                    power = upSpeed;
               }
 //              Logger.log(LoggerSystems.Elevator,"Scale limit: " + Boolean.toString(scaleLimit.get()));
               break;
            case Switch:
                if(auto){
                    if(getSwitchHeight() || getTime() > autoTime){
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
                break;
            case Stop:
//                Logger.log(LoggerSystems.Elevator,"move to height: Stop enum");
                power = 0;
                break;
        }
        if(auto) power = -power;
        if (getBottomHeight()){
            power = 0;
 //           resetEncoder();
        } else if (scaleLimit.get() && power < 0){
            power = 0;
        }
        if(!getManual()) {
            motor.setSpeed(power);
        }
    }

    public void stop () {
        //motor.setSpeed(constantSpeed);
        motor.setSpeed(0);
    }

    public void manual(double x){
        setManual(true);
        if(x < 0) x = x * .3;
       // else if(x < constantSpeed) x = constantSpeed;

       /* if(!bottomLimit.get() && x < 0) {
            motor.setSpeed(0);
            resetEncoder();
        } else if(encoder.getDistance() < scaleHeight && scaleLimit.get()){
            motor.setSpeed(x);
        } else {
            if(x < 0) motor.setSpeed(x);
            else motor.setSpeed(constantSpeed);
            System.out.println("Max Height!!");
        }*/
       motor.setSpeed(x);
      //  System.out.println("E Enc: " + encoder.getDistance());
      //  System.out.println("Manual");
    }

    public void setManual(boolean on){
        isManual = on;
        Logger.log(LoggerSystems.Elevator, "Set manual " + on);
    }

    public boolean getManual(){ return isManual; }
}