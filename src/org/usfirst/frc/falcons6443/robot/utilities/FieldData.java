package org.usfirst.frc.falcons6443.robot.utilities;

import edu.wpi.first.wpilibj.DriverStation;

/**
 *
 * This command serves as a clean interface
 * with the FMS.  It simply returns an enumerated
 * type depending on the values of the characters
 * from the FMS.  It can be used to help decide
 * which commands to run.
 *
 *  @author Aleks Vidmantas
 */

public class FieldData {

    private enum Position{
        LEFT, RIGHT
    }

    private static String gameData = DriverStation.getInstance().getGameSpecificMessage();

    /*
    * Methods that use the ternary operator to
    * get the position the game objects as an enum
    * */
    public static Position getNearSwitch(){
        return (gameData.charAt(0) == 'L') ? Position.LEFT : Position.RIGHT;
    }

    public static Position getScale(){
        return (gameData.charAt(1) == 'L') ? Position.LEFT : Position.RIGHT;
    }

    public static Position getFarSwitch(){
        return (gameData.charAt(2) == 'L') ? Position.LEFT : Position.RIGHT;
    }

    //Possibly useless method, shortens things up
    public static String getGameData(){
        return gameData;
    }
}
