package org.usfirst.frc.falcons6443.robot.commands;

import org.usfirst.frc.falcons6443.robot.hardware.DriveEncoders;
import org.usfirst.frc.falcons6443.robot.utilities.PID;

public class DriveToDistance extends SimpleCommand{

    public static final double P = 0;
    public static final double I = 0;
    public static final double D = 0;
    public static final double Eps = 0; //weakest applied power

    private static final double buffer = .5; //inches

    private double targetDistance;

    private DriveEncoders encoders;

    private PID pid;

    public DriveToDistance(int desiredDistance){
        super("Restricted PID Drive");
        requires(navigation);
        requires(driveTrain);
        pid = new PID(P, I, D, Eps);
        encoders = new DriveEncoders();
        pid.setMaxOutput(1);
        pid.setMinDoneCycles(5);
        pid.setDoneRange(buffer);
        targetDistance = desiredDistance;
    }

    public void driveToDistance(){
        double power = pid.calcPID(driveTrain.getLinearDistance());
        driveTrain.tankDrive(power, power);
    }

    public void setDistance(){
        pid.setDesiredValue(targetDistance);
    }
    public boolean isAtDistance(){
        return pid.isDone();
    }

    @Override
    public void initialize() {
        encoders.reset();
    }

    @Override
    public void execute() {
        setDistance();
        driveToDistance();
    }

    @Override
    protected boolean isFinished() {
        return isAtDistance();
    }
}
