package org.usfirst.frc.falcons6443.robot.commands;

/**
 * Moves the robot for a certain amount of time specified in the constructor.
 *
 * @author Shivashriganesh Mahato
 */
public class MoveByTime extends SimpleCommand {

    private double stopTime, leftSpeed, rightSpeed;

    public MoveByTime(double stopTime, double leftSpeed, double rightSpeed) {
        super("Move By Time");
        requires(driveTrain);

        this.stopTime = stopTime;
        this.leftSpeed = leftSpeed;
        this.rightSpeed = rightSpeed;
    }

    public MoveByTime(double stopTime, double speed) {
        this(stopTime, speed, speed);
    }

    @Override
    public void initialize() {
        setTimeout(stopTime);
    }

    @Override
    public void execute() {
        driveTrain.tankDrive(leftSpeed, rightSpeed);
    }

    @Override
    protected boolean isFinished() {
        return isTimedOut();
    }
}