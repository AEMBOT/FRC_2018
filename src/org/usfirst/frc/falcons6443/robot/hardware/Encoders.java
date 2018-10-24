package org.usfirst.frc.falcons6443.robot.hardware;

import edu.wpi.first.wpilibj.Encoder;

public class Encoders extends Encoder{
    private int offset;

    public Encoders(int channelA, int channelB) {
        super(channelA, channelB);
    }

    public Encoders(int channelA, int channelB, int offset) {
        super(channelA, channelB, offset);
        this.offset = offset;
    }

    @Override
    public int get() {
        return super.get() + this.offset;
    }

    @Override
    public void reset() {
        super.reset();
        this.offset = 0;
    }

    public void set(int val) {
        super.reset();
        this.offset = val;
    }
}