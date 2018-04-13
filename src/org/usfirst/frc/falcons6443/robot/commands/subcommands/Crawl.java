package org.usfirst.frc.falcons6443.robot.commands.subcommands;

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

    //IF STOPDRIVE WORKS IMPLEMENT HERE!!
    @Override
    public void initialize() { m_off = false; }

    @Override
    public void execute() {
        if (m_on){
            driveTrain.tankDrive(.5, .5 + .045);
            Logger.log(LoggerSystems.Auto,"Auto drive Crawl on");
        } else {
            driveTrain.tankDrive(0, 0);
            Logger.log(LoggerSystems.Auto,"Auto drive Crawl off");
        }
        m_off = true;
    }

    @Override
    public boolean isFinished() { return m_off; }
}