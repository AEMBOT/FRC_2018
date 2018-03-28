package org.usfirst.frc.falcons6443.robot.commands.subcommands.unused;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import org.usfirst.frc.falcons6443.robot.commands.SimpleCommand;
import org.usfirst.frc.falcons6443.robot.subsystems.DriveTrainSystem;


/**
 * @author Ivan Kenevich, Shivashriganesh Mahato
 */
public class AutonomousRecenter extends SimpleCommand implements PIDOutput {
    private double pidOutput;
    private double time;
    private int direction;
    private PIDController pid;

    /**
     * Constructor for SimpleCommand.
     */
    public AutonomousRecenter(double seconds, boolean isReversed) {
        super("Autonomous Re-center");
        requires(driveTrain);
        requires(navigation);
        direction = isReversed ? -1 : 1;
        time = seconds;
    }

    @Override
    public void initialize() {
        initPIDController();
        setTimeout(time);
    }

    @Override
    public void execute() {
        driveTrain.tankDrive(direction * pidOutput, -direction * pidOutput);
    }

    public void initPIDController() {
        pid = new PIDController(DriveTrainSystem.KP,
                DriveTrainSystem.KI,
                DriveTrainSystem.KD,
                DriveTrainSystem.KF,
                navigation.navx.ahrs(), this);
        pid.setInputRange(-180.0f, 180.0f);
        pid.setOutputRange(-0.6, 0.6);
        pid.setAbsoluteTolerance(2.0f);
        pid.setContinuous(true);
        pid.setSetpoint(0);
        pid.enable();
    }

    @Override
    protected boolean isFinished() {
        if (pid.onTarget() && isTimedOut()) {
            driveTrain.tankDrive(0, 0);
            pid.disable();
            pid.free();
            return true;
        }
        return false;
    }

    @Override
    public void pidWrite(double output) {
        pidOutput = output;
    }
}

