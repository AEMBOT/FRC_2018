package org.usfirst.frc.falcons6443.robot.commands.subcommands;

import org.usfirst.frc.falcons6443.robot.commands.SimpleCommand;
import org.usfirst.frc.falcons6443.robot.utilities.Logger;
import org.usfirst.frc.falcons6443.robot.utilities.enums.*;
import edu.wpi.first.wpilibj.Timer;

public class MoveElevator extends SimpleCommand{

    private ElevatorPosition position;
    private boolean off;

    public MoveElevator(ElevatorPosition pos){
        super("Move ElevatorSystem");
        requires(elevator);
        position = pos;
        off = false;
    }

    @Override
    public void initialize() {
        elevator.setToHeight(position);
        Logger.log(LoggerSystems.Auto, "Setting elevator position", position.getValue());
        if(position != ElevatorPosition.Exchange && position != ElevatorPosition.Stop){
            elevator.startTimer();
        }
    }

    @Override
    public void execute() {
       off = true;
    }

    @Override
    public boolean isFinished() {
        return off;
    }
}
