package org.usfirst.frc.falcons6443.robot.utilities.drive;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class DriveSmoother implements Runnable{
    private int _loopDelayMs = 20;
    private double _maxChange = 0.05;

    private DifferentialDrive _drive;
    private boolean _loopRunning = false;

    private double _leftPowerDesired = 0;
    private double _rightPowerDesired = 0;

    private double _leftPowerActual = 0;
    private double _rightPowerActual = 0;

    public DriveSmoother(DifferentialDrive drive){
        _drive = drive;
    }

    public void Start(){
        _loopRunning = true;
        Thread runThread = new Thread(this);
        runThread.start();
    }

    public void Stop(){
        _loopRunning = false;
    }

    public void SetPower(double leftPower, double rightPower){
        _leftPowerDesired = leftPower;
        _rightPowerDesired = rightPower;
    }

    //This will run continually in its own thread
    @Override
    public void run() {
        while (_loopRunning){
            boolean changed = false;

            //For both the left and the right, if the desired power differs from the actual power, then apply a change.
            //The change should be no larger than the value in _maxChange
            if (_leftPowerDesired != _leftPowerActual){
                double change = CalculatePowerChange(_leftPowerDesired, _leftPowerActual);
                _leftPowerActual += change;
                changed = true;
            }

            if (_rightPowerDesired != _rightPowerActual){
                double change = CalculatePowerChange(_rightPowerDesired, _rightPowerActual);
                _rightPowerActual += change;
                changed = true;
            }

            //If we changed the power during this loop iteration, then send the new values to the motors.
            if (changed){
                _drive.tankDrive(_leftPowerActual, _rightPowerActual);
            }

            try {
                Thread.sleep(_loopDelayMs);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private double CalculatePowerChange(double desired, double actual){
        double powerChange = desired - actual;

        if (powerChange > _maxChange){
            powerChange = _maxChange;
        }
        else if (-powerChange < -_maxChange){
            powerChange = -_maxChange;
        }

        return powerChange;
    }




}