package org.usfirst.frc.falcons6443.robot.utilities;

/**
 * Toggle Latch
 */
public class Toggle {
    private boolean m_state;
    private boolean m_feedback;

    /**
     * Creates a new toggle set to false
     */
    public Toggle() {
        m_feedback = false;
        m_state = false;
    }

    /**
     * Creates a new toggle set to an initial state
     *
     * @param initialState state to initialize the toggle with
     */
    public Toggle(boolean initialState) {
        m_feedback = false;
        m_state = initialState;
    }

    /**
     * Set or get the state of the toggle latch
     */
    public boolean getState() {
        return m_state;
    }

    public void setState(boolean value) {
        if (value && !m_feedback)
            m_state = !m_state;

        m_feedback = value;
    }

    /**
     * Force the internal state of the latch to a value
     */
    public void force(boolean value) {
        m_state = value;
    }
}