package org.usfirst.frc.falcons6443.robot.hardware;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Talon;
import org.usfirst.frc.falcons6443.robot.RobotMap;

public class ElevatorMotor {
    private Spark singleMotor;
    private SpeedControllerGroup redlineMotors;
    private boolean redline;

    public ElevatorMotor(){
        if(RobotMap.Redline){
            redlineMotors = new SpeedControllerGroup(new Talon(RobotMap.ElevatorRedlineMotor1),
                    new Talon(RobotMap.ElevatorRedlineMotor2), new Talon(RobotMap.ElevatorRedlineMotor3),
                    new Talon(RobotMap.ElevatorRedlineMotor4));
        } else {
            singleMotor = new Spark(RobotMap.ElevatorMotor);
            singleMotor.setInverted(true);
        }
        redline = RobotMap.Redline;
    }

    public void set(double speed) {
        if (redline) {
            redlineMotors.set(speed);
        } else {
            singleMotor.set(speed);
        }
    }
}
