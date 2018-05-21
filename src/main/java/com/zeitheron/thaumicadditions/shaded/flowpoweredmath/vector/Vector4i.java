/*
 * Decompiled with CFR 0_123.
 */
package com.zeitheron.thaumicadditions.shaded.flowpoweredmath.vector;

import java.io.Serializable;

import com.zeitheron.thaumicadditions.shaded.flowpoweredmath.GenericMath;
import com.zeitheron.thaumicadditions.shaded.flowpoweredmath.HashFunctions;
import com.zeitheron.thaumicadditions.shaded.flowpoweredmath.vector.Vector2i;
import com.zeitheron.thaumicadditions.shaded.flowpoweredmath.vector.Vector3i;
import com.zeitheron.thaumicadditions.shaded.flowpoweredmath.vector.Vector4d;
import com.zeitheron.thaumicadditions.shaded.flowpoweredmath.vector.Vector4f;
import com.zeitheron.thaumicadditions.shaded.flowpoweredmath.vector.Vector4l;
import com.zeitheron.thaumicadditions.shaded.flowpoweredmath.vector.VectorNi;
import com.zeitheron.thaumicadditions.shaded.flowpoweredmath.vector.Vectord;
import com.zeitheron.thaumicadditions.shaded.flowpoweredmath.vector.Vectorf;
import com.zeitheron.thaumicadditions.shaded.flowpoweredmath.vector.Vectori;
import com.zeitheron.thaumicadditions.shaded.flowpoweredmath.vector.Vectorl;

public class Vector4i
implements Vectori,
Comparable<Vector4i>,
Serializable,
Cloneable {
    private static final long serialVersionUID = 1;
    public static final Vector4i ZERO = new Vector4i(0, 0, 0, 0);
    public static final Vector4i UNIT_X = new Vector4i(1, 0, 0, 0);
    public static final Vector4i UNIT_Y = new Vector4i(0, 1, 0, 0);
    public static final Vector4i UNIT_Z = new Vector4i(0, 0, 1, 0);
    public static final Vector4i UNIT_W = new Vector4i(0, 0, 0, 1);
    public static final Vector4i ONE = new Vector4i(1, 1, 1, 1);
    private final int x;
    private final int y;
    private final int z;
    private final int w;
    private volatile transient boolean hashed = false;
    private volatile transient int hashCode = 0;

    public Vector4i() {
        this(0, 0, 0, 0);
    }

    public Vector4i(Vector2i v) {
        this(v, 0, 0);
    }

    public Vector4i(Vector2i v, double z, double w) {
        this(v, GenericMath.floor(z), GenericMath.floor(w));
    }

    public Vector4i(Vector2i v, int z, int w) {
        this(v.getX(), v.getY(), z, w);
    }

    public Vector4i(Vector3i v) {
        this(v, 0);
    }

    public Vector4i(Vector3i v, double w) {
        this(v, GenericMath.floor(w));
    }

    public Vector4i(Vector3i v, int w) {
        this(v.getX(), v.getY(), v.getZ(), w);
    }

    public Vector4i(Vector4i v) {
        this(v.x, v.y, v.z, v.w);
    }

    public Vector4i(VectorNi v) {
        this(v.get(0), v.get(1), v.size() > 2 ? v.get(2) : 0, v.size() > 3 ? v.get(3) : 0);
    }

    public Vector4i(double x, double y, double z, double w) {
        this(GenericMath.floor(x), GenericMath.floor(y), GenericMath.floor(z), GenericMath.floor(w));
    }

    public Vector4i(int x, int y, int z, int w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getZ() {
        return this.z;
    }

    public int getW() {
        return this.w;
    }

    public Vector4i add(Vector4i v) {
        return this.add(v.x, v.y, v.z, v.w);
    }

    public Vector4i add(double x, double y, double z, double w) {
        return this.add(GenericMath.floor(x), GenericMath.floor(y), GenericMath.floor(z), GenericMath.floor(w));
    }

    public Vector4i add(int x, int y, int z, int w) {
        return new Vector4i(this.x + x, this.y + y, this.z + z, this.w + w);
    }

    public Vector4i sub(Vector4i v) {
        return this.sub(v.x, v.y, v.z, v.w);
    }

    public Vector4i sub(double x, double y, double z, double w) {
        return this.sub(GenericMath.floor(x), GenericMath.floor(y), GenericMath.floor(z), GenericMath.floor(w));
    }

    public Vector4i sub(int x, int y, int z, int w) {
        return new Vector4i(this.x - x, this.y - y, this.z - z, this.w - w);
    }

    public Vector4i mul(double a) {
        return this.mul(GenericMath.floor(a));
    }

    @Override
    public Vector4i mul(int a) {
        return this.mul(a, a, a, a);
    }

    public Vector4i mul(Vector4i v) {
        return this.mul(v.x, v.y, v.z, v.w);
    }

    public Vector4i mul(double x, double y, double z, double w) {
        return this.mul(GenericMath.floor(x), GenericMath.floor(y), GenericMath.floor(z), GenericMath.floor(w));
    }

    public Vector4i mul(int x, int y, int z, int w) {
        return new Vector4i(this.x * x, this.y * y, this.z * z, this.w * w);
    }

    public Vector4i div(double a) {
        return this.div(GenericMath.floor(a));
    }

    @Override
    public Vector4i div(int a) {
        return this.div(a, a, a, a);
    }

    public Vector4i div(Vector4i v) {
        return this.div(v.x, v.y, v.z, v.w);
    }

    public Vector4i div(double x, double y, double z, double w) {
        return this.div(GenericMath.floor(x), GenericMath.floor(y), GenericMath.floor(z), GenericMath.floor(w));
    }

    public Vector4i div(int x, int y, int z, int w) {
        return new Vector4i(this.x / x, this.y / y, this.z / z, this.w / w);
    }

    public int dot(Vector4i v) {
        return this.dot(v.x, v.y, v.z, v.w);
    }

    public int dot(double x, double y, double z, double w) {
        return this.dot(GenericMath.floor(x), GenericMath.floor(y), GenericMath.floor(z), GenericMath.floor(w));
    }

    public int dot(int x, int y, int z, int w) {
        return this.x * x + this.y * y + this.z * z + this.w * w;
    }

    public Vector4i project(Vector4i v) {
        return this.project(v.x, v.y, v.z, v.w);
    }

    public Vector4i project(double x, double y, double z, double w) {
        return this.project(GenericMath.floor(x), GenericMath.floor(y), GenericMath.floor(z), GenericMath.floor(w));
    }

    public Vector4i project(int x, int y, int z, int w) {
        int lengthSquared = x * x + y * y + z * z + w * w;
        if (lengthSquared == 0) {
            throw new ArithmeticException("Cannot project onto the zero vector");
        }
        float a = (float)this.dot(x, y, z, w) / (float)lengthSquared;
        return new Vector4i(a * (float)x, a * (float)y, a * (float)z, a * (float)w);
    }

    public Vector4i pow(double pow) {
        return this.pow(GenericMath.floor(pow));
    }

    @Override
    public Vector4i pow(int power) {
        return new Vector4i(Math.pow(this.x, power), Math.pow(this.y, power), Math.pow(this.z, power), Math.pow(this.w, power));
    }

    @Override
    public Vector4i abs() {
        return new Vector4i(Math.abs(this.x), Math.abs(this.y), Math.abs(this.z), Math.abs(this.w));
    }

    @Override
    public Vector4i negate() {
        return new Vector4i(- this.x, - this.y, - this.z, - this.w);
    }

    public Vector4i min(Vector4i v) {
        return this.min(v.x, v.y, v.z, v.w);
    }

    public Vector4i min(double x, double y, double z, double w) {
        return this.min(GenericMath.floor(x), GenericMath.floor(y), GenericMath.floor(z), GenericMath.floor(w));
    }

    public Vector4i min(int x, int y, int z, int w) {
        return new Vector4i(Math.min(this.x, x), Math.min(this.y, y), Math.min(this.z, z), Math.min(this.w, w));
    }

    public Vector4i max(Vector4i v) {
        return this.max(v.x, v.y, v.z, v.w);
    }

    public Vector4i max(double x, double y, double z, double w) {
        return this.max(GenericMath.floor(x), GenericMath.floor(y), GenericMath.floor(z), GenericMath.floor(w));
    }

    public Vector4i max(int x, int y, int z, int w) {
        return new Vector4i(Math.max(this.x, x), Math.max(this.y, y), Math.max(this.z, z), Math.max(this.w, w));
    }

    public int distanceSquared(Vector4i v) {
        return this.distanceSquared(v.x, v.y, v.z, v.w);
    }

    public int distanceSquared(double x, double y, double z, double w) {
        return this.distanceSquared(GenericMath.floor(x), GenericMath.floor(y), GenericMath.floor(z), GenericMath.floor(w));
    }

    public int distanceSquared(int x, int y, int z, int w) {
        int dx = this.x - x;
        int dy = this.y - y;
        int dz = this.z - z;
        int dw = this.w - w;
        return dx * dx + dy * dy + dz * dz + dw * dw;
    }

    public float distance(Vector4i v) {
        return this.distance(v.x, v.y, v.z, v.w);
    }

    public float distance(double x, double y, double z, double w) {
        return this.distance(GenericMath.floor(x), GenericMath.floor(y), GenericMath.floor(z), GenericMath.floor(w));
    }

    public float distance(int x, int y, int z, int w) {
        return (float)Math.sqrt(this.distanceSquared(x, y, z, w));
    }

    @Override
    public int lengthSquared() {
        return this.x * this.x + this.y * this.y + this.z * this.z + this.w * this.w;
    }

    @Override
    public float length() {
        return (float)Math.sqrt(this.lengthSquared());
    }

    @Override
    public int getMinAxis() {
        int value = this.x;
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
        int value = this.x;
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

    public Vector2i toVector2() {
        return new Vector2i(this);
    }

    public Vector3i toVector3() {
        return new Vector3i(this);
    }

    public VectorNi toVectorN() {
        return new VectorNi(this);
    }

    @Override
    public int[] toArray() {
        return new int[]{this.x, this.y, this.z, this.w};
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
    public int compareTo(Vector4i v) {
        return this.lengthSquared() - v.lengthSquared();
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Vector4i)) {
            return false;
        }
        Vector4i vector4 = (Vector4i)o;
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

    public Vector4i clone() {
        return new Vector4i(this);
    }

    public String toString() {
        return "(" + this.x + ", " + this.y + ", " + this.z + ", " + this.w + ")";
    }

    public static Vector4i from(int n) {
        return n == 0 ? ZERO : new Vector4i(n, n, n, n);
    }

    public static Vector4i from(int x, int y, int z, int w) {
        return x == 0 && y == 0 && z == 0 && w == 0 ? ZERO : new Vector4i(x, y, z, w);
    }
}

