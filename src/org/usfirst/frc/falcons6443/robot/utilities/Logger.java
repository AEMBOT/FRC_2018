package org.usfirst.frc.falcons6443.robot.utilities;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.falcons6443.robot.RobotMap;
import org.usfirst.frc.falcons6443.robot.utilities.enums.LoggerSystems;

public class Logger {

    private static Stopwatch stopwatch;
    private static String startTime;
    private static boolean initOne = true;
    private static boolean disabled = true;

    private static LoggerSystems[] system = LoggerSystems.values();
    private static String[] oldMessage = new String[LoggerSystems.values().length];
    private static int[] condenser = new int[LoggerSystems.values().length];
    private static boolean[] logOne = new boolean[LoggerSystems.values().length];

    //Run in autonomousInit
    public static void autoInit(){
        init();
        for (LoggerSystems system : system){
            log(system, "AUTONOMOUS");
        }
    }

    //Run in teleopInit
    public static void teleopInit(){
        init();
        for (LoggerSystems system : system){
            log(system, "TELEOP");
        }
    }

    //Run in disabledInit
    public static void disabled(){
        disabled = true;
        for (LoggerSystems system : system){
            log(system, "DISABLED");
        }
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
            message = system + ": " + message;
            system = LoggerSystems.All;
        }

        String out;

        if(logOne[system.ordinal()]){
            oldMessage[system.ordinal()] = message;
            logOne[system.ordinal()] = false;
        } else if(message.equals(oldMessage[system.ordinal()])){
            condenser[system.ordinal()]++;
        } else if(condenser[system.ordinal()] > 1) {
            out = oldMessage[system.ordinal()] + "(X" + condenser[system.ordinal()] + ")";
            print(system, out);
            oldMessage[system.ordinal()] = message;
            condenser[system.ordinal()] = 0;
        } else {
            out = oldMessage[system.ordinal()];
            print(system, out);
            oldMessage[system.ordinal()] = message;
        }
    }

    private static void print(LoggerSystems system, String oldMessage) {
        if (startTime != null) {
            String fileName = "/home/lvuser/logs/" + dateStamp() + "/" + system + "/" + startTime + ".txt";
            File file = new File(fileName);
            file.getParentFile().mkdirs();
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, true))) {
                bw.write(oldMessage);
                bw.newLine();
                bw.write(timeStamp() + ", " + clockTimeStamp());
                bw.newLine();
                if(disabled) bw.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void init(){
        if(RobotMap.Logger){
            disabled = false;
            initiateTimer();
            if (initOne){
                startTime = clockTimeStamp();
                initOne = false;
                for (int i = 0; i < LoggerSystems.values().length; i++){
                    oldMessage[i] = "";
                    condenser[i] = 0;
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
}