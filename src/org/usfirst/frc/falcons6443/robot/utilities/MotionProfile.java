package org.usfirst.frc.falcons6443.robot.utilities;

/*
//Equations from ChiefDelphi user 'Ether'
//http://www.chiefdelphi.com/media/papers/download/4496
*/
public class MotionProfile {

    private double T; //time to destination
    private double K1;
    private double K2;
    private double K3;
    private double maxSpeed;
    private double M; //maxDesiredAcceleration //find suitable value!

    public MotionProfile(double distance){
        calculations(distance, true, 0);
    }

    public MotionProfile(double distance, double maxDesiredAcceleration, double maxDesiredSpeed){
        M = maxDesiredAcceleration;
        calculations(distance, true, 0);
        if(maxSpeed > maxDesiredSpeed) {
            System.out.println("Max speed higher than desired max speed! Lower Max desired Acceleration");
        }
    }

    public MotionProfile(double distance, double desiredTime, double maxDesiredSpeed, boolean usingTime){ //boolean does nothing
        calculations(distance, false, desiredTime);
        if(maxSpeed > maxDesiredSpeed) {
            System.out.println("Max speed higher than desired max speed! Lower Max desired Acceleration");
        }
    }

    private void calculations(double distance, boolean defaultVals, double desiredTime){
        if(defaultVals){
            T = Math.sqrt((2*Math.PI*distance)/M);
        } else {
            T = desiredTime;
            M = (2 * Math.PI * distance) / (T * T);
        }
        K1 = (2*Math.PI)/T;
        K2 = M/K1;
        K3 = 1/K1;
        maxSpeed = 2*K2;
    }

    public double acceleration(double currentTime){
        return M * Math.sin(K1*currentTime);
    }

    public double speed(double currentTime){
        return K2 * (1 - Math.cos(K1*currentTime));
    }

    public double distance(double currentTime){
        return K2 * (currentTime - K3 * Math.sin(K1 * currentTime));
    }

    public void printExpectedTime(){
        System.out.println("Motion profile, expected time: " + T);
    }

    public void printMaxSpeed(){
        System.out.println("Motion profile, max speed: " + maxSpeed);
    }
}
