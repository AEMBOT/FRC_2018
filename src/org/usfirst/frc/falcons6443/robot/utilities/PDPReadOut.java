package org.usfirst.frc.falcons6443.robot.utilities;

import edu.wpi.first.wpilibj.hal.PDPJNI;

public class PDPReadOut {
    public double execute(int portNum)
    {
        double voltage = PDPJNI.getPDPVoltage(portNum);

        return voltage;
    }
}