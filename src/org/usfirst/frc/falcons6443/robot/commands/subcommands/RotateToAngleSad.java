package org.usfirst.frc.falcons6443.robot.commands.subcommands;

import org.usfirst.frc.falcons6443.robot.commands.SimpleCommand;
import org.usfirst.frc.falcons6443.robot.utilities.PID;

/**
 * Command to rotate the robot to an angle specified in a constructor parameter.
 *
 * @author Christopher Medlin, Ivan Kenevich
 */
public class RotateToAngleSad extends SimpleCommand {

    private static final double P = 0.3; //.3
    private static final double I = 0;
    private static final double D = 1.23; //1.23
    private static final double Eps = 0.7; //weakest applied power

    private static final double buffer = 0.5; //1 //degrees

    private PID pid;
    private double targetAngle;

    public RotateToAngleSad(double angle) {
        super("Restricted PID Drive");
        requires(navigation);
        requires(driveTrain);
        pid = new PID(P, I, D, Eps);
        pid.setMaxOutput(.75);
        pid.setMinDoneCycles(5);
        pid.setDoneRange(buffer);
        if (angle > 180){
            angle -= 360;
        }
        targetAngle = angle;
    }

    private void turnToAngle(){
        double power = pid.calcPID(navigation.getYaw());
        driveTrain.tankDrive(power, -power );
    }

    private void setAngle(){
        pid.setDesiredValue(targetAngle);
    }
    private boolean isAtAngle(){
        return pid.isDone();
    }

    @Override
    public void initialize() {
        navigation.reset();
        //direction = targetAngle < 0 ? -1 : 1;
    }

    @Override
    public void execute() {
        setAngle();
        turnToAngle();
        if(isAtAngle()){
            driveTrain.tankDrive(0, 0);
        }
        System.out.println("yaw: " + navigation.getYaw());
    }

    @Override
    public boolean isFinished() {
        if(isAtAngle()){
            driveTrain.tankDrive(0, 0);
        }
        return isAtAngle();
    }
}
