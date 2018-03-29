package org.usfirst.frc.falcons6443.robot.commands.subcommands;

import org.usfirst.frc.falcons6443.robot.commands.SimpleCommand;
import org.usfirst.frc.falcons6443.robot.utilities.Logger;
import org.usfirst.frc.falcons6443.robot.utilities.PID;
import org.usfirst.frc.falcons6443.robot.utilities.enums.LoggerSystems;

public class DriveToDistance extends SimpleCommand {

    public static final double P = .1; //.42
    public static final double I = 0;
    public static final double D = .1; //3.5
    public static final double Eps = 0.4; //weakest applied power
    private static final double buffer = 1; //inches //0.5

    private double targetDistance;

    private PID pid;

    public DriveToDistance(double distance){
        super("Drive To Distance");
        requires(navigation);
        requires(driveTrain);
        requires(elevator);
        requires(intake);
        pid = new PID(P, I, D, Eps);
        pid.setMaxOutput(.65);
        pid.setMinDoneCycles(5);
        pid.setDoneRange(buffer);
        targetDistance = distance;
    }

    public void driveToDistance(){
        double power = pid.calcPID(driveTrain.getLinearDistance());
        driveTrain.tankDrive(power, power + .05);
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
        setDistance();
    }

    @Override
    public void execute() {
       // elevator.moveToHeight();
        intake.autoMoveIntake();
        driveToDistance();
        System.out.println("limit" + elevator.getMidLimit());
    }

    @Override
    protected boolean isFinished() {
        if(isAtDistance()){
            Logger.log(LoggerSystems.Drive,"Distance", "to distance");
        }
        return isAtDistance();
    }
}
