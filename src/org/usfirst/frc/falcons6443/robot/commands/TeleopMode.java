package org.usfirst.frc.falcons6443.robot.commands;

import org.usfirst.frc.falcons6443.robot.Robot;
import org.usfirst.frc.falcons6443.robot.hardware.Xbox;
import edu.wpi.first.wpilibj.drive.Vector2d;
import org.usfirst.frc.falcons6443.robot.utilities.enums.ElevatorPosition;

/**
 * Teleoperated mode for the robot.
 * The execute method of this class handles all possible inputs from the driver during the game.
 *
 * @author Ivan Kenevich, Christopher Medlin, Shivashriganesh Mahato
 */
public class TeleopMode extends SimpleCommand {

    private Xbox primary;         //Drive and intake/output
    private Xbox secondary;      //Secondary functions

    public TeleopMode() {
        super("Teleop Command");
        requires(driveTrain);
        requires(intake);
        requires(elevator);
        requires(navigation);
    }

    @Override
    public void initialize() {
        primary = Robot.oi.getXbox(true);
        secondary = Robot.oi.getXbox(false);
    }

    @Override
    public void execute() {
        //ALWAYS CHECK THAT MANUAL/OTHER CONTROLS DO NOT TURN THE MOTOR OFF IN OTHER SETTINGS
        //THIS SHOULD BE YOUR FIRST STEP IN MOTOR DEBUGGING!!!!!

        //intake buttons
        if (primary.A()){ intake.intake(); }
        if (primary.B()){ intake.output(); }
        if (primary.Y()){ intake.readjust(); }
        if (!primary.A() && !primary.B() && !primary.Y()){ intake.stop(); }

        //drive controls
        driveTrain.falconDrive(primary.leftStickX(), primary.leftTrigger(), primary.rightTrigger());

        //elevator set position
        if (secondary.A()){ elevator.setToHeight(ElevatorPosition.Exchange); }
        if (secondary.B()){ elevator.setToHeight(ElevatorPosition.Switch); }
        if (secondary.Y()){ elevator.setToHeight(ElevatorPosition.Scale); }
        if (secondary.X()){ elevator.setToHeight(ElevatorPosition.Stop); }

        //elevator manual
        if(secondary.seven()){ elevator.up(); }
        if(secondary.eight()){ elevator.down(); }
        if(!secondary.seven() && !secondary.eight()){ elevator.stop(); }

        //rotate
        if (secondary.rightBumper()){ intake.moveIntake(true); }
        if (secondary.leftBumper()){ intake.moveIntake(false); }
       // if (secondary.seven()) { intake.rotateMid();}
        if (!secondary.rightBumper() && !secondary.leftBumper() /*&&
                !secondary.seven()*/){ intake.rotateStop(); }

        elevator.moveToHeight();
    }

    public boolean isFinished() {
        return false;
    }
}
