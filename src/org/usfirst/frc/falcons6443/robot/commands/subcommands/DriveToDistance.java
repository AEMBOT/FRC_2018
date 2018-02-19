package org.usfirst.frc.falcons6443.robot.commands.subcommands;

import org.usfirst.frc.falcons6443.robot.commands.SimpleCommand;
import org.usfirst.frc.falcons6443.robot.commands.SimpleCommand;
import org.usfirst.frc.falcons6443.robot.hardware.DriveEncoders;
import org.usfirst.frc.falcons6443.robot.utilities.PID;

public class DriveToDistance extends SimpleCommand {

    public static final double P = .42; //.42
    public static final double I = 0;
    public static final double D = 3.5;
    public static final double Eps = 0.5; //weakest applied power

    private static final double buffer = .5; //inches

    private double targetDistance;

    private PID pid;

    public DriveToDistance(int distance){
        super("Restricted PID Drive");
        requires(navigation);
        requires(driveTrain);
        pid = new PID(P, I, D, Eps);
        pid.setMaxOutput(.6);
        pid.setMinDoneCycles(5);
        pid.setDoneRange(buffer);
        targetDistance = distance;
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
        driveTrain.reset();
    }

    @Override
    public void execute() {
        setDistance();
        driveToDistance();
        //System.out.println("Left: " + (driveTrain.getLeftDistance()));
        //System.out.println("Right: " + (driveTrain.getRightDistance()));
        System.out.println("Linear: " + driveTrain.getLinearDistance());
    }

    @Override
    protected boolean isFinished() {
        return isAtDistance();
    }
}
