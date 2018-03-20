package org.usfirst.frc.falcons6443.robot.utilities;

import java.util.Date;

public class Stopwatch {

    /**
     * Don't stop and start as these aren't completed yet.
     * @author Aleks Vidmantas
     * */

    private Date date;
    private long timeAt = 0;
    private boolean isStopped = false;
    private long startTime = 0; //time when the Stopwatch is created
    private long elapseStart = 0;
    private long elapseEnd = 0;
    private long elapsed  = 0;


    /**
     * @param startAtDefault Lets the user choose to have
     * the timer start ticking at initialization
     * */
    public Stopwatch(boolean startAtDefault){
        startTime = System.currentTimeMillis();

        if(startAtDefault){
            isStopped = false;
        }else{
            isStopped = true;
        }
    }

    //stops the timer
    public void stop(){
        timeAt = (System.currentTimeMillis() - startTime); //time at is clean time
        elapseStart = System.currentTimeMillis();
        isStopped = true;
    }

    public void start(){
        isStopped = false;
        elapseEnd = System.currentTimeMillis();
        elapsed = elapsed + elapseEnd - elapseStart;
        System.out.println("ELAPSED: " + elapsed);
    }

    public long getTime(){
        long time = 0;
        time = System.currentTimeMillis() - startTime - elapsed;
        if(isStopped){
            System.out.println("getTime isTopped");
            return (timeAt);
        }else{
            System.out.println("GetTime isn't stopped");
            return System.currentTimeMillis() - startTime - elapsed;
        }
    }


}
