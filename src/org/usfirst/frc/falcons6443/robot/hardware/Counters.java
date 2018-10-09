package org.usfirst.frc.falcons6443.robot.hardware;

import edu.wpi.first.wpilibj.Counter;

public class Counters extends Counter {
    private Counter encoder;
    private LimitSwitch limitSwitch1;
    private LimitSwitch limitSwitch2;
    private boolean forward;
    private int lastCount;

    public Counters(int counterPort, int limitPort1, int limitPort2){
        encoder = new Counter(counterPort);
        limitSwitch1 = new LimitSwitch(limitPort1);
        limitSwitch2 = new LimitSwitch(limitPort2);
        forward = true;
    }

    public Counters(int counterPort, int limitPort1){
        encoder = new Counter(counterPort);
        limitSwitch1 = new LimitSwitch(limitPort1);
        forward = true;
    }

    public Counters(int counterPort){
        encoder = new Counter(counterPort);
        forward = true;
    }

    public void reset(){
        encoder.reset();
        lastCount = 0;
    }

    public void reset(int offset){
        encoder.reset();
        lastCount = offset;
    }

    public boolean getLimit1(){
        return limitSwitch1.get();
    }

    public boolean getLimit2(){
        return limitSwitch2.get();
    }

    public double getValueReal(){
        return encoder.get();
    }

    public int getValue(){
        if(getDirection()){
            lastCount += encoder.get();
            encoder.reset();
            return lastCount;
        } else {
            lastCount -= encoder.get();
            encoder.reset();
            return lastCount;
        }
    }

    public void setDirection(boolean forward){
        lastCount = getValue(); //flipped these two to get accurate last count
        this.forward = forward;
    }

    public boolean getDirection(){
        return forward;
    }
    public double check(double desiredMotorOutput, boolean isLimitOneZero, boolean isLimitOnePositive,
                          int limitOneOffset){
        double motorOutput = desiredMotorOutput;
        if(getLimit1()){
            if(isLimitOnePositive && desiredMotorOutput > 0)motorOutput = 0;
            else if(!isLimitOnePositive && desiredMotorOutput < 0)motorOutput = 0;

            if(isLimitOneZero) reset();
            else reset(limitOneOffset);
        } else if(getLimit2()){
            if(isLimitOnePositive && desiredMotorOutput < 0)motorOutput = 0;
            else if(!isLimitOnePositive && desiredMotorOutput > 0)motorOutput = 0;
        }
        return motorOutput;
    }
}
