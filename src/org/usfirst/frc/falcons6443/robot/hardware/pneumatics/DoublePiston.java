package org.usfirst.frc.falcons6443.robot.hardware.pneumatics;

import edu.wpi.first.wpilibj.DoubleSolenoid;

/*
 * Used to create a double piston. Will create a compressor if there is not one created yet
 */
public class DoublePiston extends DoubleSolenoid{

    private boolean reversed = false;

    public DoublePiston(int doublePistonPortA, int doublePistonPortB){
        super(doublePistonPortA, doublePistonPortB);
        SingularCompressor.createCompressor();
    }

    public DoublePiston(int doublePistonPortA, int doublePistonPortB, boolean reverse){
        super(doublePistonPortA, doublePistonPortB);
        SingularCompressor.createCompressor();
        this.reversed = reverse;
    }

    public void forward() {
        if(!this.reversed) this.set(Value.kForward);
        else this.set(Value.kReverse);
    }

    public void backward() {
        if(!this.reversed) this.set(Value.kReverse);
        else this.set(Value.kForward);
    }

    public void off() { this.set(Value.kOff); }

    public void reverse() { this.reversed = !this.reversed; }
}
