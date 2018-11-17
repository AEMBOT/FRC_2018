package org.usfirst.frc.falcons6443.robot.utilities;

import edu.wpi.first.wpilibj.hal.PDPJNI;

//This class needs to be tested
public class PDPReadOut {
    public double execute(int portNum)
    {
        double voltage = PDPJNI.getPDPVoltage(portNum);

        return voltage;
    }
}

//more PDP code found in other branches

/*
package org.usfirst.frc.falcons6443.robot.utilities;
 import edu.wpi.first.wpilibj.PowerDistributionPanel;

//temporary class to log power usage of each port
public class PDPLog {
    PowerDistributionPanel pdp = new PowerDistributionPanel();
    public PDPLog(){
        log();
    }
    public void log(){
        System.out.println("Total: " + pdp.getTotalPower());
        System.out.println("Port 0: " + pdp.getCurrent(0));
        System.out.println("Port 1: " + pdp.getCurrent(1));
        System.out.println("Port 2: " + pdp.getCurrent(2));
        System.out.println("Port 3: " + pdp.getCurrent(3));
        System.out.println("Port 4: " + pdp.getCurrent(4));
        System.out.println("Port 5: " + pdp.getCurrent(5));
    }
}


    private PowerDistributionPanel pdp;
        pdp = new PowerDistributionPanel();


//VOLTAGE LIMIT
     public double getCurrent(){
        pdp.getCurrent(RobotMap.)
     }


    //power distribution panel
    public static final int IntakeRotatePDPChannel = 0;
 */