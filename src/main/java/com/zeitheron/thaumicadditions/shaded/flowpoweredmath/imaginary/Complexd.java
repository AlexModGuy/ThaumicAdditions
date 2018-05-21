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
import com.zeitheron.thaumicadditions.shaded.flowpoweredmath.vector.Vector2d;
import com.zeitheron.thaumicadditions.shaded.flowpoweredmath.vector.Vector3d;

public class Complexd
implements Imaginaryd,
Comparable<Complexd>,
Serializable,
Cloneable {
    private static final long serialVersionUID = 1;
    public static final Complexd ZERO = new Complexd(0.0f, 0.0f);
    public static final Complexd IDENTITY = new Complexd(1.0f, 0.0f);
    private final double x;
    private final double y;
    private volatile transient boolean hashed = false;
    private volatile transient int hashCode = 0;

    public Complexd() {
        this(1.0f, 0.0f);
    }

    public Complexd(float x, float y) {
        this((double)x, (double)y);
    }

    public Complexd(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Complexd(Complexd c) {
        this.x = c.x;
        this.y = c.y;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public Complexd add(Complexd c) {
        return this.add(c.x, c.y);
    }

    public Complexd add(float x, float y) {
        return this.add((double)x, (double)y);
    }

    public Complexd add(double x, double y) {
        return new Complexd(this.x + x, this.y + y);
    }

    public Complexd sub(Complexd c) {
        return this.sub(c.x, c.y);
    }

    public Complexd sub(float x, float y) {
        return this.sub((double)x, (double)y);
    }

    public Complexd sub(double x, double y) {
        return new Complexd(this.x - x, this.y - y);
    }

    public Complexd mul(float a) {
        return this.mul((double)a);
    }

    @Override
    public Complexd mul(double a) {
        return new Complexd(this.x * a, this.y * a);
    }

    public Complexd mul(Complexd c) {
        return this.mul(c.x, c.y);
    }

    public Complexd mul(float x, float y) {
        return this.mul((double)x, (double)y);
    }

    public Complexd mul(double x, double y) {
        return new Complexd(this.x * x - this.y * y, this.x * y + this.y * x);
    }

    public Complexd div(float a) {
        return this.div((double)a);
    }

    @Override
    public Complexd div(double a) {
        return new Complexd(this.x / a, this.y / a);
    }

    public Complexd div(Complexd c) {
        return this.div(c.x, c.y);
    }

    public Complexd div(float x, float y) {
        return this.div((double)x, (double)y);
    }

    public Complexd div(double x, double y) {
        double d = x * x + y * y;
        return new Complexd((this.x * x + this.y * y) / d, (this.y * x - this.x * y) / d);
    }

    public double dot(Complexd c) {
        return this.dot(c.x, c.y);
    }

    public double dot(float x, float y) {
        return this.dot((double)x, (double)y);
    }

    public double dot(double x, double y) {
        return this.x * x + this.y * y;
    }

    public Vector2d rotate(Vector2d v) {
        return this.rotate(v.getX(), v.getY());
    }

    public Vector2d rotate(float x, float y) {
        return this.rotate((double)x, (double)y);
    }

    public Vector2d rotate(double x, double y) {
        double length = this.length();
        if (Math.abs(length) < GenericMath.DBL_EPSILON) {
            throw new ArithmeticException("Cannot rotate by the zero complex");
        }
        double nx = this.x / length;
        double ny = this.y / length;
        return new Vector2d(x * nx - y * ny, y * nx + x * ny);
    }

    public Vector2d getDirection() {
        return new Vector2d(this.x, this.y).normalize();
    }

    public double getAngleRad() {
        return TrigMath.atan2(this.y, this.x);
    }

    public double getAngleDeg() {
        return Math.toDegrees(this.getAngleRad());
    }

    @Override
    public Complexd conjugate() {
        return new Complexd(this.x, - this.y);
    }

    @Override
    public Complexd invert() {
        double lengthSquared = this.lengthSquared();
        if (Math.abs(lengthSquared) < GenericMath.DBL_EPSILON) {
            throw new ArithmeticException("Cannot invert a complex of length zero");
        }
        return this.conjugate().div(lengthSquared);
    }

    @Override
    public double lengthSquared() {
        return this.x * this.x + this.y * this.y;
    }

    @Override
    public double length() {
        return Math.sqrt(this.lengthSquared());
    }

    @Override
    public Complexd normalize() {
        double length = this.length();
        if (Math.abs(length) < GenericMath.DBL_EPSILON) {
            throw new ArithmeticException("Cannot normalize the zero complex");
        }
        return new Complexd(this.x / length, this.y / length);
    }

    public Quaterniond toQuaternion() {
        return this.toQuaternion(Vector3d.UNIT_Z);
    }

    public Quaterniond toQuaternion(Vector3d axis) {
        return this.toQuaternion(axis.getX(), axis.getY(), axis.getZ());
    }

    public Quaterniond toQuaternion(float x, float y, float z) {
        return this.toQuaternion((double)x, (double)y, (double)z);
    }

    public Quaterniond toQuaternion(double x, double y, double z) {
        return Quaterniond.fromAngleRadAxis(this.getAngleRad(), x, y, z);
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
        if (!(o instanceof Complexd)) {
            return false;
        }
        Complexd complex = (Complexd)o;
        if (Double.compare(complex.x, this.x) != 0) {
            return false;
        }
        if (Double.compare(complex.y, this.y) != 0) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        if (!this.hashed) {
            int result = this.x != 0.0 ? HashFunctions.hash(this.x) : 0;
            this.hashCode = 31 * result + (this.y != 0.0 ? HashFunctions.hash(this.y) : 0);
            this.hashed = true;
        }
        return this.hashCode;
    }

    @Override
    public int compareTo(Complexd c) {
        return (int)Math.signum(this.lengthSquared() - c.lengthSquared());
    }

    public Complexd clone() {
        return new Complexd(this);
    }

    public String toString() {
        return "(" + this.x + ", " + this.y + ")";
    }

    public static Complexd fromReal(double x) {
        return x == 0.0 ? ZERO : new Complexd(x, 0.0);
    }

    public static Complexd fromImaginary(double y) {
        return y == 0.0 ? ZERO : new Complexd(0.0, y);
    }

    public static Complexd from(double x, double y) {
        return x == 0.0 && y == 0.0 ? ZERO : new Complexd(x, y);
    }

    public static Complexd fromRotationTo(Vector2d from, Vector2d to) {
        return Complexd.fromAngleRad(TrigMath.acos(from.dot(to) / (from.length() * to.length())));
    }

    public static Complexd fromRotationTo(Vector3d from, Vector3d to) {
        return Complexd.fromAngleRad(TrigMath.acos(from.dot(to) / (from.length() * to.length())));
    }

    public static Complexd fromAngleDeg(float angle) {
        return Complexd.fromAngleRad(Math.toRadians(angle));
    }

    public static Complexd fromAngleRad(float angle) {
        return Complexd.fromAngleRad((double)angle);
    }

    public static Complexd fromAngleDeg(double angle) {
        return Complexd.fromAngleRad(Math.toRadians(angle));
    }

    public static Complexd fromAngleRad(double angle) {
        return new Complexd(TrigMath.cos(angle), TrigMath.sin(angle));
    }
}

