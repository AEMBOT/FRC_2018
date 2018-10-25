package org.usfirst.frc.falcons6443.robot.hardware;

import edu.wpi.first.wpilibj.DigitalInput;

public class LimitSwitch extends DigitalInput {
    private boolean uninverted = false;

    public LimitSwitch(int channel){
        super(channel);
    }

    public LimitSwitch(int channel, boolean uninverted){
        super(channel);
        this.uninverted = uninverted;
    }

    //returns true if pushed, false if not pushed (false if pushed when uninverted is true)
    public boolean get() {
        if (!uninverted) return !this.get();
        else return this.get();
    }

    public void setUninverted(boolean uninverted) { this.uninverted = uninverted; }
}
