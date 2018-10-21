package org.usfirst.frc.falcons6443.robot.utilities.pid;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This class was imported from Simbotics code.
 * PID: proportional–integral–derivative controller
 *
 * @author Simbotics 2017
 */
public class PID {
    private double pConst;
    private double iConst;
    private double dConst;
    private double desiredVal;
    protected double previousError;
    private double errorSum;
    protected double finishedRange;
    private double maxOutput;
    private int minCycleCount;
    private int currentCycleCount;
    private boolean firstCycle;
    private boolean resetI;
    protected boolean debug;

    public PID(double p, double i, double d, double epsRange){
        this.pConst = p;
        this.iConst = i;
        this.dConst = d;
        this.finishedRange = epsRange;
        this.resetI = true;
        this.desiredVal = 0.0;
        this.firstCycle = true;
        this.maxOutput = 1.0;
        this.currentCycleCount = 0;
        this.minCycleCount = 5;
        this.debug = false;
    }

    public void setConstants(double p,double i, double d){
        this.pConst = p;
        this.iConst = i;
        this.dConst = d;
    }

    //sets the value the PID needs to reach to be done
    public void setDesiredValue(double val) {
        this.desiredVal = val;
    }

    //set the acceptable range the PID needs to be in to be done
    public void setFinishedRange(double range){
        this.finishedRange = range;
    }

    public void disableIReset(){
        this.resetI = false;
    }

    public void enableIReset(){
        this.resetI = true;
    }

    public void enableDebug(){
        this.debug = true;
    }

    public void disableDebug(){
        this.debug = false;
    }

    //sets the max speed the PID will give the motors between 0 and 1
    public void setMaxOutput(double max) {
        if(max < 0.0) {
            this.maxOutput = 0.0;
        } else if(max > 1.0) {
            this.maxOutput = 1.0;
        } else {
            this.maxOutput = max;
        }
    }

    private static double limitValue(double val, double max) {
        if(val > max) {
            return max;
        } else if(val < -max) {
            return -max;
        } else {
            return val;
        }
    }

    //sets how many cycles the PID needs to be in done range before PID is done
    public void setMinDoneCycles(int num) {
        this.minCycleCount = num;
    }

    public void resetErrorSum() {
        this.errorSum = 0.0;
    }

    public double getDesiredVal() {
        return this.desiredVal;
    }

    //use to calculate what power the motor should be set to
    public double calcPID(double current) {
        return calcPIDError(this.desiredVal - current);
    }

    private double calcPIDError (double error){
        double pVal = 0.0;
        double iVal = 0.0;
        double dVal = 0.0;

        if(this.firstCycle) {
            this.previousError = error;
            this.firstCycle = false;
        }

        ///////P Calc///////
        pVal = this.pConst * error;

        ///////I Calc///////
        if(Math.abs(pVal) >= 1.0){ // P output is >= 1.0 which means we are very far away
            this.errorSum = 0.0;
        }else if(Math.abs(error) <= this.finishedRange){ // within range
            if(this.resetI){
                this.errorSum = 0.0;
            }else{
                //this.errorSum += error; //maybe we need this?
            }
        }else if(pVal > 0.0){ // going forward
            if(this.errorSum < 0.0){ // we were going backwards
                this.errorSum = 0.0;
            }
            this.errorSum += error;
        }else{ // going backwards
            if(this.errorSum > 0.0){ // we were going forward
                this.errorSum = 0.0;
            }
            this.errorSum += error;
        }

        iVal = this.iConst * this.errorSum;

        ///////D Calc///////
        double deriv = error - this.previousError;
        dVal = this.dConst * deriv;

        //overal PID calc
        double output = pVal + iVal + dVal;

        //limit the output
        output = limitValue(output, this.maxOutput);

        //store current value as previous for next cycle
        this.previousError = error;

        if(this.debug) {
            SmartDashboard.putNumber("P out", pVal);
            SmartDashboard.putNumber("I out", iVal);
            SmartDashboard.putNumber("D out", dVal);
            SmartDashboard.putNumber("PID OutPut", output);
        }

        return output;
    }

    //use to check if PID has finished
    public boolean isDone() {
        double currError = Math.abs(this.previousError);

        //close enough to target
        if(currError <= this.finishedRange) {
            this.currentCycleCount++;
        }
        //not close enough to target
        else {
            this.currentCycleCount = 0;
        }

        return this.currentCycleCount > this.minCycleCount;
    }

    public boolean getFirstCycle(){
        return this.firstCycle;
    }

    public void resetPreviousVal() {
        this.firstCycle = true;
    }
}