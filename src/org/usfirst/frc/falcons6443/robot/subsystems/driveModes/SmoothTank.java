package org.usfirst.frc.falcons6443.robot.subsystems.driveModes;

import org.usfirst.frc.falcons6443.robot.subsystems.DriveTrainSystemV2;
import org.usfirst.frc.falcons6443.robot.utilities.drive.DriveSmoother;

public class SmoothTank {

     private DriveTrainSystemV2 drive = new DriveTrainSystemV2();

    //Implements Mark's attempted at a smoother drive system  TODO: Test to see if this actually works
    public void SmoothTankDrive(double leftStickY, double rightStickY){drive.driveSmoother.SetPower(leftStickY, rightStickY); }
}
