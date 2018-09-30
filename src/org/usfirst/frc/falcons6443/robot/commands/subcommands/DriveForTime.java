package org.usfirst.frc.falcons6443.robot.commands.subcommands;

import edu.wpi.first.wpilibj.Timer;
import org.usfirst.frc.falcons6443.robot.commands.SimpleCommand;

public class DriveForTime extends SimpleCommand {

    private Timer timer;
    private double desTime;
    private double powerl;
    private double powerr;


    public DriveForTime(double desTime, double powerl, double powerr){
        super("Move For Time");
        requires(driveTrain);
        timer = new Timer();
        this.desTime = desTime;
        this.powerl = powerl;
        this.powerr = powerr;
    }

    @Override
    public void initialize() {
        driveTrain.reset();
        timer.start();
    }

    @Override
    public void execute() {
        driveTrain.tankDrive(powerl, powerr);
 //       System.out.println(driveTrain.getLeftDistance());
        if(timer.get() > desTime) driveTrain.tankDrive(0, 0);
    }

    @Override
    protected boolean isFinished() {
        if(timer.get() > desTime) driveTrain.tankDrive(0, 0);
        return timer.get() > desTime;
    }
}