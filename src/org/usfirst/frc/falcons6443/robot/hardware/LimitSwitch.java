package org.usfirst.frc.falcons6443.robot.hardware;

import edu.wpi.first.wpilibj.DigitalInput;

public class LimitSwitch {
    private DigitalInput limit;
    private boolean uninverted = false;

    public LimitSwitch(int channel){
        limit = new DigitalInput(channel);
    }

    public LimitSwitch(int channel, boolean uninverted){
        limit = new DigitalInput(channel);
        this.uninverted = uninverted;
    }

    //returns true if pushed, false if not pushed (false if pushed when uninverted is true)
    public boolean get() {
        if (!uninverted) return !limit.get();
        else return limit.get();
    }
}
