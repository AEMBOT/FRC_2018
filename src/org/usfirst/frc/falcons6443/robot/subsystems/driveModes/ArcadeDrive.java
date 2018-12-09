package org.usfirst.frc.falcons6443.robot.subsystems.driveModes;

import org.usfirst.frc.falcons6443.robot.subsystems.DriveTrainSystemV2;

public class ArcadeDrive {


    DriveTrainSystemV2 drive = new DriveTrainSystemV2();

    /*RC Style drive
     * Left Trigger = Forward
     * Right Trigger = Backward
     * Left Stick x = Turns
     * */
    public void ArcadeDrive(double rightTrigger, double leftTrigger, double leftStickX){

        //Drives robot straight forward
        if(leftTrigger > 0.05 && rightTrigger < 0.05){
            drive.tankDrive(rightTrigger, rightTrigger);
        }
        if(leftTrigger > 0.05 && rightTrigger < 0.05){
            drive.tankDrive(-rightTrigger, -rightTrigger);
        }

        if(leftStickX > 0.15){
            drive.tankDrive(-leftStickX, leftStickX);
        }
        else if(leftStickX < -0.15){
            drive.tankDrive(leftStickX, -leftStickX);
        }
    }
}
