package org.usfirst.frc.falcons6443.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.falcons6443.robot.RobotMap;
import org.usfirst.frc.falcons6443.robot.utilities.Enums.Enums;

public class Elevator extends Subsystem {

    private Spark motor;

    private DigitalInput topLimit;
    private DigitalInput scaleLimit;
    private DigitalInput switchLimit;
    private DigitalInput bottomLimit;

    private Enums desiredState;
    private Enums previousLimit;

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

   /* private void updatePreviousLimit(){
        if (!scaleLimit.get() || !topLimit.get()){
            previousLimit = Enums.OverSwitch;
        } else if(!bottomLimit.get()){
            previousLimit = Enums.UnderSwitch;
        }
    }

    public void setToHeight (Enums elevatorState){
        switch (elevatorState){
            case Exchange:
                desiredState = Enums.Exchange;
                break;
            case Switch:
                desiredState = Enums.Switch;
                break;
            case Scale:
                desiredState = Enums.Scale;
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
                    if (previousLimit == Enums.UnderSwitch){
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
