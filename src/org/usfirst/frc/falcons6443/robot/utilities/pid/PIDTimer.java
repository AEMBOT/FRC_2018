package org.usfirst.frc.falcons6443.robot.utilities.pid;

/**
 * This class was imported from Simbotics code.
 * PID: proportional–integral–derivative controller
 *
 * @author Simbotics 2017
 */
public class PIDTimer extends PID {
    private long startTime = -1;
    private long timeout;

    public PIDTimer(double p, double i, double d, double eps, long timeout) {
        super(p, i, d, eps);
        this.timeout = timeout;
    }

    @Override
    public double calcPID(double current) {
        if (this.startTime == -1 || this.getFirstCycle()) {
            this.startTime = System.currentTimeMillis();
        }
        return super.calcPID(current);

    }

    @Override
    public boolean isDone() {
        long currentTime = System.currentTimeMillis();
        if ((currentTime - this.startTime) >= this.timeout) {
            System.out.println("PIDTimer TIMEOUT");
        }
        return super.isDone() || ((currentTime - this.startTime) >= this.timeout);
    }

    public void setTimeOut(long timeout) {
        this.timeout = timeout;
    }
}