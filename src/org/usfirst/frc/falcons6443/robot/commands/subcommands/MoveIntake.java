package org.usfirst.frc.falcons6443.robot.commands.subcommands;

import org.usfirst.frc.falcons6443.robot.commands.SimpleCommand;
import org.usfirst.frc.falcons6443.robot.utilities.Enums.IntakePosition;

public class MoveIntake extends SimpleCommand {

    private IntakePosition position;
    private boolean out;
    private boolean stp;
    private boolean off;

    public MoveIntake(IntakePosition pos, boolean output, boolean stop){
        super("Move ElevatorSystem");
        requires(intake);
        position = pos;
        off = false;
        out = output;
        stp = stop;
    }

    @Override
    public void initialize() {
        intake.setIntakePosition(position);
    }

    @Override
    public void execute() {
        if (out){
            intake.output();
        }
        if(stp){
            intake.stop();
        }
        off = true;
    }

    @Override
    public boolean isFinished() {
        return off;
    }
}
