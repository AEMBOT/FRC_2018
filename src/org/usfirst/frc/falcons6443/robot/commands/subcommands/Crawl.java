package org.usfirst.frc.falcons6443.robot.commands.subcommands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.falcons6443.robot.commands.SimpleCommand;

public class Crawl extends SimpleCommand {
    private boolean m_on;
    private boolean m_off;

    public Crawl(boolean on){
        super("Crawl");
        requires(driveTrain);
        m_on = on;
    }

    @Override
    public void initialize() { m_off = false; }

    @Override
    public void execute() {
        if (m_on){
            driveTrain.tankDrive(.3, .3);
        } else {
            driveTrain.tankDrive(0, 0);
        }

        m_off = true;
    }

    @Override
    public boolean isFinished() { return m_off; }
}