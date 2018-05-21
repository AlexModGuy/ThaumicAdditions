/*
 * Decompiled with CFR 0_123.
 */
package com.zeitheron.thaumicadditions.shaded.flowpoweredmath.imaginary;

import java.io.Serializable;

import com.zeitheron.thaumicadditions.shaded.flowpoweredmath.GenericMath;
import com.zeitheron.thaumicadditions.shaded.flowpoweredmath.HashFunctions;
import com.zeitheron.thaumicadditions.shaded.flowpoweredmath.TrigMath;
import com.zeitheron.thaumicadditions.shaded.flowpoweredmath.imaginary.Complexd;
import com.zeitheron.thaumicadditions.shaded.flowpoweredmath.imaginary.Imaginaryd;
import com.zeitheron.thaumicadditions.shaded.flowpoweredmath.imaginary.Imaginaryf;
import com.zeitheron.thaumicadditions.shaded.flowpoweredmath.imaginary.Quaternionf;
import com.zeitheron.thaumicadditions.shaded.flowpoweredmath.matrix.Matrix3d;
import com.zeitheron.thaumicadditions.shaded.flowpoweredmath.vector.Vector3d;

public class Quaterniond
implements Imaginaryd,
Comparable<Quaterniond>,
Serializable,
Cloneable {
    private static final long serialVersionUID = 1;
    public static final Quaterniond ZERO = new Quaterniond(0.0f, 0.0f, 0.0f, 0.0f);
    public static final Quaterniond IDENTITY = new Quaterniond(0.0f, 0.0f, 0.0f, 1.0f);
    private final double x;
    private final double y;
    private final double z;
    private final double w;
    private volatile transient boolean hashed = false;
    private volatile transient int hashCode = 0;

    public Quaterniond() {
        this(0.0f, 0.0f, 0.0f, 1.0f);
    }

    public Quaterniond(float x, float y, float z, float w) {
        this((double)x, (double)y, (double)z, (double)w);
    }

    public Quaterniond(double x, double y, double z, double w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public Quaterniond(Quaterniond q) {
        this(q.x, q.y, q.z, q.w);
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double getZ() {
        return this.z;
    }

    public double getW() {
        return this.w;
    }

    public Quaterniond add(Quaterniond q) {
        return this.add(q.x, q.y, q.z, q.w);
    }

    public Quaterniond add(float x, float y, float z, float w) {
        return this.add((double)x, (double)y, (double)z, (double)w);
    }

    public Quaterniond add(double x, double y, double z, double w) {
        return new Quaterniond(this.x + x, this.y + y, this.z + z, this.w + w);
    }

    public Quaterniond sub(Quaterniond q) {
        return this.sub(q.x, q.y, q.z, q.w);
    }

    public Quaterniond sub(float x, float y, float z, float w) {
        return this.sub((double)x, (double)y, (double)z, (double)w);
    }

    public Quaterniond sub(double x, double y, double z, double w) {
        return new Quaterniond(this.x - x, this.y - y, this.z - z, this.w - w);
    }

    public Quaterniond mul(float a) {
        return this.mul((double)a);
    }

    @Override
    public Quaterniond mul(double a) {
        return new Quaterniond(this.x * a, this.y * a, this.z * a, this.w * a);
    }

    public Quaterniond mul(Quaterniond q) {
        return this.mul(q.x, q.y, q.z, q.w);
    }

    public Quaterniond mul(float x, float y, float z, float w) {
        return this.mul((double)x, (double)y, (double)z, (double)w);
    }

    public Quaterniond mul(double x, double y, double z, double w) {
        return new Quaterniond(this.w * x + this.x * w + this.y * z - this.z * y, this.w * y + this.y * w + this.z * x - this.x * z, this.w * z + this.z * w + this.x * y - this.y * x, this.w * w - this.x * x - this.y * y - this.z * z);
    }

    public Quaterniond div(float a) {
        return this.div((double)a);
    }

    @Override
    public Quaterniond div(double a) {
        return new Quaterniond(this.x / a, this.y / a, this.z / a, this.w / a);
    }

    public Quaterniond div(Quaterniond q) {
        return this.div(q.x, q.y, q.z, q.w);
    }

    public Quaterniond div(float x, float y, float z, float w) {
        return this.div((double)x, (double)y, (double)z, (double)w);
    }

    public Quaterniond div(double x, double y, double z, double w) {
        double d = x * x + y * y + z * z + w * w;
        return new Quaterniond((this.x * w - this.w * x - this.z * y + this.y * z) / d, (this.y * w + this.z * x - this.w * y - this.x * z) / d, (this.z * w - this.y * x + this.x * y - this.w * z) / d, (this.w * w + this.x * x + this.y * y + this.z * z) / d);
    }

    public double dot(Quaterniond q) {
        return this.dot(q.x, q.y, q.z, q.w);
    }

    public double dot(float x, float y, float z, float w) {
        return this.dot((double)x, (double)y, (double)z, (double)w);
    }

    public double dot(double x, double y, double z, double w) {
        return this.x * x + this.y * y + this.z * z + this.w * w;
    }

    public Vector3d rotate(Vector3d v) {
        return this.rotate(v.getX(), v.getY(), v.getZ());
    }

    public Vector3d rotate(float x, float y, float z) {
        return this.rotate((double)x, (double)y, (double)z);
    }

    public Vector3d rotate(double x, double y, double z) {
        double length = this.length();
        if (Math.abs(length) < GenericMath.DBL_EPSILON) {
            throw new ArithmeticException("Cannot rotate by the zero quaternion");
        }
        double nx = this.x / length;
        double ny = this.y / length;
        double nz = this.z / length;
        double nw = this.w / length;
        double px = nw * x + ny * z - nz * y;
        double py = nw * y + nz * x - nx * z;
        double pz = nw * z + nx * y - ny * x;
        double pw = (- nx) * x - ny * y - nz * z;
        return new Vector3d(pw * (- nx) + px * nw - py * nz + pz * ny, pw * (- ny) + py * nw - pz * nx + px * nz, pw * (- nz) + pz * nw - px * ny + py * nx);
    }

    public Vector3d getDirection() {
        return this.rotate(Vector3d.FORWARD);
    }

    public Vector3d getAxis() {
        double q = Math.sqrt(1.0 - this.w * this.w);
        return new Vector3d(this.x / q, this.y / q, this.z / q);
    }

    public Vector3d getAxesAnglesDeg() {
        return this.getAxesAnglesRad().mul(57.29577951308232);
    }

    public Vector3d getAxesAnglesRad() {
        double yaw;
        double pitch;
        double roll;
        double test = this.w * this.x - this.y * this.z;
        if (Math.abs(test) < 0.4999) {
            roll = TrigMath.atan2(2.0 * (this.w * this.z + this.x * this.y), 1.0 - 2.0 * (this.x * this.x + this.z * this.z));
            pitch = TrigMath.asin(2.0 * test);
            yaw = TrigMath.atan2(2.0 * (this.w * this.y + this.z * this.x), 1.0 - 2.0 * (this.x * this.x + this.y * this.y));
        } else {
            int sign = test < 0.0 ? -1 : 1;
            roll = 0.0;
            pitch = (double)sign * 3.141592653589793 / 2.0;
            yaw = (double)((- sign) * 2) * TrigMath.atan2(this.z, this.w);
        }
        return new Vector3d(pitch, yaw, roll);
    }

    @Override
    public Quaterniond conjugate() {
        return new Quaterniond(- this.x, - this.y, - this.z, this.w);
    }

    @Override
    public Quaterniond invert() {
        double lengthSquared = this.lengthSquared();
        if (Math.abs(lengthSquared) < GenericMath.DBL_EPSILON) {
            throw new ArithmeticException("Cannot invert a quaternion of length zero");
        }
        return this.conjugate().div(lengthSquared);
    }

    @Override
    public double lengthSquared() {
        return this.x * this.x + this.y * this.y + this.z * this.z + this.w * this.w;
    }

    @Override
    public double length() {
        return Math.sqrt(this.lengthSquared());
    }

    @Override
    public Quaterniond normalize() {
        double length = this.length();
        if (Math.abs(length) < GenericMath.DBL_EPSILON) {
            throw new ArithmeticException("Cannot normalize the zero quaternion");
        }
        return new Quaterniond(this.x / length, this.y / length, this.z / length, this.w / length);
    }

    public Complexd toComplex() {
        double w2 = this.w * this.w;
        return new Complexd(2.0 * w2 - 1.0, 2.0 * this.w * Math.sqrt(1.0 - w2));
    }

    @Override
    public Quaternionf toFloat() {
        return new Quaternionf(this.x, this.y, this.z, this.w);
    }

    @Override
    public Quaterniond toDouble() {
        return new Quaterniond(this.x, this.y, this.z, this.w);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Quaterniond)) {
            return false;
        }
        Quaterniond quaternion = (Quaterniond)o;
        if (Double.compare(quaternion.w, this.w) != 0) {
            return false;
        }
        if (Double.compare(quaternion.x, this.x) != 0) {
            return false;
        }
        if (Double.compare(quaternion.y, this.y) != 0) {
            return false;
        }
        if (Double.compare(quaternion.z, this.z) != 0) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        if (!this.hashed) {
            int result = this.x != 0.0 ? HashFunctions.hash(this.x) : 0;
            result = 31 * result + (this.y != 0.0 ? HashFunctions.hash(this.y) : 0);
            result = 31 * result + (this.z != 0.0 ? HashFunctions.hash(this.z) : 0);
            this.hashCode = 31 * result + (this.w != 0.0 ? HashFunctions.hash(this.w) : 0);
            this.hashed = true;
        }
        return this.hashCode;
    }

    @Override
    public int compareTo(Quaterniond q) {
        return (int)Math.signum(this.lengthSquared() - q.lengthSquared());
    }

    public Quaterniond clone() {
        return new Quaterniond(this);
    }

    public String toString() {
        return "(" + this.x + ", " + this.y + ", " + this.z + ", " + this.w + ")";
    }

    public static Quaterniond fromReal(double w) {
        return w == 0.0 ? ZERO : new Quaterniond(0.0, 0.0, 0.0, w);
    }

    public static Quaterniond fromImaginary(double x, double y, double z) {
        return x == 0.0 && y == 0.0 && z == 0.0 ? ZERO : new Quaterniond(x, y, z, 0.0);
    }

    public static Quaterniond from(double x, double y, double z, double w) {
        return x == 0.0 && y == 0.0 && z == 0.0 && w == 0.0 ? ZERO : new Quaterniond(x, y, z, w);
    }

    public static Quaterniond fromAxesAnglesDeg(float pitch, float yaw, float roll) {
        return Quaterniond.fromAxesAnglesDeg((double)pitch, (double)yaw, (double)roll);
    }

    public static Quaterniond fromAxesAnglesRad(float pitch, float yaw, float roll) {
        return Quaterniond.fromAxesAnglesRad((double)pitch, (double)yaw, (double)roll);
    }

    public static Quaterniond fromAxesAnglesDeg(double pitch, double yaw, double roll) {
        return Quaterniond.fromAngleDegAxis(yaw, Vector3d.UNIT_Y).mul(Quaterniond.fromAngleDegAxis(pitch, Vector3d.UNIT_X)).mul(Quaterniond.fromAngleDegAxis(roll, Vector3d.UNIT_Z));
    }

    public static Quaterniond fromAxesAnglesRad(double pitch, double yaw, double roll) {
        return Quaterniond.fromAngleRadAxis(yaw, Vector3d.UNIT_Y).mul(Quaterniond.fromAngleRadAxis(pitch, Vector3d.UNIT_X)).mul(Quaterniond.fromAngleRadAxis(roll, Vector3d.UNIT_Z));
    }

    public static Quaterniond fromRotationTo(Vector3d from, Vector3d to) {
        return Quaterniond.fromAngleRadAxis(TrigMath.acos(from.dot(to) / (from.length() * to.length())), from.cross(to));
    }

    public static Quaterniond fromAngleDegAxis(float angle, Vector3d axis) {
        return Quaterniond.fromAngleRadAxis(Math.toRadians(angle), axis);
    }

    public static Quaterniond fromAngleRadAxis(float angle, Vector3d axis) {
        return Quaterniond.fromAngleRadAxis((double)angle, axis);
    }

    public static Quaterniond fromAngleDegAxis(double angle, Vector3d axis) {
        return Quaterniond.fromAngleRadAxis(Math.toRadians(angle), axis);
    }

    public static Quaterniond fromAngleRadAxis(double angle, Vector3d axis) {
        return Quaterniond.fromAngleRadAxis(angle, axis.getX(), axis.getY(), axis.getZ());
    }

    public static Quaterniond fromAngleDegAxis(float angle, float x, float y, float z) {
        return Quaterniond.fromAngleRadAxis(Math.toRadians(angle), (double)x, (double)y, (double)z);
    }

    public static Quaterniond fromAngleRadAxis(float angle, float x, float y, float z) {
        return Quaterniond.fromAngleRadAxis((double)angle, (double)x, (double)y, (double)z);
    }

    public static Quaterniond fromAngleDegAxis(double angle, double x, double y, double z) {
        return Quaterniond.fromAngleRadAxis(Math.toRadians(angle), x, y, z);
    }

    public static Quaterniond fromAngleRadAxis(double angle, double x, double y, double z) {
        double halfAngle = angle / 2.0;
        double q = (double)TrigMath.sin(halfAngle) / Math.sqrt(x * x + y * y + z * z);
        return new Quaterniond(x * q, y * q, z * q, (double)TrigMath.cos(halfAngle));
    }

    public static Quaterniond fromRotationMatrix(Matrix3d matrix) {
        double trace = matrix.trace();
        if (trace < 0.0) {
            if (matrix.get(1, 1) > matrix.get(0, 0)) {
                if (matrix.get(2, 2) > matrix.get(1, 1)) {
                    double r = Math.sqrt(matrix.get(2, 2) - matrix.get(0, 0) - matrix.get(1, 1) + 1.0);
                    double s = 0.5 / r;
                    return new Quaterniond((matrix.get(2, 0) + matrix.get(0, 2)) * s, (matrix.get(1, 2) + matrix.get(2, 1)) * s, 0.5 * r, (matrix.get(1, 0) - matrix.get(0, 1)) * s);
                }
                double r = Math.sqrt(matrix.get(1, 1) - matrix.get(2, 2) - matrix.get(0, 0) + 1.0);
                double s = 0.5 / r;
                return new Quaterniond((matrix.get(0, 1) + matrix.get(1, 0)) * s, 0.5 * r, (matrix.get(1, 2) + matrix.get(2, 1)) * s, (matrix.get(0, 2) - matrix.get(2, 0)) * s);
            }
            if (matrix.get(2, 2) > matrix.get(0, 0)) {
                double r = Math.sqrt(matrix.get(2, 2) - matrix.get(0, 0) - matrix.get(1, 1) + 1.0);
                double s = 0.5 / r;
                return new Quaterniond((matrix.get(2, 0) + matrix.get(0, 2)) * s, (matrix.get(1, 2) + matrix.get(2, 1)) * s, 0.5 * r, (matrix.get(1, 0) - matrix.get(0, 1)) * s);
            }
            double r = Math.sqrt(matrix.get(0, 0) - matrix.get(1, 1) - matrix.get(2, 2) + 1.0);
            double s = 0.5 / r;
            return new Quaterniond(0.5 * r, (matrix.get(0, 1) + matrix.get(1, 0)) * s, (matrix.get(2, 0) - matrix.get(0, 2)) * s, (matrix.get(2, 1) - matrix.get(1, 2)) * s);
        }
        double r = Math.sqrt(trace + 1.0);
        double s = 0.5 / r;
        return new Quaterniond((matrix.get(2, 1) - matrix.get(1, 2)) * s, (matrix.get(0, 2) - matrix.get(2, 0)) * s, (matrix.get(1, 0) - matrix.get(0, 1)) * s, 0.5 * r);
    }
}

