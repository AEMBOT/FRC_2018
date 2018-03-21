package org.usfirst.frc.falcons6443.robot.commands.subcommands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.falcons6443.robot.commands.SimpleCommand;
import org.usfirst.frc.falcons6443.robot.utilities.Logger;
import org.usfirst.frc.falcons6443.robot.utilities.enums.LoggerSystems;

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
            Logger.log(LoggerSystems.Auto, "Auto drive ", "Crawl on");
        } else {
            driveTrain.tankDrive(0, 0);
            Logger.log(LoggerSystems.Auto, "Auto drive", "Crawl off");
        }
        m_off = true;
    }

    @Override
    public boolean isFinished() { return m_off; }
}