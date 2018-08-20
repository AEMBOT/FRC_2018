package org.usfirst.frc.falcons6443.robot.Autonomous;

import edu.wpi.first.wpilibj.Timer;
import org.usfirst.frc.falcons6443.robot.subsystems.ElevatorSystem;
import org.usfirst.frc.falcons6443.robot.subsystems.FlywheelSystem;
import org.usfirst.frc.falcons6443.robot.subsystems.RotationSystem;
import org.usfirst.frc.falcons6443.robot.utilities.enums.ElevatorPosition;
import org.usfirst.frc.falcons6443.robot.utilities.enums.RotationPosition;
import java.util.function.Supplier;

public class AutoPath{

    private AutoDrive autoDrive;
    private ElevatorSystem elevator;
    private FlywheelSystem flywheel;
    private RotationSystem rotation;

    public AutoPath(AutoDrive autoDrive, ElevatorSystem elevator, FlywheelSystem flywheel, RotationSystem rotation){
        this.autoDrive = autoDrive;
        this.elevator = elevator;
        this.flywheel = flywheel;
        this.rotation = rotation;
    }

    private void begin(){
        rotation.setIntakePosition(RotationPosition.IntakeUpPosition);
        elevator.setToHeight(ElevatorPosition.Exchange);
    }

    public void centerToLeftSwitch() {
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

    public void centerToRightSwitch(){
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

    private void sleep(double seconds){
        long time = Math.round(seconds * 1000);
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void waitFor(Supplier<Boolean> expression, Runnable periodic) {
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

    private void waitDrive(boolean distance, boolean elevator, boolean rotation){
        if (distance){
            waitFor(() -> autoDrive.isAtDistance(), () -> {
                autoDrive.driveToDistance();
                if (elevator) this.elevator.moveToHeight(true);
                if (rotation) this.rotation.autoMoveIntake();
            });
        } else {
            waitFor(() -> autoDrive.isAtAngle(), () -> autoDrive.turnToAngle());
        }
    }
}
