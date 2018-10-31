package org.usfirst.frc.falcons6443.robot.commands.subcommands.Intake;

import org.usfirst.frc.falcons6443.robot.commands.SimpleCommand;
import org.usfirst.frc.falcons6443.robot.utilities.enums.RotationPosition;
import sun.nio.ch.SelectorImpl;

public class AutoRotateIntake extends SimpleCommand {

    private RotationPosition position;

    public AutoRotateIntake(RotationPosition position){
        super("Rotate Intake");
        requires(rotation);
        this.position = position;
    }

    @Override
    public void initialize() {
        rotation.setIntakePosition(position);
        setTimeout(2.5);
    }

    @Override
    public void execute() { rotation.autoMoveIntake(); }

    @Override
    protected boolean isFinished() {
        return isTimedOut();
    }
}
