package org.usfirst.frc.falcons6443.robot.commands.subcommands.Intake;

import org.usfirst.frc.falcons6443.robot.commands.SimpleCommand;

public class OutputCube extends SimpleCommand {

    private boolean slowOutput;

    public OutputCube(boolean slowOutput) {
        super("Output cube");
        requires(flywheel);
        this.slowOutput = slowOutput;
    }

    @Override
    public void initialize() {
        setTimeout(2);
    }

    @Override
    public void execute() {
        if(slowOutput) flywheel.slowOutput();
        else flywheel.output();
    }

    @Override
    public void end(){
        flywheel.stop();
    }

    @Override
    protected boolean isFinished() {
        return isTimedOut();
    }
}
