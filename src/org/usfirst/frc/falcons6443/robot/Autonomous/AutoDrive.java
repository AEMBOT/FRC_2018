package org.usfirst.frc.falcons6443.robot.Autonomous;

import org.usfirst.frc.falcons6443.robot.subsystems.DriveTrainSystem;
import org.usfirst.frc.falcons6443.robot.hardware.NavX;
import org.usfirst.frc.falcons6443.robot.utilities.pid.PID;

public class AutoDrive extends DriveTrainSystem {
    private static final double distanceP = .15; //.42
    private static final double distanceI = 0;
    private static final double distanceD = .1; //3.5
    private static final double distanceEps = 0.5; //0.4???
    private static final double distanceBuffer = 1; //inches //0.5

    private PID distancePID;
    private PID anglePID;
    private NavX navX;

    private static final double angleP = 0.1; //.3
    private static final double angleI = 0;
    private static final double angleD = .2; //1.23
    private static final double angleEps = 0.68; //.44 //try upping more???
    private static final double angleBuffer = 4; //degrees

    public AutoDrive(){
        super();
        distancePID = new PID(distanceP, distanceI, distanceD, distanceEps);
        distancePID.setMaxOutput(.65);
        distancePID.setMinDoneCycles(5);
        distancePID.setFinishedRange(distanceBuffer);
        navX = NavX.get();
        anglePID = new PID(angleP, angleI, angleD, angleEps);
        anglePID.setMaxOutput(.7);
        anglePID.setMinDoneCycles(2);
        anglePID.setFinishedRange(angleBuffer);
    }

    public void driveToDistance(){
        double power = distancePID.calcPID(getLeftDistance());
        tankDrive(power, power + .05);
    }

    public void setDistance(double distance, boolean reset){
        if(reset) reset();
        distancePID.setDesiredValue(distance);
    }

    public boolean isAtDistance(){
        return distancePID.isDone();
    }

    public void turnToAngle(){
        double power = anglePID.calcPID(navX.getYaw());
        if (isAtAngle()) power = 0;
        tankDrive(power, -power );
    }

    public void setAngle(double angle, boolean reset){
        if(reset) navX.reset();
        if (angle > 180){
            angle -= 360;
        } else if (angle == 180){
            angle = 179.99;
        }
        anglePID.setDesiredValue(angle);
    }

    public boolean isAtAngle(){
        return anglePID.isDone();
    }

    public void crawl(boolean on){
        if(on) tankDrive(0.5, 0.5);
        else tankDrive(0, 0);
    }
}
