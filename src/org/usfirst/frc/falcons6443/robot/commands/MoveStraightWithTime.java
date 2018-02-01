package org.usfirst.frc.falcons6443.robot.commands;

import edu.wpi.first.wpilibj.PIDOutput;

/**
 * Command to drive straight ahead using PID for a certain amount of time.
 *
 * @author Ivan Kenevich
 */
public class MoveStraightWithTime extends SimpleCommand implements PIDOutput {

    private double pidOutput;
    private double duration;

    /**
     * Constructor for MoveStraightWithTime.
     *
     * @param sec duration of the command.
     */
    public MoveStraightWithTime(double sec) {
        super("Move Straight With Tine");
        requires(navigation);
        requires(driveTrain);
        duration = sec;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    @Override
    public void initialize() {
        navigation.reset();
        navigation.initPIDController(this);
        navigation.pidSetPoint(0);
        setTimeout(duration);
    }

    @Override
    public void execute() {
        driveTrain.tankDrive(0.6 + pidOutput, 0.6 - pidOutput);
    }

    @Override
    public boolean isFinished() {
        if (isTimedOut()) {
            driveTrain.tankDrive(0, 0);
            return true;
        }
        return false;
    }

    @Override
    public void pidWrite(double output) {
        pidOutput = output;
    }
}