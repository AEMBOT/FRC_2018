package org.usfirst.frc.falcons6443.robot.utilities;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.usfirst.frc.falcons6443.robot.RobotMap;
import org.usfirst.frc.falcons6443.robot.utilities.enums.LoggerSystems;

public class Logger {

    private static Stopwatch stopwatch;
    private static String startTime;
    private static int numberOfSystems = 7;
    //private static int cacheSize = 25;
    private static boolean initOne = true;

    private static String[] oldMessage = new String[numberOfSystems];
    private static int[] condenser = new int[numberOfSystems];
    // private static int[] cacheNumber = new int[numberOfSystems];
    private static boolean[] logOne = new boolean[numberOfSystems];

    //Run in autonomousInit
    public static void autoInit(){
        init();
        Logger.log(LoggerSystems.Drive,"AUTONOMOUS");
        Logger.log(LoggerSystems.Elevator,"AUTONOMOUS");
        Logger.log(LoggerSystems.Flywheel,"AUTONOMOUS");
        Logger.log(LoggerSystems.Rotation,"AUTONOMOUS");
        Logger.log(LoggerSystems.Gyro,"AUTONOMOUS");
        Logger.log(LoggerSystems.Auto,"AUTONOMOUS");
    }

    //Run in teleopInit
    public static void teleopInit(){
        init();
        Logger.log(LoggerSystems.Drive,"TELEOP");
        Logger.log(LoggerSystems.Elevator,"TELEOP");
        Logger.log(LoggerSystems.Flywheel,"TELEOP");
        Logger.log(LoggerSystems.Rotation,"TELEOP");
        Logger.log(LoggerSystems.Gyro,"TELEOP");
        Logger.log(LoggerSystems.Auto,"TELEOP");
    }

    //Run in disabledInit
    public static void disabled(){
        Logger.log(LoggerSystems.Drive,"DISABLED");
        Logger.log(LoggerSystems.Elevator,"DISABLED");
        Logger.log(LoggerSystems.Flywheel,"DISABLED");
        Logger.log(LoggerSystems.Rotation,"DISABLED");
        Logger.log(LoggerSystems.Gyro,"DISABLED");
        Logger.log(LoggerSystems.Auto,"DISABLED");
    }

    //Run to log, using system, message name, and message
    public static void log(LoggerSystems system, String message) {
        if(RobotMap.Logger){
            logInterior(system, message, true);
            if (system != LoggerSystems.All) {
                logInterior(system, message, false);
            }
        }
    }

    private static void logInterior(LoggerSystems system, String message, boolean all){
        if(all){
            message = system.getName() + ": " + message;
            system = LoggerSystems.All;
        }
        
        String out;

        if(logOne[system.getValue()]){
            oldMessage[system.getValue()] = message;
            logOne[system.getValue()] = false;
        } else if(message.equals(oldMessage[system.getValue()])){
            condenser[system.getValue()]++;
        } else if(condenser[system.getValue()] > 1) {
            out = oldMessage[system.getValue()] + "(X" + condenser[system.getValue()] + ")";
            print(system, out);
            oldMessage[system.getValue()] = message;
            condenser[system.getValue()] = 0;
        } else {
            out = oldMessage[system.getValue()];
            print(system, out);
            oldMessage[system.getValue()] = message;
        }
    }

    private static void print(LoggerSystems system, String oldMessage) {
        if (startTime != null) {
            String fileName = "/home/lvuser/logs/" + dateStamp() + "/" + system + "/" + startTime + ".txt" /*".json"*/;
            File file = new File(fileName);
            file.getParentFile().mkdirs();
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, true))) {
                //if (cacheNumber[system.getName()] < cacheSize) {
                bw.write(oldMessage);
                bw.newLine();
                bw.write(timeStamp() + ", " + clockTimeStamp());
                bw.newLine();
                bw.flush();
                bw.close();
                //cacheNumber[system.getName()] = 0;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void init(){
        if(RobotMap.Logger){
            initiateTimer();
            if (initOne){
                startTime = clockTimeStamp();
                initOne = false;
                for (int i = 0; i < numberOfSystems; i++){
                    oldMessage[i] = "";
                    condenser[i] = 0;
                    //cacheNumber[i] = 0;
                    logOne[i] = true;
                }
            }
        }
    }

    private static String millisecondStamp() {
        Date date = new Date();
        return Long.toString(date.getTime());
    }

    private static String timeStamp() {
        if(stopwatch == null){
            return ("Stopwatch hasn't been initiated");
        }else{
            return stopwatch.getTime();
        }
    }

    private static String clockTimeStamp() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("HH-mm-ss-SSSS");
        String dateString = sdf.format(date);
        dateString.replaceAll(":", "-");
        return dateString;
    }

    private static String dateStamp() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = sdf.format(date);
        dateString.replaceAll(":", "-");
        return dateString;
    }

    private static void initiateTimer(){
        if (stopwatch == null){
            stopwatch = new Stopwatch(true);
        }
    }

    public enum Dashboard {
        ONE_SEVEN_TWO, TEN, NONE
    }

    //Get programming a permanent thumbdrive!
    public static void pullLogFiles(boolean oneSevenTwo){
        Runtime runtime = Runtime.getRuntime();

        try {
            if(oneSevenTwo) runtime.exec("cmd /c start \"\" loggerRetrieval_172.bat");
            else runtime.exec("cmd /c start \"\" loggerRetrieval_10.bat");
            System.out.println("running");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}