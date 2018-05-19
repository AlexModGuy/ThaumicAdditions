/*
 * Decompiled with CFR 0_123.
 */
package com.endie.thaumicadditions.shaded.flowpoweredmath.vector;

import java.io.Serializable;

import com.endie.thaumicadditions.shaded.flowpoweredmath.GenericMath;
import com.endie.thaumicadditions.shaded.flowpoweredmath.HashFunctions;
import com.endie.thaumicadditions.shaded.flowpoweredmath.vector.Vector2d;
import com.endie.thaumicadditions.shaded.flowpoweredmath.vector.Vector3d;
import com.endie.thaumicadditions.shaded.flowpoweredmath.vector.Vector4f;
import com.endie.thaumicadditions.shaded.flowpoweredmath.vector.Vector4i;
import com.endie.thaumicadditions.shaded.flowpoweredmath.vector.Vector4l;
import com.endie.thaumicadditions.shaded.flowpoweredmath.vector.VectorNd;
import com.endie.thaumicadditions.shaded.flowpoweredmath.vector.Vectord;
import com.endie.thaumicadditions.shaded.flowpoweredmath.vector.Vectorf;
import com.endie.thaumicadditions.shaded.flowpoweredmath.vector.Vectori;
import com.endie.thaumicadditions.shaded.flowpoweredmath.vector.Vectorl;

public class Vector4d
implements Vectord,
Comparable<Vector4d>,
Serializable,
Cloneable {
    private static final long serialVersionUID = 1;
    public static final Vector4d ZERO = new Vector4d(0.0f, 0.0f, 0.0f, 0.0f);
    public static final Vector4d UNIT_X = new Vector4d(1.0f, 0.0f, 0.0f, 0.0f);
    public static final Vector4d UNIT_Y = new Vector4d(0.0f, 1.0f, 0.0f, 0.0f);
    public static final Vector4d UNIT_Z = new Vector4d(0.0f, 0.0f, 1.0f, 0.0f);
    public static final Vector4d UNIT_W = new Vector4d(0.0f, 0.0f, 0.0f, 1.0f);
    public static final Vector4d ONE = new Vector4d(1.0f, 1.0f, 1.0f, 1.0f);
    private final double x;
    private final double y;
    private final double z;
    private final double w;
    private volatile transient boolean hashed = false;
    private volatile transient int hashCode = 0;

    public Vector4d() {
        this(0.0f, 0.0f, 0.0f, 0.0f);
    }

    public Vector4d(Vector2d v) {
        this(v, 0.0f, 0.0f);
    }

    public Vector4d(Vector2d v, float z, float w) {
        this(v, (double)z, (double)w);
    }

    public Vector4d(Vector2d v, double z, double w) {
        this(v.getX(), v.getY(), z, w);
    }

    public Vector4d(Vector3d v) {
        this(v, 0.0f);
    }

    public Vector4d(Vector3d v, float w) {
        this(v, (double)w);
    }

    public Vector4d(Vector3d v, double w) {
        this(v.getX(), v.getY(), v.getZ(), w);
    }

    public Vector4d(Vector4d v) {
        this(v.x, v.y, v.z, v.w);
    }

    public Vector4d(VectorNd v) {
        this(v.get(0), v.get(1), v.size() > 2 ? v.get(2) : 0.0, v.size() > 3 ? v.get(3) : 0.0);
    }

    public Vector4d(float x, float y, float z, float w) {
        this((double)x, (double)y, (double)z, (double)w);
    }

    public Vector4d(double x, double y, double z, double w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
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

    public int getFloorX() {
        return GenericMath.floor(this.x);
    }

    public int getFloorY() {
        return GenericMath.floor(this.y);
    }

    public int getFloorZ() {
        return GenericMath.floor(this.z);
    }

    public int getFloorW() {
        return GenericMath.floor(this.w);
    }

    public Vector4d add(Vector4d v) {
        return this.add(v.x, v.y, v.z, v.w);
    }

    public Vector4d add(float x, float y, float z, float w) {
        return this.add((double)x, (double)y, (double)z, (double)w);
    }

    public Vector4d add(double x, double y, double z, double w) {
        return new Vector4d(this.x + x, this.y + y, this.z + z, this.w + w);
    }

    public Vector4d sub(Vector4d v) {
        return this.sub(v.x, v.y, v.z, v.w);
    }

    public Vector4d sub(float x, float y, float z, float w) {
        return this.sub((double)x, (double)y, (double)z, (double)w);
    }

    public Vector4d sub(double x, double y, double z, double w) {
        return new Vector4d(this.x - x, this.y - y, this.z - z, this.w - w);
    }

    public Vector4d mul(float a) {
        return this.mul((double)a);
    }

    @Override
    public Vector4d mul(double a) {
        return this.mul(a, a, a, a);
    }

    public Vector4d mul(Vector4d v) {
        return this.mul(v.x, v.y, v.z, v.w);
    }

    public Vector4d mul(float x, float y, float z, float w) {
        return this.mul((double)x, (double)y, (double)z, (double)w);
    }

    public Vector4d mul(double x, double y, double z, double w) {
        return new Vector4d(this.x * x, this.y * y, this.z * z, this.w * w);
    }

    public Vector4d div(float a) {
        return this.div((double)a);
    }

    @Override
    public Vector4d div(double a) {
        return this.div(a, a, a, a);
    }

    public Vector4d div(Vector4d v) {
        return this.div(v.x, v.y, v.z, v.w);
    }

    public Vector4d div(float x, float y, float z, float w) {
        return this.div((double)x, (double)y, (double)z, (double)w);
    }

    public Vector4d div(double x, double y, double z, double w) {
        return new Vector4d(this.x / x, this.y / y, this.z / z, this.w / w);
    }

    public double dot(Vector4d v) {
        return this.dot(v.x, v.y, v.z, v.w);
    }

    public double dot(float x, float y, float z, float w) {
        return this.dot((double)x, (double)y, (double)z, (double)w);
    }

    public double dot(double x, double y, double z, double w) {
        return this.x * x + this.y * y + this.z * z + this.w * w;
    }

    public Vector4d project(Vector4d v) {
        return this.project(v.x, v.y, v.z, v.w);
    }

    public Vector4d project(float x, float y, float z, float w) {
        return this.project((double)x, (double)y, (double)z, (double)w);
    }

    public Vector4d project(double x, double y, double z, double w) {
        double lengthSquared = x * x + y * y + z * z + w * w;
        if (Math.abs(lengthSquared) < GenericMath.DBL_EPSILON) {
            throw new ArithmeticException("Cannot project onto the zero vector");
        }
        double a = this.dot(x, y, z, w) / lengthSquared;
        return new Vector4d(a * x, a * y, a * z, a * w);
    }

    public Vector4d pow(float pow) {
        return this.pow((double)pow);
    }

    @Override
    public Vector4d pow(double power) {
        return new Vector4d(Math.pow(this.x, power), Math.pow(this.y, power), Math.pow(this.z, power), Math.pow(this.w, power));
    }

    @Override
    public Vector4d ceil() {
        return new Vector4d(Math.ceil(this.x), Math.ceil(this.y), Math.ceil(this.z), Math.ceil(this.w));
    }

    @Override
    public Vector4d floor() {
        return new Vector4d(GenericMath.floor(this.x), GenericMath.floor(this.y), GenericMath.floor(this.z), GenericMath.floor(this.w));
    }

    @Override
    public Vector4d round() {
        return new Vector4d(Math.round(this.x), Math.round(this.y), Math.round(this.z), Math.round(this.w));
    }

    @Override
    public Vector4d abs() {
        return new Vector4d(Math.abs(this.x), Math.abs(this.y), Math.abs(this.z), Math.abs(this.w));
    }

    @Override
    public Vector4d negate() {
        return new Vector4d(- this.x, - this.y, - this.z, - this.w);
    }

    public Vector4d min(Vector4d v) {
        return this.min(v.x, v.y, v.z, v.w);
    }

    public Vector4d min(float x, float y, float z, float w) {
        return this.min((double)x, (double)y, (double)z, (double)w);
    }

    public Vector4d min(double x, double y, double z, double w) {
        return new Vector4d(Math.min(this.x, x), Math.min(this.y, y), Math.min(this.z, z), Math.min(this.w, w));
    }

    public Vector4d max(Vector4d v) {
        return this.max(v.x, v.y, v.z, v.w);
    }

    public Vector4d max(float x, float y, float z, float w) {
        return this.max((double)x, (double)y, (double)z, (double)w);
    }

    public Vector4d max(double x, double y, double z, double w) {
        return new Vector4d(Math.max(this.x, x), Math.max(this.y, y), Math.max(this.z, z), Math.max(this.w, w));
    }

    public double distanceSquared(Vector4d v) {
        return this.distanceSquared(v.x, v.y, v.z, v.w);
    }

    public double distanceSquared(float x, float y, float z, float w) {
        return this.distanceSquared((double)x, (double)y, (double)z, (double)w);
    }

    public double distanceSquared(double x, double y, double z, double w) {
        double dx = this.x - x;
        double dy = this.y - y;
        double dz = this.z - z;
        double dw = this.w - w;
        return dx * dx + dy * dy + dz * dz + dw * dw;
    }

    public double distance(Vector4d v) {
        return this.distance(v.x, v.y, v.z, v.w);
    }

    public double distance(float x, float y, float z, float w) {
        return this.distance((double)x, (double)y, (double)z, (double)w);
    }

    public double distance(double x, double y, double z, double w) {
        return Math.sqrt(this.distanceSquared(x, y, z, w));
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
    public Vector4d normalize() {
        double length = this.length();
        if (Math.abs(length) < GenericMath.DBL_EPSILON) {
            throw new ArithmeticException("Cannot normalize the zero vector");
        }
        return new Vector4d(this.x / length, this.y / length, this.z / length, this.w / length);
    }

    @Override
    public int getMinAxis() {
        double value = this.x;
        int axis = 0;
        if (this.y < value) {
            value = this.y;
            axis = 1;
        }
        if (this.z < value) {
            value = this.z;
            axis = 2;
        }
        if (this.w < value) {
            axis = 3;
        }
        return axis;
    }

    @Override
    public int getMaxAxis() {
        double value = this.x;
        int axis = 0;
        if (this.y > value) {
            value = this.y;
            axis = 1;
        }
        if (this.z > value) {
            value = this.z;
            axis = 2;
        }
        if (this.w > value) {
            axis = 3;
        }
        return axis;
    }

    public Vector2d toVector2() {
        return new Vector2d(this);
    }

    public Vector3d toVector3() {
        return new Vector3d(this);
    }

    public VectorNd toVectorN() {
        return new VectorNd(this);
    }

    @Override
    public double[] toArray() {
        return new double[]{this.x, this.y, this.z, this.w};
    }

    @Override
    public Vector4i toInt() {
        return new Vector4i(this.x, this.y, this.z, this.w);
    }

    @Override
    public Vector4l toLong() {
        return new Vector4l(this.x, this.y, this.z, this.w);
    }

    @Override
    public Vector4f toFloat() {
        return new Vector4f(this.x, this.y, this.z, this.w);
    }

    @Override
    public Vector4d toDouble() {
        return new Vector4d(this.x, this.y, this.z, this.w);
    }

    @Override
    public int compareTo(Vector4d v) {
        return (int)Math.signum(this.lengthSquared() - v.lengthSquared());
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Vector4d)) {
            return false;
        }
        Vector4d vector4 = (Vector4d)o;
        if (Double.compare(vector4.w, this.w) != 0) {
            return false;
        }
        if (Double.compare(vector4.x, this.x) != 0) {
            return false;
        }
        if (Double.compare(vector4.y, this.y) != 0) {
            return false;
        }
        if (Double.compare(vector4.z, this.z) != 0) {
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

    public Vector4d clone() {
        return new Vector4d(this);
    }

    public String toString() {
        return "(" + this.x + ", " + this.y + ", " + this.z + ", " + this.w + ")";
    }

    public static Vector4d from(double n) {
        return n == 0.0 ? ZERO : new Vector4d(n, n, n, n);
    }

    public static Vector4d from(double x, double y, double z, double w) {
        return x == 0.0 && y == 0.0 && z == 0.0 && w == 0.0 ? ZERO : new Vector4d(x, y, z, w);
    }
}

