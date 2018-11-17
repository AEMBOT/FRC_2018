package org.usfirst.frc.falcons6443.robot.hardware;

import edu.wpi.first.wpilibj.Counter;

/*
 * Used with encoders that do not register negative ticks. This class should be used with motor direction
 * to check if counter is adding or subtracting ticks. Can be used with a limit switch to reset the counter.
 * CheckMotorOutput is an example of how to use this class in your Subsystems.
 */
public class Counters {
    private Counter counter;
    private LimitSwitch limitSwitch;  //Useful to reset encoder at specific location, but not required
    private boolean forward;
    private int lastCount;

    public Counters(int counterPort, int offset){
        this.counter = new Counter(counterPort);
        this.forward = true;
    }

    public Counters(int counterPort){
        this.counter = new Counter(counterPort);
        this.forward = true;
    }

    public Counters(int counterPort, int limitPort, int offset){
        this.counter = new Counter(counterPort);
        this.limitSwitch = new LimitSwitch(limitPort);
        this.lastCount = offset;
        this.forward = true;
    }

    public void reset(){
        this.counter.reset();
        this.lastCount = 0;
    }

    public void reset(int offset){
        this.counter.reset();
        lastCount = offset;
    }

    //returns straight from the counter, so this value has never been subtracted from even with motor running backwards
    public int getTicksReal(){
        return this.counter.get();
    }

    public int getTicks(){ //returns ticks
        if(getDirection()){
            this.lastCount += this.counter.get();
            this.counter.reset();
            return this.lastCount;
        } else {
            this.lastCount -= this.counter.get();
            this.counter.reset();
            return this.lastCount;
        }
    }

    public void setDirection(boolean forward){
        this.lastCount = getTicks(); //updates the tick count before changing the direction
        this.forward = forward;
    }

    public boolean getDirection(){ return this.forward; }



    public boolean getLimit(){ return this.limitSwitch.get(); }

    // -- if motorOutput > 0 is forwards, than isPositivePowerForward is true
    //
    //This function is used to check if the limit switch is pressed or if the max tick limit is reached and to
    // correctly set the motor output, direction of counter, and reset when needed. This is not a necessary
    // function but could be useful in knowing how to use the Counters class effectively.
    public double checkMotorOutput(double desiredMotorOutput, boolean isLimitForward, int limitOffset,
                                   boolean isPositivePowerForward, int tickLimit){
        double motorOutput = desiredMotorOutput;
        if(getLimit()){
            if(isLimitForward && desiredMotorOutput > 0)motorOutput = 0;
            else if(!isLimitForward && desiredMotorOutput < 0)motorOutput = 0;

            reset(limitOffset);
        } else if(getTicks() > tickLimit){
            if(isLimitForward && desiredMotorOutput < 0)motorOutput = 0;
            else if(!isLimitForward && desiredMotorOutput > 0)motorOutput = 0;
        }

        if(motorOutput > 0) setDirection(isPositivePowerForward);
        else if(motorOutput < 0) setDirection(!isPositivePowerForward);

        return motorOutput;
    }
}
