package org.usfirst.frc.falcons6443.robot.communication;

//package org.usfirst.frc.falcons6443.robot.communication;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

/**
 * This is a static accessor class used to ease the
 * use of WPI's cumbersome network tables.  Useful terminology:
 *
 *      * NetworkTable - A table that is shared with any devices connected to the router on the robot.
 *          Examples: SmartDashboard, LiveWindow
 *
 *      * NetworkTableEntry - An entry in a network table that can store any one of a number, string, or boolean
 *          Exampels: SmartDashboard/isAutoOn where isAutoOn stores a boolean
 *
 *      * NetworkTableInstance - The overarching table instace.  Unimportant unless using multiple network table instances
 * WIP
 * @author Aleks Vidmantas
 */
public class NetTables {

    /**
     * Gets the specific table from the default network table instance
     *
     * @param table the specific table key used to retrieve a table
     * @return the NetworkTable that has the key of the table string
     * */
    public static NetworkTable getTable(String table){
        return NetworkTableInstance.getDefault().getTable(table);
    }

    /**
     * Gets entry in a network table. Entry can store a value.
     *
     * @param table the key that is used to get a specific NetworkTable that will house the entry
     * @param entry the specific entry from the specific table
     * @return a NetworkTableEntry capable of storing a value
     *
     * */
    public static NetworkTableEntry getEntry(String table, String entry){
        return NetworkTableInstance.getDefault().getTable(table).getEntry(entry);
    }

    /**
     * Gets entry in a network table. Entry can store a value.
     *
     * @param nt the specific table that holds a specific entry
     * @param entry the specific entry from the specific table
     * @return a NetworkTableEntry capable of storing a value
     *
     * */
    public static NetworkTableEntry getEntry(NetworkTable nt, String entry){
        return nt.getEntry(entry);
    }

    /**
     * Returns an entry from the Default table
     *
     * @param entry the key that will be used to find the entry
     * @return a specified entry from the Default table
     * */
    public static NetworkTableEntry getEntry(String entry){
        return getEntry("Default", entry);
    }

    /**
     * Sets an entry from a specific table to store a string.
     *
     * @param table the key that is used to get a specific NetworkTable that will house the entry
     * @param entry the specific entry from the specific table
     * @param text string that the entry will now store
     *
     * */
    public static void setString(String table, String entry, String text){
        getEntry(table, entry).setString(text);
    }

    /**
     * Sets an entry from the Default table to store a string.
     *
     * @param entry the specific entry from the specific table
     * @param text string that the entry will now store
     *
     * */
    public static void setString(String entry, String text){
        setString("Default", entry, text);
    }

    /**
     * Sets a specific entry from a specific table to store a boolean
     * @param entry the specific entry from the specific table
     * @param table specific table that the entry rests in
     * @param bool boolean that entry will now have
     *
     * */
    public static void setBoolean(String table, String entry, boolean bool){
        getTable(table).getEntry(entry).setBoolean(bool);
    }

    /**
     * Sets a specific entry to store a boolean in the Default table
     * @param entry the specific entry from the specific table
     * @param bool boolean that entry will now have
     *
     * */
    public static void setBoolean(String entry, boolean bool){
        setBoolean("Default", entry, bool);
    }

    /**
     * Sets a specific entry to a double value from a specifc table
     * @param table the specific table where the entry is located
     * @param entry the specific entry that will would the double
     * @param val the double that entry will soon hold
     * */
    public static void setDouble(String table, String entry, double val){
        getTable(table).getEntry(entry).setDouble(val);
    }

    /**
     * Sets a specific entry to a double value from a specifc table
     * @param val the double that entry will soon hold
     * @param entry the specific entry that will would the double
     * */
    public static void setDouble(String entry, double val){
        setDouble("Default", entry, val);
    }

    /**
     * Sets a specicic entry from a specific table to hold a number.
     *
     * @param table table where the entry belongs in
     * @param entry specific entry that will house number
     * @param number specific number, can be anything
     * */
    public static void setNumber(String table, String entry, Number number){
        getTable(table).getEntry(entry).setNumber(number);
    }

    /**
     * Sets a specicic entry from the Default table to hold a number.
     *
     * @param entry specific entry that will house number
     * @param number specific number, can be anything
     * */
    public static void setNumber(String entry, Number number){
        getEntry("Default",entry).setNumber(number);
    }

    public static void flush(){
        NetworkTableInstance.getDefault().flush();
    }

}