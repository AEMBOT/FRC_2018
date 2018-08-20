package org.usfirst.frc.falcons6443.robot.hardware;

import edu.wpi.first.wpilibj.DigitalInput;

public class LimitSwitch {
    private DigitalInput limit;

    public LimitSwitch(int channel){
        limit = new DigitalInput(channel);
    }

    public boolean get() {
        return !limit.get();
    }
}