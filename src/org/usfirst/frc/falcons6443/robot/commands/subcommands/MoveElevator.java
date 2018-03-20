package org.usfirst.frc.falcons6443.robot.commands.subcommands;

import org.usfirst.frc.falcons6443.robot.commands.SimpleCommand;
import org.usfirst.frc.falcons6443.robot.utilities.enums.ElevatorPosition;

public class MoveElevator extends SimpleCommand{

    ElevatorPosition position;
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
