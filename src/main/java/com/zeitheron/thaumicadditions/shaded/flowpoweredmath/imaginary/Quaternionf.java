/*
 * Decompiled with CFR 0_123.
 */
package com.zeitheron.thaumicadditions.shaded.flowpoweredmath.imaginary;

import java.io.Serializable;

import com.zeitheron.thaumicadditions.shaded.flowpoweredmath.GenericMath;
import com.zeitheron.thaumicadditions.shaded.flowpoweredmath.HashFunctions;
import com.zeitheron.thaumicadditions.shaded.flowpoweredmath.TrigMath;
import com.zeitheron.thaumicadditions.shaded.flowpoweredmath.imaginary.Complexf;
import com.zeitheron.thaumicadditions.shaded.flowpoweredmath.imaginary.Imaginaryd;
import com.zeitheron.thaumicadditions.shaded.flowpoweredmath.imaginary.Imaginaryf;
import com.zeitheron.thaumicadditions.shaded.flowpoweredmath.imaginary.Quaterniond;
import com.zeitheron.thaumicadditions.shaded.flowpoweredmath.matrix.Matrix3f;
import com.zeitheron.thaumicadditions.shaded.flowpoweredmath.vector.Vector3f;

public class Quaternionf
implements Imaginaryf,
Comparable<Quaternionf>,
Serializable,
Cloneable {
    private static final long serialVersionUID = 1;
    public static final Quaternionf ZERO = new Quaternionf(0.0f, 0.0f, 0.0f, 0.0f);
    public static final Quaternionf IDENTITY = new Quaternionf(0.0f, 0.0f, 0.0f, 1.0f);
    private final float x;
    private final float y;
    private final float z;
    private final float w;
    private volatile transient boolean hashed = false;
    private volatile transient int hashCode = 0;

    public Quaternionf() {
        this(0.0f, 0.0f, 0.0f, 1.0f);
    }

    public Quaternionf(double x, double y, double z, double w) {
        this((float)x, (float)y, (float)z, (float)w);
    }

    public Quaternionf(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public Quaternionf(Quaternionf q) {
        this(q.x, q.y, q.z, q.w);
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    public float getZ() {
        return this.z;
    }

    public float getW() {
        return this.w;
    }

    public Quaternionf add(Quaternionf q) {
        return this.add(q.x, q.y, q.z, q.w);
    }

    public Quaternionf add(double x, double y, double z, double w) {
        return this.add((float)x, (float)y, (float)z, (float)w);
    }

    public Quaternionf add(float x, float y, float z, float w) {
        return new Quaternionf(this.x + x, this.y + y, this.z + z, this.w + w);
    }

    public Quaternionf sub(Quaternionf q) {
        return this.sub(q.x, q.y, q.z, q.w);
    }

    public Quaternionf sub(double x, double y, double z, double w) {
        return this.sub((float)x, (float)y, (float)z, (float)w);
    }

    public Quaternionf sub(float x, float y, float z, float w) {
        return new Quaternionf(this.x - x, this.y - y, this.z - z, this.w - w);
    }

    public Quaternionf mul(double a) {
        return this.mul((float)a);
    }

    @Override
    public Quaternionf mul(float a) {
        return new Quaternionf(this.x * a, this.y * a, this.z * a, this.w * a);
    }

    public Quaternionf mul(Quaternionf q) {
        return this.mul(q.x, q.y, q.z, q.w);
    }

    public Quaternionf mul(double x, double y, double z, double w) {
        return this.mul((float)x, (float)y, (float)z, (float)w);
    }

    public Quaternionf mul(float x, float y, float z, float w) {
        return new Quaternionf(this.w * x + this.x * w + this.y * z - this.z * y, this.w * y + this.y * w + this.z * x - this.x * z, this.w * z + this.z * w + this.x * y - this.y * x, this.w * w - this.x * x - this.y * y - this.z * z);
    }

    public Quaternionf div(double a) {
        return this.div((float)a);
    }

    @Override
    public Quaternionf div(float a) {
        return new Quaternionf(this.x / a, this.y / a, this.z / a, this.w / a);
    }

    public Quaternionf div(Quaternionf q) {
        return this.div(q.x, q.y, q.z, q.w);
    }

    public Quaternionf div(double x, double y, double z, double w) {
        return this.div((float)x, (float)y, (float)z, (float)w);
    }

    public Quaternionf div(float x, float y, float z, float w) {
        float d = x * x + y * y + z * z + w * w;
        return new Quaternionf((this.x * w - this.w * x - this.z * y + this.y * z) / d, (this.y * w + this.z * x - this.w * y - this.x * z) / d, (this.z * w - this.y * x + this.x * y - this.w * z) / d, (this.w * w + this.x * x + this.y * y + this.z * z) / d);
    }

    public float dot(Quaternionf q) {
        return this.dot(q.x, q.y, q.z, q.w);
    }

    public float dot(double x, double y, double z, double w) {
        return this.dot((float)x, (float)y, (float)z, (float)w);
    }

    public float dot(float x, float y, float z, float w) {
        return this.x * x + this.y * y + this.z * z + this.w * w;
    }

    public Vector3f rotate(Vector3f v) {
        return this.rotate(v.getX(), v.getY(), v.getZ());
    }

    public Vector3f rotate(double x, double y, double z) {
        return this.rotate((float)x, (float)y, (float)z);
    }

    public Vector3f rotate(float x, float y, float z) {
        float length = this.length();
        if (Math.abs(length) < GenericMath.FLT_EPSILON) {
            throw new ArithmeticException("Cannot rotate by the zero quaternion");
        }
        float nx = this.x / length;
        float ny = this.y / length;
        float nz = this.z / length;
        float nw = this.w / length;
        float px = nw * x + ny * z - nz * y;
        float py = nw * y + nz * x - nx * z;
        float pz = nw * z + nx * y - ny * x;
        float pw = (- nx) * x - ny * y - nz * z;
        return new Vector3f(pw * (- nx) + px * nw - py * nz + pz * ny, pw * (- ny) + py * nw - pz * nx + px * nz, pw * (- nz) + pz * nw - px * ny + py * nx);
    }

    public Vector3f getDirection() {
        return this.rotate(Vector3f.FORWARD);
    }

    public Vector3f getAxis() {
        float q = (float)Math.sqrt(1.0f - this.w * this.w);
        return new Vector3f(this.x / q, this.y / q, this.z / q);
    }

    public Vector3f getAxesAnglesDeg() {
        return this.getAxesAnglesRad().mul(57.29577951308232);
    }

    public Vector3f getAxesAnglesRad() {
        double yaw;
        double pitch;
        double roll;
        double test = this.w * this.x - this.y * this.z;
        if (Math.abs(test) < 0.4999) {
            roll = TrigMath.atan2(2.0f * (this.w * this.z + this.x * this.y), 1.0f - 2.0f * (this.x * this.x + this.z * this.z));
            pitch = TrigMath.asin(2.0 * test);
            yaw = TrigMath.atan2(2.0f * (this.w * this.y + this.z * this.x), 1.0f - 2.0f * (this.x * this.x + this.y * this.y));
        } else {
            int sign = test < 0.0 ? -1 : 1;
            roll = 0.0;
            pitch = (double)sign * 3.141592653589793 / 2.0;
            yaw = (double)((- sign) * 2) * TrigMath.atan2(this.z, this.w);
        }
        return new Vector3f(pitch, yaw, roll);
    }

    @Override
    public Quaternionf conjugate() {
        return new Quaternionf(- this.x, - this.y, - this.z, this.w);
    }

    @Override
    public Quaternionf invert() {
        float lengthSquared = this.lengthSquared();
        if (Math.abs(lengthSquared) < GenericMath.FLT_EPSILON) {
            throw new ArithmeticException("Cannot invert a quaternion of length zero");
        }
        return this.conjugate().div(lengthSquared);
    }

    @Override
    public float lengthSquared() {
        return this.x * this.x + this.y * this.y + this.z * this.z + this.w * this.w;
    }

    @Override
    public float length() {
        return (float)Math.sqrt(this.lengthSquared());
    }

    @Override
    public Quaternionf normalize() {
        float length = this.length();
        if (Math.abs(length) < GenericMath.FLT_EPSILON) {
            throw new ArithmeticException("Cannot normalize the zero quaternion");
        }
        return new Quaternionf(this.x / length, this.y / length, this.z / length, this.w / length);
    }

    public Complexf toComplex() {
        float w2 = this.w * this.w;
        return new Complexf(2.0f * w2 - 1.0f, 2.0f * this.w * (float)Math.sqrt(1.0f - w2));
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
        if (!(o instanceof Quaternionf)) {
            return false;
        }
        Quaternionf quaternion = (Quaternionf)o;
        if (Float.compare(quaternion.w, this.w) != 0) {
            return false;
        }
        if (Float.compare(quaternion.x, this.x) != 0) {
            return false;
        }
        if (Float.compare(quaternion.y, this.y) != 0) {
            return false;
        }
        if (Float.compare(quaternion.z, this.z) != 0) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        if (!this.hashed) {
            int result = this.x != 0.0f ? HashFunctions.hash(this.x) : 0;
            result = 31 * result + (this.y != 0.0f ? HashFunctions.hash(this.y) : 0);
            result = 31 * result + (this.z != 0.0f ? HashFunctions.hash(this.z) : 0);
            this.hashCode = 31 * result + (this.w != 0.0f ? HashFunctions.hash(this.w) : 0);
            this.hashed = true;
        }
        return this.hashCode;
    }

    @Override
    public int compareTo(Quaternionf q) {
        return (int)Math.signum(this.lengthSquared() - q.lengthSquared());
    }

    public Quaternionf clone() {
        return new Quaternionf(this);
    }

    public String toString() {
        return "(" + this.x + ", " + this.y + ", " + this.z + ", " + this.w + ")";
    }

    public static Quaternionf fromReal(float w) {
        return w == 0.0f ? ZERO : new Quaternionf(0.0f, 0.0f, 0.0f, w);
    }

    public static Quaternionf fromImaginary(float x, float y, float z) {
        return x == 0.0f && y == 0.0f && z == 0.0f ? ZERO : new Quaternionf(x, y, z, 0.0f);
    }

    public static Quaternionf from(float x, float y, float z, float w) {
        return x == 0.0f && y == 0.0f && z == 0.0f && w == 0.0f ? ZERO : new Quaternionf(x, y, z, w);
    }

    public static Quaternionf fromAxesAnglesDeg(double pitch, double yaw, double roll) {
        return Quaternionf.fromAxesAnglesDeg((float)pitch, (float)yaw, (float)roll);
    }

    public static Quaternionf fromAxesAnglesRad(double pitch, double yaw, double roll) {
        return Quaternionf.fromAxesAnglesRad((float)pitch, (float)yaw, (float)roll);
    }

    public static Quaternionf fromAxesAnglesDeg(float pitch, float yaw, float roll) {
        return Quaternionf.fromAngleDegAxis(yaw, Vector3f.UNIT_Y).mul(Quaternionf.fromAngleDegAxis(pitch, Vector3f.UNIT_X)).mul(Quaternionf.fromAngleDegAxis(roll, Vector3f.UNIT_Z));
    }

    public static Quaternionf fromAxesAnglesRad(float pitch, float yaw, float roll) {
        return Quaternionf.fromAngleRadAxis(yaw, Vector3f.UNIT_Y).mul(Quaternionf.fromAngleRadAxis(pitch, Vector3f.UNIT_X)).mul(Quaternionf.fromAngleRadAxis(roll, Vector3f.UNIT_Z));
    }

    public static Quaternionf fromRotationTo(Vector3f from, Vector3f to) {
        return Quaternionf.fromAngleRadAxis(TrigMath.acos(from.dot(to) / (from.length() * to.length())), from.cross(to));
    }

    public static Quaternionf fromAngleDegAxis(double angle, Vector3f axis) {
        return Quaternionf.fromAngleRadAxis(Math.toRadians(angle), axis);
    }

    public static Quaternionf fromAngleRadAxis(double angle, Vector3f axis) {
        return Quaternionf.fromAngleRadAxis((float)angle, axis);
    }

    public static Quaternionf fromAngleDegAxis(float angle, Vector3f axis) {
        return Quaternionf.fromAngleRadAxis((float)Math.toRadians(angle), axis);
    }

    public static Quaternionf fromAngleRadAxis(float angle, Vector3f axis) {
        return Quaternionf.fromAngleRadAxis(angle, axis.getX(), axis.getY(), axis.getZ());
    }

    public static Quaternionf fromAngleDegAxis(double angle, double x, double y, double z) {
        return Quaternionf.fromAngleRadAxis(Math.toRadians(angle), x, y, z);
    }

    public static Quaternionf fromAngleRadAxis(double angle, double x, double y, double z) {
        return Quaternionf.fromAngleRadAxis((float)angle, (float)x, (float)y, (float)z);
    }

    public static Quaternionf fromAngleDegAxis(float angle, float x, float y, float z) {
        return Quaternionf.fromAngleRadAxis((float)Math.toRadians(angle), x, y, z);
    }

    public static Quaternionf fromAngleRadAxis(float angle, float x, float y, float z) {
        float halfAngle = angle / 2.0f;
        float q = TrigMath.sin(halfAngle) / (float)Math.sqrt(x * x + y * y + z * z);
        return new Quaternionf(x * q, y * q, z * q, TrigMath.cos(halfAngle));
    }

    public static Quaternionf fromRotationMatrix(Matrix3f matrix) {
        float trace = matrix.trace();
        if (trace < 0.0f) {
            if (matrix.get(1, 1) > matrix.get(0, 0)) {
                if (matrix.get(2, 2) > matrix.get(1, 1)) {
                    float r = (float)Math.sqrt(matrix.get(2, 2) - matrix.get(0, 0) - matrix.get(1, 1) + 1.0f);
                    float s = 0.5f / r;
                    return new Quaternionf((matrix.get(2, 0) + matrix.get(0, 2)) * s, (matrix.get(1, 2) + matrix.get(2, 1)) * s, 0.5f * r, (matrix.get(1, 0) - matrix.get(0, 1)) * s);
                }
                float r = (float)Math.sqrt(matrix.get(1, 1) - matrix.get(2, 2) - matrix.get(0, 0) + 1.0f);
                float s = 0.5f / r;
                return new Quaternionf((matrix.get(0, 1) + matrix.get(1, 0)) * s, 0.5f * r, (matrix.get(1, 2) + matrix.get(2, 1)) * s, (matrix.get(0, 2) - matrix.get(2, 0)) * s);
            }
            if (matrix.get(2, 2) > matrix.get(0, 0)) {
                float r = (float)Math.sqrt(matrix.get(2, 2) - matrix.get(0, 0) - matrix.get(1, 1) + 1.0f);
                float s = 0.5f / r;
                return new Quaternionf((matrix.get(2, 0) + matrix.get(0, 2)) * s, (matrix.get(1, 2) + matrix.get(2, 1)) * s, 0.5f * r, (matrix.get(1, 0) - matrix.get(0, 1)) * s);
            }
            float r = (float)Math.sqrt(matrix.get(0, 0) - matrix.get(1, 1) - matrix.get(2, 2) + 1.0f);
            float s = 0.5f / r;
            return new Quaternionf(0.5f * r, (matrix.get(0, 1) + matrix.get(1, 0)) * s, (matrix.get(2, 0) - matrix.get(0, 2)) * s, (matrix.get(2, 1) - matrix.get(1, 2)) * s);
        }
        float r = (float)Math.sqrt(trace + 1.0f);
        float s = 0.5f / r;
        return new Quaternionf((matrix.get(2, 1) - matrix.get(1, 2)) * s, (matrix.get(0, 2) - matrix.get(2, 0)) * s, (matrix.get(1, 0) - matrix.get(0, 1)) * s, 0.5f * r);
    }
}

