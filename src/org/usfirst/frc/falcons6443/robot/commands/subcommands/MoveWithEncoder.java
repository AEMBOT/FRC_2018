package org.usfirst.frc.falcons6443.robot.commands.subcommands;

import org.usfirst.frc.falcons6443.robot.commands.SimpleCommand;
import org.usfirst.frc.falcons6443.robot.hardware.DriveEncoders;


/**
*
*Auto program to test use of encoders, lacks navigation utilization
* Lowkey Deprecated in favor for DriveToDistance
* @author Owen Engbretson
 */

import java.beans.Encoder;

public class MoveWithEncoder extends SimpleCommand {

    DriveEncoders Encoders = new DriveEncoders();

    private double distance;
    private double drivePower;
    private double fullRotation =  1024;
    // fullRotation of wheel in terms of ticks
    private double wheelCircumference = Math.PI * 2 * 3;
    // wheel circumference in inches

    // Parameter length is in feet
    MoveWithEncoder(double length, double power){
        super("Encoder Auto");
        requires(driveTrain);
        distance = ((length * 12)/ wheelCircumference) * fullRotation;
        drivePower = power;
    }

    /*
         * length is first converted to inches
         * then divided by circumference to find how many full rotations are required
         * this is then put into terms of ticks to be used as a measure for distance traveled
         * with getRaw()
         */


    @Override
    public void initialize() {
        super.initialize();
        Encoders.reset();
    }

    @Override
    public void execute() {
        super.execute();
        while (distance < Encoders.getLinearDistance()){
            driveTrain.tankDrive(drivePower,drivePower);
        }
    }

    @Override
    protected boolean isFinished() {
        if(distance >= Encoders.getLinearDistance()) return true;
        else return false;
    }
}
