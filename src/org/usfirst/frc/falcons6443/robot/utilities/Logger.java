package org.usfirst.frc.falcons6443.robot.utilities;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.usfirst.frc.falcons6443.robot.utilities.enums.LoggerSystems;

public class Logger {

    private static Stopwatch stopwatch;
    private static String startTime;
    private static int numberOfSystems = 10;
    //private static int cacheSize = 25;
    private static boolean disabled;
    private static boolean initOne = true;

    private static String[] oldMessageName = new String[numberOfSystems];
    private static String[] oldMessage = new String[numberOfSystems];
    private static int[] condenser = new int[numberOfSystems];
    // private static int[] cacheNumber = new int[numberOfSystems];
    private static boolean[] logOne = new boolean[numberOfSystems];

    //Run in autonomousInit and teleopInit
    public static void init(){
        disabled = false;
        initiateTimer();
        if (initOne){
            startTime = clockTimeStamp();
            initOne = false;
            for (int i = 0; i < numberOfSystems; i++){
                oldMessageName[i] = "";
                oldMessage[i] = "";
                condenser[i] = 0;
                //cacheNumber[i] = 0;
                logOne[i] = true;
            }
        }
    }

    //Run in disabledInit
    public static void disabled(){
        disabled = true;
        Logger.log(LoggerSystems.Master,"disabled", "disabled");
        Logger.log(LoggerSystems.Drive,"disabled", "disabled");
        Logger.log(LoggerSystems.Elevator,"disabled", "disabled");
        Logger.log(LoggerSystems.Intake,"disabled", "disabled");
        Logger.log(LoggerSystems.Gyro,"disabled", "disabled");
        Logger.log(LoggerSystems.Auto,"disabled", "disabled");
    }

    //Run to log, using system, message name, and message
    public static void log(LoggerSystems system, String messageName, String message){
        logInterior(messageName, message);
        logInterior(system, messageName, message);
    }

    private static void logInterior(String messageName, String message){
        LoggerSystems system = LoggerSystems.Master;

        if(logOne[system.getValue()]){
            oldMessageName[system.getValue()] = messageName;
            oldMessage[system.getValue()] = message;
            logOne[system.getValue()] = false;
        }

        String out;
        if(messageName.equals(oldMessageName[system.getValue()]) && message.equals(oldMessage[system.getValue()])){
            condenser[system.getValue()]++;
        } else if(condenser[system.getValue()] > 1) {
            out = oldMessageName[system.getValue()] + ":" + oldMessage[system.getValue()] + "(X" + condenser[system.getValue()] + ")";
            print(system, out);
            oldMessageName[system.getValue()] = messageName;
            oldMessage[system.getValue()] = message;
            condenser[system.getValue()] = 0;
        } else {
            out = oldMessageName[system.getValue()] + ":" + oldMessage[system.getValue()];
            print(system, out);
            oldMessageName[system.getValue()] = messageName;
            oldMessage[system.getValue()] = message;
        }
    }

    private static void logInterior(LoggerSystems system, String messageName, String message){
        if(logOne[system.getValue()]){
            oldMessageName[system.getValue()] = messageName;
            oldMessage[system.getValue()] = message;
            logOne[system.getValue()] = false;
        }

        String out;
        if(messageName.equals(oldMessageName[system.getValue()]) && message.equals(oldMessage[system.getValue()])){
            condenser[system.getValue()]++;
        } else if(condenser[system.getValue()] > 1) {
            out = oldMessageName[system.getValue()] + ":" + oldMessage[system.getValue()] + "(X" + condenser[system.getValue()] + ")";
            print(system, out);
            oldMessageName[system.getValue()] = messageName;
            oldMessage[system.getValue()] = message;
            condenser[system.getValue()] = 0;
        } else {
            out = oldMessageName[system.getValue()] + ":" + oldMessage[system.getValue()];
            print(system, out);
            oldMessageName[system.getValue()] = messageName;
            oldMessage[system.getValue()] = message;
        }
    }

    private static void print(LoggerSystems system, String oldMessage) {
        String fileName = "/home/lvuser/logs/" + dateStamp() + "/" + system + "/" + startTime + ".txt" /*".json"*/;
        File file = new File(fileName);
        file.getParentFile().mkdirs();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, true))) {
            //if (cacheNumber[system.getValue()] < cacheSize) {
            bw.write(oldMessage);
            bw.newLine();
            bw.write(timeStamp() + ", " + clockTimeStamp());
            bw.newLine();
            bw.flush();
            bw.close();
            //cacheNumber[system.getValue()] = 0;
        } catch (IOException e) {
            e.printStackTrace();
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
}