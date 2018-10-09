package org.usfirst.frc.falcons6443.robot.hardware;

import edu.wpi.first.wpilibj.Counter;

public class Counters extends Counter {
    private Counter encoder;
    private LimitSwitch limitSwitch1;  //Useful to reset encoder at specific location
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

    public int getValueReal(){
        return encoder.get();
    }

    public int getValue(){ //returns ticks
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

    // -- if limit one is not zero ticks, then declare a limit one offset
    // -- if motorOutput > 0 is direction true, than isPositivePowerDirection is true
    //
    //This function is used to check if the limit switches are pressed and to correctly set
    // the motor output, direction of counter, and reset when needed. This is not a necessary
    // function but could be useful in knowing how to use the Counters class effectively.
    public double checkMotorOutput(double desiredMotorOutput, boolean isLimitOneZero,
                                   boolean isLimitOnePositive, int limitOneOffset,
                                   boolean isPositivePowerDirection){
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

        if(motorOutput > 0) setDirection(isPositivePowerDirection);
        else if(motorOutput < 0) setDirection(!isPositivePowerDirection);

        return motorOutput;
    }
}
