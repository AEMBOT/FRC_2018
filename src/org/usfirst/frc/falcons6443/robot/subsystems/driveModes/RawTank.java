package org.usfirst.frc.falcons6443.robot.subsystems.driveModes;

import org.usfirst.frc.falcons6443.robot.subsystems.DriveTrainSystemV2;

public class RawTank {

    private DriveTrainSystemV2 drive = new DriveTrainSystemV2();

    public void TankDrive(double leftStickY, double rightStickY){
        drive.tankDrive(-leftStickY, -rightStickY);
    }
}
