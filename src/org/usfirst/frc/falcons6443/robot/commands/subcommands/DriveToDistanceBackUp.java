package org.usfirst.frc.falcons6443.robot.commands.subcommands;

import org.usfirst.frc.falcons6443.robot.commands.SimpleCommand;
import org.usfirst.frc.falcons6443.robot.utilities.*;
import org.usfirst.frc.falcons6443.robot.utilities.enums.LoggerSystems;

public class DriveToDistanceBackUp extends SimpleCommand{

    private double targetDistance;
    private double buffer = 1; //inches
    private boolean done;

    public DriveToDistanceBackUp(int distance){
        super("Drive To Distance");
        requires(driveTrain);
        requires(elevator);
        requires(intake);
        targetDistance = distance;
    }

    private void driveToDistance(){
        double power = 0.6; //faster? slower?
        if(driveTrain.getLinearDistance() > (targetDistance - buffer)){
            power = 0;
            done = true;
        }
        driveTrain.tankDrive(power, power);
    }

    @Override
    public void initialize() {
        driveTrain.reset();
        done = false;
    }

    @Override
    public void execute() {
     //   elevator.moveToHeight();
        intake.autoMoveIntake();
        driveToDistance();
        Logger.log(LoggerSystems.Drive,"Distance", Double.toString(driveTrain.getLinearDistance()));
    }

    @Override
    protected boolean isFinished() {
        if(done){
            Logger.log(LoggerSystems.Drive,"Distance " + targetDistance, "At distance " + driveTrain.getLinearDistance());
        }
        return done;
    }
}
