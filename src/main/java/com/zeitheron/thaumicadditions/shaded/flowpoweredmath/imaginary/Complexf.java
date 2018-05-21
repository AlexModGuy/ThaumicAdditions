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
import com.zeitheron.thaumicadditions.shaded.flowpoweredmath.vector.Vector2f;
import com.zeitheron.thaumicadditions.shaded.flowpoweredmath.vector.Vector3f;

public class Complexf
implements Imaginaryf,
Comparable<Complexf>,
Serializable,
Cloneable {
    private static final long serialVersionUID = 1;
    public static final Complexf ZERO = new Complexf(0.0f, 0.0f);
    public static final Complexf IDENTITY = new Complexf(1.0f, 0.0f);
    private final float x;
    private final float y;
    private volatile transient boolean hashed = false;
    private volatile transient int hashCode = 0;

    public Complexf() {
        this(1.0f, 0.0f);
    }

    public Complexf(double x, double y) {
        this((float)x, (float)y);
    }

    public Complexf(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Complexf(Complexf c) {
        this.x = c.x;
        this.y = c.y;
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    public Complexf add(Complexf c) {
        return this.add(c.x, c.y);
    }

    public Complexf add(double x, double y) {
        return this.add((float)x, (float)y);
    }

    public Complexf add(float x, float y) {
        return new Complexf(this.x + x, this.y + y);
    }

    public Complexf sub(Complexf c) {
        return this.sub(c.x, c.y);
    }

    public Complexf sub(double x, double y) {
        return this.sub((float)x, (float)y);
    }

    public Complexf sub(float x, float y) {
        return new Complexf(this.x - x, this.y - y);
    }

    public Complexf mul(double a) {
        return this.mul((float)a);
    }

    @Override
    public Complexf mul(float a) {
        return new Complexf(this.x * a, this.y * a);
    }

    public Complexf mul(Complexf c) {
        return this.mul(c.x, c.y);
    }

    public Complexf mul(double x, double y) {
        return this.mul((float)x, (float)y);
    }

    public Complexf mul(float x, float y) {
        return new Complexf(this.x * x - this.y * y, this.x * y + this.y * x);
    }

    public Complexf div(double a) {
        return this.div((float)a);
    }

    @Override
    public Complexf div(float a) {
        return new Complexf(this.x / a, this.y / a);
    }

    public Complexf div(Complexf c) {
        return this.div(c.x, c.y);
    }

    public Complexf div(double x, double y) {
        return this.div((float)x, (float)y);
    }

    public Complexf div(float x, float y) {
        float d = x * x + y * y;
        return new Complexf((this.x * x + this.y * y) / d, (this.y * x - this.x * y) / d);
    }

    public float dot(Complexf c) {
        return this.dot(c.x, c.y);
    }

    public float dot(double x, double y) {
        return this.dot((float)x, (float)y);
    }

    public float dot(float x, float y) {
        return this.x * x + this.y * y;
    }

    public Vector2f rotate(Vector2f v) {
        return this.rotate(v.getX(), v.getY());
    }

    public Vector2f rotate(double x, double y) {
        return this.rotate((float)x, (float)y);
    }

    public Vector2f rotate(float x, float y) {
        float length = this.length();
        if (Math.abs(length) < GenericMath.FLT_EPSILON) {
            throw new ArithmeticException("Cannot rotate by the zero complex");
        }
        float nx = this.x / length;
        float ny = this.y / length;
        return new Vector2f(x * nx - y * ny, y * nx + x * ny);
    }

    public Vector2f getDirection() {
        return new Vector2f(this.x, this.y).normalize();
    }

    public float getAngleRad() {
        return (float)TrigMath.atan2(this.y, this.x);
    }

    public float getAngleDeg() {
        return (float)Math.toDegrees(this.getAngleRad());
    }

    @Override
    public Complexf conjugate() {
        return new Complexf(this.x, - this.y);
    }

    @Override
    public Complexf invert() {
        float lengthSquared = this.lengthSquared();
        if (Math.abs(lengthSquared) < GenericMath.FLT_EPSILON) {
            throw new ArithmeticException("Cannot invert a complex of length zero");
        }
        return this.conjugate().div(lengthSquared);
    }

    @Override
    public float lengthSquared() {
        return this.x * this.x + this.y * this.y;
    }

    @Override
    public float length() {
        return (float)Math.sqrt(this.lengthSquared());
    }

    @Override
    public Complexf normalize() {
        float length = this.length();
        if (Math.abs(length) < GenericMath.FLT_EPSILON) {
            throw new ArithmeticException("Cannot normalize the zero complex");
        }
        return new Complexf(this.x / length, this.y / length);
    }

    public Quaternionf toQuaternion() {
        return this.toQuaternion(Vector3f.UNIT_Z);
    }

    public Quaternionf toQuaternion(Vector3f axis) {
        return this.toQuaternion(axis.getX(), axis.getY(), axis.getZ());
    }

    public Quaternionf toQuaternion(double x, double y, double z) {
        return this.toQuaternion((float)x, (float)y, (float)z);
    }

    public Quaternionf toQuaternion(float x, float y, float z) {
        return Quaternionf.fromAngleRadAxis(this.getAngleRad(), x, y, z);
    }

    @Override
    public Complexf toFloat() {
        return new Complexf(this.x, this.y);
    }

    @Override
    public Complexd toDouble() {
        return new Complexd(this.x, this.y);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Complexf)) {
            return false;
        }
        Complexf complex = (Complexf)o;
        if (Float.compare(complex.x, this.x) != 0) {
            return false;
        }
        if (Float.compare(complex.y, this.y) != 0) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        if (!this.hashed) {
            int result = this.x != 0.0f ? HashFunctions.hash(this.x) : 0;
            this.hashCode = 31 * result + (this.y != 0.0f ? HashFunctions.hash(this.y) : 0);
            this.hashed = true;
        }
        return this.hashCode;
    }

    @Override
    public int compareTo(Complexf c) {
        return (int)Math.signum(this.lengthSquared() - c.lengthSquared());
    }

    public Complexf clone() {
        return new Complexf(this);
    }

    public String toString() {
        return "(" + this.x + ", " + this.y + ")";
    }

    public static Complexf fromReal(float x) {
        return x == 0.0f ? ZERO : new Complexf(x, 0.0f);
    }

    public static Complexf fromImaginary(float y) {
        return y == 0.0f ? ZERO : new Complexf(0.0f, y);
    }

    public static Complexf from(float x, float y) {
        return x == 0.0f && y == 0.0f ? ZERO : new Complexf(x, y);
    }

    public static Complexf fromRotationTo(Vector2f from, Vector2f to) {
        return Complexf.fromAngleRad(TrigMath.acos(from.dot(to) / (from.length() * to.length())));
    }

    public static Complexf fromRotationTo(Vector3f from, Vector3f to) {
        return Complexf.fromAngleRad(TrigMath.acos(from.dot(to) / (from.length() * to.length())));
    }

    public static Complexf fromAngleDeg(double angle) {
        return Complexf.fromAngleRad(Math.toRadians(angle));
    }

    public static Complexf fromAngleRad(double angle) {
        return Complexf.fromAngleRad((float)angle);
    }

    public static Complexf fromAngleDeg(float angle) {
        return Complexf.fromAngleRad((float)Math.toRadians(angle));
    }

    public static Complexf fromAngleRad(float angle) {
        return new Complexf(TrigMath.cos(angle), TrigMath.sin(angle));
    }
}

