package org.usfirst.frc.falcons6443.robot.commands.subcommands;

import edu.wpi.first.wpilibj.Timer;
import org.usfirst.frc.falcons6443.robot.commands.SimpleCommand;

/* 
* Moves the robot for a specified period of time. Can set each side to the same power or different powers
*/
public class DriveForTime extends SimpleCommand {

    private Timer timer;
    private double desiredTime;
    private double leftPower;
    private double rightPower;


    public DriveForTime(double desiredTime, double leftPower, double rightPower){
        super("Move For Time");
        requires(driveTrain);
        timer = new Timer();
        this.desiredTime = desiredTime;
        this.leftPower = leftPower;
        this.rightPower = rightPower;
    }

    @Override
    public void initialize() {
        driveTrain.reset();
        timer.start();
    }

    @Override
    public void execute() {
        driveTrain.tankDrive(leftPower, rightPower);
        //       System.out.println(driveTrain.getLeftDistance());
        if(timer.get() > desiredTime) driveTrain.tankDrive(0, 0);
    }

    @Override
    protected boolean isFinished() {
        if(timer.get() > desiredTime) driveTrain.tankDrive(0, 0);
        return timer.get() > desiredTime;
    }
}
