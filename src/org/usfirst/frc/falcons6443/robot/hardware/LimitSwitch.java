package org.usfirst.frc.falcons6443.robot.hardware;

import edu.wpi.first.wpilibj.DigitalInput;

/*
 * Creates a limit switch, inverting the output so that get() returns true when the
 * limit switch is pushed and false if it is not pushed.
 */
public class LimitSwitch extends DigitalInput {
    private boolean uninverted = false;

    public LimitSwitch(int channel){
        super(channel);
    }

    public LimitSwitch(int channel, boolean uninverted){
        super(channel);
        this.uninverted = uninverted;
    }

    @Override
    //returns true if pushed, false if not pushed (false if pushed when uninverted is true)
    public boolean get() {
        if (!this.uninverted) return !super.get();
        else return super.get();
    }

    public void setUninverted(boolean uninverted) { this.uninverted = uninverted; }
}
