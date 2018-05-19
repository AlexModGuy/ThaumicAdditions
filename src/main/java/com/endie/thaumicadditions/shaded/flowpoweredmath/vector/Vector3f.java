/*
 * Decompiled with CFR 0_123.
 */
package com.endie.thaumicadditions.shaded.flowpoweredmath.vector;

import java.io.Serializable;
import java.util.Random;

import com.endie.thaumicadditions.shaded.flowpoweredmath.GenericMath;
import com.endie.thaumicadditions.shaded.flowpoweredmath.HashFunctions;
import com.endie.thaumicadditions.shaded.flowpoweredmath.TrigMath;
import com.endie.thaumicadditions.shaded.flowpoweredmath.vector.Vector2f;
import com.endie.thaumicadditions.shaded.flowpoweredmath.vector.Vector3d;
import com.endie.thaumicadditions.shaded.flowpoweredmath.vector.Vector3i;
import com.endie.thaumicadditions.shaded.flowpoweredmath.vector.Vector3l;
import com.endie.thaumicadditions.shaded.flowpoweredmath.vector.Vector4f;
import com.endie.thaumicadditions.shaded.flowpoweredmath.vector.VectorNf;
import com.endie.thaumicadditions.shaded.flowpoweredmath.vector.Vectord;
import com.endie.thaumicadditions.shaded.flowpoweredmath.vector.Vectorf;
import com.endie.thaumicadditions.shaded.flowpoweredmath.vector.Vectori;
import com.endie.thaumicadditions.shaded.flowpoweredmath.vector.Vectorl;

public class Vector3f
implements Vectorf,
Comparable<Vector3f>,
Serializable,
Cloneable {
    private static final long serialVersionUID = 1;
    public static final Vector3f ZERO = new Vector3f(0.0f, 0.0f, 0.0f);
    public static final Vector3f UNIT_X = new Vector3f(1.0f, 0.0f, 0.0f);
    public static final Vector3f UNIT_Y = new Vector3f(0.0f, 1.0f, 0.0f);
    public static final Vector3f UNIT_Z = new Vector3f(0.0f, 0.0f, 1.0f);
    public static final Vector3f ONE = new Vector3f(1.0f, 1.0f, 1.0f);
    public static final Vector3f RIGHT = UNIT_X;
    public static final Vector3f UP = UNIT_Y;
    public static final Vector3f FORWARD = UNIT_Z;
    private final float x;
    private final float y;
    private final float z;
    private volatile transient boolean hashed = false;
    private volatile transient int hashCode = 0;

    public Vector3f() {
        this(0.0f, 0.0f, 0.0f);
    }

    public Vector3f(Vector2f v) {
        this(v, 0.0f);
    }

    public Vector3f(Vector2f v, double z) {
        this(v, (float)z);
    }

    public Vector3f(Vector2f v, float z) {
        this(v.getX(), v.getY(), z);
    }

    public Vector3f(Vector3f v) {
        this(v.x, v.y, v.z);
    }

    public Vector3f(Vector4f v) {
        this(v.getX(), v.getY(), v.getZ());
    }

    public Vector3f(VectorNf v) {
        this(v.get(0), v.get(1), v.size() > 2 ? v.get(2) : 0.0f);
    }

    public Vector3f(double x, double y, double z) {
        this((float)x, (float)y, (float)z);
    }

    public Vector3f(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
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

    public int getFloorX() {
        return GenericMath.floor(this.x);
    }

    public int getFloorY() {
        return GenericMath.floor(this.y);
    }

    public int getFloorZ() {
        return GenericMath.floor(this.z);
    }

    public Vector3f add(Vector3f v) {
        return this.add(v.x, v.y, v.z);
    }

    public Vector3f add(double x, double y, double z) {
        return this.add((float)x, (float)y, (float)z);
    }

    public Vector3f add(float x, float y, float z) {
        return new Vector3f(this.x + x, this.y + y, this.z + z);
    }

    public Vector3f sub(Vector3f v) {
        return this.sub(v.x, v.y, v.z);
    }

    public Vector3f sub(double x, double y, double z) {
        return this.sub((float)x, (float)y, (float)z);
    }

    public Vector3f sub(float x, float y, float z) {
        return new Vector3f(this.x - x, this.y - y, this.z - z);
    }

    public Vector3f mul(double a) {
        return this.mul((float)a);
    }

    @Override
    public Vector3f mul(float a) {
        return this.mul(a, a, a);
    }

    public Vector3f mul(Vector3f v) {
        return this.mul(v.x, v.y, v.z);
    }

    public Vector3f mul(double x, double y, double z) {
        return this.mul((float)x, (float)y, (float)z);
    }

    public Vector3f mul(float x, float y, float z) {
        return new Vector3f(this.x * x, this.y * y, this.z * z);
    }

    public Vector3f div(double a) {
        return this.div((float)a);
    }

    @Override
    public Vector3f div(float a) {
        return this.div(a, a, a);
    }

    public Vector3f div(Vector3f v) {
        return this.div(v.x, v.y, v.z);
    }

    public Vector3f div(double x, double y, double z) {
        return this.div((float)x, (float)y, (float)z);
    }

    public Vector3f div(float x, float y, float z) {
        return new Vector3f(this.x / x, this.y / y, this.z / z);
    }

    public float dot(Vector3f v) {
        return this.dot(v.x, v.y, v.z);
    }

    public float dot(double x, double y, double z) {
        return this.dot((float)x, (float)y, (float)z);
    }

    public float dot(float x, float y, float z) {
        return this.x * x + this.y * y + this.z * z;
    }

    public Vector3f project(Vector3f v) {
        return this.project(v.x, v.y, v.z);
    }

    public Vector3f project(double x, double y, double z) {
        return this.project((float)x, (float)y, (float)z);
    }

    public Vector3f project(float x, float y, float z) {
        float lengthSquared = x * x + y * y + z * z;
        if (Math.abs(lengthSquared) < GenericMath.FLT_EPSILON) {
            throw new ArithmeticException("Cannot project onto the zero vector");
        }
        float a = this.dot(x, y, z) / lengthSquared;
        return new Vector3f(a * x, a * y, a * z);
    }

    public Vector3f cross(Vector3f v) {
        return this.cross(v.x, v.y, v.z);
    }

    public Vector3f cross(double x, double y, double z) {
        return this.cross((float)x, (float)y, (float)z);
    }

    public Vector3f cross(float x, float y, float z) {
        return new Vector3f(this.y * z - this.z * y, this.z * x - this.x * z, this.x * y - this.y * x);
    }

    public Vector3f pow(double pow) {
        return this.pow((float)pow);
    }

    @Override
    public Vector3f pow(float power) {
        return new Vector3f(Math.pow(this.x, power), Math.pow(this.y, power), Math.pow(this.z, power));
    }

    @Override
    public Vector3f ceil() {
        return new Vector3f(Math.ceil(this.x), Math.ceil(this.y), Math.ceil(this.z));
    }

    @Override
    public Vector3f floor() {
        return new Vector3f(GenericMath.floor(this.x), GenericMath.floor(this.y), GenericMath.floor(this.z));
    }

    @Override
    public Vector3f round() {
        return new Vector3f(Math.round(this.x), Math.round(this.y), Math.round(this.z));
    }

    @Override
    public Vector3f abs() {
        return new Vector3f(Math.abs(this.x), Math.abs(this.y), Math.abs(this.z));
    }

    @Override
    public Vector3f negate() {
        return new Vector3f(- this.x, - this.y, - this.z);
    }

    public Vector3f min(Vector3f v) {
        return this.min(v.x, v.y, v.z);
    }

    public Vector3f min(double x, double y, double z) {
        return this.min((float)x, (float)y, (float)z);
    }

    public Vector3f min(float x, float y, float z) {
        return new Vector3f(Math.min(this.x, x), Math.min(this.y, y), Math.min(this.z, z));
    }

    public Vector3f max(Vector3f v) {
        return this.max(v.x, v.y, v.z);
    }

    public Vector3f max(double x, double y, double z) {
        return this.max((float)x, (float)y, (float)z);
    }

    public Vector3f max(float x, float y, float z) {
        return new Vector3f(Math.max(this.x, x), Math.max(this.y, y), Math.max(this.z, z));
    }

    public float distanceSquared(Vector3f v) {
        return this.distanceSquared(v.x, v.y, v.z);
    }

    public float distanceSquared(double x, double y, double z) {
        return this.distanceSquared((float)x, (float)y, (float)z);
    }

    public float distanceSquared(float x, float y, float z) {
        float dx = this.x - x;
        float dy = this.y - y;
        float dz = this.z - z;
        return dx * dx + dy * dy + dz * dz;
    }

    public float distance(Vector3f v) {
        return this.distance(v.x, v.y, v.z);
    }

    public float distance(double x, double y, double z) {
        return this.distance((float)x, (float)y, (float)z);
    }

    public float distance(float x, float y, float z) {
        return (float)Math.sqrt(this.distanceSquared(x, y, z));
    }

    @Override
    public float lengthSquared() {
        return this.x * this.x + this.y * this.y + this.z * this.z;
    }

    @Override
    public float length() {
        return (float)Math.sqrt(this.lengthSquared());
    }

    @Override
    public Vector3f normalize() {
        float length = this.length();
        if (Math.abs(length) < GenericMath.FLT_EPSILON) {
            throw new ArithmeticException("Cannot normalize the zero vector");
        }
        return new Vector3f(this.x / length, this.y / length, this.z / length);
    }

    @Override
    public int getMinAxis() {
        return this.x < this.y ? (this.x < this.z ? 0 : 2) : (this.y < this.z ? 1 : 2);
    }

    @Override
    public int getMaxAxis() {
        return this.x < this.y ? (this.y < this.z ? 2 : 1) : (this.x < this.z ? 2 : 0);
    }

    public Vector2f toVector2() {
        return new Vector2f(this);
    }

    public Vector2f toVector2(boolean useZ) {
        return new Vector2f(this.x, useZ ? this.z : this.y);
    }

    public Vector4f toVector4() {
        return this.toVector4(0.0f);
    }

    public Vector4f toVector4(double w) {
        return this.toVector4((float)w);
    }

    public Vector4f toVector4(float w) {
        return new Vector4f(this, w);
    }

    public VectorNf toVectorN() {
        return new VectorNf(this);
    }

    @Override
    public float[] toArray() {
        return new float[]{this.x, this.y, this.z};
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
    public int compareTo(Vector3f v) {
        return (int)Math.signum(this.lengthSquared() - v.lengthSquared());
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Vector3f)) {
            return false;
        }
        Vector3f vector3 = (Vector3f)o;
        if (Float.compare(vector3.x, this.x) != 0) {
            return false;
        }
        if (Float.compare(vector3.y, this.y) != 0) {
            return false;
        }
        if (Float.compare(vector3.z, this.z) != 0) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        if (!this.hashed) {
            int result = this.x != 0.0f ? HashFunctions.hash(this.x) : 0;
            result = 31 * result + (this.y != 0.0f ? HashFunctions.hash(this.y) : 0);
            this.hashCode = 31 * result + (this.z != 0.0f ? HashFunctions.hash(this.z) : 0);
            this.hashed = true;
        }
        return this.hashCode;
    }

    public Vector3f clone() {
        return new Vector3f(this);
    }

    public String toString() {
        return "(" + this.x + ", " + this.y + ", " + this.z + ")";
    }

    public static Vector3f from(float n) {
        return n == 0.0f ? ZERO : new Vector3f(n, n, n);
    }

    public static Vector3f from(float x, float y, float z) {
        return x == 0.0f && y == 0.0f && z == 0.0f ? ZERO : new Vector3f(x, y, z);
    }

    public static Vector3f createRandomDirection(Random random) {
        return Vector3f.createDirectionRad(random.nextFloat() * 6.2831855f, random.nextFloat() * 6.2831855f);
    }

    public static Vector3f createDirectionDeg(double theta, double phi) {
        return Vector3f.createDirectionDeg((float)theta, (float)phi);
    }

    public static Vector3f createDirectionDeg(float theta, float phi) {
        return Vector3f.createDirectionRad((float)Math.toRadians(theta), (float)Math.toRadians(phi));
    }

    public static Vector3f createDirectionRad(double theta, double phi) {
        return Vector3f.createDirectionRad((float)theta, (float)phi);
    }

    public static Vector3f createDirectionRad(float theta, float phi) {
        float f = TrigMath.sin(phi);
        return new Vector3f(f * TrigMath.cos(theta), f * TrigMath.sin(theta), TrigMath.cos(phi));
    }
}

