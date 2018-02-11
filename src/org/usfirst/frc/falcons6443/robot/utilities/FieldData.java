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
 *  @author Aleks Vidmantas, Owen Engbretson
 *
 */

public class FieldData {

    //The main game pieces, for now assume SWITCH is closest switch
    //because no one will need the FARSWITCH
    public enum Object {
        SWITCH, SCALE, FARSWITCH
    }

    private enum Position{
        LEFT, RIGHT, UNKNOWN
    }

    private static String gameData;

    //Simple char collection of gameData that handles empty strings
    public static char getChar(Object object){

        update();
        if(!gameData.isEmpty()){
            switch (object) {
                case SWITCH:
                    return gameData.charAt(0);
                case SCALE:
                    return gameData.charAt(1);
                case FARSWITCH:
                    return gameData.charAt(2);
            }
        }
        return 'X'; //in case something went wrong getting data
    }

    //enum version of getChar()
    public static Position getPos(Object object){

        update();
        if(!gameData.isEmpty()){
            switch (object) {
                case SWITCH:
                    return (gameData.charAt(0) == 'L') ? Position.LEFT : Position.RIGHT ;
                case SCALE:
                    return (gameData.charAt(1) == 'L') ? Position.LEFT : Position.RIGHT;
                case FARSWITCH:
                    return (gameData.charAt(2) == 'L') ? Position.LEFT : Position.RIGHT;
            }
        }
        return Position.UNKNOWN;
    }

    //updates, should be called after autoInit() is called
    private static void update(){
        gameData = DriverStation.getInstance().getGameSpecificMessage();
    }

    //Possibly useless method, shortens things up
    public static String getGameData(){
        update();
        return gameData;
    }
}
