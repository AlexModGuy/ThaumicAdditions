/*
 * Decompiled with CFR 0_123.
 */
package com.endie.thaumicadditions.shaded.flowpoweredmath.vector;

import java.io.Serializable;

import com.endie.thaumicadditions.shaded.flowpoweredmath.GenericMath;
import com.endie.thaumicadditions.shaded.flowpoweredmath.HashFunctions;
import com.endie.thaumicadditions.shaded.flowpoweredmath.vector.Vector2l;
import com.endie.thaumicadditions.shaded.flowpoweredmath.vector.Vector3l;
import com.endie.thaumicadditions.shaded.flowpoweredmath.vector.Vector4d;
import com.endie.thaumicadditions.shaded.flowpoweredmath.vector.Vector4f;
import com.endie.thaumicadditions.shaded.flowpoweredmath.vector.Vector4i;
import com.endie.thaumicadditions.shaded.flowpoweredmath.vector.VectorNl;
import com.endie.thaumicadditions.shaded.flowpoweredmath.vector.Vectord;
import com.endie.thaumicadditions.shaded.flowpoweredmath.vector.Vectorf;
import com.endie.thaumicadditions.shaded.flowpoweredmath.vector.Vectori;
import com.endie.thaumicadditions.shaded.flowpoweredmath.vector.Vectorl;

public class Vector4l
implements Vectorl,
Comparable<Vector4l>,
Serializable,
Cloneable {
    private static final long serialVersionUID = 1;
    public static final Vector4l ZERO = new Vector4l(0, 0, 0, 0);
    public static final Vector4l UNIT_X = new Vector4l(1, 0, 0, 0);
    public static final Vector4l UNIT_Y = new Vector4l(0, 1, 0, 0);
    public static final Vector4l UNIT_Z = new Vector4l(0, 0, 1, 0);
    public static final Vector4l UNIT_W = new Vector4l(0, 0, 0, 1);
    public static final Vector4l ONE = new Vector4l(1, 1, 1, 1);
    private final long x;
    private final long y;
    private final long z;
    private final long w;
    private volatile transient boolean hashed = false;
    private volatile transient int hashCode = 0;

    public Vector4l() {
        this(0, 0, 0, 0);
    }

    public Vector4l(Vector2l v) {
        this(v, 0, 0);
    }

    public Vector4l(Vector2l v, double z, double w) {
        this(v, GenericMath.floorl(z), GenericMath.floorl(w));
    }

    public Vector4l(Vector2l v, long z, long w) {
        this(v.getX(), v.getY(), z, w);
    }

    public Vector4l(Vector3l v) {
        this(v, 0);
    }

    public Vector4l(Vector3l v, double w) {
        this(v, GenericMath.floorl(w));
    }

    public Vector4l(Vector3l v, long w) {
        this(v.getX(), v.getY(), v.getZ(), w);
    }

    public Vector4l(Vector4l v) {
        this(v.x, v.y, v.z, v.w);
    }

    public Vector4l(VectorNl v) {
        this(v.get(0), v.get(1), v.size() > 2 ? v.get(2) : 0, v.size() > 3 ? v.get(3) : 0);
    }

    public Vector4l(double x, double y, double z, double w) {
        this(GenericMath.floorl(x), GenericMath.floorl(y), GenericMath.floorl(z), GenericMath.floorl(w));
    }

    public Vector4l(long x, long y, long z, long w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
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

    public long getW() {
        return this.w;
    }

    public Vector4l add(Vector4l v) {
        return this.add(v.x, v.y, v.z, v.w);
    }

    public Vector4l add(double x, double y, double z, double w) {
        return this.add(GenericMath.floorl(x), GenericMath.floorl(y), GenericMath.floorl(z), GenericMath.floorl(w));
    }

    public Vector4l add(long x, long y, long z, long w) {
        return new Vector4l(this.x + x, this.y + y, this.z + z, this.w + w);
    }

    public Vector4l sub(Vector4l v) {
        return this.sub(v.x, v.y, v.z, v.w);
    }

    public Vector4l sub(double x, double y, double z, double w) {
        return this.sub(GenericMath.floorl(x), GenericMath.floorl(y), GenericMath.floorl(z), GenericMath.floorl(w));
    }

    public Vector4l sub(long x, long y, long z, long w) {
        return new Vector4l(this.x - x, this.y - y, this.z - z, this.w - w);
    }

    public Vector4l mul(double a) {
        return this.mul(GenericMath.floorl(a));
    }

    @Override
    public Vector4l mul(long a) {
        return this.mul(a, a, a, a);
    }

    public Vector4l mul(Vector4l v) {
        return this.mul(v.x, v.y, v.z, v.w);
    }

    public Vector4l mul(double x, double y, double z, double w) {
        return this.mul(GenericMath.floorl(x), GenericMath.floorl(y), GenericMath.floorl(z), GenericMath.floorl(w));
    }

    public Vector4l mul(long x, long y, long z, long w) {
        return new Vector4l(this.x * x, this.y * y, this.z * z, this.w * w);
    }

    public Vector4l div(double a) {
        return this.div(GenericMath.floorl(a));
    }

    @Override
    public Vector4l div(long a) {
        return this.div(a, a, a, a);
    }

    public Vector4l div(Vector4l v) {
        return this.div(v.x, v.y, v.z, v.w);
    }

    public Vector4l div(double x, double y, double z, double w) {
        return this.div(GenericMath.floorl(x), GenericMath.floorl(y), GenericMath.floorl(z), GenericMath.floorl(w));
    }

    public Vector4l div(long x, long y, long z, long w) {
        return new Vector4l(this.x / x, this.y / y, this.z / z, this.w / w);
    }

    public long dot(Vector4l v) {
        return this.dot(v.x, v.y, v.z, v.w);
    }

    public long dot(double x, double y, double z, double w) {
        return this.dot(GenericMath.floorl(x), GenericMath.floorl(y), GenericMath.floorl(z), GenericMath.floorl(w));
    }

    public long dot(long x, long y, long z, long w) {
        return this.x * x + this.y * y + this.z * z + this.w * w;
    }

    public Vector4l project(Vector4l v) {
        return this.project(v.x, v.y, v.z, v.w);
    }

    public Vector4l project(double x, double y, double z, double w) {
        return this.project(GenericMath.floorl(x), GenericMath.floorl(y), GenericMath.floorl(z), GenericMath.floorl(w));
    }

    public Vector4l project(long x, long y, long z, long w) {
        long lengthSquared = x * x + y * y + z * z + w * w;
        if (lengthSquared == 0) {
            throw new ArithmeticException("Cannot project onto the zero vector");
        }
        double a = (double)this.dot(x, y, z, w) / (double)lengthSquared;
        return new Vector4l(a * (double)x, a * (double)y, a * (double)z, a * (double)w);
    }

    public Vector4l pow(double pow) {
        return this.pow(GenericMath.floorl(pow));
    }

    @Override
    public Vector4l pow(long power) {
        return new Vector4l(Math.pow(this.x, power), Math.pow(this.y, power), Math.pow(this.z, power), Math.pow(this.w, power));
    }

    @Override
    public Vector4l abs() {
        return new Vector4l(Math.abs(this.x), Math.abs(this.y), Math.abs(this.z), Math.abs(this.w));
    }

    @Override
    public Vector4l negate() {
        return new Vector4l(- this.x, - this.y, - this.z, - this.w);
    }

    public Vector4l min(Vector4l v) {
        return this.min(v.x, v.y, v.z, v.w);
    }

    public Vector4l min(double x, double y, double z, double w) {
        return this.min(GenericMath.floorl(x), GenericMath.floorl(y), GenericMath.floorl(z), GenericMath.floorl(w));
    }

    public Vector4l min(long x, long y, long z, long w) {
        return new Vector4l(Math.min(this.x, x), Math.min(this.y, y), Math.min(this.z, z), Math.min(this.w, w));
    }

    public Vector4l max(Vector4l v) {
        return this.max(v.x, v.y, v.z, v.w);
    }

    public Vector4l max(double x, double y, double z, double w) {
        return this.max(GenericMath.floorl(x), GenericMath.floorl(y), GenericMath.floorl(z), GenericMath.floorl(w));
    }

    public Vector4l max(long x, long y, long z, long w) {
        return new Vector4l(Math.max(this.x, x), Math.max(this.y, y), Math.max(this.z, z), Math.max(this.w, w));
    }

    public long distanceSquared(Vector4l v) {
        return this.distanceSquared(v.x, v.y, v.z, v.w);
    }

    public long distanceSquared(double x, double y, double z, double w) {
        return this.distanceSquared(GenericMath.floorl(x), GenericMath.floorl(y), GenericMath.floorl(z), GenericMath.floorl(w));
    }

    public long distanceSquared(long x, long y, long z, long w) {
        long dx = this.x - x;
        long dy = this.y - y;
        long dz = this.z - z;
        long dw = this.w - w;
        return dx * dx + dy * dy + dz * dz + dw * dw;
    }

    public double distance(Vector4l v) {
        return this.distance(v.x, v.y, v.z, v.w);
    }

    public double distance(double x, double y, double z, double w) {
        return this.distance(GenericMath.floorl(x), GenericMath.floorl(y), GenericMath.floorl(z), GenericMath.floorl(w));
    }

    public double distance(long x, long y, long z, long w) {
        return Math.sqrt(this.distanceSquared(x, y, z, w));
    }

    @Override
    public long lengthSquared() {
        return this.x * this.x + this.y * this.y + this.z * this.z + this.w * this.w;
    }

    @Override
    public double length() {
        return Math.sqrt(this.lengthSquared());
    }

    @Override
    public int getMinAxis() {
        long value = this.x;
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
        long value = this.x;
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

    public Vector2l toVector2() {
        return new Vector2l(this);
    }

    public Vector3l toVector3() {
        return new Vector3l(this);
    }

    public VectorNl toVectorN() {
        return new VectorNl(this);
    }

    @Override
    public long[] toArray() {
        return new long[]{this.x, this.y, this.z, this.w};
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
    public int compareTo(Vector4l v) {
        return (int)(this.lengthSquared() - v.lengthSquared());
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Vector4l)) {
            return false;
        }
        Vector4l vector4 = (Vector4l)o;
        if (vector4.x != this.x) {
            return false;
        }
        if (vector4.y != this.y) {
            return false;
        }
        if (vector4.z != this.z) {
            return false;
        }
        if (vector4.w != this.w) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        if (!this.hashed) {
            int result = (float)this.x != 0.0f ? HashFunctions.hash(this.x) : 0;
            result = 31 * result + ((float)this.y != 0.0f ? HashFunctions.hash(this.y) : 0);
            result = 31 * result + ((float)this.z != 0.0f ? HashFunctions.hash(this.z) : 0);
            this.hashCode = 31 * result + ((float)this.w != 0.0f ? HashFunctions.hash(this.w) : 0);
            this.hashed = true;
        }
        return this.hashCode;
    }

    public Vector4l clone() {
        return new Vector4l(this);
    }

    public String toString() {
        return "(" + this.x + ", " + this.y + ", " + this.z + ", " + this.w + ")";
    }

    public static Vector4l from(long n) {
        return n == 0 ? ZERO : new Vector4l(n, n, n, n);
    }

    public static Vector4l from(long x, long y, long z, long w) {
        return x == 0 && y == 0 && z == 0 && w == 0 ? ZERO : new Vector4l(x, y, z, w);
    }
}

