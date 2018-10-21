package org.usfirst.frc.falcons6443.robot.utilities.pid;

/**
 * This class was imported from Simbotics code.
 * PIDF: proportional–integral–derivative-feedforward controller
 *
 * @author Simbotics 2017
 */
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class PIDF extends PID {

    //The feedForward term is used to start the PID at a certain value and then try to approach the target.
    //Very useful in shooter PIDs and others to get up to speed faster.
    private double feedForward;

    public PIDF(double p, double i, double d, double f, double eps) {
        super(p, i, d, eps);
        this.feedForward = f;
    }

    @Override
    //use to calculate what power the motor should be set to
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
    //use to check if PID has finished
    public boolean isDone() {
        double currError = Math.abs(this.previousError);

        //close enough to target
        return currError <= this.finishedRange;
    }

    public void setFeedForward(double f) {
        this.feedForward = f;
    }
}