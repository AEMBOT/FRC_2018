/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.falcons6443.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.cscore.VideoMode;
import edu.wpi.first.wpilibj.CameraServer;
import org.usfirst.frc.falcons6443.robot.autonomous.AutoDrive;
import org.usfirst.frc.falcons6443.robot.autonomous.AutoMain;
import org.usfirst.frc.falcons6443.robot.hardware.joysticks.Xbox;
import org.usfirst.frc.falcons6443.robot.subsystems.*;
import org.usfirst.frc.falcons6443.robot.utilities.TeleopStructure;
import org.usfirst.frc.falcons6443.robot.utilities.Logger;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
// If you rename or move this class, update the build.properties file in the project root
public class Robot extends IterativeRobot {
    private Xbox primary;
    private Xbox secondary;
    private TeleopStructure teleop;
    private DriveTrainSystem driveTrain;
    private ElevatorSystem elevator;
    private FlywheelSystem flywheel;
    private RotationSystem rotation;
    private AutoDrive autoDrive;
    private AutoMain autoMain;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    @Override
    public void robotInit()
    {
        primary = new Xbox(new XboxController(0));
        secondary = new Xbox(new XboxController(1));
        teleop = new TeleopStructure();
        driveTrain = new DriveTrainSystem();
        elevator = new ElevatorSystem();
        flywheel = new FlywheelSystem();
        rotation = new RotationSystem();
        autoDrive = new AutoDrive();
        autoMain = new AutoMain(autoDrive, elevator, flywheel, rotation);
        //CameraServer.getInstance().putVideo();
        //format 1 is kMJPEG
        VideoMode vm = new VideoMode(1, 640, 480, 60);
        CameraServer.getInstance().startAutomaticCapture().setVideoMode(vm);
    }

    /*
     * Called when the robot first enters autonomous mode.
     */
    @Override
    public void autonomousInit()
    {
        Logger.autoInit();
        autoMain.runAutoPath();
    }

    /**
     * This function is called periodically during autonomous.
     */
    @Override
    public void autonomousPeriodic() {   }

    /*
     * Called when the robot first enter teleop mode.
     */
    @Override
    public void teleopInit(){
        teleop.addIsManualGetterSetter(TeleopStructure.ManualControls.Elevator, () -> elevator.getManual(), (Boolean set) -> elevator.setManual(set));
        teleop.addIsManualGetterSetter(TeleopStructure.ManualControls.Rotate, () -> rotation.getManual(), (Boolean set) -> rotation.setManual(set));
        Logger.teleopInit();
    }
    /**
     * This function is called periodically during operator control.
     */
    @Override
    public void teleopPeriodic()
    {
        //drive
        driveTrain.falconDrive(primary.leftStickX(), primary.leftTrigger(), primary.rightTrigger());
        // driveTrain.tankDrive(driveProfile.calculate()); TODO: TEST this cause profiles are cool

        //shifting
        teleop.press(primary.rightBumper(), () -> driveTrain.upShift());
        teleop.press(primary.leftBumper(), () -> driveTrain.downShift());

        //elevator
//        teleop.press((Boolean set) -> elevator.setManual(set), secondary.A(), () -> elevator.setToHeight(ElevatorPosition.Exchange));
//        teleop.press((Boolean set) -> elevator.setManual(set), secondary.B(), () -> elevator.setToHeight(ElevatorPosition.Switch));
//        teleop.press((Boolean set) -> elevator.setManual(set), secondary.X(), () -> elevator.setToHeight(ElevatorPosition.Stop));
//        teleop.press((Boolean set) -> elevator.setManual(set), secondary.Y(), () -> elevator.setToHeight(ElevatorPosition.Scale));
        teleop.manual(TeleopStructure.ManualControls.Elevator, secondary.leftStickY(), () -> elevator.manual(-secondary.leftStickY()));

        //flywheels
        teleop.press(primary.A(), () -> flywheel.intake());
        teleop.press(primary.B(), () -> flywheel.output());
        teleop.press(primary.Y(), () -> flywheel.slowOutput());
        teleop.runOncePerPress(secondary.seven(), () -> flywheel.toggleKill(), true); //toggles slow spin while off

        //rotation
        teleop.press(TeleopStructure.ManualControls.Rotate, secondary.rightBumper(), () -> rotation.up());
        teleop.press(TeleopStructure.ManualControls.Rotate, secondary.leftBumper(), () -> rotation.down());
        teleop.manual(TeleopStructure.ManualControls.Rotate, secondary.rightStickY(), () -> rotation.manual(-secondary.rightStickY()));

        //off functions
        teleop.off(() -> elevator.stop(), TeleopStructure.ManualControls.Elevator);
        teleop.off(() -> flywheel.stop(), primary.A(), primary.B(), primary.Y());
        teleop.off(() -> rotation.stop(), TeleopStructure.ManualControls.Rotate, secondary.rightBumper(), secondary.leftBumper());

        //general periodic functions
        //elevator.moveToHeight(false);
        teleop.periodicEnd();
    }

    /**
     * This function is called periodically during test mode.
     */
    @Override
    public void testPeriodic() {    }

    /*
     * Called when the robot first enters disabled mode.
     */
    @Override
    public void disabledInit(){
        try{
            Logger.printSpace();
        } catch (Exception e){
            System.out.println("Failed to print storage");
        }
        Logger.disabled();
    }

    /*
     * Called periodically when the robot is in disabled mode.
     */
    @Override
    public void disabledPeriodic(){    }
}
