package org.usfirst.frc.falcons6443.robot.hardware.pneumatics;

import edu.wpi.first.wpilibj.DoubleSolenoid;

/*
 * Used to create a double piston. Will create a compressor if there is not one created yet
 */
public class DoublePiston extends DoubleSolenoid{

    public DoublePiston(int doublePistonPortA, int doublePistonPortB){
        super(doublePistonPortA, doublePistonPortB);
        SingularCompressor.createCompressor();
    }

    public void forward() { this.set(Value.kForward); }

    public void backward() { this.set(Value.kForward); }

    public void off() { this.set(Value.kOff);}
}
