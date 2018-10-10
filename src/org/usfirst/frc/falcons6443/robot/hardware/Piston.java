package org.usfirst.frc.falcons6443.robot.hardware;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Solenoid;

//Functions start, stop, enabled, and others are necessary Compressor functions
public class Piston extends Compressor{
    Compressor compressor;
    Solenoid piston;
    boolean isCompressorCreated = false;

    //only create compressor once!
    public Piston(int pistonPort, boolean createCompressor){
        if(createCompressor && !isCompressorCreated) {
            compressor = new Compressor();
            isCompressorCreated = true;
        }
        piston = new Solenoid(pistonPort);
    }

    public void out(){
        piston.set(true);
    }

    public void in(){
        piston.set(false);
    }
}
