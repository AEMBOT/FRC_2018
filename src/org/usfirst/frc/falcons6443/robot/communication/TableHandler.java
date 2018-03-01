package org.usfirst.frc.falcons6443.robot.communication;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

/**
 * This class will make it easier to work with network tables.
 * It will serve as an interface to quickly log and retrieve data.
 *
 * @author Aleks Vidmantas
 */
public class TableHandler {

    static NetworkTableInstance inst;
    static NetworkTable table;

    //TODO: clean after tested
    public static void init(){
        inst = NetworkTableInstance.getDefault();
        inst.startClientTeam(6443);
        table = inst.getTable("test");
        NetworkTableEntry entry1 = table.getEntry("y");
        entry1.setString("Boogaloo");
    }

    public static String getTest(){
        return table.getEntry("y").getString("No string found :(");
    }

    public static void logData(String key, boolean b){
        NetworkTableEntry entry = table.getEntry(key);
        entry.setBoolean(b);
    }

    public static void logNumber(String key, Number number){
        NetworkTableEntry entry = table.getEntry(key);
        entry.setNumber(number);
    }

    public static void logString(String key, String text){
        NetworkTableEntry entry = table.getEntry(key);
        entry.setString(text);
    }

    public static String getString(String key){
        return table.getEntry(key).getString("No string found :(");
    }

    public static Number getNumber(String key){
        return table.getEntry(key).getNumber(666.666);  //not 0 because 0 may come up fairly often
    }

    public static boolean getBoolean(String key){
        return table.getEntry(key).getBoolean(false); //gonna have to watch out here because you'll be getting false regardless if the table exists
    }

}
