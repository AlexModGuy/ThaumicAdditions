/*
 * Decompiled with CFR 0_123.
 */
package com.endie.thaumicadditions.shaded.flowpoweredmath.vector;

import java.io.Serializable;

import com.endie.thaumicadditions.shaded.flowpoweredmath.GenericMath;
import com.endie.thaumicadditions.shaded.flowpoweredmath.HashFunctions;
import com.endie.thaumicadditions.shaded.flowpoweredmath.vector.Vector2d;
import com.endie.thaumicadditions.shaded.flowpoweredmath.vector.Vector2f;
import com.endie.thaumicadditions.shaded.flowpoweredmath.vector.Vector2i;
import com.endie.thaumicadditions.shaded.flowpoweredmath.vector.Vector3l;
import com.endie.thaumicadditions.shaded.flowpoweredmath.vector.Vector4l;
import com.endie.thaumicadditions.shaded.flowpoweredmath.vector.VectorNl;
import com.endie.thaumicadditions.shaded.flowpoweredmath.vector.Vectord;
import com.endie.thaumicadditions.shaded.flowpoweredmath.vector.Vectorf;
import com.endie.thaumicadditions.shaded.flowpoweredmath.vector.Vectori;
import com.endie.thaumicadditions.shaded.flowpoweredmath.vector.Vectorl;

public class Vector2l
implements Vectorl,
Comparable<Vector2l>,
Serializable,
Cloneable {
    private static final long serialVersionUID = 1;
    public static final Vector2l ZERO = new Vector2l(0, 0);
    public static final Vector2l UNIT_X = new Vector2l(1, 0);
    public static final Vector2l UNIT_Y = new Vector2l(0, 1);
    public static final Vector2l ONE = new Vector2l(1, 1);
    private final long x;
    private final long y;
    private volatile transient boolean hashed = false;
    private volatile transient int hashCode = 0;

    public Vector2l() {
        this(0, 0);
    }

    public Vector2l(Vector2l v) {
        this(v.x, v.y);
    }

    public Vector2l(Vector3l v) {
        this(v.getX(), v.getY());
    }

    public Vector2l(Vector4l v) {
        this(v.getX(), v.getY());
    }

    public Vector2l(VectorNl v) {
        this(v.get(0), v.get(1));
    }

    public Vector2l(double x, double y) {
        this(GenericMath.floorl(x), GenericMath.floorl(y));
    }

    public Vector2l(long x, long y) {
        this.x = x;
        this.y = y;
    }

    public long getX() {
        return this.x;
    }

    public long getY() {
        return this.y;
    }

    public Vector2l add(Vector2l v) {
        return this.add(v.x, v.y);
    }

    public Vector2l add(double x, double y) {
        return this.add(GenericMath.floorl(x), GenericMath.floorl(y));
    }

    public Vector2l add(long x, long y) {
        return new Vector2l(this.x + x, this.y + y);
    }

    public Vector2l sub(Vector2l v) {
        return this.sub(v.x, v.y);
    }

    public Vector2l sub(double x, double y) {
        return this.sub(GenericMath.floorl(x), GenericMath.floorl(y));
    }

    public Vector2l sub(long x, long y) {
        return new Vector2l(this.x - x, this.y - y);
    }

    public Vector2l mul(double a) {
        return this.mul(GenericMath.floorl(a));
    }

    @Override
    public Vector2l mul(long a) {
        return this.mul(a, a);
    }

    public Vector2l mul(Vector2l v) {
        return this.mul(v.x, v.y);
    }

    public Vector2l mul(double x, double y) {
        return this.mul(GenericMath.floorl(x), GenericMath.floorl(y));
    }

    public Vector2l mul(long x, long y) {
        return new Vector2l(this.x * x, this.y * y);
    }

    public Vector2l div(double a) {
        return this.div(GenericMath.floorl(a));
    }

    @Override
    public Vector2l div(long a) {
        return this.div(a, a);
    }

    public Vector2l div(Vector2l v) {
        return this.div(v.x, v.y);
    }

    public Vector2l div(double x, double y) {
        return this.div(GenericMath.floorl(x), GenericMath.floorl(y));
    }

    public Vector2l div(long x, long y) {
        return new Vector2l(this.x / x, this.y / y);
    }

    public long dot(Vector2l v) {
        return this.dot(v.x, v.y);
    }

    public long dot(double x, double y) {
        return this.dot(GenericMath.floorl(x), GenericMath.floorl(y));
    }

    public long dot(long x, long y) {
        return this.x * x + this.y * y;
    }

    public Vector2l project(Vector2l v) {
        return this.project(v.x, v.y);
    }

    public Vector2l project(double x, double y) {
        return this.project(GenericMath.floorl(x), GenericMath.floorl(y));
    }

    public Vector2l project(long x, long y) {
        long lengthSquared = x * x + y * y;
        if (lengthSquared == 0) {
            throw new ArithmeticException("Cannot project onto the zero vector");
        }
        double a = (double)this.dot(x, y) / (double)lengthSquared;
        return new Vector2l(a * (double)x, a * (double)y);
    }

    public Vector2l pow(double pow) {
        return this.pow(GenericMath.floorl(pow));
    }

    @Override
    public Vector2l pow(long power) {
        return new Vector2l(Math.pow(this.x, power), Math.pow(this.y, power));
    }

    @Override
    public Vector2l abs() {
        return new Vector2l(Math.abs(this.x), Math.abs(this.y));
    }

    @Override
    public Vector2l negate() {
        return new Vector2l(- this.x, - this.y);
    }

    public Vector2l min(Vector2l v) {
        return this.min(v.x, v.y);
    }

    public Vector2l min(double x, double y) {
        return this.min(GenericMath.floorl(x), GenericMath.floorl(y));
    }

    public Vector2l min(long x, long y) {
        return new Vector2l(Math.min(this.x, x), Math.min(this.y, y));
    }

    public Vector2l max(Vector2l v) {
        return this.max(v.x, v.y);
    }

    public Vector2l max(double x, double y) {
        return this.max(GenericMath.floorl(x), GenericMath.floorl(y));
    }

    public Vector2l max(long x, long y) {
        return new Vector2l(Math.max(this.x, x), Math.max(this.y, y));
    }

    public long distanceSquared(Vector2l v) {
        return this.distanceSquared(v.x, v.y);
    }

    public long distanceSquared(double x, double y) {
        return this.distanceSquared(GenericMath.floorl(x), GenericMath.floorl(y));
    }

    public long distanceSquared(long x, long y) {
        long dx = this.x - x;
        long dy = this.y - y;
        return dx * dx + dy * dy;
    }

    public double distance(Vector2l v) {
        return this.distance(v.x, v.y);
    }

    public double distance(double x, double y) {
        return this.distance(GenericMath.floorl(x), GenericMath.floorl(y));
    }

    public double distance(long x, long y) {
        return Math.sqrt(this.distanceSquared(x, y));
    }

    @Override
    public long lengthSquared() {
        return this.x * this.x + this.y * this.y;
    }

    @Override
    public double length() {
        return Math.sqrt(this.lengthSquared());
    }

    @Override
    public int getMinAxis() {
        return this.x < this.y ? 0 : 1;
    }

    @Override
    public int getMaxAxis() {
        return this.x > this.y ? 0 : 1;
    }

    public Vector3l toVector3() {
        return this.toVector3(0);
    }

    public Vector3l toVector3(double z) {
        return this.toVector3(GenericMath.floorl(z));
    }

    public Vector3l toVector3(long z) {
        return new Vector3l(this, z);
    }

    public Vector4l toVector4() {
        return this.toVector4(0, 0);
    }

    public Vector4l toVector4(double z, double w) {
        return this.toVector4(GenericMath.floorl(z), GenericMath.floorl(w));
    }

    public Vector4l toVector4(long z, long w) {
        return new Vector4l(this, z, w);
    }

    public VectorNl toVectorN() {
        return new VectorNl(this);
    }

    @Override
    public long[] toArray() {
        return new long[]{this.x, this.y};
    }

    @Override
    public Vector2i toInt() {
        return new Vector2i(this.x, this.y);
    }

    @Override
    public Vector2l toLong() {
        return new Vector2l(this.x, this.y);
    }

    @Override
    public Vector2f toFloat() {
        return new Vector2f(this.x, this.y);
    }

    @Override
    public Vector2d toDouble() {
        return new Vector2d(this.x, this.y);
    }

    @Override
    public int compareTo(Vector2l v) {
        return (int)(this.lengthSquared() - v.lengthSquared());
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Vector2l)) {
            return false;
        }
        Vector2l vector2 = (Vector2l)o;
        if (vector2.x != this.x) {
            return false;
        }
        if (vector2.y != this.y) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        if (!this.hashed) {
            int result = (float)this.x != 0.0f ? HashFunctions.hash(this.x) : 0;
            this.hashCode = 31 * result + ((float)this.y != 0.0f ? HashFunctions.hash(this.y) : 0);
            this.hashed = true;
        }
        return this.hashCode;
    }

    public Vector2l clone() {
        return new Vector2l(this);
    }

    public String toString() {
        return "(" + this.x + ", " + this.y + ")";
    }

    public static Vector2l from(long n) {
        return n == 0 ? ZERO : new Vector2l(n, n);
    }

    public static Vector2l from(long x, long y) {
        return x == 0 && y == 0 ? ZERO : new Vector2l(x, y);
    }
}

