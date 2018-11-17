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

    public DriveForTime(double desiredTime, double power){
        super("Move For Time");
        requires(driveTrain);
        timer = new Timer();
        this.desiredTime = desiredTime;
        this.leftPower = power;
        this.rightPower = power;
    }

    @Override
    public void initialize() {
        driveTrain.reset();
        timer.start();
    }

    @Override
    public void execute() {
        driveTrain.tankDrive(leftPower, rightPower);
    }

    @Override
    protected boolean isFinished() {
        return timer.get() > desiredTime;
    }

    //test this end function
    @Override
    public void end(){
        driveTrain.tankDrive(0, 0);
    }
}
