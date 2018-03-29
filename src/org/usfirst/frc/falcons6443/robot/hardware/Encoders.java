package org.usfirst.frc.falcons6443.robot.hardware;

import edu.wpi.first.wpilibj.Encoder;
public class Encoders {
    private Encoder encoder;

    public Encoders(int channelA, int channelB) {
        encoder = new Encoder(channelA, channelB);
    }

    public double getDistance() {
        return encoder.getRaw();
    }

    public void reset() {
        encoder.reset();
    }

    public void setReverseDirection(boolean reversed) {
        encoder.setReverseDirection(reversed);
    }
}