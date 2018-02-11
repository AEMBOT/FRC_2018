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

    private enum GameObject {
        NEARSWITCH, SCALE, FARSWITCH
    }

    private enum Position{
        LEFT, RIGHT, UNKNOWN
    }

    private static String gameData;

    /*
    * Methods that use the ternary operator to
    * get the position the game objects as an enum
    * */

    public static void update(){
        gameData = DriverStation.getInstance().getGameSpecificMessage();
    }

    //Designed to be null safe
    public static Position getObjectSide(GameObject gameObject){

        if(gameData.isEmpty()){
            update();
        }else{

            switch (gameObject){
                case SCALE:
                    return getScale();
                case NEARSWITCH:
                    return getNearSwitch();
                case FARSWITCH:
                    return getFarSwitch();

            }
        }

        return Position.UNKNOWN;
    }

    //Returns Postion type enum
    public static Position getNearSwitch(){
        return (gameData.charAt(0) == 'L') ? Position.LEFT : Position.RIGHT;
    }

    public static Position getScale(){
        return (gameData.charAt(1) == 'L') ? Position.LEFT : Position.RIGHT;
    }

    public static Position getFarSwitch(){
        return (gameData.charAt(2) == 'L') ? Position.LEFT : Position.RIGHT;
    }

    //Returns char at specifc place
    public static char getCharSwitch(){return gameData.charAt(1);}

    public static char getCharScale(){return gameData.charAt(2);}

    public static char getCharFarSwitch(){return gameData.charAt(3);}



    //Possibly useless method, shortens things up
    public static String getGameData(){
        return gameData;
    }
}
