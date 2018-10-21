package org.usfirst.frc.falcons6443.robot.autonomous;

import edu.wpi.first.wpilibj.Timer;
import org.usfirst.frc.falcons6443.robot.subsystems.ElevatorSystem;
import org.usfirst.frc.falcons6443.robot.subsystems.FlywheelSystem;
import org.usfirst.frc.falcons6443.robot.subsystems.RotationSystem;
import org.usfirst.frc.falcons6443.robot.utilities.enums.ElevatorPosition;
import org.usfirst.frc.falcons6443.robot.utilities.enums.RotationPosition;
import java.util.function.Supplier;

/*
 * A package-private class where you create all of the auto paths. Contains wait and other
 * private functions to make building auto paths easier. The auto path is selected by
 * AutoMain and only runs one path per match.
 */
class AutoPaths {

    private AutoDrive autoDrive;
    private ElevatorSystem elevator;
    private FlywheelSystem flywheel;
    private RotationSystem rotation;

    AutoPaths(AutoDrive autoDrive, ElevatorSystem elevator, FlywheelSystem flywheel, RotationSystem rotation){
        this.autoDrive = autoDrive;
        this.elevator = elevator;
        this.flywheel = flywheel;
        this.rotation = rotation;
    }

    //Put initial positions, sensor resets, or other actions needed at the start of EVERY auto path
    private void begin(){
        rotation.setIntakePosition(RotationPosition.IntakeUpPosition);
        elevator.setToHeight(ElevatorPosition.Exchange);
    }

    void centerToLeftSwitch() {
        begin();
        autoDrive.setDistance(25, true);
        waitDrive(true, true, true);
        autoDrive.setAngle(270, true);
        waitDrive(false, false, false);
        elevator.startTimer();
        autoDrive.setDistance(100, true);
        waitDrive(true, true, false);
        autoDrive.setAngle(90, true);
        waitDrive(false, false, false);
        elevator.setToHeight(ElevatorPosition.Switch);
        autoDrive.setDistance(40, true);
        waitDrive(true, true, false);
        autoDrive.crawl(true);
        elevator.stop();
        rotation.setIntakePosition(RotationPosition.IntakeDownPosition);
        sleepAndRun(2, () -> rotation.autoMoveIntake());
        flywheel.output();
        sleep(2);
        flywheel.stop();
    }

    void centerToRightSwitch(){
        begin();
        elevator.setToHeight(ElevatorPosition.Switch);
        autoDrive.setDistance(75, true);
        waitDrive(true, true, false);
        elevator.stop();
        autoDrive.crawl(true);
        sleep(0.6);
        flywheel.output();
        sleep(2);
        flywheel.stop();
    }

    void driveToLine(){
        begin();
        autoDrive.setDistance(120, true);
        autoDrive.driveToDistance();
        waitDrive(true, false, false);
    }

    //stops the thread until the time has passed
    private void sleep(double seconds){
        long time = Math.round(seconds * 1000);
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //stops the thread and runs the periodic function until the time has passed
    private void sleepAndRun(double seconds, Runnable periodic){
        periodic.run();
        Timer t = new Timer();
        t.start();
        while (t.get() < seconds){
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            periodic.run();
        }
    }

    //stops the thread and runs the periodic function until the expression is true
    private void waitForTrue(Supplier<Boolean> expression, Runnable periodic) {
        periodic.run();
        while (!expression.get()) {
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
                return;
            }
            periodic.run();
        }
    }

    //the wait function used while driving
    //distance false is angle, elevator/rotation true will move those subsystems while driving
    // if set to a position (does not work while turning)
    private void waitDrive(boolean distance, boolean elevator, boolean rotation){
        if (distance){
            waitForTrue(() -> autoDrive.isAtDistance(), () -> {
                autoDrive.driveToDistance();
                if (elevator) this.elevator.moveToHeight(true);
                if (rotation) this.rotation.autoMoveIntake();
            });
        } else {
            waitForTrue(() -> autoDrive.isAtAngle(), () -> autoDrive.turnToAngle());
        }
    }
}
