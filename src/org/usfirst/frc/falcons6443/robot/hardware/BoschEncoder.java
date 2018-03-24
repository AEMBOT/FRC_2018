package org.usfirst.frc.falcons6443.robot.hardware;

import edu.wpi.first.wpilibj.Counter;
//ADD LIMIT SWITCH RESET

public class BoschEncoder {
    private Counter encoder;
    private boolean m_forward;
    private double m_lastCount;

    public BoschEncoder(int port){
        encoder = new Counter(port);
        m_forward = true;
    }

    public void reset(){
        encoder.reset();
    }

    public double getValueReal(){
        return encoder.get();
    }

    public double getValue(){
        if(getDirection()){
            m_lastCount += encoder.get();
            reset();
            return m_lastCount;
        } else {
            m_lastCount -= encoder.get();
            reset();
            return m_lastCount;
        }
    }

    public void setDirection(boolean forward){
        if(m_forward != forward && forward){
            m_forward = forward;
            m_lastCount = getValue();
        }
        if(m_forward != forward && !forward){
            m_forward = forward;
            m_lastCount = getValue();
        }

    }

    public boolean getDirection(){
        return m_forward;
    }
}