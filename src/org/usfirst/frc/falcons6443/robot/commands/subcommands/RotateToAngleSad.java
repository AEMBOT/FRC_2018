package org.usfirst.frc.falcons6443.robot.commands.subcommands;

import org.usfirst.frc.falcons6443.robot.commands.SimpleCommand;
import org.usfirst.frc.falcons6443.robot.hardware.NavX;
import org.usfirst.frc.falcons6443.robot.utilities.*;
import org.usfirst.frc.falcons6443.robot.utilities.enums.LoggerSystems;

/**
 * Command to rotate the robot to an angle specified in a constructor parameter.
 *
 * @author Christopher Medlin, Ivan Kenevich
 */
public class RotateToAngleSad extends SimpleCommand {

    private static final double P = 0.15; //.3
    private static final double I = 0;
    private static final double D = .4; //1.23
    private static final double Eps = 0.44; //weakest applied power

    private static final double buffer = 1; //0.5?? //degrees
    private static final double counterBuffer = 0.5; //degrees

    private double oldAngle;
    private int counter;
    private boolean directionPos;
    private boolean done;

    private NavX navX;
    private PID pid;
    private double targetAngle;

    public RotateToAngleSad(double angle) {
        super("Rotate To Angle Beta");
        requires(driveTrain);
        requires(elevator);
        requires(intake);
        navX = navX.get();
        directionPos = true;
        pid = new PID(P, I, D, Eps);
        pid.setMaxOutput(.7);
        pid.setMinDoneCycles(5);
        pid.setDoneRange(buffer);
        if (angle > 180){
            angle -= 360;
            directionPos = false;
        } else if (angle == 180){
            angle = 179.99;
        }
        targetAngle = angle;
    }

    private void turnToAngle(){
        double power = pid.calcPID(navX.getYaw());
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
        navX.reset();
        oldAngle = 0;
        counter = 0;
        done = false;
    }

    @Override
    public void execute() {
        //backup counter
/*        if(counter > 50) {
            oldAngle = navigation.getYaw();
            counter = 0;
        } else if (counter == 50){
            if((oldAngle + counterBuffer) >= navigation.getYaw() && directionPos){
                done = true;
            } else if((oldAngle - counterBuffer) <= navigation.getYaw() && !directionPos){
                done = true;
            }
        } else {
            counter++;
        }*/
       // elevator.moveToHeight(true);
        intake.autoMoveIntake();
        setAngle();
        turnToAngle();
        if(isAtAngle()){
            driveTrain.tankDrive(0, 0);
        }
        Logger.log(LoggerSystems.Gyro,"Angle", Float.toString(navX.getYaw()));
    }

    @Override
    public boolean isFinished() {
        if(isAtAngle()){
            done = true;
            driveTrain.tankDrive(0, 0);
            Logger.log(LoggerSystems.Gyro,"Angle", "at angle");
        }
        return done;
    }
}
