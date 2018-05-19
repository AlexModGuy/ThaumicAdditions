/*
 * Decompiled with CFR 0_123.
 */
package com.endie.thaumicadditions.shaded.flowpoweredmath.vector;

import java.io.Serializable;
import java.util.Arrays;

import com.endie.thaumicadditions.shaded.flowpoweredmath.GenericMath;
import com.endie.thaumicadditions.shaded.flowpoweredmath.vector.Vector2d;
import com.endie.thaumicadditions.shaded.flowpoweredmath.vector.Vector3d;
import com.endie.thaumicadditions.shaded.flowpoweredmath.vector.Vector4d;
import com.endie.thaumicadditions.shaded.flowpoweredmath.vector.VectorNf;
import com.endie.thaumicadditions.shaded.flowpoweredmath.vector.VectorNi;
import com.endie.thaumicadditions.shaded.flowpoweredmath.vector.VectorNl;
import com.endie.thaumicadditions.shaded.flowpoweredmath.vector.Vectord;
import com.endie.thaumicadditions.shaded.flowpoweredmath.vector.Vectorf;
import com.endie.thaumicadditions.shaded.flowpoweredmath.vector.Vectori;
import com.endie.thaumicadditions.shaded.flowpoweredmath.vector.Vectorl;

public class VectorNd
implements Vectord,
Comparable<VectorNd>,
Serializable,
Cloneable {
    public static VectorNd ZERO_2 = new ImmutableZeroVectorN(0.0, 0.0);
    public static VectorNd ZERO_3 = new ImmutableZeroVectorN(0.0, 0.0, 0.0);
    public static VectorNd ZERO_4 = new ImmutableZeroVectorN(0.0, 0.0, 0.0, 0.0);
    private static final long serialVersionUID = 1;
    private final double[] vec;

    public VectorNd(int size) {
        if (size < 2) {
            throw new IllegalArgumentException("Minimum vector size is 2");
        }
        this.vec = new double[size];
    }

    public VectorNd(Vector2d v) {
        this(v.getX(), v.getY());
    }

    public VectorNd(Vector3d v) {
        this(v.getX(), v.getY(), v.getZ());
    }

    public VectorNd(Vector4d v) {
        this(v.getX(), v.getY(), v.getZ(), v.getW());
    }

    public VectorNd(VectorNd v) {
        this(v.vec);
    }

    public /* varargs */ VectorNd(double ... v) {
        this.vec = (double[])v.clone();
    }

    public int size() {
        return this.vec.length;
    }

    public double get(int comp) {
        return this.vec[comp];
    }

    public int getFloored(int comp) {
        return GenericMath.floor(this.get(comp));
    }

    public void set(int comp, float val) {
        this.set(comp, (double)val);
    }

    public void set(int comp, double val) {
        this.vec[comp] = val;
    }

    public void setZero() {
        Arrays.fill(this.vec, 0.0);
    }

    public VectorNd resize(int size) {
        VectorNd d = new VectorNd(size);
        System.arraycopy(this.vec, 0, d.vec, 0, Math.min(size, this.size()));
        return d;
    }

    public VectorNd add(VectorNd v) {
        return this.add(v.vec);
    }

    public /* varargs */ VectorNd add(double ... v) {
        int size = this.size();
        if (size != v.length) {
            throw new IllegalArgumentException("Vector sizes must be the same");
        }
        VectorNd d = new VectorNd(size);
        for (int comp = 0; comp < size; ++comp) {
            d.vec[comp] = this.vec[comp] + v[comp];
        }
        return d;
    }

    public VectorNd sub(VectorNd v) {
        return this.sub(v.vec);
    }

    public /* varargs */ VectorNd sub(double ... v) {
        int size = this.size();
        if (size != v.length) {
            throw new IllegalArgumentException("Vector sizes must be the same");
        }
        VectorNd d = new VectorNd(size);
        for (int comp = 0; comp < size; ++comp) {
            d.vec[comp] = this.vec[comp] - v[comp];
        }
        return d;
    }

    public VectorNd mul(float a) {
        return this.mul((double)a);
    }

    @Override
    public VectorNd mul(double a) {
        int size = this.size();
        VectorNd d = new VectorNd(size);
        for (int comp = 0; comp < size; ++comp) {
            d.vec[comp] = this.vec[comp] * a;
        }
        return d;
    }

    public VectorNd mul(VectorNd v) {
        return this.mul(v.vec);
    }

    public /* varargs */ VectorNd mul(double ... v) {
        int size = this.size();
        if (size != v.length) {
            throw new IllegalArgumentException("Vector sizes must be the same");
        }
        VectorNd d = new VectorNd(size);
        for (int comp = 0; comp < size; ++comp) {
            d.vec[comp] = this.vec[comp] * v[comp];
        }
        return d;
    }

    public VectorNd div(float a) {
        return this.div((double)a);
    }

    @Override
    public VectorNd div(double a) {
        int size = this.size();
        VectorNd d = new VectorNd(size);
        for (int comp = 0; comp < size; ++comp) {
            d.vec[comp] = this.vec[comp] / a;
        }
        return d;
    }

    public VectorNd div(VectorNd v) {
        return this.div(v.vec);
    }

    public /* varargs */ VectorNd div(double ... v) {
        int size = this.size();
        if (size != v.length) {
            throw new IllegalArgumentException("Vector sizes must be the same");
        }
        VectorNd d = new VectorNd(size);
        for (int comp = 0; comp < size; ++comp) {
            d.vec[comp] = this.vec[comp] / v[comp];
        }
        return d;
    }

    public double dot(VectorNd v) {
        return this.dot(v.vec);
    }

    public /* varargs */ double dot(double ... v) {
        int size = this.size();
        if (size != v.length) {
            throw new IllegalArgumentException("Vector sizes must be the same");
        }
        double d = 0.0;
        for (int comp = 0; comp < size; ++comp) {
            d += this.vec[comp] * v[comp];
        }
        return d;
    }

    public VectorNd project(VectorNd v) {
        return this.project(v.vec);
    }

    public /* varargs */ VectorNd project(double ... v) {
        int size = this.size();
        if (size != v.length) {
            throw new IllegalArgumentException("Vector sizes must be the same");
        }
        double lengthSquared = 0.0;
        for (int comp = 0; comp < size; ++comp) {
            lengthSquared += v[comp] * v[comp];
        }
        if (Math.abs(lengthSquared) < GenericMath.DBL_EPSILON) {
            throw new ArithmeticException("Cannot project onto the zero vector");
        }
        double a = this.dot(v) / lengthSquared;
        VectorNd d = new VectorNd(size);
        for (int comp = 0; comp < size; ++comp) {
            d.vec[comp] = a * v[comp];
        }
        return d;
    }

    public VectorNd pow(float pow) {
        return this.pow((double)pow);
    }

    @Override
    public VectorNd pow(double power) {
        int size = this.size();
        VectorNd d = new VectorNd(size);
        for (int comp = 0; comp < size; ++comp) {
            d.vec[comp] = Math.pow(this.vec[comp], power);
        }
        return d;
    }

    @Override
    public VectorNd ceil() {
        int size = this.size();
        VectorNd d = new VectorNd(size);
        for (int comp = 0; comp < size; ++comp) {
            d.vec[comp] = Math.ceil(this.vec[comp]);
        }
        return d;
    }

    @Override
    public VectorNd floor() {
        int size = this.size();
        VectorNd d = new VectorNd(size);
        for (int comp = 0; comp < size; ++comp) {
            d.vec[comp] = GenericMath.floor(this.vec[comp]);
        }
        return d;
    }

    @Override
    public VectorNd round() {
        int size = this.size();
        VectorNd d = new VectorNd(size);
        for (int comp = 0; comp < size; ++comp) {
            d.vec[comp] = Math.round(this.vec[comp]);
        }
        return d;
    }

    @Override
    public VectorNd abs() {
        int size = this.size();
        VectorNd d = new VectorNd(size);
        for (int comp = 0; comp < size; ++comp) {
            d.vec[comp] = Math.abs(this.vec[comp]);
        }
        return d;
    }

    @Override
    public VectorNd negate() {
        int size = this.size();
        VectorNd d = new VectorNd(size);
        for (int comp = 0; comp < size; ++comp) {
            d.vec[comp] = - this.vec[comp];
        }
        return d;
    }

    public VectorNd min(VectorNd v) {
        return this.min(v.vec);
    }

    public /* varargs */ VectorNd min(double ... v) {
        int size = this.size();
        if (size != v.length) {
            throw new IllegalArgumentException("Vector sizes must be the same");
        }
        VectorNd d = new VectorNd(size);
        for (int comp = 0; comp < size; ++comp) {
            d.vec[comp] = Math.min(this.vec[comp], v[comp]);
        }
        return d;
    }

    public VectorNd max(VectorNd v) {
        return this.max(v.vec);
    }

    public /* varargs */ VectorNd max(double ... v) {
        int size = this.size();
        if (size != v.length) {
            throw new IllegalArgumentException("Vector sizes must be the same");
        }
        VectorNd d = new VectorNd(size);
        for (int comp = 0; comp < size; ++comp) {
            d.vec[comp] = Math.max(this.vec[comp], v[comp]);
        }
        return d;
    }

    public double distanceSquared(VectorNd v) {
        return this.distanceSquared(v.vec);
    }

    public /* varargs */ double distanceSquared(double ... v) {
        int size = this.size();
        if (size != v.length) {
            throw new IllegalArgumentException("Vector sizes must be the same");
        }
        double d = 0.0;
        for (int comp = 0; comp < size; ++comp) {
            double delta = this.vec[comp] - v[comp];
            d += delta * delta;
        }
        return d;
    }

    public double distance(VectorNd v) {
        return this.distance(v.vec);
    }

    public /* varargs */ double distance(double ... v) {
        return Math.sqrt(this.distanceSquared(v));
    }

    @Override
    public double lengthSquared() {
        int size = this.size();
        double l = 0.0;
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
    public VectorNd normalize() {
        double length = this.length();
        if (Math.abs(length) < GenericMath.DBL_EPSILON) {
            throw new ArithmeticException("Cannot normalize the zero vector");
        }
        int size = this.size();
        VectorNd d = new VectorNd(size);
        for (int comp = 0; comp < size; ++comp) {
            d.vec[comp] = this.vec[comp] / length;
        }
        return d;
    }

    @Override
    public int getMinAxis() {
        int axis = 0;
        double value = this.vec[axis];
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
        double value = this.vec[axis];
        int size = this.size();
        for (int comp = 1; comp < size; ++comp) {
            if (this.vec[comp] <= value) continue;
            value = this.vec[comp];
            axis = comp;
        }
        return axis;
    }

    public Vector2d toVector2() {
        return new Vector2d(this);
    }

    public Vector3d toVector3() {
        return new Vector3d(this);
    }

    public Vector4d toVector4() {
        return new Vector4d(this);
    }

    @Override
    public double[] toArray() {
        return (double[])this.vec.clone();
    }

    @Override
    public VectorNi toInt() {
        int size = this.size();
        int[] intVec = new int[size];
        for (int comp = 0; comp < size; ++comp) {
            intVec[comp] = GenericMath.floor(this.vec[comp]);
        }
        return new VectorNi(intVec);
    }

    @Override
    public VectorNl toLong() {
        int size = this.size();
        long[] longVec = new long[size];
        for (int comp = 0; comp < size; ++comp) {
            longVec[comp] = GenericMath.floorl(this.vec[comp]);
        }
        return new VectorNl(longVec);
    }

    @Override
    public VectorNf toFloat() {
        int size = this.size();
        float[] floatVec = new float[size];
        for (int comp = 0; comp < size; ++comp) {
            floatVec[comp] = (float)this.vec[comp];
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
    public int compareTo(VectorNd v) {
        return (int)Math.signum(this.lengthSquared() - v.lengthSquared());
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof VectorNd)) {
            return false;
        }
        return Arrays.equals(this.vec, ((VectorNd)obj).vec);
    }

    public int hashCode() {
        return 335 + Arrays.hashCode(this.vec);
    }

    public VectorNd clone() {
        return new VectorNd(this);
    }

    public String toString() {
        return Arrays.toString(this.vec).replace('[', '(').replace(']', ')');
    }

    private static class ImmutableZeroVectorN
    extends VectorNd {
        public /* varargs */ ImmutableZeroVectorN(double ... v) {
            super(v);
        }

        @Override
        public void set(int comp, double val) {
            throw new UnsupportedOperationException("You may not alter this vector");
        }
    }

}

