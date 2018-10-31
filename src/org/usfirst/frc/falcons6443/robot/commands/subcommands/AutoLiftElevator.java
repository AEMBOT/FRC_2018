package org.usfirst.frc.falcons6443.robot.commands.subcommands;

import org.usfirst.frc.falcons6443.robot.commands.SimpleCommand;
import org.usfirst.frc.falcons6443.robot.utilities.enums.ElevatorPosition;

public class AutoLiftElevator extends SimpleCommand {

    private ElevatorPosition position;

    public AutoLiftElevator(ElevatorPosition position){
        super("Lift ElevatorSystem");
        requires(elevator);
        this.position = position;
    }

    @Override
    public void initialize() {
        elevator.setToHeight(position);
        elevator.startTimer();
    }

    @Override
    public void execute() {  elevator.moveToHeight(true);  }

    @Override
    public boolean isFinished() {
        return elevator.getTime() > 1;
    }
}
