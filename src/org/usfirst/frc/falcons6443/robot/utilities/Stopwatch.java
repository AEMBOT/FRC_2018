package org.usfirst.frc.falcons6443.robot.utilities;

public class Stopwatch {

    private long timeAt = 0;
    private boolean isStopped = false;
    private long startTime = 0; //time when the Stopwatch is created
    private long elapseStart = 0;
    private long elapseEnd = 0;
    private long elapsed  = 0;

    /* Gets time based on ms */
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
        //System.out.println("It is stopped.");
    }

    public void start(){
        isStopped = false;
        elapseEnd = System.currentTimeMillis();
        elapsed = elapsed + elapseEnd - elapseStart;
        //  System.out.println("ELAPSED: " + elapsed);
    }

    public String getTime(){
        float time = 0;
        //    System.out.println("" + time + " "+isStopped);
        //      System.out.println("");

        if(isStopped){
//            System.out.println("Took isStopped");
            return (""+timeAt);
        }else{
            time =  System.currentTimeMillis() - startTime - elapsed;
            return ""+time/1000f;
        }

    }

}
