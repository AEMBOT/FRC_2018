package org.usfirst.frc.falcons6443.robot.utilities.pid;

/**
 * This class was imported from Simbotics code.
 * PID: proportional–integral–derivative controller
 *
 * @author Simbotics 2017
 */
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class PIDF extends PID {

    private double feedForward;

    public PIDF(double p, double i, double d, double f, double eps) {
        super(p, i, d, eps);
        this.feedForward = f;
    }

    @Override
    public double calcPID(double current) {
        double feedForwardOutput = (super.getDesiredVal() * this.feedForward);
        if (this.debug) {
            SmartDashboard.putNumber("FF out", feedForwardOutput);
        }
        return super.calcPID(current) + feedForwardOutput;
    }

    public void setConstants(double p, double i, double d, double f) {
        super.setConstants(p, i, d);
        this.feedForward = f;
    }

    @Override
    public boolean isDone() {
        double currError = Math.abs(this.previousError);

        //close enough to target
        return currError <= this.finishedRange;
    }

    public void setFeedForward(double f) {
        this.feedForward = f;
    }
}