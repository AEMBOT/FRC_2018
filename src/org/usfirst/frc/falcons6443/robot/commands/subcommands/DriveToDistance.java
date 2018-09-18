package org.usfirst.frc.falcons6443.robot.commands.subcommands;

import org.usfirst.frc.falcons6443.robot.commands.SimpleCommand;
import org.usfirst.frc.falcons6443.robot.utilities.*;
import org.usfirst.frc.falcons6443.robot.utilities.enums.LoggerSystems;
import org.usfirst.frc.falcons6443.robot.utilities.pid.PID;

public class DriveToDistance extends SimpleCommand {

    private static final double P = .15; //.42
    private static final double I = 0;
    private static final double D = .1; //3.5
    private static final double Eps = 0.5; //weakest applied power //0.4???

    private static final double buffer = 1; //inches //0.5
    private static final double counterBuffer = 2; //inches //0.5

    private double targetDistance;
    private double oldDistance;
    private int counter;
    private boolean done;

    private PID pid;

    public DriveToDistance(double distance){
        super("Drive To Distance");
        requires(driveTrain);
        requires(elevator);
        requires(flywheel);
        requires(rotation);
        pid = new PID(P, I, D, Eps);
        pid.setMaxOutput(.65);
        pid.setMinDoneCycles(5);
        pid.setDoneRange(buffer);
        targetDistance = distance;
    }

    private void driveToDistance(){
        double power = pid.calcPID(driveTrain.getLeftDistance());
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
        if(counter > 50) {
            oldDistance = driveTrain.getLeftDistance();
            counter = 0;
        } else if (counter == 50){
            if((oldDistance + counterBuffer) >= driveTrain.getLeftDistance()){
               done = true;
            }
        } else {
            counter++;
        }
        elevator.moveToHeight(true);
        rotation.autoMoveIntake();
        driveToDistance();
       // System.out.println("enc: " + driveTrain.getLeftDistance());
        //System.out.println("counter: " + counter);
    }

    @Override
    protected boolean isFinished() {
        if(isAtDistance()){
            done = true;
            Logger.log(LoggerSystems.Drive,"At distance");
        }
        return done;
    }
}
