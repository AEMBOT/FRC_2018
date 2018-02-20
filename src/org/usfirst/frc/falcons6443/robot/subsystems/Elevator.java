package org.usfirst.frc.falcons6443.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.falcons6443.robot.RobotMap;
import org.usfirst.frc.falcons6443.robot.utilities.ElevatorEnums;

public class Elevator extends Subsystem {

    private Spark motor;

    private DigitalInput topLimit;
    private DigitalInput scaleLimit;
    private DigitalInput switchLimit;
    private DigitalInput bottomLimit;

    private ElevatorEnums desiredState;
    private ElevatorEnums previousLimit;

    public Elevator (){
        motor = new Spark (RobotMap.ElevatorMotor);
        //topLimit = new DigitalInput (8);
        scaleLimit = new DigitalInput (8);
        //switchLimit = new DigitalInput (RobotMap.ElevatorBottomSwitchLimit);
        bottomLimit = new DigitalInput (9);
        motor.setInverted(true);
    }

    @Override
    public void initDefaultCommand() {
    }

   /* private void updatePreviousLimit(){
        if (!scaleLimit.get() || !topLimit.get()){
            previousLimit = ElevatorEnums.OverSwitch;
        } else if(!bottomLimit.get()){
            previousLimit = ElevatorEnums.UnderSwitch;
        }
    }

    public void setToHeight (ElevatorEnums elevatorState){
        switch (elevatorState){
            case Exchange:
                desiredState = ElevatorEnums.Exchange;
                break;
            case Switch:
                desiredState = ElevatorEnums.Switch;
                break;
            case Scale:
                desiredState = ElevatorEnums.Scale;
                break;
        }
    }

    //put in periodic function
    public void moveToHeight(){
        double power = 0;
        updatePreviousLimit();

        switch (desiredState) {
            case Exchange:
                if (!bottomLimit.get()){
                    power = 0;
                } else {
                    power = -1;
                }
                break;
            case Scale:
                if (!scaleLimit.get()){
                    power = 0;
                } else {
                    power = 1;
                }
                break;
            case Switch:
                if (!switchLimit.get()){
                    power = 0;
                } else {
                    if (previousLimit == ElevatorEnums.UnderSwitch){
                        power = 1;
                    } else {
                        power = -1;
                }
                break;
            }
        }
        if(!topLimit.get()){
            power = -1;
        }
        motor.set(power);
    }

    public void manual(double power){
        motor.set(power);
    }

    public boolean lowerLimit(){
        return !bottomLimit.get();
    }*/

   public void limitTest(){
       double power = -1;
       if (!bottomLimit.get()){
           power = 0;
       }
       //if (!scaleLimit.get()){
         ///  power = 0;
       //}
       motor.set(power);
   }

    public void up (boolean on){
        if (on){
            motor.set(1);
        }
    }

    public void down (boolean on){
        if (on){
            motor.set(-1);
        }
    }

    public void stop () {
        motor.set(0);
    }
}
