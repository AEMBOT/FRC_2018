package org.usfirst.frc.falcons6443.robot.utilities;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class Reader {

    FileReader fileReader;
    BufferedReader bufferedReader;
    String fileName;
    File[] files;
    List<String> results = new ArrayList<String>();
//home/lvuser/logs/2018-04-05/drive
    public Reader() throws FileNotFoundException {

        files = new File("/home/lvuser/logs/2018-04-05/drive").listFiles();
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for(File f : files){
            if(f.isFile()){
                System.out.println(f.toString());
            }
        }
       // fileName = "/home/lvuser/logs/2018-04-05/drive/17-49-12-0770.txt";
       // fileReader = new FileReader(fileName);
        //bufferedReader = new BufferedReader(fileReader);
    }

    public String readLine(int line) throws IOException {
        for(File f : files){
            if(f.isFile()){
                System.out.println(f.toString());
            }
        }/*
        String output = bufferedReader.readLine();
        bufferedReader.close();
        */

        return "";
    }

}
