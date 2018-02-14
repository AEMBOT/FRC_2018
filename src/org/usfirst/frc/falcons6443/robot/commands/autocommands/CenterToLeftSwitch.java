package org.usfirst.frc.falcons6443.robot.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.falcons6443.robot.commands.autonomous.DriveToDistance;
import org.usfirst.frc.falcons6443.robot.commands.autonomous.RotateToAngle;
import org.usfirst.frc.falcons6443.robot.subsystems.FlywheelSystem;

/**
 * Command to move to the left switch from the right starting position and place block
 *
 * @author Goirick Saha
 */

public class CenterToLeftSwitch extends CommandGroup {

    private FlywheelSystem flywheelSystem;
    //private Delay delay;


    public CenterToLeftSwitch() {

        addSequential(new DriveToDistance(45));
        addSequential(new RotateToAngle(270)); //Turns 90 degrees left. Since 90 is to the right 270 would be 90 to the left.
        addSequential(new DriveToDistance(110));
        addSequential(new RotateToAngle(180));
        addSequential(new DriveToDistance(24));

        //Elevator code to go here

        flywheelSystem = new FlywheelSystem();

        flywheelSystem.output();
        //delay = new Delay(2);
        //flywheelSystem.stop();
        //Commented these out since the flywheel can be kept on.

    }
}
