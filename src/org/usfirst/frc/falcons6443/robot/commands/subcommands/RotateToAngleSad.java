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
    private PIDTimer pidt;
    private NavX navX;

    private static final double P = 0.1; //.3
    private static final double I = 0;
    private static final double D = .2; //1.23
    private static final double Eps = 0;

    private static final double buffer = 2; //degrees

    private double targetAngle;

    public RotateToAngleSad(double angle) {
        super("Rotate To Angle Beta");
        requires(driveTrain);
        navX = NavX.get();
        pidt = new PIDTimer(P, I, D, Eps, 10);
        pidt.setMaxOutput(.7);
        pidt.setMinDoneCycles(5);
        pidt.setFinishedRange(buffer);
        if (angle > 180){
            angle -= 360;
        } else if (angle == 180){
            angle = 179.99;
        }
        targetAngle = angle;
        navX.reset();
    }

    private void turnToAngle(){
        double power = pidt.calcPID(navX.getYaw());
        driveTrain.tankDrive(power, -power );
    }

    private void setAngle(){
        pidt.setDesiredValue(targetAngle);
    }

    private boolean isAtAngle(){
        return pidt.isDone();
    }

    @Override
    public void initialize() {
        navX.reset();
    }

    @Override
    public void execute() {
        setAngle();
        turnToAngle();
        if(isAtAngle()){
            driveTrain.tankDrive(0, 0);
        }
        System.out.println("angle: " + navX.getYaw());
        //Logger.log(LoggerSystems.Gyro,"Angle" + Float.toString(navX.getYaw()));
    }

    @Override
    public boolean isFinished() {
        if(isAtAngle()){
            System.out.println("Finishing angle: " + navX.getYaw());
            driveTrain.tankDrive(0, 0);
            //Logger.log(LoggerSystems.Gyro,"At angle");
        }
        return isAtAngle();
    }

    @Override
    public void end(){
        driveTrain.tankDrive(0, 0);
    }
}
