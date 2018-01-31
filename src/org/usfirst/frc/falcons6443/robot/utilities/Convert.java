package org.usfirst.frc.falcons6443.robot.utilities;

public class Convert {
    /**
     * Converts ticks to inches and inches to ticks
     *
     * @param toInches  true if ticks to inches, false if inches to ticks
     * @param input the value you wish to convert in inches or ticks
     * @param diameter the diameter of the wheel
     */
    public double convert(boolean toInches, double input, double diameter){
        // Encoder clicks per rotation = 1024
        if (toInches){
            return input * diameter * Math.PI / 1024.0; // In inches
        } else {
            return input / diameter / Math.PI * 1024.0; // In ticks
        }
    }
}
