package org.usfirst.frc.falcons6443.robot.commands.subcommands.Elevator;

import org.usfirst.frc.falcons6443.robot.commands.SimpleCommand;
import org.usfirst.frc.falcons6443.robot.utilities.enums.ElevatorPosition;

public class MoveElevatorTeleOp extends SimpleCommand {

    public MoveElevatorTeleOp(){
        super("Momve elevator teleop");
        requires(elevator);
    }

    //put in periodic function
    private void moveToHeightButton(){
        double power = 0;
        elevator.updatePreviousLimit();
        elevator.stopTimer();

        switch (elevator.desiredState) {
            case Exchange:
                if (elevator.getBottomHeight()){
                    power = 0;
                    //    resetEncoder();
                } else {
                    power = elevator.downSpeed;
                }
//                Logger.log(LoggerSystems.Elevator,"Bottom limit: " + Boolean.toString(bottomLimit.get()));
                break;
            case Scale:
                if (elevator.getScaleHeight()){
                    power = 0;
                } else {
                    power = elevator.upSpeed;
                }
                //              Logger.log(LoggerSystems.Elevator,"Scale limit: " + Boolean.toString(scaleLimit.get()));
                break;
            case Switch:
                    if (elevator.getSwitchHeight()) {
                        power = 0;
                    } else {
                        if (elevator.previousLimit == ElevatorPosition.UnderSwitch) {
                            power = elevator.upSpeed;
                        } else {
                            power = elevator.downSpeed;
                        }
                }
                break;
            case Stop:
//                Logger.log(LoggerSystems.Elevator,"move to height: Stop enum");
                power = 0;
                break;
        }
        if (elevator.getBottomHeight()){
            power = 0;
            //    resetEncoder();
        } else if (elevator.scaleLimit.get() && power < 0){
            power = 0;
        }
        if(!elevator.getManual()) {
            elevator.motor.setSpeed(power);
        }
    }

    private void manual(double x){
        elevator.setManual(true);
        if(Math.abs(x) < elevator.constantSpeed) x = elevator.constantSpeed;
        if(x < 0) x = x * .4;

       /* if(!bottomLimit.get() && x < 0) {
            motor.setSpeed(0);
            resetEncoder();
        } else if(encoder.getDistance() < scaleHeight && scaleLimit.get()){
            motor.setSpeed(x);
        } else {
            if(x < 0) motor.setSpeed(x);
            else motor.setSpeed(constantSpeed);
            System.out.println("Max Height!!");
        }*/
        elevator.motor.setSpeed(x);
        //System.out.println("E Enc: " + encoder.get());
        System.out.println("Manual");
    }

    @Override
    public void initialize() {  }

    @Override
    public void execute() {
        if(elevator.getManual()){
            manual(elevator.manualSpeed);
        } else {
            moveToHeightButton();
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

}
