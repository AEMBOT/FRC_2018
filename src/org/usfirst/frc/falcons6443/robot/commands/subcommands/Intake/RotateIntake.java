package org.usfirst.frc.falcons6443.robot.commands.subcommands.Intake;

import org.usfirst.frc.falcons6443.robot.commands.SimpleCommand;

public class RotateIntake extends SimpleCommand {

    private boolean auto = false;

    public RotateIntake() {
        super("Rotate intake");
        requires(rotation);
    }

    public RotateIntake(boolean auto) {
        super("Rotate intake");
        requires(rotation);
        this.auto = auto;
    }

    private void up() {
        double speed = rotation.upSpeed;
        if (rotation.encoder.getDistance() > rotation.upEncVal) {
            speed = 0;
            rotation.constantPower = false;
        } else rotation.constantPower = true;
        rotation.rotateMotor.set(speed);
        System.out.println("Encoder: " + rotation.encoder.getDistance());
    }

    private void down(){
        double speed = rotation.downSpeed;
        if (rotation.encoder.getDistance() < rotation.downEncVal) {
            speed = 0;
            rotation.constantPower = false;
        } else rotation.constantPower = true;
        rotation.rotateMotor.set(speed);
        System.out.println("Encoder: " + rotation.encoder.getDistance());
    }

    private void middle(){
        if(rotation.encoder.getDistance() > (rotation.midEncVal + rotation.buffer)){
            rotation.rotateMotor.set(rotation.downSpeed);
        } else if(rotation.encoder.getDistance() < (rotation.midEncVal - rotation.buffer)){
            rotation.rotateMotor.set(rotation.upSpeed);
        } else {
            rotation.constantPower = true;
            stop();
        }
    }

    private void stop(){
        if(rotation.constantPower)
            rotation.rotateMotor.set(0.17);
        else rotation.rotateMotor.set(0);
    }

    @Override
    public void initialize() { if(auto) setTimeout(2.5); }

    @Override
    public void execute() {
        switch (rotation.currentPosition) {
            case IntakeUpPosition:
                up();
                break;
            case IntakeDownPosition:
                down();
                break;
            case IntakeHalfPosition:
                middle();
                break;
            case stop:
                stop();
                break;
        }
    }

    @Override
    protected boolean isFinished() {
        if(auto) return isTimedOut();
        else return false;
    }

    @Override
    public void end(){ stop(); }

    @Override
    public void interrupted(){ stop(); }
}
