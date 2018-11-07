package org.usfirst.frc.falcons6443.robot.commands.subcommands.Elevator;

import org.usfirst.frc.falcons6443.robot.utilities.enums.ElevatorPosition;

public class MoveElevatorTeleOp {

    //put in periodic function
    public void moveToHeight(boolean auto){
        double power = 0;
        updatePreviousLimit();
            stopTimer();

        switch (desiredState) {
            case Exchange:
                if (getBottomHeight()){
                    power = 0;
                    //    resetEncoder();
                } else {
                    power = downSpeed;
                }
//                Logger.log(LoggerSystems.Elevator,"Bottom limit: " + Boolean.toString(bottomLimit.get()));
                break;
            case Scale:
                if (getScaleHeight()){
                    power = 0;
                } else {
                    power = upSpeed;
                }
                //              Logger.log(LoggerSystems.Elevator,"Scale limit: " + Boolean.toString(scaleLimit.get()));
                break;
            case Switch:
                if(auto){
                    if(getSwitchHeight() || getTime() > autoTime){
                        power = 0;
                    } else if (getTime() < autoTime)  {
                        power = upSpeed;
                    }
                } else {
                    if (getSwitchHeight()) {
                        power = 0;
                    } else {
                        if (previousLimit == ElevatorPosition.UnderSwitch) {
                            power = upSpeed;
                        } else {
                            power = downSpeed;
                        }
                    }
                }
                break;
            case Stop:
//                Logger.log(LoggerSystems.Elevator,"move to height: Stop enum");
                power = 0;
                break;
        }
        if (getBottomHeight()){
            power = 0;
            //    resetEncoder();
        } else if (scaleLimit.get() && power < 0){
            power = 0;
        }
        if(!getManual()) {
            motor.setSpeed(power);
        }
    }
}
