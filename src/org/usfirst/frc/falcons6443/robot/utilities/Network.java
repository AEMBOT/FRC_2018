package org.usfirst.frc.falcons6443.robot.utilities;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

/**
 * This class will make it easier to work with network tables.
 *
 * @author Aleks Vidmantas
 */

public class Network {
    
    NetworkTableInstance inst;

    public Network(){
        inst = NetworkTableInstance.getDefault();
        NetworkTable table = inst.getTable("datatable");
        NetworkTableEntry entry1 = table.getEntry("Y");
        inst.startClientTeam(6443);
    }

}
