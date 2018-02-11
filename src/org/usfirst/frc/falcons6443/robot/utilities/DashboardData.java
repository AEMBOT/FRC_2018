package org.usfirst.frc.falcons6443.robot.utilities;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.falcons6443.robot.commands.AutoChooser;


/**
 * Adds the sendable chooser options to the smart dashboard.
 *
 * @author Goirick Saha
 */

public class DashboardData {
    private SendableChooser autoChoice;

    public DashboardData() {
       autoChoice = new SendableChooser();
       autoChoice.addDefault("Left", AutoChooser.Position.LEFT);
       autoChoice.addObject("Center", AutoChooser.Position.CENTER);
       autoChoice.addObject("Right", AutoChooser.Position.RIGHT);
    }

    public AutoChooser.Position getSelectedPos() {
        return (AutoChooser.Position)autoChoice.getSelected();
    }

}
