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
    private boolean m_elevator;

    public DriveToDistanceBackUp(int distance, boolean fast, boolean elvator){
        super("Drive To Distance");
        requires(driveTrain);
        requires(elevator);
        requires(flywheel);
        requires(rotation);
        targetDistance = distance;
        m_fast = fast;
        m_elevator = elvator;
    }

    private void driveToDistance(boolean fast){
      //  System.out.println("enc " + driveTrain.getLeftDistance());
        double power;
        if(!m_fast){
            power = 0.8; //faster? slower? //.53

        } else {
            power = .7;
        }

       /* if(driveTrain.getLeftDistance() > (targetDistance - buffer)){
            power = 0;
            done = true;
        }*/
        driveTrain.tankDrive(power, power);
    }

    @Override
    public void initialize() {
        driveTrain.reset();
        done = false;
        rotation.startTimer();
    }

    @Override
    public void execute() {
        if(m_elevator){
            elevator.moveToHeight(true);
        }
        rotation.autoMoveIntake();
        driveToDistance(m_fast);
        if(elevator.getTime() > 1){
            elevator.setToHeight(ElevatorPosition.Stop);
        }
      //  Logger.log(LoggerSystems.Drive,"Distance: " + Double.toString(driveTrain.getLeftDistance()));
    }

    @Override
    protected boolean isFinished() {
        if(done){
          //  Logger.log(LoggerSystems.Drive,"TargetDist: " + targetDistance + ". At distance: " + driveTrain.getLeftDistance());
        }
        return done;
    }
}
