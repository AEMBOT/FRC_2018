package org.usfirst.frc.falcons6443.robot.commands.subcommands.Intake;

import org.usfirst.frc.falcons6443.robot.commands.SimpleCommand;

public class IntakeCube extends SimpleCommand {

    public IntakeCube() {
        super("Intake cube");
        requires(flywheel);
    }

    @Override
    public void initialize() { }

    @Override
    public void execute() {  flywheel.intake();  }


    @Override
    protected boolean isFinished() {  return false; }

    @Override
    public void end(){ flywheel.stop(); }
}
