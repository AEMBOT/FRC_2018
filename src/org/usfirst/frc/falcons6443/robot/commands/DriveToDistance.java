package org.usfirst.frc.falcons6443.robot.commands;

import org.usfirst.frc.falcons6443.robot.hardware.DriveEncoders;
import org.usfirst.frc.falcons6443.robot.utilities.PID;
import org.usfirst.frc.falcons6443.robot.subsystems.NavigationSystem;
import org.usfirst.frc.falcons6443.robot.subsystems.DriveTrainSystem;

public class DriveToDistance extends SimpleCommand{

    public static final double P = 0;
    public static final double I = 0;
    public static final double D = 0;
    public static final double Eps = 0; //weakest applied power

    private float angle;
    private double targetAngle;
    private static final double AngleBuffer = 2;

    private DriveEncoders encoders;

    private PID pid;
    private Contert convert;

    public DriveToDistance(){
        super("Restricted PID Drive");
        requires(navigation);
        requires(driveTrain);
        this.angle = angle;
        pid = new PID(P, I, D, Eps);
    }

    public void driveToDistance(){
        double power = pid.calcPID(encoders.getLinearDistance()); //ticks
        motor.set(power);
    }

    public void setDistance(double desiredDistance){
        pid.setDesiredValue(convert.convert(desiredDistance));
    }
    public boolean isAtDistance(){
        return pid.isDone();
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {

    }

    @Override
    protected boolean isFinished() {
        return isAtDistance();
    }
}
