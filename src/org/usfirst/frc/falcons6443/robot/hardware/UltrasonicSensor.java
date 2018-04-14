package org.usfirst.frc.falcons6443.robot.hardware;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.Timer;

/**
 * A Maxbotix I2C Ultrasonic Rangefinder Device.
 * Part No.: <a href="http://www.maxbotix.com/Ultrasonic_Sensors/MB1222.htm"> MB1222
 * I2CXL-MaxSonar-EZ2</a>
 *
 * @author Patrick Higgins, Ivan Kenevich, Shivashriganesh Mahato
 */
public class UltrasonicSensor extends I2C {

    private int deviceAddress; //deviceAddress = write register, deviceAddress + 1 = read register

    private int[] lastFewValues;
    private final double JUMP_TOLERANCE = 2.5;

    /**
     * Initializes a new Ultrasonic Sensor via the onboard I2C bus.
     * Before initializing sensors on a bus, it is highly recommended that you setSpeed each address individually.
     *
     * @param deviceAddress the address location of the sensor on the bus. Default address is 224.
     */
    public UltrasonicSensor(int deviceAddress) {
        //to initialize via navx bus, replace kOnboard with kMXP
        super(Port.kOnboard, deviceAddress);
        this.deviceAddress = deviceAddress;
        // Populate with sensible possible average readings at initialization
        lastFewValues = new int[]{32, 32, 32};
    }

    public void ping() {
        //command the sensor to measure range
        write(deviceAddress, 81);
        Timer.delay(0.101);
    }

    public int readLow() {
        ping();
        byte[] buffer = new byte[1];

        //read the first byte from the sensor, range-low
        read(deviceAddress + 1, 1, buffer);
        return buffer[0];
    }

    public double read() {
        ping();
        byte[] buffer = new byte[2];

        //read the two bytes from the sensor, range-low and range-high
        read(deviceAddress + 1, 2, buffer);

        int combinedBytes = ((buffer[0] & 0xFF) << 8 | (buffer[1] & 0xFF));

        /** smooth METHOD IS NEW AND HAS NOT BEEN TESTED */
        /** RETURN combinedBytes IF THE METHOD MALFUNCTIONS */
        return combinedBytes;
    }

    /*
        The method is meant to catch erroneous readings and ignore them
        Thus improving the quality of readings returned by the sensor.

        The sensitivity can be adjusted by changing the size of the
        lastFewValues array and/or the value of the JUMP_TOLERANCE constant
     */
    private double smooth(int sensorReading) {
        // Find the average of the last few read values
        double sum = 0;
        for (int i : lastFewValues) {
            sum += i;
        }
        double average = sum / lastFewValues.length;

        // If the new reading is not too far from last few readings
        if (Math.abs(average - (double) sensorReading) < 30) {
            // Shift the array of the last few values to the right one index
            // Thereby getting rid of the "oldest" reading
            for (int i = lastFewValues.length - 1; i > 1; i--) {
                lastFewValues[i] = lastFewValues[i - 1];
            }
            // Set the last reading as the "newest" value
            lastFewValues[0] = sensorReading;

            return average;
        }
        // If the reading is way off
        else
            return average;
    }

    public double readInches() {
        ping();
        //multiply return by cm:inch ratio
        return read() * 0.393700787402;
    }

    private double averagedRange(byte[] values) {
        double average;

        if (values.length < 2) {
            average = values[0];
        } else {
            average = (values[0] + values[1]) / 2;
        }

        return average;
    }
}
