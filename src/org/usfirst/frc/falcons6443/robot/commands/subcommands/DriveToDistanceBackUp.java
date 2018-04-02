package org.usfirst.frc.falcons6443.robot.commands.subcommands;

import org.usfirst.frc.falcons6443.robot.commands.SimpleCommand;
import org.usfirst.frc.falcons6443.robot.utilities.*;
import org.usfirst.frc.falcons6443.robot.utilities.enums.ElevatorPosition;
import org.usfirst.frc.falcons6443.robot.utilities.enums.LoggerSystems;

public class DriveToDistanceBackUp extends SimpleCommand{

    private double targetDistance;
    private double buffer = 1; //inches
    private boolean done;
    private boolean m_fast;

    public DriveToDistanceBackUp(int distance, boolean fast){
        super("Drive To Distance");
        requires(navigation);
        requires(driveTrain);
        requires(elevator);
        requires(intake);
        targetDistance = distance;
        m_fast = fast;
    }

    private void driveToDistance(boolean fast){
        System.out.println("enc " + driveTrain.getLeftDistance());
        double power;
        if(fast){
            power = 0.6; //faster? slower? //.53
        } else {
            power = 0.57;
        }
        if(driveTrain.getLeftDistance() > (targetDistance - buffer)){
            power = 0;
            done = true;
        }
        driveTrain.tankDrive(power, power);
    }

    @Override
    public void initialize() {
        driveTrain.reset();
        done = false;
        intake.startTimer();
    }

    @Override
    public void execute() {
        elevator.moveToHeight(true);
        intake.autoMoveIntake();
        driveToDistance(m_fast);
        if(elevator.getTime() > 5){
            elevator.setToHeight(ElevatorPosition.Stop);
        }
        Logger.log(LoggerSystems.Drive,"Distance", Double.toString(driveTrain.getLeftDistance()));
    }

    @Override
    protected boolean isFinished() {
        if(done){
            Logger.log(LoggerSystems.Drive,"Distance " + targetDistance, "At distance " + driveTrain.getLeftDistance());
        }
        return done;
    }
}
