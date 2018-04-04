package org.usfirst.frc.falcons6443.robot.commands;

import org.usfirst.frc.falcons6443.robot.Robot;
import org.usfirst.frc.falcons6443.robot.hardware.Playstation;
import org.usfirst.frc.falcons6443.robot.hardware.Xbox;
import org.usfirst.frc.falcons6443.robot.utilities.drive.FalconDrive;
import org.usfirst.frc.falcons6443.robot.utilities.drive.WCDProfile;
import org.usfirst.frc.falcons6443.robot.utilities.enums.ElevatorPosition;

/**
 * Teleoperated mode for the robot.
 * The execute method of this class handles all possible inputs from the driver during the game.
 *
 * @author Ivan Kenevich, Christopher Medlin, Shivashriganesh Mahato
 */
public class TeleopMode extends SimpleCommand {

    private Xbox primary;           //Drive and intake/output
    private Xbox secondary;         //Secondary functions
    //private WCDProfile driveProfile;//Profile used to calculate robot drive power
   // private Playstation secondary;

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
        //driveProfile = new FalconDrive(primary);
        //  secondary = Robot.oi.getPlay();
       // intake.resetEnc();
    }

    @Override
    public void execute() {
        //ALWAYS CHECK THAT MANUAL/OTHER CONTROLS DO NOT TURN THE MOTOR OFF IN OTHER SETTINGS
        //THIS SHOULD BE YOUR FIRST STEP IN MOTOR DEBUGGING!!!!!

        //intake buttons
        if (primary.A()){ intake.intake(); }
        if (primary.B()){ intake.output(); }
        if (primary.Y()){ intake.slowOutput(); }
        if (!primary.A() && !primary.B() && !primary.Y()){ intake.stop(); }

        //drive controls
        driveTrain.falconDrive(primary.leftStickX(), primary.leftTrigger(), primary.rightTrigger());
        // driveTrain.tankDrive(driveProfile.calculate()); TODO: TEST this cause profiles are cool

        //elevator set position
        if (secondary.A()){ elevator.setToHeight(ElevatorPosition.Exchange); }
        if (secondary.B()){ elevator.setToHeight(ElevatorPosition.Switch); }
        if (secondary.Y()){ elevator.setToHeight(ElevatorPosition.Scale); }
        if (secondary.X()){ elevator.setToHeight(ElevatorPosition.Stop); }

        //elevator manual
        //if(secondary.seven()){ elevator.up(); }
        //if(secondary.eight()){ elevator.down(); }
       // if(!secondary.seven() && !secondary.eight()){ elevator.stop(); }

        //rotate
        if (secondary.rightBumper()){ intake.moveIntake(true); }
        if (secondary.leftBumper()){ intake.moveIntake(false); }
        //if (secondary.seven()) { intake.rotateMid();}
        if (!secondary.rightBumper() && !secondary.leftBumper() /*&&
                !secondary.seven()*/){ intake.rotateStop(); }
       // System.out.println("enc" + intake.getIntekeEnc());
//intake.getIntekeEnc();
        elevator.moveToHeight(false);

        System.out.println("Left: " + driveTrain.getLeftDistance());
        System.out.println("right: " + driveTrain.getRightDistance());

    }

    public void reset(){
        //driveProfile = null;
    }

    public boolean isFinished() {
        return false;
    }
}
