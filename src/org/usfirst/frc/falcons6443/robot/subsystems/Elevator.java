package org.usfirst.frc.falcons6443.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.falcons6443.robot.RobotMap;
import org.usfirst.frc.falcons6443.robot.utilities.ElevatorEnums;

public class Elevator extends Subsystem {

    //private static final double TransferHeight = 0; //inches -- just leave in ticks???
    //private static final double SwitchHeight = 0; //inches
    //private static final double ScaleHeight = 0; //inches
    //private static final double TopLimitHeight = 0; //inches
    //private static final double BottomLimitHeight = 0; //inches
    //private static final double Buffer = 1; //inches
    //private static final double Diameter = 2; //inches -- do we want??
    //private static final double P = 0;
    //private static final double I = 0;
    //private static final double D = 0;
    //private static final double Eps = 0; //weakest applied power
    //private ElevatorEncoder encoder;
    //private PID pid;

    private Spark motor;

    /*private DigitalInput topScaleLimit;
    private DigitalInput bottomScaleLimit;
    private DigitalInput topSwitchLimit;
    private DigitalInput bottomSwitchLimit;
    private DigitalInput topExchangeLimit;
    private DigitalInput bottomExchangeLimit;*/

    private ElevatorEnums desiredState;
    private ElevatorEnums previousLimit;

    public Elevator (){
        motor = new Spark (RobotMap.ElevatorMotor);
       /* topScaleLimit = new DigitalInput (RobotMap.ElevatorTopScaleLimit);
        bottomScaleLimit = new DigitalInput (RobotMap.ElevatorBottomScaleLimit);
        topSwitchLimit = new DigitalInput (RobotMap.ElevatorTopSwitchLimit);
        bottomSwitchLimit = new DigitalInput (RobotMap.ElevatorBottomSwitchLimit);
        topExchangeLimit = new DigitalInput (RobotMap.ElevatorTopExchangeLimit);
        bottomExchangeLimit = new DigitalInput (RobotMap.ElevatorBottomExchangeLimit);*/
        //encoder = new ElevatorEncoder();
        //pid = new PID(P, I, D, Eps);
        //pid.setMaxOutput(.5);
        //pid.setMinDoneCycles(5);
        //pid.setDoneRange(Buffer);
    }

    @Override
    public void initDefaultCommand() {
    }

    /*private void updatePreviousLimit(){
        if (topScaleLimit.get() || bottomScaleLimit.get()){
            previousLimit = ElevatorEnums.OverSwitch;
        } else if(topExchangeLimit.get() || bottomExchangeLimit.get()){
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
                if (bottomExchangeLimit.get()){
                    power = 0;
                } else if (topExchangeLimit.get()){
                    power = -0.5;
                } else {
                    power = -1;
                }
                break;
            case Scale:
                if (topScaleLimit.get()){
                    power = 0;
                } else if (bottomScaleLimit.get()){
                    power = 0.5;
                } else {
                    power = 1;
                }
                break;
            case Switch:
                if (bottomSwitchLimit.get()) {
                    if (previousLimit == ElevatorEnums.UnderSwitch){
                        power = 0.5;
                        } else if (previousLimit == ElevatorEnums.OverSwitch){
                        power = 0;
                    }
                 }
                if (topSwitchLimit.get()){
                    if (previousLimit == ElevatorEnums.OverSwitch){
                         power = -0.5;
                    } else if (previousLimit == ElevatorEnums.UnderSwitch){
                    power = 0;
                }
                } else {
                    if (previousLimit == ElevatorEnums.UnderSwitch){
                        power = 1;
                    } else {
                        power = -1;
                }
                break;
            }
        }
        motor.set(power);
    }
*/
    public void manual(double power){
        motor.set(power);
    }


    public void up (boolean on){
        if (on){
            motor.set(-1);
        }
    }

    public void down (boolean on){
        if (on){
            motor.set(1);
        }
    }

    public void stop () {
        motor.set(0);
    }

    /*public void setToHeight(ElevatorEnums elevatorState){
        switch(elevatorState){
            case Transfer:
                pid.setDesiredValue(TransferHeight);
                desiredState = ElevatorEnums.Transfer;
                break;
            case Switch:
                pid.setDesiredValue(SwitchHeight);
                desiredState = ElevatorEnums.Switch;
                break;
            case Scale:
                pid.setDesiredValue(ScaleHeight);
                desiredState = ElevatorEnums.Scale;
                break;
            case BottomLimit:
                pid.setDesiredValue(BottomLimitHeight);
                desiredState = ElevatorEnums.BottomLimit;
                break;
            case TopLimit:
                pid.setDesiredValue(TopLimitHeight);
                desiredState = ElevatorEnums.TopLimit;
                break;
        }
    }
    //put in periodic function
    public void moveToHeight(){
        double power = pid.calcPID(getHeight());
        if(bottomLimit.get()){
            encoder.reset();
            setToHeight(ElevatorEnums.BottomLimit);
            power = Math.abs(power);
        } else if (topLimit.get()){
            setToHeight(ElevatorEnums.TopLimit);
            power = -Math.abs(power);
        }
        if (isAtHeight()) {
            stop();
        } else {
            motor.set(power);
        }
    }
    public void stop(){
        motor.set(0);
    }
    public boolean isAtHeight(){
        return pid.isDone();
        /*if((getHeight() + Buffer) > height && (getHeight() - Buffer) < height){
            return true;
        } else {
            return false;
        }
    }
    public double getHeight(){
        return encoder.getTicks() * Diameter * Math.PI / 1024.0;
    } //inches*/
}
