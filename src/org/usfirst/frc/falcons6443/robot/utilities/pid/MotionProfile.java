package org.usfirst.frc.falcons6443.robot.utilities.pid;

import edu.wpi.first.wpilibj.Timer;

/*
 * Equations from ChiefDelphi user 'Ether':
 * http://www.chiefdelphi.com/media/papers/download/4496
 *
 * Desmos graph of distance, velocity, and acceleration (modify at will. Example values: D = 5, MaxAcceleration = 3.5):
 * https://www.desmos.com/calculator/la5fv4ohqy
 *
 * Use the MotionProfile class with a PID. Feed the getDistance() (or speed or acceleration) into
 * the setDesiredValue() of the PID.
 */
public class MotionProfile {

    private Timer timer;
    private double T; //time to destination
    private double K1;
    private double K2;
    private double K3;
    private double MaxAcceleration;
    private double maxSpeed;

    public MotionProfile(double distance, double maxAcceleration){
        timer = new Timer();
        calculate(distance, maxAcceleration);
    }

    //helps trial and error different max accelerations to find an acceptable max speed. Prints results.
    public MotionProfile(double distance, double maxAcceleration, double desiredMaxSpeed){
        this(distance, maxAcceleration);
        if(maxSpeed <= desiredMaxSpeed) System.out.println("Max speed calculations correct. Max speed: " + maxSpeed);
        while (maxSpeed > desiredMaxSpeed) {
            double newMaxAcceleration;
            System.out.println("Max speed higher than desired max speed!");
            newMaxAcceleration = (Math.pow(desiredMaxSpeed, 2) - Math.pow(desiredMaxSpeed - maxSpeed, 2) / (2 * Math.PI * distance));
            System.out.println("Changing max acceleration to: " + newMaxAcceleration);
            calculate(distance, maxAcceleration);
        }
    }

    //calculates constants. DO NOT MODIFY THIS!!!
    private void calculate(double distance, double maxAcceleration){
        MaxAcceleration = maxAcceleration;
        T = Math.sqrt((2*Math.PI*distance)/ MaxAcceleration);
        K1 = (2*Math.PI)/T;
        K2 = MaxAcceleration /K1;
        K3 = 1/K1;
        maxSpeed = 2*K2;
    }

    //start timer to start the motion profiler
    public void startTimer() { timer.start(); }

    //stop timer to stop the motion profiler from updating getDistance (or speed or acceleration)
    public void stopTimer() { timer.stop(); }

    //reset timer every time before you use the motion profiler
    public void resetTimer() { timer.reset(); }

    public double getTime() { return timer.get(); }

    //get acceleration is the acceleration you should be at at timer.get()
    public double getAcceleration() { return MaxAcceleration * Math.sin(K1 * timer.get()); }

    //get speed is the speed you should be at timer.get()
    public double getSpeed(){
        return K2 * (1 - Math.cos(K1 * timer.get()));
    }

    //get distance is where you should be at timer.get()
    public double getDistance(){
        return K2 * (timer.get() - K3 * Math.sin(K1 * timer.get()));
    }

    public void printExpectedTime(){
        System.out.println("Motion profile, expected time: " + T);
    }

    public void printMaxSpeed(){
        System.out.println("Motion profile, max speed: " + maxSpeed);
    }

    //helps to check values that feed into max speed
    public void printMaxSpeed(double desiredMaxSpeed){
        if(maxSpeed > desiredMaxSpeed) {
            System.out.println("Max speed higher than desired max speed!");
            System.out.println("Motion profile, desired  max speed: " + desiredMaxSpeed);
        }
        System.out.println("Motion profile, max speed: " + maxSpeed);
    }
}
