package org.usfirst.frc.falcons6443.robot.commands.subcommands;

import org.usfirst.frc.falcons6443.robot.commands.SimpleCommand;
import org.usfirst.frc.falcons6443.robot.hardware.NavX;
import org.usfirst.frc.falcons6443.robot.utilities.*;
import org.usfirst.frc.falcons6443.robot.utilities.enums.LoggerSystems;
import org.usfirst.frc.falcons6443.robot.utilities.pid.PID;
import org.usfirst.frc.falcons6443.robot.utilities.pid.PIDTimer;

/**
 * Command to rotate the robot to an angle specified in a constructor parameter.
 *
 * @author Christopher Medlin, Ivan Kenevich
 */
public class RotateToAngleSad extends SimpleCommand {
    private PID pid;
    private NavX navX;

    public static double P = 0.2; //.3
    public static double I = 0;
    public static double D = 0.85; //1.23
    public static double Eps = 0;

    private static final double buffer = 1; //degrees

    private double targetAngle;

    public RotateToAngleSad(double angle) {
        super("Rotate To Angle Beta");
        requires(driveTrain);
        navX = NavX.get();
        //pidt = new PIDTimer(P, I, D, Eps, 20000);
        pid = new PIDTimer(P, I, D, Eps, 20000);
        pid.setMaxOutput(.72);
        pid.setMinDoneCycles(2);
        pid.setFinishedRange(buffer);
        if (angle > 180){
            angle -= 360;
        } else if (angle == 180){
            angle = 179.99;
        }
        targetAngle = angle;
        navX.reset();
        //pidt.resetTimeOut();
    }

    private void turnToAngle(){
        double power = pid.calcPID(navX.getYaw());
        driveTrain.tankDrive(power, -power );
        System.out.println("speed: " + power);
    }

    private void setAngle(){
        pid.setDesiredValue(targetAngle);
    }

    private boolean isAtAngle(){
        return pid.isDone();
    }

    @Override
    public void initialize() {
        navX.reset();
    }

    @Override
    public void execute() {
        setAngle();
        turnToAngle();
        System.out.println("angle: " + navX.getYaw());
        //Logger.log(LoggerSystems.Gyro,"Angle" + Float.toString(navX.getYaw()));
    }

    @Override
    public boolean isFinished() {
        if(isAtAngle()){
            driveTrain.tankDrive(0, 0);
            System.out.println("DONE");
        }
        return isAtAngle();
    }
}
