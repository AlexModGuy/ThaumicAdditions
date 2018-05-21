/*
 * Decompiled with CFR 0_123.
 */
package com.zeitheron.thaumicadditions.shaded.flowpoweredmath.vector;

import java.io.Serializable;
import java.util.Random;

import com.zeitheron.thaumicadditions.shaded.flowpoweredmath.GenericMath;
import com.zeitheron.thaumicadditions.shaded.flowpoweredmath.HashFunctions;
import com.zeitheron.thaumicadditions.shaded.flowpoweredmath.TrigMath;
import com.zeitheron.thaumicadditions.shaded.flowpoweredmath.vector.Vector2d;
import com.zeitheron.thaumicadditions.shaded.flowpoweredmath.vector.Vector3f;
import com.zeitheron.thaumicadditions.shaded.flowpoweredmath.vector.Vector3i;
import com.zeitheron.thaumicadditions.shaded.flowpoweredmath.vector.Vector3l;
import com.zeitheron.thaumicadditions.shaded.flowpoweredmath.vector.Vector4d;
import com.zeitheron.thaumicadditions.shaded.flowpoweredmath.vector.VectorNd;
import com.zeitheron.thaumicadditions.shaded.flowpoweredmath.vector.Vectord;
import com.zeitheron.thaumicadditions.shaded.flowpoweredmath.vector.Vectorf;
import com.zeitheron.thaumicadditions.shaded.flowpoweredmath.vector.Vectori;
import com.zeitheron.thaumicadditions.shaded.flowpoweredmath.vector.Vectorl;

public class Vector3d
implements Vectord,
Comparable<Vector3d>,
Serializable,
Cloneable {
    private static final long serialVersionUID = 1;
    public static final Vector3d ZERO = new Vector3d(0.0f, 0.0f, 0.0f);
    public static final Vector3d UNIT_X = new Vector3d(1.0f, 0.0f, 0.0f);
    public static final Vector3d UNIT_Y = new Vector3d(0.0f, 1.0f, 0.0f);
    public static final Vector3d UNIT_Z = new Vector3d(0.0f, 0.0f, 1.0f);
    public static final Vector3d ONE = new Vector3d(1.0f, 1.0f, 1.0f);
    public static final Vector3d RIGHT = UNIT_X;
    public static final Vector3d UP = UNIT_Y;
    public static final Vector3d FORWARD = UNIT_Z;
    private final double x;
    private final double y;
    private final double z;
    private volatile transient boolean hashed = false;
    private volatile transient int hashCode = 0;

    public Vector3d() {
        this(0.0f, 0.0f, 0.0f);
    }

    public Vector3d(Vector2d v) {
        this(v, 0.0f);
    }

    public Vector3d(Vector2d v, float z) {
        this(v, (double)z);
    }

    public Vector3d(Vector2d v, double z) {
        this(v.getX(), v.getY(), z);
    }

    public Vector3d(Vector3d v) {
        this(v.x, v.y, v.z);
    }

    public Vector3d(Vector4d v) {
        this(v.getX(), v.getY(), v.getZ());
    }

    public Vector3d(VectorNd v) {
        this(v.get(0), v.get(1), v.size() > 2 ? v.get(2) : 0.0);
    }

    public Vector3d(float x, float y, float z) {
        this((double)x, (double)y, (double)z);
    }

    public Vector3d(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
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

    public int getFloorX() {
        return GenericMath.floor(this.x);
    }

    public int getFloorY() {
        return GenericMath.floor(this.y);
    }

    public int getFloorZ() {
        return GenericMath.floor(this.z);
    }

    public Vector3d add(Vector3d v) {
        return this.add(v.x, v.y, v.z);
    }

    public Vector3d add(float x, float y, float z) {
        return this.add((double)x, (double)y, (double)z);
    }

    public Vector3d add(double x, double y, double z) {
        return new Vector3d(this.x + x, this.y + y, this.z + z);
    }

    public Vector3d sub(Vector3d v) {
        return this.sub(v.x, v.y, v.z);
    }

    public Vector3d sub(float x, float y, float z) {
        return this.sub((double)x, (double)y, (double)z);
    }

    public Vector3d sub(double x, double y, double z) {
        return new Vector3d(this.x - x, this.y - y, this.z - z);
    }

    public Vector3d mul(float a) {
        return this.mul((double)a);
    }

    @Override
    public Vector3d mul(double a) {
        return this.mul(a, a, a);
    }

    public Vector3d mul(Vector3d v) {
        return this.mul(v.x, v.y, v.z);
    }

    public Vector3d mul(float x, float y, float z) {
        return this.mul((double)x, (double)y, (double)z);
    }

    public Vector3d mul(double x, double y, double z) {
        return new Vector3d(this.x * x, this.y * y, this.z * z);
    }

    public Vector3d div(float a) {
        return this.div((double)a);
    }

    @Override
    public Vector3d div(double a) {
        return this.div(a, a, a);
    }

    public Vector3d div(Vector3d v) {
        return this.div(v.x, v.y, v.z);
    }

    public Vector3d div(float x, float y, float z) {
        return this.div((double)x, (double)y, (double)z);
    }

    public Vector3d div(double x, double y, double z) {
        return new Vector3d(this.x / x, this.y / y, this.z / z);
    }

    public double dot(Vector3d v) {
        return this.dot(v.x, v.y, v.z);
    }

    public double dot(float x, float y, float z) {
        return this.dot((double)x, (double)y, (double)z);
    }

    public double dot(double x, double y, double z) {
        return this.x * x + this.y * y + this.z * z;
    }

    public Vector3d project(Vector3d v) {
        return this.project(v.x, v.y, v.z);
    }

    public Vector3d project(float x, float y, float z) {
        return this.project((double)x, (double)y, (double)z);
    }

    public Vector3d project(double x, double y, double z) {
        double lengthSquared = x * x + y * y + z * z;
        if (Math.abs(lengthSquared) < GenericMath.DBL_EPSILON) {
            throw new ArithmeticException("Cannot project onto the zero vector");
        }
        double a = this.dot(x, y, z) / lengthSquared;
        return new Vector3d(a * x, a * y, a * z);
    }

    public Vector3d cross(Vector3d v) {
        return this.cross(v.x, v.y, v.z);
    }

    public Vector3d cross(float x, float y, float z) {
        return this.cross((double)x, (double)y, (double)z);
    }

    public Vector3d cross(double x, double y, double z) {
        return new Vector3d(this.y * z - this.z * y, this.z * x - this.x * z, this.x * y - this.y * x);
    }

    public Vector3d pow(float pow) {
        return this.pow((double)pow);
    }

    @Override
    public Vector3d pow(double power) {
        return new Vector3d(Math.pow(this.x, power), Math.pow(this.y, power), Math.pow(this.z, power));
    }

    @Override
    public Vector3d ceil() {
        return new Vector3d(Math.ceil(this.x), Math.ceil(this.y), Math.ceil(this.z));
    }

    @Override
    public Vector3d floor() {
        return new Vector3d(GenericMath.floor(this.x), GenericMath.floor(this.y), GenericMath.floor(this.z));
    }

    @Override
    public Vector3d round() {
        return new Vector3d(Math.round(this.x), Math.round(this.y), Math.round(this.z));
    }

    @Override
    public Vector3d abs() {
        return new Vector3d(Math.abs(this.x), Math.abs(this.y), Math.abs(this.z));
    }

    @Override
    public Vector3d negate() {
        return new Vector3d(- this.x, - this.y, - this.z);
    }

    public Vector3d min(Vector3d v) {
        return this.min(v.x, v.y, v.z);
    }

    public Vector3d min(float x, float y, float z) {
        return this.min((double)x, (double)y, (double)z);
    }

    public Vector3d min(double x, double y, double z) {
        return new Vector3d(Math.min(this.x, x), Math.min(this.y, y), Math.min(this.z, z));
    }

    public Vector3d max(Vector3d v) {
        return this.max(v.x, v.y, v.z);
    }

    public Vector3d max(float x, float y, float z) {
        return this.max((double)x, (double)y, (double)z);
    }

    public Vector3d max(double x, double y, double z) {
        return new Vector3d(Math.max(this.x, x), Math.max(this.y, y), Math.max(this.z, z));
    }

    public double distanceSquared(Vector3d v) {
        return this.distanceSquared(v.x, v.y, v.z);
    }

    public double distanceSquared(float x, float y, float z) {
        return this.distanceSquared((double)x, (double)y, (double)z);
    }

    public double distanceSquared(double x, double y, double z) {
        double dx = this.x - x;
        double dy = this.y - y;
        double dz = this.z - z;
        return dx * dx + dy * dy + dz * dz;
    }

    public double distance(Vector3d v) {
        return this.distance(v.x, v.y, v.z);
    }

    public double distance(float x, float y, float z) {
        return this.distance((double)x, (double)y, (double)z);
    }

    public double distance(double x, double y, double z) {
        return Math.sqrt(this.distanceSquared(x, y, z));
    }

    @Override
    public double lengthSquared() {
        return this.x * this.x + this.y * this.y + this.z * this.z;
    }

    @Override
    public double length() {
        return Math.sqrt(this.lengthSquared());
    }

    @Override
    public Vector3d normalize() {
        double length = this.length();
        if (Math.abs(length) < GenericMath.DBL_EPSILON) {
            throw new ArithmeticException("Cannot normalize the zero vector");
        }
        return new Vector3d(this.x / length, this.y / length, this.z / length);
    }

    @Override
    public int getMinAxis() {
        return this.x < this.y ? (this.x < this.z ? 0 : 2) : (this.y < this.z ? 1 : 2);
    }

    @Override
    public int getMaxAxis() {
        return this.x < this.y ? (this.y < this.z ? 2 : 1) : (this.x < this.z ? 2 : 0);
    }

    public Vector2d toVector2() {
        return new Vector2d(this);
    }

    public Vector2d toVector2(boolean useZ) {
        return new Vector2d(this.x, useZ ? this.z : this.y);
    }

    public Vector4d toVector4() {
        return this.toVector4(0.0f);
    }

    public Vector4d toVector4(float w) {
        return this.toVector4((double)w);
    }

    public Vector4d toVector4(double w) {
        return new Vector4d(this, w);
    }

    public VectorNd toVectorN() {
        return new VectorNd(this);
    }

    @Override
    public double[] toArray() {
        return new double[]{this.x, this.y, this.z};
    }

    @Override
    public Vector3i toInt() {
        return new Vector3i(this.x, this.y, this.z);
    }

    @Override
    public Vector3l toLong() {
        return new Vector3l(this.x, this.y, this.z);
    }

    @Override
    public Vector3f toFloat() {
        return new Vector3f(this.x, this.y, this.z);
    }

    @Override
    public Vector3d toDouble() {
        return new Vector3d(this.x, this.y, this.z);
    }

    @Override
    public int compareTo(Vector3d v) {
        return (int)Math.signum(this.lengthSquared() - v.lengthSquared());
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Vector3d)) {
            return false;
        }
        Vector3d vector3 = (Vector3d)o;
        if (Double.compare(vector3.x, this.x) != 0) {
            return false;
        }
        if (Double.compare(vector3.y, this.y) != 0) {
            return false;
        }
        if (Double.compare(vector3.z, this.z) != 0) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        if (!this.hashed) {
            int result = this.x != 0.0 ? HashFunctions.hash(this.x) : 0;
            result = 31 * result + (this.y != 0.0 ? HashFunctions.hash(this.y) : 0);
            this.hashCode = 31 * result + (this.z != 0.0 ? HashFunctions.hash(this.z) : 0);
            this.hashed = true;
        }
        return this.hashCode;
    }

    public Vector3d clone() {
        return new Vector3d(this);
    }

    public String toString() {
        return "(" + this.x + ", " + this.y + ", " + this.z + ")";
    }

    public static Vector3d from(double n) {
        return n == 0.0 ? ZERO : new Vector3d(n, n, n);
    }

    public static Vector3d from(double x, double y, double z) {
        return x == 0.0 && y == 0.0 && z == 0.0 ? ZERO : new Vector3d(x, y, z);
    }

    public static Vector3d createRandomDirection(Random random) {
        return Vector3d.createDirectionRad(random.nextDouble() * 6.283185307179586, random.nextDouble() * 6.283185307179586);
    }

    public static Vector3d createDirectionDeg(float theta, float phi) {
        return Vector3d.createDirectionDeg((double)theta, (double)phi);
    }

    public static Vector3d createDirectionDeg(double theta, double phi) {
        return Vector3d.createDirectionRad(Math.toRadians(theta), Math.toRadians(phi));
    }

    public static Vector3d createDirectionRad(float theta, float phi) {
        return Vector3d.createDirectionRad((double)theta, (double)phi);
    }

    public static Vector3d createDirectionRad(double theta, double phi) {
        double f = TrigMath.sin(phi);
        return new Vector3d(f * (double)TrigMath.cos(theta), f * (double)TrigMath.sin(theta), (double)TrigMath.cos(phi));
    }
}

