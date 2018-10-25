package org.usfirst.frc.falcons6443.robot.hardware.pneumatics;

/*
 * static class used to make one instance of the compressor and functions to turn on or off
 */
public class Compressor {
    static edu.wpi.first.wpilibj.Compressor compressor;
    private static boolean isCompressorCreated = false;

    //Use in Piston classes to guarantee a compressor is created
    public static void createCompressor(){
        if(!isCompressorCreated) {
            compressor = new edu.wpi.first.wpilibj.Compressor();//Only creates one compressor
            isCompressorCreated = true;
        }
    }

    //turns on the compressor
    public static void start() { compressor.start(); }

    //turns off the compressor
    public static void stop(){ compressor.stop(); }

    //returns whether the compressor is on or off
    public static boolean isEnabled(){ return compressor.enabled(); }
}
