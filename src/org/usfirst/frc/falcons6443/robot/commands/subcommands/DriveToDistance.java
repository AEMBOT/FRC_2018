package org.usfirst.frc.falcons6443.robot.commands.subcommands;

import org.usfirst.frc.falcons6443.robot.commands.SimpleCommand;
import org.usfirst.frc.falcons6443.robot.utilities.*;
import org.usfirst.frc.falcons6443.robot.utilities.enums.LoggerSystems;

public class DriveToDistance extends SimpleCommand {

    public static final double P = .1; //.42
    public static final double I = 0;
    public static final double D = .1; //3.5
    public static final double Eps = 0.5; //weakest applied power //0.4???

    private static final double buffer = 1; //inches //0.5
    private static final double counterBuffer = 2; //inches //0.5

    private double targetDistance;
    private double oldDistance;
    private int counter;
    private boolean done;

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

    private void driveToDistance(){
        double power = pid.calcPID(driveTrain.getLinearDistance());
        driveTrain.tankDrive(power, power + .05);
    }

    private void setDistance(){
        pid.setDesiredValue(targetDistance);
    }

    private boolean isAtDistance(){
        return pid.isDone();
    }

    @Override
    public void initialize() {
        driveTrain.reset();
        setDistance();
        oldDistance = 0;
        counter = 0;
        done = false;
    }

    @Override
    public void execute() {
        //try w/o first
  //      if(counter > 50) {
  //          oldDistance = driveTrain.getLinearDistance();
  //          counter = 0;
  //      } else if (counter == 50){
  //          if((oldDistance + counterBuffer) >= driveTrain.getLinearDistance()){
  //             done = true;
  //          }
  //      } else {
  //          counter++;
  //      }
        //elevator.moveToHeight(true);
        intake.autoMoveIntake();
        driveToDistance();
        System.out.println("limit" + elevator.getSwitchLimit());
    }

    @Override
    protected boolean isFinished() {
        if(isAtDistance()){
            done = true;
            Logger.log(LoggerSystems.Drive,"Distance", "to distance");
        }
        return done;
    }
}
