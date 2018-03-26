package org.usfirst.frc.falcons6443.robot.commands.subcommands;

import org.usfirst.frc.falcons6443.robot.commands.SimpleCommand;
import org.usfirst.frc.falcons6443.robot.utilities.Logger;
import org.usfirst.frc.falcons6443.robot.utilities.PID;
import org.usfirst.frc.falcons6443.robot.utilities.enums.LoggerSystems;

public class DriveToDistanceBackUp extends SimpleCommand{

    public static final double P = .42; //.42
    public static final double I = 0;
    public static final double D = 3.5; //3.5
    public static final double Eps = 0.4; //weakest applied power //.5

    private static final double buffer = 1.5; //inches

    private double targetDistance;

    private PID pid;

    public DriveToDistanceBackUp(int distance){
        super("Drive To Distance");
        requires(navigation);
        requires(driveTrain);
        requires(elevator);
        requires(intake);
        pid = new PID(P, I, D, Eps);
        pid.setMaxOutput(.65);//.65
        pid.setMinDoneCycles(5);
        pid.setDoneRange(buffer);
        targetDistance = distance;
    }

    public void driveToDistance(){
        double power = pid.calcPID(driveTrain.getLeftDistance());
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
        setDistance();
    }

    @Override
    public void execute() {
        elevator.moveToHeight();
        intake.autoMoveIntake();
        driveToDistance();
        Logger.log(LoggerSystems.Drive,"Distance", Double.toString(driveTrain.getLinearDistance()));
    }

    @Override
    protected boolean isFinished() {
        if(isAtDistance()){
            Logger.log(LoggerSystems.Drive,"Distance", "to distance");
        }
        return isAtDistance();
    }
}
