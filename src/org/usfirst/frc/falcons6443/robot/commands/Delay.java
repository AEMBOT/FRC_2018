package org.usfirst.frc.falcons6443.robot.commands;

/**
 * Command to stay stationary for x seconds
 *
 * @author Shivashriganesh Mahato
 */
public class Delay extends SimpleCommand {

    private double duration;

    /**
     * Constructor for Delay.
     */
    public Delay(double seconds) {
        super("Delay");
        this.duration = seconds;
    }

    @Override
    public void initialize() {
        setTimeout(duration);
    }

    @Override
    protected boolean isFinished() {
        return isTimedOut();
    }

}
