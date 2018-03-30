package org.usfirst.frc.falcons6443.robot.commands.subcommands;

import org.usfirst.frc.falcons6443.robot.commands.SimpleCommand;
import org.usfirst.frc.falcons6443.robot.hardware.NavX;
import org.usfirst.frc.falcons6443.robot.utilities.*;
import org.usfirst.frc.falcons6443.robot.utilities.enums.LoggerSystems;

public class RotateToAngleSadBackUp extends SimpleCommand {

    private double buffer = 1; //0.5?? //degrees
    private boolean done;
    private double targetAngle;

    private NavX navX;

    public RotateToAngleSadBackUp(double angle) {
        super("Rotate To Angle Beta");
        requires(driveTrain);
        requires(elevator);
        requires(intake);
        navX = navX.get();
        if (angle > 180){
            angle -= 360;
        } else if (angle == 180){
            angle = 179.99;
        }
        targetAngle = angle;
    }

    private void turnToAngle(){
        double power = .72; //too fast? too slow?

        if(targetAngle >= 0 && navX.getYaw() > (targetAngle - buffer)){
            done = true;
            power = 0;
        } else if (targetAngle < 0 && navX.getYaw() < (targetAngle + buffer)){
            done = true;
            power = 0;
        }

        driveTrain.tankDrive(power, -power ); //correct directions??
    }

    @Override
    public void initialize() {
        navX.reset();
        done = false;
    }

    @Override
    public void execute() {
        // elevator.moveToHeight(true);
        intake.autoMoveIntake();
        turnToAngle();
        Logger.log(LoggerSystems.Gyro,"Angle: " + Float.toString(navX.getYaw()));
    }

    @Override
    public boolean isFinished() {
        if(done){
            driveTrain.tankDrive(0, 0);
            Logger.log(LoggerSystems.Gyro,"Angle: " + targetAngle+ ", at angle " + navX.getYaw());
        }
        return done;
    }
}
