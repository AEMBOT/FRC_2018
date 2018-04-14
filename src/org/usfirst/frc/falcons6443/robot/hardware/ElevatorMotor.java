package org.usfirst.frc.falcons6443.robot.hardware;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Talon;
import org.usfirst.frc.falcons6443.robot.Robot;
import org.usfirst.frc.falcons6443.robot.RobotMap;

public class ElevatorMotor {
    private Spark singleMotor;
    private SpeedControllerGroup redLineMotors;
    private boolean redLine;

    public ElevatorMotor(){
        if(RobotMap.RedLine){
            redLineMotors = new SpeedControllerGroup(new Talon(RobotMap.ElevatorRedLineMotor1),
                    new Talon(RobotMap.ElevatorRedLineMotor2), new Talon(RobotMap.ElevatorRedLineMotor3),
                    new Talon(RobotMap.ElevatorRedLineMotor4));
        } else {
            singleMotor = new Spark(RobotMap.ElevatorMotor);
            singleMotor.setInverted(true);
        }
        redLine = RobotMap.RedLine;
    }

    public void setSpeed(double speed) {
        if (redLine) {
            redLineMotors.set(speed);
        } else {
            singleMotor.set(speed);
        }
    }

    public double getSpeed() {
        if (redLine) {
            return redLineMotors.get();
        } else {
            return singleMotor.get();
        }
    }
}
