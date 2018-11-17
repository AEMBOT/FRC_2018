package org.usfirst.frc.falcons6443.robot.hardware;

import edu.wpi.first.wpilibj.CounterBase;
import edu.wpi.first.wpilibj.Encoder;

/**
 * This class was imported from Simbotics code.
 * Includes more encoder options
 *
 * @author Simbotics 2017
 */
public class Encoders extends Encoder{
    private int prev;
    private int speed;
    private int offset;

    public Encoders(int channelA, int channelB) {
        super(channelA, channelB);
    }

    public Encoders(int channelA, int channelB, int offset) {
        super(channelA, channelB);
        this.offset = offset;
    }

    public Encoders(int aChannel, int bChannel, boolean reverseDirection,
                       CounterBase.EncodingType encodingType, int offset) {
        super(aChannel, bChannel, reverseDirection, encodingType);
        this.offset = offset;
        this.setDistancePerPulse(1);
    }

    public Encoders(int aChannel, int bChannel, boolean reverseDirection,
                      CounterBase.EncodingType encodingType) {
        this(aChannel, bChannel, reverseDirection, encodingType, 0);
    }

    public Encoders(int aChannel, int bChannel, boolean reverseDirection,
                      int offset) {
        this(aChannel, bChannel, reverseDirection,
                CounterBase.EncodingType.k4X, offset);
    }

    public Encoders(int aChannel, int bChannel, boolean reverseDirection) {
        this(aChannel, bChannel, reverseDirection,
                CounterBase.EncodingType.k4X, 0);
    }

    public double getDistance() {
        return super.getRaw();
    }

    public void set(int val) {
        super.reset();
        this.offset = val;
    }

    @Override
    public int get() {
        return super.get() + this.offset;
    }

    public void setReverseDirection(boolean reversed) {
        super.setReverseDirection(reversed);
    }

    @Override
    public void reset() {
        super.reset();
        this.offset = 0;
        this.prev = 0;
    }

    public int speed(){
        return this.speed;
    }

    public void updateSpeed(){
        int curr = this.get();
        this.speed = curr - this.prev;
        this.prev = curr;
    }

    public double rawSpeed(){
        return this.getRate();
    }
}