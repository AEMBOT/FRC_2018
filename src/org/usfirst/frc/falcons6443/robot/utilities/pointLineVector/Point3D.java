package org.usfirst.frc.falcons6443.robot.utilities.pointLineVector;

/**
 * Expresses the features of a point in 3 dimensions. Contains data to represent x, y, and z coordinates of an object
 *
 * @author Shivashriganesh Mahato, Ivan Kenevich
 */
public class Point3D {
    public float x;
    public float y;
    public float z;

    /**
     * Construct this object with x, y, and z setSpeed to 0
     */
    public Point3D() {
        this.setZero();
    }

    /**
     * Construct this object with raw x, y, and z coordinates
     *
     * @param x X coordinate of point
     * @param y Y coordinate of point
     * @param z Z coordinate of point
     */
    public Point3D(float x, float y, float z) {
        this.set(x, y, z);
    }

    /**
     * Construct this object with a predefined Point3D object
     *
     * @param point The predefined Point3D to construct this with
     */
    public Point3D(Point3D point) {
        this.set(point);
    }

    /**
     * Set this point with raw x, y, and z parameters
     *
     * @param x X coordinate of point
     * @param y Y coordinate of point
     * @param z Z coordinate of point
     * @return This point after setting values
     */
    public Point3D set(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }

    /**
     * Set this point with predefined Point3D object
     *
     * @param point The predefined Point3D to construct this with
     * @return This point after setting values
     */
    public Point3D set(Point3D point) {
        return this.set(point.x, point.y, point.z);
    }

    /**
     * Set this point to 0 at x, y, and z
     *
     * @return This point after setting values
     */
    public Point3D setZero() {
        return this.set(0, 0, 0);
    }

    /**
     * Add to this point via raw x, y, and z parameters
     *
     * @param dx Change in X to add
     * @param dy Change in Y to add
     * @param dz Change in Z to add
     * @return This point after adding values
     */
    public Point3D add(float dx, float dy, float dz) {
        return this.set(this.x + dx, this.y + dy, this.z + dz);
    }

    /**
     * Move this point along a vector
     *
     * @param vector Vector to move along with
     * @return This point after adding values
     */
    public Point3D move(Vector3D vector) {
        return this.set(this.x + vector.x, this.y + vector.y, this.z + vector.z);
    }

    /**
     * Subtract from this point raw x, y, and z parameters
     *
     * @param dx Change in X to subtract
     * @param dy Change in Y to subtract
     * @param dz Change in Z to subtract
     * @return This point after subtracting values
     */
    public Point3D subtract(float dx, float dy, float dz) {
        return this.set(this.x - dx, this.y - dy, this.z - dz);
    }

    /**
     * Scale this point with raw x, y, and z parameters
     *
     * @param cx Scalar to multiply X by
     * @param cy Scalar to multiply Y by
     * @param cz Scalar to multiply Z by
     * @return This point after scaling values
     */
    public Point3D scale(float cx, float cy, float cz) {
        return this.set(this.x * cx, this.y * cy, this.z * cz);
    }

    /**
     * Scale this point with a raw parameter that will be multiplied by each coordinate
     *
     * @param scalar Scalar value to multiply by X, Y, and Z each
     * @return This point after scaling values
     */
    public Point3D scale(float scalar) {
        return this.scale(scalar, scalar, scalar);
    }

    /**
     * Compute distance between two points
     *
     * @param point The point to which the distance is computed
     * @return Distance between this and parameter
     */
    public float dist(Point3D point) {
        return (float) Math.sqrt(Math.pow(this.x - point.x, 2) + Math.pow(this.y - point.y, 2) + Math.pow(this.z - point.z, 2));
    }
}
