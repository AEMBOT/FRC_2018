package org.usfirst.frc.falcons6443.robot.commands.subcommands;

import org.usfirst.frc.falcons6443.robot.commands.SimpleCommand;
import org.usfirst.frc.falcons6443.robot.utilities.Logger;
import org.usfirst.frc.falcons6443.robot.utilities.enums.*;

public class MoveIntake extends SimpleCommand {

    private RotationPosition m_position;
    private boolean m_output;
    private boolean m_stop;

    public MoveIntake(RotationPosition position, boolean output, boolean stop, boolean reset){
        super("Move Elevator System");
        requires(flywheel);
        requires(rotation);
        m_position = position;
        m_output = output;
        m_stop = stop;
    }

    @Override
    public void initialize() {
        if(m_position == RotationPosition.IntakeHalfPosition){
            rotation.setIntakePosition(m_position);
        }
    }

    @Override
    public void execute() {
        if (m_output){
            Logger.log(LoggerSystems.Flywheel,"Auto output");
            flywheel.output();
        }
        if(m_stop){
            Logger.log(LoggerSystems.Flywheel,"Auto stop");
            flywheel.stop();
        }
    }

    @Override
    public boolean isFinished() { return true; }
}
