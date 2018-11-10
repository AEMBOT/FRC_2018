package org.usfirst.frc.falcons6443.robot.commands.subcommands.Elevator;

import org.usfirst.frc.falcons6443.robot.commands.SimpleCommand;
import org.usfirst.frc.falcons6443.robot.utilities.enums.ElevatorPosition;

public class SetElevatorPosition extends SimpleCommand {

    private ElevatorPosition position;

    public SetElevatorPosition(ElevatorPosition position){
        super("Set Elevator Position");
        requires(elevator);
        this.position = position;
    }

    public SetElevatorPosition(double manualSpeed){
        super("Set Elevator Position");
        requires(elevator);
        elevator.manualSpeed = manualSpeed;
        elevator.setManual(true);
    }

    @Override
    public void initialize() { if(!elevator.getManual()) setToHeight(position); }

    @Override
    protected boolean isFinished() {
        return true;
    }

    private void setToHeight (ElevatorPosition elevatorState){
//        Logger.log(LoggerSystems.Elevator,"Set elevator position: " + elevatorState.getName());
        switch (elevatorState){
            case Exchange:
                elevator.desiredState = ElevatorPosition.Exchange;
                elevator.setManual(false);
                break;
            case Switch:
                elevator.desiredState = ElevatorPosition.Switch;
                elevator.setManual(false);
                break;
            case Scale:
                elevator.desiredState = ElevatorPosition.Scale;
                elevator.setManual(false);
                break;
            case Stop:
                elevator.desiredState = ElevatorPosition.Stop;
                break;
        }
    }
}
