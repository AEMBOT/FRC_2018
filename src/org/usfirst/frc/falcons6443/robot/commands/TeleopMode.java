package org.usfirst.frc.falcons6443.robot.commands;

import org.usfirst.frc.falcons6443.robot.Robot;
import org.usfirst.frc.falcons6443.robot.subsystems.IntakeSystem;
import org.usfirst.frc.falcons6443.robot.hardware.Xbox;
import java.util.function.*;
import java.util.Arrays;

/**
 * Teleoperated mode for the robot.
 * The execute method of this class handles all possible inputs from the driver during the game.
 *
 * @author Ivan Kenevich, Christopher Medlin, Shivashriganesh Mahato
 */
public class TeleopMode extends SimpleCommand {

    private Xbox primary;           //Drive and intake/output
    private Xbox secondary;         //Secondary functions
    private boolean[] on = {};
    //private boolean[] oon = new boolean[];
    //private WCDProfile driveProfile;//Profile used to calculate robot drive power

    public TeleopMode() {
        super("Teleop Command");
        requires(driveTrain);
        requires(intake);
        requires(elevator);
        Arrays.fill(on, false);
    }

    @Override
    public void initialize() {
        primary = Robot.oi.getXbox(true);
        secondary = Robot.oi.getXbox(false);
        //driveProfile = new FalconDrive(primary);
        //intake.resetEnc();
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

        depress(secondary.seven(),() -> intake.toggleKill(), 0);

        //rotate
        if (secondary.rightBumper()){ intake.moveIntake(true); }
        if (secondary.leftBumper()){ intake.moveIntake(false); }
        //if (secondary.seven()) { intake.rotateMid();}
        if (!secondary.rightBumper() && !secondary.leftBumper() /*&&
                !secondary.seven()*/){ intake.rotateStop(); }

        double[] intakeManual = new double[] {};
        boolean[] intakeButtons = new boolean[] {secondary.rightBumper(), secondary.leftBumper()};
        Runnable[] intakeFunctionsManual = new Runnable[] {};
        Runnable[] intakeFunctions = new Runnable[] {() -> intake.moveIntake(true),
                () -> intake.moveIntake(false), () -> intake.rotateStop()};
        controls(intake.m_manual, intakeManual, intakeButtons, intakeFunctionsManual, intakeFunctions);



        //drive controls
        if (primary.rightBumper()){driveTrain.upShift();}
        if (primary.leftBumper()){driveTrain.downShift();}
        driveTrain.falconDrive(primary.leftStickX(), primary.leftTrigger(), primary.rightTrigger());
        // driveTrain.tankDrive(driveProfile.calculate()); TODO: TEST this cause profiles are cool

        //elevator setSpeed position
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
       // if(secondary.Y()){ elevator.resetEncoder();}



        elevator.moveToHeight(false);
    }

    public void depress(boolean button, Runnable function, int rank){
        if(button){
            if(!on[rank]){
                function.run();
                on[rank] = true;
            }
        } else {
            on[rank] = false;
        }
    }

    public void controls(boolean manualbool, double[] manual, boolean[] button,
                         Runnable[] manualFunction, Runnable[] buttonFunction) {
        for(int i = 0; i < button.length; i++){
            if(button[i]){
                manualbool = false;
                buttonFunction[i].run();
            }
        }

        if(areAllFalse(button) && !manualbool){
            buttonFunction[buttonFunction.length].run();
        }

        for(int i = 0; i < manual.length; i++){
            if(Math.abs(manual[i]) > 0.2){
                manualbool = true;
                manualFunction[i].run();
            }
        }

        if(areAllZero(0.2, manual) && manualbool){
            buttonFunction[buttonFunction.length].run();
        }
    }

    public void teleop(double manual, boolean[] buttons){
        if(buttons[0]){
            m_manual = false;
            moveIntake(true);
        } else if(buttons[1]){
            m_manual = false;
            moveIntake(false);
        } else if(buttons[2]){
            m_manual = false;
            rotateMid();
        } else if (!m_manual){
            rotateStop();
        }

        if(Math.abs(manual) > 0.2){
            m_manual = true;
            rotateMotor.set(manual);
        } else if(m_manual){
            rotateStop();
        }

        if(buttons[3]){
            intake();
        } else if(buttons[4]){
            output();
        } else {
            stop();
        }
    }

    public static boolean areAllFalse(boolean[] array) {
        for(boolean b : array) if(b) return false;
        return true;
    }

    public static boolean areAllZero(double buffer, double[] array) {
        for(double d : array) if(d > buffer) return false;
        return true;
    }

    public void reset(){
        //driveProfile = null;
    }

    public boolean isFinished() {
        return false;
    }
}
