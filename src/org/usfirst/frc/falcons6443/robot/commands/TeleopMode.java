package org.usfirst.frc.falcons6443.robot.commands;

import org.usfirst.frc.falcons6443.robot.Robot;
import org.usfirst.frc.falcons6443.robot.hardware.Xbox;

/**
 * Teleoperated mode for the robot.
 * The execute method of this class handles all possible inputs from the driver during the game.
 *
 * @author Ivan Kenevich, Christopher Medlin, Shivashriganesh Mahato
 */
public class TeleopMode extends SimpleCommand {

    private Xbox primary;           //Drive and intake/output
    private Xbox secondary;         //Secondary functions
    private boolean toggle;
    private boolean on;
    //private WCDProfile driveProfile;//Profile used to calculate robot drive power

    public TeleopMode() {
        super("Teleop Command");
        requires(driveTrain);
        requires(intake);
        requires(elevator);
    }

    @Override
    public void initialize() {
        primary = Robot.oi.getXbox(true);
        secondary = Robot.oi.getXbox(false);
        //driveProfile = new FalconDrive(primary);
        //intake.resetEnc();
        toggle = false;
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
        if(primary.rightBumper()){driveTrain.upShift();}
        if (primary.leftBumper()){driveTrain.downShift();}
        //drive controls
        driveTrain.falconDrive(primary.leftStickX(), primary.leftTrigger(), primary.rightTrigger());
        // driveTrain.tankDrive(driveProfile.calculate()); TODO: TEST this cause profiles are cool

        //elevator set position
  //      if (secondary.A()){ elevator.setToHeight(ElevatorPosition.Exchange); }
        //if (secondary.B()){ elevator.setToHeight(ElevatorPosition.Switch); }
  //      if (secondary.Y()){ elevator.setToHeight(ElevatorPosition.Scale); }
  //      if (secondary.X()){ elevator.setToHeight(ElevatorPosition.Stop); }

        //elevator manual
        //if(secondary.seven()){ elevator.up(); }
        //if(secondary.eight()){ elevator.down(); }
        //if(!secondary.seven() && !secondary.eight()){ elevator.stop(); }
       // if(Math.abs(secondary.leftStickY()) > .1){
        elevator.manual(secondary.leftStickY());
        elevator.setManual(true);
       // System.out.println("Stick: " + secondary.leftStickY());
      //  } else if(secondary.B()){
       //     elevator.up();
            //elevator.setManual(false);
        //} else {
        //    elevator.stop();
       // }
      //  System.out.println("elevator: " + elevator.getEncoderDistance());
       // if(secondary.Y()){ elevator.resetEncoder();}

        if(secondary.seven() && !on){
            toggle = !toggle;
            intake.setKill(toggle);
            on = true;
        }
        if(!secondary.seven()){
            on = false;
        }
        //rotate
        if (secondary.rightBumper()){ intake.moveIntake(true); }
        if (secondary.leftBumper()){ intake.moveIntake(false); }
        //if (secondary.seven()) { intake.rotateMid();}
        if (!secondary.rightBumper() && !secondary.leftBumper() /*&&
                !secondary.seven()*/){ intake.rotateStop(); }
//        System.out.println("enc" + intake.getIntekeEnc());
//        System.out.println("Left: " + driveTrain.getLeftDistance());
//        System.out.println("right: " + driveTrain.getRightDistance());

        elevator.moveToHeight(false);
       // System.out.println("Eenc: " + elevator.getEncoderDistance());
    }

    public void reset(){
        //driveProfile = null;
    }

    public boolean isFinished() {
        return false;
    }
}
