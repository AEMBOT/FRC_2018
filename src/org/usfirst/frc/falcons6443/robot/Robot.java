package org.usfirst.frc.falcons6443.robot;
import edu.wpi.cscore.VideoMode;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

import org.usfirst.frc.falcons6443.robot.commands.AutoChooser;
import org.usfirst.frc.falcons6443.robot.commands.TeleopMode;
import org.usfirst.frc.falcons6443.robot.commands.autocommands.*;
import org.usfirst.frc.falcons6443.robot.communication.NetTables;
import org.usfirst.frc.falcons6443.robot.subsystems.DriveTrainSystem;
import org.usfirst.frc.falcons6443.robot.subsystems.ElevatorSystem;
import org.usfirst.frc.falcons6443.robot.subsystems.IntakeSystem;
import org.usfirst.frc.falcons6443.robot.subsystems.NavigationSystem;
import org.usfirst.frc.falcons6443.robot.utilities.Logger;

/**
 * ROBOTS DON'T QUIT!
 * The Robot class is FRC team 6443's implementation of WPIlib's IterativeRobot class.
 *
 * @author Christopher Medlin
 */
public class Robot extends IterativeRobot {

    // All the subsystems that the robot possesses
    // If a new subsystem is added, it must also be added to SimpleCommand.
    // From there the subsystem can be referred to from any command that inherits SimpleCommand.
    public static final DriveTrainSystem DriveTrain = new DriveTrainSystem();
    public static final ElevatorSystem Elevator = new ElevatorSystem();
    public static final NavigationSystem Navigation = new NavigationSystem();
    public static final IntakeSystem Intake = new IntakeSystem();

    public static OI oi;

    private AutoChooser chooser;
    private Command autonomy;
    private Command teleop;

    /*
     * Called when the robot first starts.
     */
    @Override
    public void robotInit() {
        oi = new OI();
        //autonomy = new RotateToAngle(90);
        teleop = new TeleopMode();
        Logger.init();

        //format 1 is kMJPEG
        VideoMode vm = new VideoMode(1, 640, 480, 60);
        CameraServer.getInstance().startAutomaticCapture().setVideoMode(vm);
        //CameraServer.getInstance().putVideo();
        NetTables.setBoolean("left", false);
        NetTables.setBoolean("center", false);
        NetTables.setBoolean("right", false);
        NetTables.flush();
    }

    /*
     * Called when the robot first enters disabled mode.
     */
    @Override
    public void disabledInit() {
        Logger.disabled();
    }

    /*
     * Called periodically when the robot is in disabled mode.
     */
    @Override
    public void disabledPeriodic() {
        Scheduler.getInstance().run();
    }

    /*
     * Called when the robot first enters autonomous mode.
     */
    @Override
    public void autonomousInit() {
        Logger.init();
        //chooser = new AutoChooser(AutoChooser.Position.UNKNOWN);
        autonomy = new LaneToLine();
        if (autonomy != null) {
            autonomy.start();
        }

    }

    /*
     * Called periodically when the robot is in autonomous mode.
     */
    @Override
    public void autonomousPeriodic() {
        // ElevatorSystem.moveToHeight();
        //Intake.autoMoveIntake();
        Scheduler.getInstance().run();
    }

    /*
     * Called when the robot first enter teleop mode.
     */
    @Override
    public void teleopInit() {
        Logger.init();
        if (autonomy != null) autonomy.cancel();
        if (teleop != null) teleop.start();
    }

    /*
     * Called periodically when the robot is in teleop mode.
     */
    @Override
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
    }

    /*
     * Called periodically when the robot is in testing mode.
     */
    @Override
    public void testPeriodic() {
        LiveWindow.run();
    }
}
