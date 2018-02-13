package org.usfirst.frc.falcons6443.robot.communication;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

import java.util.ArrayList;

/**
 * This class will make it easier to work with network tables.
 *
 * @author Aleks Vidmantas
 */
public class TableHandler {

    NetworkTableInstance inst;
    NetworkTable table;

    public TableHandler(){
        inst = NetworkTableInstance.getDefault();
        inst.startClientTeam(6443);
        table = inst.getTable("test");
        NetworkTableEntry entry1 = table.getEntry("y");
        entry1.setString("Boogaloo");
    }

    public String getTest(){
        return table.getEntry("y").getString("No string found :(");
    }


}
