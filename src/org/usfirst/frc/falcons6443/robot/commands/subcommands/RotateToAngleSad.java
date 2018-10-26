package org.usfirst.frc.falcons6443.robot.commands.subcommands;

import org.usfirst.frc.falcons6443.robot.commands.SimpleCommand;
import org.usfirst.frc.falcons6443.robot.hardware.NavX;
import org.usfirst.frc.falcons6443.robot.utilities.*;
import org.usfirst.frc.falcons6443.robot.utilities.enums.LoggerSystems;
import org.usfirst.frc.falcons6443.robot.utilities.pid.PID;

/**
 * Command to rotate the robot to an angle specified in a constructor parameter.
 *
 * @author Christopher Medlin, Ivan Kenevich
 */
public class RotateToAngleSad extends SimpleCommand {
    private PID pid;
    private NavX navX;

    private static final double P = 0.1; //.3
    private static final double I = 0;
    private static final double D = .2; //1.23
    private static final double Eps = 0.68; //.44 //weakest applied power //try upping more???

    private static final double buffer = 4; //degrees
    private static final double counterBuffer = 0.75; //degrees

    private double targetAngle;
    private double oldAngle;
    private int counter;
    private boolean directionPos;
    private boolean done;

    public RotateToAngleSad(double angle) {
        super("Rotate To Angle Beta");
        requires(driveTrain);
        requires(elevator);
        requires(flywheel);
        requires(rotation);
        navX = NavX.get();
        directionPos = true;
        pid = new PID(P, I, D, Eps);
        pid.setMaxOutput(.7);
        pid.setMinDoneCycles(2);
        pid.setFinishedRange(buffer);
        if (angle > 180){
            angle -= 360;
            directionPos = false;
        } else if (angle == 180){
            angle = 179.99;
        }
        targetAngle = angle;
        navX.reset();
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
        if(counter > 50) {
            oldAngle = navX.getYaw();
            counter = 0;
        } else if (counter == 50){
            if((oldAngle + counterBuffer) >= navX.getYaw() && directionPos){
                done = true;
            } else if((oldAngle - counterBuffer) <= navX.getYaw() && !directionPos){
                done = true;
            }
        } else {
            counter++;
        }//was commented out. if issues occur get rid of it, but maybe it magically works??
        rotation.autoMoveIntake();
       // elevator.moveToHeight(true);
        setAngle();
        turnToAngle();
        if(isAtAngle()){
            driveTrain.tankDrive(0, 0);
        }
        //System.out.println("angle: " + navX.getYaw());
        //Logger.log(LoggerSystems.Gyro,"Angle" + Float.toString(navX.getYaw()));
    }

    @Override
    public boolean isFinished() {
        if(isAtAngle()){
            System.out.println("angle: " + navX.getYaw());
            done = true;
            driveTrain.tankDrive(0, 0);
            Logger.log(LoggerSystems.Gyro,"At angle");
        }
        return done;
    }
}
