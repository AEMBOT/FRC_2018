package org.usfirst.frc.falcons6443.robot.commands;

import org.usfirst.frc.falcons6443.robot.Robot;
import org.usfirst.frc.falcons6443.robot.hardware.Xbox;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.drive.Vector2d;
import org.usfirst.frc.falcons6443.robot.utilities.Enums.ElevatorPosition;
import org.usfirst.frc.falcons6443.robot.utilities.Enums.IntakePosition;
import org.usfirst.frc.falcons6443.robot.utilities.Enums.XboxRumble;


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
        requires(flywheel);
        requires(elevator);
        requires(navigation);
    }

    @Override
    public void initialize() {
        primary = Robot.oi.getXbox(true);
        secondary = Robot.oi.getXbox(false);
    }

    Vector2d drive = new Vector2d(0,0);
    double differential = 0;
    @Override
    public void execute() {
        //EMERGENCY ELEVATOR CONTROLS
//        if(secondary.seven()){ elevator.up(true); }
//       if(secondary.eight()){ elevator.down(true); }
//        if (!secondary.seven() && !secondary.eight()) { elevator.stop(); }

        //elevator set position
        if (secondary.A()){ elevator.setToHeight(ElevatorPosition.Exchange); }
        if (secondary.B()){ elevator.setToHeight(ElevatorPosition.Switch); }
        if (secondary.Y()){ elevator.setToHeight(ElevatorPosition.Scale); }
        if (secondary.X()){ elevator.setToHeight(ElevatorPosition.Stop); }

        drive.x = 0;
        drive.y = 0;
        if (Math.abs(primary.leftStickX()) < .15) {
            differential = 0;
        } else {
            differential = Math.signum(-1 * primary.leftStickX()) * Math.pow(primary.leftStickX(), 2) / 1.8;
        }
        if (primary.rightTrigger() > 0) {
            drive.x = primary.rightTrigger() * .5 * (primary.rightTrigger() * .7 + .44f) + (differential + .2 * primary.rightTrigger());//x is right
            drive.y = primary.rightTrigger() * .5 * (primary.rightTrigger() * .7 + .44f) - (differential - .2 * primary.rightTrigger());//y is left
        } else if (primary.leftTrigger() > 0) {
            drive.x = primary.leftTrigger() * -.1 * (primary.leftTrigger() * .7 + .44f) + .8 * (differential + primary.leftTrigger());//x is right
            drive.y = primary.leftTrigger() * -.1 * (primary.leftTrigger() * .7 + .44f) - .8 * (differential - primary.leftTrigger());//y is left
            drive.x *= -1;
            drive.y *= -1;
        } else {
            drive.x = primary.rightTrigger() * 1.2 * (primary.rightTrigger() * .7 + .44f) + (differential + .2 * primary.rightTrigger());//x is right
            drive.y = primary.rightTrigger() * 1.2 * (primary.rightTrigger() * .7 + .44f) - (differential - .2 * primary.rightTrigger());//y is left
        }
        // set the driveTrain power.
        driveTrain.tankDrive(drive.y, drive.x);

        //intake button
        if (primary.A()) { flywheel.intake(); }
        //output button
        if (primary.B()) { flywheel.output(); }
        //flywheel stop
        if (!primary.A() && !primary.B() && !primary.Y()){ flywheel.stop(); }
        //readjust
        if (primary.Y()) { flywheel.readjust(); }

        //rotate CHANGE ROTATE POWERS/TIMES!!!
//        if (secondary.leftBumper()){ flywheel.setIntakePosition(IntakePosition.IntakeUpPosition); }
//        if (secondary.rightBumper()){ flywheel.setIntakePosition(IntakePosition.IntakeDownPosition);}

        //manual rotate
        flywheel.manual(secondary.leftStickY());

        //rotate
        if (secondary.rightBumper()){ flywheel.moveIntake(true); }
        if (secondary.leftBumper()){ flywheel.moveIntake(false); }
        if (!secondary.rightBumper() && !secondary.leftBumper()){ flywheel.rotateStop(); }

        /* VIBRATE IF HAS BLOCK
        if (flywheel.hasBlock() && primary.leftBumper()){
            primary.setRumble(XboxRumble.RumbleLeft, 1);
            //primary.controller.setRumble(RumbleType.kLeftRumble, 1);
        } else {
            primary.setRumble(XboxRumble.RumbleLeft, 0);
            //primary.controller.setRumble(RumbleType.kRightRumble, 0);
        }*/

        elevator.moveToHeight();
    }

    public boolean isFinished() {
        return false;
    }
}
