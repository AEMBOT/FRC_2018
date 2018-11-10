package org.usfirst.frc.falcons6443.robot.commands.subcommands.Intake;

import org.usfirst.frc.falcons6443.robot.commands.SimpleCommand;
import org.usfirst.frc.falcons6443.robot.utilities.enums.RotationPosition;

public class SetRotationPosition extends SimpleCommand {

    private RotationPosition position;

    public SetRotationPosition(RotationPosition position) {
        super("Set rotation position");
        requires(rotation);
        this.position = position;
    }

    private void setIntakePosition(RotationPosition intakeState){
        //       Logger.log(LoggerSystems.Rotation,"Set flywheel position: " + intakeState.getName());
        switch (intakeState){
            case IntakeUpPosition:
                rotation.currentPosition = RotationPosition.IntakeUpPosition;
                break;
            case IntakeDownPosition:
                rotation.currentPosition = RotationPosition.IntakeDownPosition;
                break;
            case IntakeHalfPosition:
                rotation.currentPosition = RotationPosition.IntakeHalfPosition;
                break;
        }
    }

    @Override
    public void initialize() { setIntakePosition(position);}

    @Override
    protected boolean isFinished() {  return true; }
}
