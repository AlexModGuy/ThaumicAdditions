/*
 * Decompiled with CFR 0_123.
 */
package com.endie.thaumicadditions.shaded.flowpoweredmath.vector;

import java.io.Serializable;

import com.endie.thaumicadditions.shaded.flowpoweredmath.GenericMath;
import com.endie.thaumicadditions.shaded.flowpoweredmath.HashFunctions;
import com.endie.thaumicadditions.shaded.flowpoweredmath.vector.Vector2l;
import com.endie.thaumicadditions.shaded.flowpoweredmath.vector.Vector3d;
import com.endie.thaumicadditions.shaded.flowpoweredmath.vector.Vector3f;
import com.endie.thaumicadditions.shaded.flowpoweredmath.vector.Vector3i;
import com.endie.thaumicadditions.shaded.flowpoweredmath.vector.Vector4l;
import com.endie.thaumicadditions.shaded.flowpoweredmath.vector.VectorNl;
import com.endie.thaumicadditions.shaded.flowpoweredmath.vector.Vectord;
import com.endie.thaumicadditions.shaded.flowpoweredmath.vector.Vectorf;
import com.endie.thaumicadditions.shaded.flowpoweredmath.vector.Vectori;
import com.endie.thaumicadditions.shaded.flowpoweredmath.vector.Vectorl;

public class Vector3l
implements Vectorl,
Comparable<Vector3l>,
Serializable,
Cloneable {
    private static final long serialVersionUID = 1;
    public static final Vector3l ZERO = new Vector3l(0, 0, 0);
    public static final Vector3l UNIT_X = new Vector3l(1, 0, 0);
    public static final Vector3l UNIT_Y = new Vector3l(0, 1, 0);
    public static final Vector3l UNIT_Z = new Vector3l(0, 0, 1);
    public static final Vector3l ONE = new Vector3l(1, 1, 1);
    public static final Vector3l RIGHT = UNIT_X;
    public static final Vector3l UP = UNIT_Y;
    public static final Vector3l FORWARD = UNIT_Z;
    private final long x;
    private final long y;
    private final long z;
    private volatile transient boolean hashed = false;
    private volatile transient int hashCode = 0;

    public Vector3l() {
        this(0, 0, 0);
    }

    public Vector3l(Vector2l v) {
        this(v, 0);
    }

    public Vector3l(Vector2l v, double z) {
        this(v, GenericMath.floorl(z));
    }

    public Vector3l(Vector2l v, long z) {
        this(v.getX(), v.getY(), z);
    }

    public Vector3l(Vector3l v) {
        this(v.x, v.y, v.z);
    }

    public Vector3l(Vector4l v) {
        this(v.getX(), v.getY(), v.getZ());
    }

    public Vector3l(VectorNl v) {
        this(v.get(0), v.get(1), v.size() > 2 ? v.get(2) : 0);
    }

    public Vector3l(double x, double y, double z) {
        this(GenericMath.floorl(x), GenericMath.floorl(y), GenericMath.floorl(z));
    }

    public Vector3l(long x, long y, long z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public long getX() {
        return this.x;
    }

    public long getY() {
        return this.y;
    }

    public long getZ() {
        return this.z;
    }

    public Vector3l add(Vector3l v) {
        return this.add(v.x, v.y, v.z);
    }

    public Vector3l add(double x, double y, double z) {
        return this.add(GenericMath.floorl(x), GenericMath.floorl(y), GenericMath.floorl(z));
    }

    public Vector3l add(long x, long y, long z) {
        return new Vector3l(this.x + x, this.y + y, this.z + z);
    }

    public Vector3l sub(Vector3l v) {
        return this.sub(v.x, v.y, v.z);
    }

    public Vector3l sub(double x, double y, double z) {
        return this.sub(GenericMath.floorl(x), GenericMath.floorl(y), GenericMath.floorl(z));
    }

    public Vector3l sub(long x, long y, long z) {
        return new Vector3l(this.x - x, this.y - y, this.z - z);
    }

    public Vector3l mul(double a) {
        return this.mul(GenericMath.floorl(a));
    }

    @Override
    public Vector3l mul(long a) {
        return this.mul(a, a, a);
    }

    public Vector3l mul(Vector3l v) {
        return this.mul(v.x, v.y, v.z);
    }

    public Vector3l mul(double x, double y, double z) {
        return this.mul(GenericMath.floorl(x), GenericMath.floorl(y), GenericMath.floorl(z));
    }

    public Vector3l mul(long x, long y, long z) {
        return new Vector3l(this.x * x, this.y * y, this.z * z);
    }

    public Vector3l div(double a) {
        return this.div(GenericMath.floorl(a));
    }

    @Override
    public Vector3l div(long a) {
        return this.div(a, a, a);
    }

    public Vector3l div(Vector3l v) {
        return this.div(v.x, v.y, v.z);
    }

    public Vector3l div(double x, double y, double z) {
        return this.div(GenericMath.floorl(x), GenericMath.floorl(y), GenericMath.floorl(z));
    }

    public Vector3l div(long x, long y, long z) {
        return new Vector3l(this.x / x, this.y / y, this.z / z);
    }

    public long dot(Vector3l v) {
        return this.dot(v.x, v.y, v.z);
    }

    public long dot(double x, double y, double z) {
        return this.dot(GenericMath.floorl(x), GenericMath.floorl(y), GenericMath.floorl(z));
    }

    public long dot(long x, long y, long z) {
        return this.x * x + this.y * y + this.z * z;
    }

    public Vector3l project(Vector3l v) {
        return this.project(v.x, v.y, v.z);
    }

    public Vector3l project(double x, double y, double z) {
        return this.project(GenericMath.floorl(x), GenericMath.floorl(y), GenericMath.floorl(z));
    }

    public Vector3l project(long x, long y, long z) {
        long lengthSquared = x * x + y * y + z * z;
        if (lengthSquared == 0) {
            throw new ArithmeticException("Cannot project onto the zero vector");
        }
        double a = (double)this.dot(x, y, z) / (double)lengthSquared;
        return new Vector3l(a * (double)x, a * (double)y, a * (double)z);
    }

    public Vector3l cross(Vector3l v) {
        return this.cross(v.x, v.y, v.z);
    }

    public Vector3l cross(double x, double y, double z) {
        return this.cross(GenericMath.floorl(x), GenericMath.floorl(y), GenericMath.floorl(z));
    }

    public Vector3l cross(long x, long y, long z) {
        return new Vector3l(this.y * z - this.z * y, this.z * x - this.x * z, this.x * y - this.y * x);
    }

    public Vector3l pow(double pow) {
        return this.pow(GenericMath.floorl(pow));
    }

    @Override
    public Vector3l pow(long power) {
        return new Vector3l(Math.pow(this.x, power), Math.pow(this.y, power), Math.pow(this.z, power));
    }

    @Override
    public Vector3l abs() {
        return new Vector3l(Math.abs(this.x), Math.abs(this.y), Math.abs(this.z));
    }

    @Override
    public Vector3l negate() {
        return new Vector3l(- this.x, - this.y, - this.z);
    }

    public Vector3l min(Vector3l v) {
        return this.min(v.x, v.y, v.z);
    }

    public Vector3l min(double x, double y, double z) {
        return this.min(GenericMath.floorl(x), GenericMath.floorl(y), GenericMath.floorl(z));
    }

    public Vector3l min(long x, long y, long z) {
        return new Vector3l(Math.min(this.x, x), Math.min(this.y, y), Math.min(this.z, z));
    }

    public Vector3l max(Vector3l v) {
        return this.max(v.x, v.y, v.z);
    }

    public Vector3l max(double x, double y, double z) {
        return this.max(GenericMath.floorl(x), GenericMath.floorl(y), GenericMath.floorl(z));
    }

    public Vector3l max(long x, long y, long z) {
        return new Vector3l(Math.max(this.x, x), Math.max(this.y, y), Math.max(this.z, z));
    }

    public long distanceSquared(Vector3l v) {
        return this.distanceSquared(v.x, v.y, v.z);
    }

    public long distanceSquared(double x, double y, double z) {
        return this.distanceSquared(GenericMath.floorl(x), GenericMath.floorl(y), GenericMath.floorl(z));
    }

    public long distanceSquared(long x, long y, long z) {
        long dx = this.x - x;
        long dy = this.y - y;
        long dz = this.z - z;
        return dx * dx + dy * dy + dz * dz;
    }

    public double distance(Vector3l v) {
        return this.distance(v.x, v.y, v.z);
    }

    public double distance(double x, double y, double z) {
        return this.distance(GenericMath.floorl(x), GenericMath.floorl(y), GenericMath.floorl(z));
    }

    public double distance(long x, long y, long z) {
        return Math.sqrt(this.distanceSquared(x, y, z));
    }

    @Override
    public long lengthSquared() {
        return this.x * this.x + this.y * this.y + this.z * this.z;
    }

    @Override
    public double length() {
        return Math.sqrt(this.lengthSquared());
    }

    @Override
    public int getMinAxis() {
        return this.x < this.y ? (this.x < this.z ? 0 : 2) : (this.y < this.z ? 1 : 2);
    }

    @Override
    public int getMaxAxis() {
        return this.x < this.y ? (this.y < this.z ? 2 : 1) : (this.x < this.z ? 2 : 0);
    }

    public Vector2l toVector2() {
        return new Vector2l(this);
    }

    public Vector2l toVector2(boolean useZ) {
        return new Vector2l(this.x, useZ ? this.z : this.y);
    }

    public Vector4l toVector4() {
        return this.toVector4(0);
    }

    public Vector4l toVector4(double w) {
        return this.toVector4(GenericMath.floorl(w));
    }

    public Vector4l toVector4(long w) {
        return new Vector4l(this, w);
    }

    public VectorNl toVectorN() {
        return new VectorNl(this);
    }

    @Override
    public long[] toArray() {
        return new long[]{this.x, this.y, this.z};
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
    public int compareTo(Vector3l v) {
        return (int)(this.lengthSquared() - v.lengthSquared());
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Vector3l)) {
            return false;
        }
        Vector3l vector3 = (Vector3l)o;
        if (vector3.x != this.x) {
            return false;
        }
        if (vector3.y != this.y) {
            return false;
        }
        if (vector3.z != this.z) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        if (!this.hashed) {
            this.hashCode = (HashFunctions.hash(this.x) * 211 + HashFunctions.hash(this.y)) * 97 + HashFunctions.hash(this.z);
            this.hashed = true;
        }
        return this.hashCode;
    }

    public Vector3l clone() {
        return new Vector3l(this);
    }

    public String toString() {
        return "(" + this.x + ", " + this.y + ", " + this.z + ")";
    }

    public static Vector3l from(long n) {
        return n == 0 ? ZERO : new Vector3l(n, n, n);
    }

    public static Vector3l from(long x, long y, long z) {
        return x == 0 && y == 0 && z == 0 ? ZERO : new Vector3l(x, y, z);
    }
}

