package org.usfirst.frc.falcons6443.robot.hardware.pneumatics;

import edu.wpi.first.wpilibj.Solenoid;

/*
 * Used to create a piston. Will create a compressor if there is not one created yet. The
 * outTrue allows for in() out() rather than remembering if set(true) is pushing out or in
 */
public class Piston extends Solenoid{

    boolean outTrue; //says if the piston moving out is true or false.

    public Piston(int pistonPort, boolean outTrue){
        super(pistonPort);
        Compressor.createCompressor();
        this.outTrue = outTrue;
    }

    public void out(){ this.set(outTrue); }

    public void in(){ this.set(!outTrue); }

    public void setOutTrue(boolean outTrue) {
        this.outTrue = outTrue;
    }
}
