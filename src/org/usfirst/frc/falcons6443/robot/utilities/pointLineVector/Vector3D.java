package org.usfirst.frc.falcons6443.robot.utilities.pointLineVector;

/**
 * Expresses the features of a vector in 3 dimensions. Contains data to represent x, y, and z components of a vector
 *
 * @author Ivan Kenevich, Shivashriganesh Mahato
 */
public class Vector3D {
    public float x;
    public float y;
    public float z;

    /**
     * Construct this object with x, y, and z setSpeed to 0
     */
    public Vector3D() {
        this.setZero();
    }

    /**
     * Construct this object with raw x, y, and z components
     *
     * @param x X component of vector
     * @param y Y component of vector
     * @param z Z component of vector
     */
    public Vector3D(float x, float y, float z) {
        this.set(x, y, z);
    }

    /**
     * Construct vector that points from the origin to the predefined Point3D object
     *
     * @param point The predefined Point3D to construct this with
     */
    public Vector3D(Point3D point) {
        this.set(point);
    }

    /**
     * Construct this object with a predefined Vector3D object
     *
     * @param vector The predefined Vector3D to construct this with
     */
    public Vector3D(Vector3D vector) {
        this.set(vector);
    }

    /**
     * Set this vector with raw x, y, and z parameters
     *
     * @param x X component of vector
     * @param y Y component of vector
     * @param z Z component of vector
     * @return This vector after setting values
     */
    public Vector3D set(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }

    /**
     * Set this vector with predefined Point3D object
     *
     * @param point The predefined Point3D to construct this with
     * @return This vector after setting values
     */
    public Vector3D set(Point3D point) {
        return this.set(point.x, point.y, point.z);
    }

    /**
     * Set this vector with predefined Vector3D object
     *
     * @param vector The predefined Vector3D to construct this with
     * @return This vector after setting values
     */
    public Vector3D set(Vector3D vector) {
        return this.set(vector.x, vector.y, vector.z);
    }

    /**
     * Set this vector to 0 at x, y, and z
     *
     * @return This vector after setting values
     */
    public Vector3D setZero() {
        return this.set(0, 0, 0);
    }

    /**
     * Add to this vector via raw x, y, and z parameters
     *
     * @param dx Change in X to add
     * @param dy Change in Y to add
     * @param dz Change in Z to add
     * @return This vector after adding values
     */
    public Vector3D add(float dx, float dy, float dz) {
        return this.set(this.x + dx, this.y + dy, this.z + dz);
    }

    /**
     * Add vector to this vector via raw x, y, and z parameters
     *
     * @param vector Vector to be added
     * @return This vector after adding values
     */
    public Vector3D add(Vector3D vector) {
        return this.set(this.x + vector.x, this.y + vector.y, this.z + vector.z);
    }

    /**
     * Subtract vector from this vector via raw x, y, and z parameters
     *
     * @param vector Vector to be subtracted
     * @return This vector after subtracting values
     */
    public Vector3D subtract(Vector3D vector) {
        return this.set(this.x - vector.x, this.y - vector.y, this.z - vector.z);
    }

    /**
     * Scale this vector with raw x, y, and z parameters
     *
     * @param cx Scalar to multiply X by
     * @param cy Scalar to multiply Y by
     * @param cz Scalar to multiply Z by
     * @return This vector after scaling values
     */
    public Vector3D scale(float cx, float cy, float cz) {
        return this.set(this.x * cx, this.y * cy, this.z * cz);
    }

    /**
     * Scale each component of this vector with a raw parameter
     *
     * @param scalar Scalar value to multiply by X, Y, and Z each
     * @return This vector after scaling values
     */
    public Vector3D scale(float scalar) {
        return this.scale(scalar, scalar, scalar);
    }

    /**
     * Compute the magnitude (length) of this vector
     *
     * @return The magnitude of this vector
     */
    public float abs() {
        return (float) Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
    }

    /**
     * Computes a dot product of two vectors.
     * This product is equal to 0 when the two vectors are perpendicular.
     * It is equal to the product of their magnitudes if the vectors are parallel.
     *
     * @param vector Vector to be dotted with this one
     * @return The dot product of two vectors.
     */
    public float dot(Vector3D vector) {
        return (this.x * vector.x + this.y * vector.y + this.z * vector.z);
    }

    /**
     * Computes a cross product of two vectors. The cross product of two vectors
     * is a vector that is orthogonal (perpendicular) to both of the vectors.
     * The direction of that vector is determined by the right hand rule.
     * The order of vectors in the cross product MATTERS: v x u = - (u x v)
     *
     * @param vector Second vector in the cross product
     * @return The vector orthogonal to this and parameter
     */
    public Vector3D cross(Vector3D vector) {
        return new Vector3D(this.y * vector.z - this.z * vector.y,
                this.z * vector.x - this.x * vector.z,
                this.x * vector.y - this.y * vector.x);
    }
}
