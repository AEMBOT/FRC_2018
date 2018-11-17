package org.usfirst.frc.falcons6443.robot.utilities.pointLineVector;

/**
 * Represents a two-dimensional linear equation.
 * <p>
 * This is to be used in conjunction with the ultrasonic sensors in
 * order to understand when the robot has intersected a line.
 *
 * @author Shivashriganesh Mahato, Ivan Kenevich
 */
public class Line2D {

    private double m;
    private double b;

    /**
     * @param m Slope of the line.
     * @param b The Y intersect.
     */
    public Line2D(double m, double b) {
        this.m = m;
        this.b = b;
    }

    /**
     * Calculates the y value for the given x coordinate.
     *
     * @param x The given x coordinate.
     * @return The calculated y value.
     */
    public double calcY(double x) {
        return (m * x) + b;
    }
}
