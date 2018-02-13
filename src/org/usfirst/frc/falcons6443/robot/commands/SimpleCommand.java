package org.usfirst.frc.falcons6443.robot.commands;

import org.usfirst.frc.falcons6443.robot.Robot;
import org.usfirst.frc.falcons6443.robot.subsystems.*;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Serves as a class to build commands off of.
 * <p>
 * Implements all of the methods of the Command class and contains fields referring to
 * components of the Robot class. When creating a new command, inherit from this class, not
 * the Command class. This excludes the isFinished() method, because there is no situation in
 * which you wouldn't implement this for a certain situation.
 *
 * @author Christopher Medlin
 */
public abstract class SimpleCommand extends Command {

    // Subsystems.
    // When a subsystem is added to Robot, it must also be added here.
    // It can then be referred to from any command inheriting this class.
    public DriveTrainSystem driveTrain = Robot.DriveTrain;
    public Elevator elevator = Robot.Elevator;
    public NavigationSystem navigation = Robot.Navigation;
    public FlywheelSystem flywheel = Robot.Flywheel;

    /**
     * Constructor for SimpleCommand.
     *
     * @param name name of the command.
     */
    public SimpleCommand(String name) {
        super(name);
    }

    public void initialize() {
    }

    public void end() {
    }

    public void execute() {
    }

    public void interrupted() {
    }

    /**
     * Multipies input by the absolute value of input to maintain sign when squaring.
     * <p>
     * This is important for axes.
     *
     * @param input the unadjusted input.
     * @return the adjusted input.
     */
    protected double adjustedInput(double input) {
        return input * Math.abs(input);
    }

}
