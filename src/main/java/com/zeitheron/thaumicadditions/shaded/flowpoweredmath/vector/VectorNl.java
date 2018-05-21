/*
 * Decompiled with CFR 0_123.
 */
package com.zeitheron.thaumicadditions.shaded.flowpoweredmath.vector;

import java.io.Serializable;
import java.util.Arrays;

import com.zeitheron.thaumicadditions.shaded.flowpoweredmath.GenericMath;
import com.zeitheron.thaumicadditions.shaded.flowpoweredmath.vector.Vector2l;
import com.zeitheron.thaumicadditions.shaded.flowpoweredmath.vector.Vector3l;
import com.zeitheron.thaumicadditions.shaded.flowpoweredmath.vector.Vector4l;
import com.zeitheron.thaumicadditions.shaded.flowpoweredmath.vector.VectorNd;
import com.zeitheron.thaumicadditions.shaded.flowpoweredmath.vector.VectorNf;
import com.zeitheron.thaumicadditions.shaded.flowpoweredmath.vector.VectorNi;
import com.zeitheron.thaumicadditions.shaded.flowpoweredmath.vector.Vectord;
import com.zeitheron.thaumicadditions.shaded.flowpoweredmath.vector.Vectorf;
import com.zeitheron.thaumicadditions.shaded.flowpoweredmath.vector.Vectori;
import com.zeitheron.thaumicadditions.shaded.flowpoweredmath.vector.Vectorl;

public class VectorNl
implements Vectorl,
Comparable<VectorNl>,
Serializable,
Cloneable {
    public static VectorNl ZERO_2 = new ImmutableZeroVectorN(0, 0);
    public static VectorNl ZERO_3 = new ImmutableZeroVectorN(0, 0, 0);
    public static VectorNl ZERO_4 = new ImmutableZeroVectorN(0, 0, 0, 0);
    private static final long serialVersionUID = 1;
    private final long[] vec;

    public VectorNl(int size) {
        if (size < 2) {
            throw new IllegalArgumentException("Minimum vector size is 2");
        }
        this.vec = new long[size];
    }

    public VectorNl(Vector2l v) {
        this(v.getX(), v.getY());
    }

    public VectorNl(Vector3l v) {
        this(v.getX(), v.getY(), v.getZ());
    }

    public VectorNl(Vector4l v) {
        this(v.getX(), v.getY(), v.getZ(), v.getW());
    }

    public VectorNl(VectorNl v) {
        this(v.vec);
    }

    public /* varargs */ VectorNl(long ... v) {
        this.vec = (long[])v.clone();
    }

    public int size() {
        return this.vec.length;
    }

    public long get(int comp) {
        return this.vec[comp];
    }

    public void set(int comp, long val) {
        this.vec[comp] = val;
    }

    public void setZero() {
        Arrays.fill(this.vec, 0);
    }

    public VectorNl resize(int size) {
        VectorNl d = new VectorNl(size);
        System.arraycopy(this.vec, 0, d.vec, 0, Math.min(size, this.size()));
        return d;
    }

    public VectorNl add(VectorNl v) {
        return this.add(v.vec);
    }

    public /* varargs */ VectorNl add(long ... v) {
        int size = this.size();
        if (size != v.length) {
            throw new IllegalArgumentException("Vector sizes must be the same");
        }
        VectorNl d = new VectorNl(size);
        for (int comp = 0; comp < size; ++comp) {
            d.vec[comp] = this.vec[comp] + v[comp];
        }
        return d;
    }

    public VectorNl sub(VectorNl v) {
        return this.sub(v.vec);
    }

    public /* varargs */ VectorNl sub(long ... v) {
        int size = this.size();
        if (size != v.length) {
            throw new IllegalArgumentException("Vector sizes must be the same");
        }
        VectorNl d = new VectorNl(size);
        for (int comp = 0; comp < size; ++comp) {
            d.vec[comp] = this.vec[comp] - v[comp];
        }
        return d;
    }

    public VectorNl mul(double a) {
        return this.mul(GenericMath.floorl(a));
    }

    @Override
    public VectorNl mul(long a) {
        int size = this.size();
        VectorNl d = new VectorNl(size);
        for (int comp = 0; comp < size; ++comp) {
            d.vec[comp] = this.vec[comp] * a;
        }
        return d;
    }

    public VectorNl mul(VectorNl v) {
        return this.mul(v.vec);
    }

    public /* varargs */ VectorNl mul(long ... v) {
        int size = this.size();
        if (size != v.length) {
            throw new IllegalArgumentException("Vector sizes must be the same");
        }
        VectorNl d = new VectorNl(size);
        for (int comp = 0; comp < size; ++comp) {
            d.vec[comp] = this.vec[comp] * v[comp];
        }
        return d;
    }

    public VectorNl div(double a) {
        return this.div(GenericMath.floorl(a));
    }

    @Override
    public VectorNl div(long a) {
        int size = this.size();
        VectorNl d = new VectorNl(size);
        for (int comp = 0; comp < size; ++comp) {
            d.vec[comp] = this.vec[comp] / a;
        }
        return d;
    }

    public VectorNl div(VectorNl v) {
        return this.div(v.vec);
    }

    public /* varargs */ VectorNl div(long ... v) {
        int size = this.size();
        if (size != v.length) {
            throw new IllegalArgumentException("Vector sizes must be the same");
        }
        VectorNl d = new VectorNl(size);
        for (int comp = 0; comp < size; ++comp) {
            d.vec[comp] = this.vec[comp] / v[comp];
        }
        return d;
    }

    public long dot(VectorNl v) {
        return this.dot(v.vec);
    }

    public /* varargs */ long dot(long ... v) {
        int size = this.size();
        if (size != v.length) {
            throw new IllegalArgumentException("Vector sizes must be the same");
        }
        long d = 0;
        for (int comp = 0; comp < size; ++comp) {
            d += this.vec[comp] * v[comp];
        }
        return d;
    }

    public VectorNl project(VectorNl v) {
        return this.project(v.vec);
    }

    public /* varargs */ VectorNl project(long ... v) {
        int size = this.size();
        if (size != v.length) {
            throw new IllegalArgumentException("Vector sizes must be the same");
        }
        long lengthSquared = 0;
        for (int comp = 0; comp < size; ++comp) {
            lengthSquared += v[comp] * v[comp];
        }
        if (lengthSquared == 0) {
            throw new ArithmeticException("Cannot project onto the zero vector");
        }
        double a = (double)this.dot(v) / (double)lengthSquared;
        VectorNl d = new VectorNl(size);
        for (int comp = 0; comp < size; ++comp) {
            d.vec[comp] = GenericMath.floorl(a * (double)v[comp]);
        }
        return d;
    }

    public VectorNl pow(double pow) {
        return this.pow(GenericMath.floorl(pow));
    }

    @Override
    public VectorNl pow(long power) {
        int size = this.size();
        VectorNl d = new VectorNl(size);
        for (int comp = 0; comp < size; ++comp) {
            d.vec[comp] = GenericMath.floorl(Math.pow(this.vec[comp], power));
        }
        return d;
    }

    @Override
    public VectorNl abs() {
        int size = this.size();
        VectorNl d = new VectorNl(size);
        for (int comp = 0; comp < size; ++comp) {
            d.vec[comp] = Math.abs(this.vec[comp]);
        }
        return d;
    }

    @Override
    public VectorNl negate() {
        int size = this.size();
        VectorNl d = new VectorNl(size);
        for (int comp = 0; comp < size; ++comp) {
            d.vec[comp] = - this.vec[comp];
        }
        return d;
    }

    public VectorNl min(VectorNl v) {
        return this.min(v.vec);
    }

    public /* varargs */ VectorNl min(long ... v) {
        int size = this.size();
        if (size != v.length) {
            throw new IllegalArgumentException("Vector sizes must be the same");
        }
        VectorNl d = new VectorNl(size);
        for (int comp = 0; comp < size; ++comp) {
            d.vec[comp] = Math.min(this.vec[comp], v[comp]);
        }
        return d;
    }

    public VectorNl max(VectorNl v) {
        return this.max(v.vec);
    }

    public /* varargs */ VectorNl max(long ... v) {
        int size = this.size();
        if (size != v.length) {
            throw new IllegalArgumentException("Vector sizes must be the same");
        }
        VectorNl d = new VectorNl(size);
        for (int comp = 0; comp < size; ++comp) {
            d.vec[comp] = Math.max(this.vec[comp], v[comp]);
        }
        return d;
    }

    public long distanceSquared(VectorNl v) {
        return this.distanceSquared(v.vec);
    }

    public /* varargs */ long distanceSquared(long ... v) {
        int size = this.size();
        if (size != v.length) {
            throw new IllegalArgumentException("Vector sizes must be the same");
        }
        long d = 0;
        for (int comp = 0; comp < size; ++comp) {
            long delta = this.vec[comp] - v[comp];
            d += delta * delta;
        }
        return d;
    }

    public double distance(VectorNl v) {
        return this.distance(v.vec);
    }

    public /* varargs */ double distance(long ... v) {
        return Math.sqrt(this.distanceSquared(v));
    }

    @Override
    public long lengthSquared() {
        int size = this.size();
        long l = 0;
        for (int comp = 0; comp < size; ++comp) {
            l += this.vec[comp] * this.vec[comp];
        }
        return l;
    }

    @Override
    public double length() {
        return Math.sqrt(this.lengthSquared());
    }

    @Override
    public int getMinAxis() {
        int axis = 0;
        long value = this.vec[axis];
        int size = this.size();
        for (int comp = 1; comp < size; ++comp) {
            if (this.vec[comp] >= value) continue;
            value = this.vec[comp];
            axis = comp;
        }
        return axis;
    }

    @Override
    public int getMaxAxis() {
        int axis = 0;
        long value = this.vec[axis];
        int size = this.size();
        for (int comp = 1; comp < size; ++comp) {
            if (this.vec[comp] <= value) continue;
            value = this.vec[comp];
            axis = comp;
        }
        return axis;
    }

    public Vector2l toVector2() {
        return new Vector2l(this);
    }

    public Vector3l toVector3() {
        return new Vector3l(this);
    }

    public Vector4l toVector4() {
        return new Vector4l(this);
    }

    @Override
    public long[] toArray() {
        return (long[])this.vec.clone();
    }

    @Override
    public VectorNi toInt() {
        int size = this.size();
        int[] intVec = new int[size];
        for (int comp = 0; comp < size; ++comp) {
            intVec[comp] = (int)this.vec[comp];
        }
        return new VectorNi(intVec);
    }

    @Override
    public VectorNl toLong() {
        int size = this.size();
        long[] longVec = new long[size];
        for (int comp = 0; comp < size; ++comp) {
            longVec[comp] = this.vec[comp];
        }
        return new VectorNl(longVec);
    }

    @Override
    public VectorNf toFloat() {
        int size = this.size();
        float[] floatVec = new float[size];
        for (int comp = 0; comp < size; ++comp) {
            floatVec[comp] = this.vec[comp];
        }
        return new VectorNf(floatVec);
    }

    @Override
    public VectorNd toDouble() {
        int size = this.size();
        double[] doubleVec = new double[size];
        for (int comp = 0; comp < size; ++comp) {
            doubleVec[comp] = this.vec[comp];
        }
        return new VectorNd(doubleVec);
    }

    @Override
    public int compareTo(VectorNl v) {
        return (int)(this.lengthSquared() - v.lengthSquared());
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof VectorNl)) {
            return false;
        }
        return Arrays.equals(this.vec, ((VectorNl)obj).vec);
    }

    public int hashCode() {
        return 335 + Arrays.hashCode(this.vec);
    }

    public VectorNl clone() {
        return new VectorNl(this);
    }

    public String toString() {
        return Arrays.toString(this.vec).replace('[', '(').replace(']', ')');
    }

    private static class ImmutableZeroVectorN
    extends VectorNl {
        public /* varargs */ ImmutableZeroVectorN(long ... v) {
            super(v);
        }

        @Override
        public void set(int comp, long val) {
            throw new UnsupportedOperationException("You may not alter this vector");
        }
    }

}

