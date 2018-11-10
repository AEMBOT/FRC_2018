package org.usfirst.frc.falcons6443.robot.commands.subcommands.Elevator;

import org.usfirst.frc.falcons6443.robot.commands.SimpleCommand;
import org.usfirst.frc.falcons6443.robot.utilities.enums.ElevatorPosition;

public class AutoLiftElevator extends SimpleCommand {

    private ElevatorPosition position;

    public AutoLiftElevator(){
        super("Lift ElevatorSystem");
        requires(elevator);
        this.position = position;
    }

    @Override
    public void initialize() {
        elevator.startTimer();
    }

    @Override
    public void execute() {
            double power = 0;
            elevator.updatePreviousLimit();

            if(elevator.getTime() > elevator.autoTime){
                elevator.desiredState = ElevatorPosition.Stop;
//            Logger.log(LoggerSystems.Elevator, "Ran overtime in auto: stopped elevator");
            }

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
                        if(elevator.getSwitchHeight() || elevator.getTime() > elevator.autoTime){
                            power = 0;
                        } else if (elevator.getTime() < elevator.autoTime)  {
                            power = elevator.upSpeed;
                        }
                    break;
                case Stop:
//                Logger.log(LoggerSystems.Elevator,"move to height: Stop enum");
                    power = 0;
                    break;
            }
            power = -power;
            if (elevator.scaleLimit.get() && power < 0){
                power = 0;
            }
            elevator.motor.setSpeed(power);
    }

    @Override
    public boolean isFinished() {
        return elevator.getTime() > 1;
    }

    @Override
    public void end(){ elevator.stop();}
}
