package org.usfirst.frc.falcons6443.robot.commands.subcommands;

import edu.wpi.first.wpilibj.Timer;
import org.usfirst.frc.falcons6443.robot.commands.SimpleCommand;

public class DriveForTime extends SimpleCommand {

    private Timer timer;
    private double desTime;
    private double power;


    public DriveForTime(double desTime, double power){
        super("Move For Time");
        requires(driveTrain);
        timer = new Timer();
        this.desTime = desTime;
        this.power = power;
    }

    @Override
    public void initialize() {
        driveTrain.reset();
        timer.start();
    }

    @Override
    public void execute() {
        driveTrain.tankDrive(power, power);
        System.out.println(driveTrain.getLeftDistance());
        if(timer.get() > desTime) driveTrain.tankDrive(0, 0);
    }

    @Override
    protected boolean isFinished() {
        if(timer.get() > desTime) driveTrain.tankDrive(0, 0);
        return timer.get() > desTime;
    }
}