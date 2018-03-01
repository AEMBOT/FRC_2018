package org.usfirst.frc.falcons6443.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.falcons6443.robot.RobotMap;
import org.usfirst.frc.falcons6443.robot.utilities.Enums.ElevatorPosition;

public class Elevator extends Subsystem {

    private Spark motor;

    private DigitalInput topLimit;
    private DigitalInput scaleLimit;
    private DigitalInput switchLimit;
    private DigitalInput bottomLimit;

    private ElevatorPosition desiredState;
    private ElevatorPosition previousLimit;

    public Elevator (){
        motor = new Spark (RobotMap.ElevatorMotor);
        //topLimit = new DigitalInput (RobotMap.ElevatorTopLimit);
        scaleLimit = new DigitalInput (RobotMap.ElevatorScaleLimit);
        //switchLimit = new DigitalInput (RobotMap.ElevatorSwitchLimit);
        bottomLimit = new DigitalInput (RobotMap.ElevatorBottomLimit);
        motor.setInverted(true);
    }

    @Override
    public void initDefaultCommand() {
    }

    private void updatePreviousLimit(){
        if (!scaleLimit.get() || !topLimit.get()){
            previousLimit = ElevatorPosition.OverSwitch;
        } else if(!bottomLimit.get()){
            previousLimit = ElevatorPosition.UnderSwitch;
        }
    }

    /*public void setToHeight (ElevatorPosition elevatorState){
        switch (elevatorState){
            case Exchange:
                desiredState = ElevatorPosition.Exchange;
                break;
            case Switch:
                desiredState = ElevatorPosition.Switch;
                break;
            case Scale:
                desiredState = ElevatorPosition.Scale;
                break;
            case Stop:
                desiredState = ElevatorPosition.Stop;
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
                    if (previousLimit == ElevatorPosition.UnderSwitch){
                        power = 1;
                    } else {
                        power = -1;
                    }
                }
                break;
            case Stop:
                power = 0;
                break;
        }
        if(!topLimit.get()){
            power = -1;
        }
        motor.set(power);
    }*/

   public void limitTest(){
       double power = -1;
      // if (!bottomLimit.get()){
           power = 0;
      // }
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
