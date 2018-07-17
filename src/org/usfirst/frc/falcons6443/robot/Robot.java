package org.usfirst.frc.falcons6443.robot;

import edu.wpi.cscore.VideoMode;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.falcons6443.robot.commands.*;
import org.usfirst.frc.falcons6443.robot.commands.complete.LaneToLine;
import org.usfirst.frc.falcons6443.robot.commands.subcommands.unused.AutoChooser;
import org.usfirst.frc.falcons6443.robot.communication.NetTables;
import org.usfirst.frc.falcons6443.robot.subsystems.*;
import org.usfirst.frc.falcons6443.robot.utilities.*;

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
    public static final FlywheelSystem Flywheel = new FlywheelSystem();
    public static final RotationSystem Rotation = new RotationSystem();

    public static OI oi;

    private AutoChooser chooser;
    private Command autonomy;
    private Command teleop;

    public Stopwatch autoWatch;
    public static SendableChooser sendable1;
    public Logger.Dashboard pullLogs;

    //public Reader autoReader;
    /*
     * Called when the robot first starts.
     */
    @Override
    public void robotInit() {
        /*
        try {
            autoReader = new Reader();
            autoReader.readLine(3);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        oi = new OI();
        autonomy = null;
        teleop = new TeleopMode();

        //CameraServer.getInstance().putVideo();
        //NetTables.setBoolean("left", false);
       // NetTables.setBoolean("center", false);
        //NetTables.setBoolean("right", false);
        //NetTables.flush();
        //format 1 is kMJPEG
        //VideoMode vm = new VideoMode(1, 640, 480, 60);
        //CameraServer.getInstance().startAutomaticCapture().setVideoMode(vm);
        sendable1 = new SendableChooser();
        sendable1.addObject("Left", AutoChooser.Position.LEFT);
        sendable1.addObject("Center", AutoChooser.Position.CENTER);
        sendable1.addObject("Right", AutoChooser.Position.RIGHT);
        sendable1.addDefault("Line", AutoChooser.Position.LINE);
        SmartDashboard.putData("Auto Path", sendable1);

        SendableChooser sendable2 = new SendableChooser();
        sendable2.addObject("172.22.11.2", Logger.Dashboard.ONE_SEVEN_TWO);
        sendable2.addObject("10.64.43.2", Logger.Dashboard.TEN);
        sendable2.addDefault("Do Not Pull", Logger.Dashboard.NONE);
        SmartDashboard.putData("Pull Log Files", sendable2);

        pullLogs = (Logger.Dashboard) sendable2.getSelected();

    }

    /*
     * Called when the robot first enters disabled mode.
     */
    @Override
    public void disabledInit() {
        Logger.disabled();
        Scheduler.getInstance().removeAll();
       /* switch (button){
            case ONE_SEVEN_TWO:
                Logger.pullLogFiles(true);
                break;
            case TEN:
                Logger.pullLogFiles(false);
            case NONE:
                break;
        }*/
    }

    /*
     * Called periodically when the robot is in disabled mode.
     */
    @Override
    public void disabledPeriodic() {
        Scheduler.getInstance().removeAll();
        //Scheduler.getInstance().run();
    }

    /*
     * Called when the robot first enters autonomous mode.
     */
    @Override
    public void autonomousInit() {
        Logger.autoInit();
        autoWatch = new Stopwatch(true);//begins timing
        chooser = new AutoChooser();
        autonomy = chooser.getFinalAuto();
        if (autonomy != null) autonomy.start();
    }

    /*
     * Called periodically when the robot is in autonomous mode.
     */
    @Override
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    /*
     * Called when the robot first enter teleop mode.
     */
    @Override
    public void teleopInit() {
        Logger.teleopInit();
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