package org.usfirst.frc.falcons6443.robot.hardware.pneumatics;

import edu.wpi.first.wpilibj.Compressor;

/*
 * used to make one instance of the compressor, guarantees creation when Piston class(es) used
 *
 * to instantiate: Compressor compressor = SingularCompressor.get();
 * to use: compressor.start();   (or stop() or enabled() etc.)
 */
public class SingularCompressor {
    public static Compressor instance;

    //used in Piston classes to guarantee a compressor is created
    static void createCompressor(){
        if(instance == null) {
            instance = new Compressor();
        }
    }

    //creates compressor using the specified PCM ID (module); can only be between 0 and 62
    static void createCompressor(int module){
        if(module >= 0 && module <= 62) {
            instance = new Compressor(module);//Only creates one compressor. PCM ID: module
        } else {
            createCompressor();
        }
    }

    /**
     * @return the one instance of this class.
     */
    public static Compressor get(){
        createCompressor();
        return instance;
    }
}