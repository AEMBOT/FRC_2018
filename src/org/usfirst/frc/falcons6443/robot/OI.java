package org.usfirst.frc.falcons6443.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import org.usfirst.frc.falcons6443.robot.hardware.Gamepad;

import java.util.HashMap;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 *
 * @author Christopher Medlin
 */
public class OI {

    private final int GAMEPAD_PORT_NUMBER = 0;

    private Gamepad gamepad;

    private HashMap<String, Button> buttons;

    /**
     * Constructor for OI.
     */
    public OI() {
        gamepad = new Gamepad(new Joystick(GAMEPAD_PORT_NUMBER));
        buttons = new HashMap<String, Button>(4);
    }

    /**
     * Returns the Joystick associated with this OI object.
     *
     * @return the Joystick associated with this OI object.
     */
    public Gamepad getGamepad() {
        return gamepad;
    }

    /**
     * Returns the Button object associated with the key.
     *
     * @param key the name of the button.
     * @return the button associated with the key.
     */
    public Button getButton(String key) {
        return buttons.get(key);
    }
}
