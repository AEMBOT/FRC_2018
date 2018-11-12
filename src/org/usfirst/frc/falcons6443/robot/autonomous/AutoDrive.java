package org.usfirst.frc.falcons6443.robot.autonomous;

import org.usfirst.frc.falcons6443.robot.subsystems.DriveTrainSystem;
import org.usfirst.frc.falcons6443.robot.hardware.NavX;
import org.usfirst.frc.falcons6443.robot.utilities.pid.PID;
import org.usfirst.frc.falcons6443.robot.utilities.pid.PIDTimer;

/*
 * A class with drive encoder and NavX PIDs and functions to move according to
 * the PIDs. Functions are used in the AutoPaths class.
 */
public class AutoDrive extends DriveTrainSystem {
    private static final double distanceP = .15; //.42
    private static final double distanceI = 0;
    private static final double distanceD = .1; //3.5
    private static final double distanceEps = 0;
    private static final double distanceBuffer = 1; //inches

    private PIDTimer distancePID;
    private PIDTimer anglePID;
    private NavX navX;

    private static final double angleP = 0.1; //.3
    private static final double angleI = 0;
    private static final double angleD = .2; //1.23
    private static final double angleEps = 0;
    private static final double angleBuffer = 4; //degrees //1

    private static final long distanceTime = 5;  //the time when the PID "gives up" and moves onto the next auto action
    private static final long angleTime = 2;  //ditto

    public AutoDrive(){
        super();
        distancePID = new PIDTimer(distanceP, distanceI, distanceD, distanceEps, distanceTime);
        distancePID.setMaxOutput(.65);
        distancePID.setMinDoneCycles(5);
        distancePID.setFinishedRange(distanceBuffer);
        navX = NavX.get();
        anglePID = new PIDTimer(angleP, angleI, angleD, angleEps, angleTime);
        anglePID.setMaxOutput(.7);
        anglePID.setMinDoneCycles(2);
        anglePID.setFinishedRange(angleBuffer);
    }

    //a periodic function used to provide power to the drive wheels according to the PID output
    void driveToDistance(){
        double power = distancePID.calcPID(getDistanceSafe());
        tankDrive(power, power + .05);
    }

    //distance is in inches, reset will reset the encoders
    void setDistance(double distance, boolean reset){
        if(reset) reset();
        distancePID.setDesiredValue(distance);
    }

    //returns if the PID is complete, meaning the robot is at the set distance
    boolean isAtDistance(){
        return distancePID.isDone();
    }

    //changes the distance PID time out
    void setDistanceTimeOut(long time){
        distancePID.setTimeOut(time);
    }
    //a periodic function used to provide power to the drive wheels according to the PID output
    void turnToAngle(){
        double power = anglePID.calcPID(navX.getYaw());
        if (isAtAngle()) power = 0;
        tankDrive(power, -power );
    }

    //reset will reset the NavX
    void setAngle(double angle, boolean reset){
        if(reset) navX.reset();
        if (angle > 180){
            angle -= 360;
        } else if (angle == 180){
            angle = 179.99;
        }
        anglePID.setDesiredValue(angle);
    }

    //returns if the PID is complete, meaning the robot is at the set angle
    boolean isAtAngle(){
        return anglePID.isDone();
    }

    //changes the angle PID time out
    void setAngleTimeOut(long time){
        anglePID.setTimeOut(time);
    }

    //if on will slowly move the robot forward
    void crawl(boolean on){
        if(on) tankDrive(0.5, 0.5);
        else tankDrive(0, 0);
    }

    void stop(){
        tankDrive(0, 0);
    }
}
