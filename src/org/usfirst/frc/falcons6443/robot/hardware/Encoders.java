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
    private int ticksPerRev = 850;
    private double diameter;

    public Encoders(int channelA, int channelB) {
        super(channelA, channelB);
    }

    public Encoders(int channelA, int channelB, int offset) {
        super(channelA, channelB);
        this.offset = offset;
    }

    //look into counterbase and encoding types
    public Encoders(int aChannel, int bChannel, CounterBase.EncodingType encodingType, int offset) {
        super(aChannel, bChannel, false, encodingType);
        this.offset = offset;
        this.setDistancePerPulse(1);
    }

    public Encoders(int aChannel, int bChannel, CounterBase.EncodingType encodingType) {
        this(aChannel, bChannel, encodingType, 0);
    }

    public void set(int val) {
        super.reset();
        this.offset = val;
    }

    @Override
    public int get() {
        return super.get() + this.offset;
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

    //periodic function (TEST)
    public void updateSpeed(){
        int curr = this.get();
        this.speed = curr - this.prev;
        this.prev = curr;
    }

    public double rawSpeed(){
        return this.getRate();
    }

    public double getDistanceWithDiameter(){
        return this.get() * this.diameter * Math.PI / this.ticksPerRev;
    }

    public void setDiameter(double wheelDiameter){
        diameter = wheelDiameter;
    }

    public void setTicksPerRev(int ticksPerRev){
        this.ticksPerRev = ticksPerRev;
    }
}