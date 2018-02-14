package org.usfirst.frc.falcons6443.robot.utilities;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Utility class for logging values because the default
 * driver station is not adequate for log monitoring.
 *
 * @author Aleks Vidmantas
 */

public class Logger {

    public Logger() {
    }

    public static void log(String str, Path path) throws IOException {
        List<String> lines = Arrays.asList(str);
        Files.write(path, lines,  Charset.forName("UTF-8"));
    }

    //main method that will be used
    public static void log(String str) {

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat smallTime = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat grandelTime = new SimpleDateFormat("MM:DD:yy");
        String textFileName = "src\\org\\usfirst\\frc\\falcons6443\\robot\\utilities\\log\\log-"+cal.getTime() +".txt";

        List<String> lines = Arrays.asList(str);
        String finalName = "";
        for(int i = 0; i < textFileName.length(); i++){
            if( !(textFileName.charAt(i) == ':')){
                finalName += textFileName.charAt(i);
            }else{
                finalName += '-';
            }
        }

        Path path = Paths.get(""+ finalName);
        try {
            Files.write(path, lines,  Charset.forName("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //alternate main method
    public static void logAndForget(String text){
        forceDelete("src\\org\\usfirst\\frc\\falcons6443\\robot\\utilities\\log\\");
        log(text);
    }

    public static void delete(Path file) throws IOException {
        Files.delete(file);
    }

    public static void delete(String path)  {
        Path file = Paths.get(path);
        try {
            Files.delete(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void forceDelete(String path)  {
        Path file = Paths.get(path);
        try {
            Files.deleteIfExists(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public static void addFile(String path, List list) throws IOException {
        Path file = Paths.get(path);
        //List<String> lines = Arrays.asList(txt);
        Files.write(file, list, Charset.forName("UTF-8"));
    }

    public static void toString(String path) throws IOException {
        Path file = Paths.get(path);
        System.out.println(Files.readAllLines(file, Charset.forName("UTF-8")));
    }

    public static String getString(String path) throws IOException {
        Path file = Paths.get(path);
        String ret = Files.readAllLines(file, Charset.forName("UTF-8")).toString();
        return ret;
    }

}