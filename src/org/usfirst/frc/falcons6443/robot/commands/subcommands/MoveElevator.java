package org.usfirst.frc.falcons6443.robot.commands.subcommands;

import org.usfirst.frc.falcons6443.robot.commands.SimpleCommand;
import org.usfirst.frc.falcons6443.robot.utilities.Logger;
import org.usfirst.frc.falcons6443.robot.utilities.enums.*;

public class MoveElevator extends SimpleCommand{

    private ElevatorPosition position;

    public MoveElevator(ElevatorPosition pos){
        super("Move ElevatorSystem");
        //requires(elevator);
        position = pos;
    }

    @Override
    public void initialize() {
        //elevator.setToHeight(position);
        Logger.log(LoggerSystems.Auto, "Setting elevator position" + position);
       // if(position != ElevatorPosition.Exchange && position != ElevatorPosition.Stop){
            //elevator.startTimer();
       // }
    }

    @Override
    public void execute() {    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
