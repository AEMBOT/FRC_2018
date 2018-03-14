package org.usfirst.frc.falcons6443.robot.commands;

import org.usfirst.frc.falcons6443.robot.Robot;
import org.usfirst.frc.falcons6443.robot.hardware.Xbox;
import edu.wpi.first.wpilibj.drive.Vector2d;
import org.usfirst.frc.falcons6443.robot.utilities.Enums.ElevatorPosition;

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
        Vector2d drive = new Vector2d(0,0);

        //elevator set position
        if (secondary.A()){ elevator.setToHeight(ElevatorPosition.Exchange); }
        if (secondary.B()){ elevator.setToHeight(ElevatorPosition.Switch); }
        if (secondary.Y()){ elevator.setToHeight(ElevatorPosition.Scale); }
        if (secondary.X()){ elevator.setToHeight(ElevatorPosition.Stop); }

        //manual elevator
        if (secondary.seven()) { elevator.up(true);}
        if (secondary.eight()) { elevator.down(true);}
        if (!secondary.seven() && !secondary.eight() && elevator.manual) {elevator.stop();}

        driveTrain.calcDrive(drive, primary.leftStickX(), primary.leftTrigger(), primary.rightTrigger());

        // set the driveTrain power.
        driveTrain.tankDrive(drive.y, drive.x);

        //intake buttons
        if (primary.A()){ intake.intake(); }
        if (primary.B()){ intake.output(); }
        if (!primary.A() && !primary.B() && !primary.Y()){ intake.stop(); }
        if (primary.Y()){ intake.readjust(); }

        //manual rotate
        intake.manual(secondary.leftStickY());

        //rotate
        if (secondary.rightBumper()){ intake.moveIntake(true); }
        if (secondary.leftBumper()){ intake.moveIntake(false); }
        if (!secondary.rightBumper() && !secondary.leftBumper()){ intake.rotateStop(); }

        elevator.moveToHeight();
    }

    public boolean isFinished() {
        return false;
    }
}
