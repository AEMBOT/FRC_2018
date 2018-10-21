package org.usfirst.frc.falcons6443.robot.utilities.pid;

/**
 * This class was imported from Simbotics code.
 * PID: proportional–integral–derivative controller
 * See PIDF class to understand the feedforward term
 *
 * @author Simbotics 2017
 */
public class PIDFTimer extends PIDF {
    //The timer is used to time out the PID, keeping it from forever attempting to reach
    //the target. Can also increase done range to reduce need of timer.
    private long startTime = -1;
    private long timeout;

    public PIDFTimer(double p, double i, double d, double f, double eps, long timeout) {
        super(p, i, d, f, eps);
        this.timeout = timeout;
    }

    @Override
    //use to calculate what power the motor should be set to
    public double calcPID(double current) {
        if (this.startTime == -1 || this.getFirstCycle()) {
            this.startTime = System.currentTimeMillis();
        }
        return super.calcPID(current);
    }

    @Override
    //use to check if PID has finished
    public boolean isDone() {
        long currentTime = System.currentTimeMillis();
        if ((currentTime - this.startTime) >= this.timeout) {
            System.out.println("PIDTimer TIMEOUT");
        }
        return super.isDone() || ((currentTime - this.startTime) >= this.timeout);
    }

    //set at what time the PID should stop. When timed out isDone() returns true
    public void setTimeOut(long timeout) {
        this.timeout = timeout;
    }
}