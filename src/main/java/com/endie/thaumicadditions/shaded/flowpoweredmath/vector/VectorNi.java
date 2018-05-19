/*
 * Decompiled with CFR 0_123.
 */
package com.endie.thaumicadditions.shaded.flowpoweredmath.vector;

import java.io.Serializable;
import java.util.Arrays;

import com.endie.thaumicadditions.shaded.flowpoweredmath.GenericMath;
import com.endie.thaumicadditions.shaded.flowpoweredmath.vector.Vector2i;
import com.endie.thaumicadditions.shaded.flowpoweredmath.vector.Vector3i;
import com.endie.thaumicadditions.shaded.flowpoweredmath.vector.Vector4i;
import com.endie.thaumicadditions.shaded.flowpoweredmath.vector.VectorNd;
import com.endie.thaumicadditions.shaded.flowpoweredmath.vector.VectorNf;
import com.endie.thaumicadditions.shaded.flowpoweredmath.vector.VectorNl;
import com.endie.thaumicadditions.shaded.flowpoweredmath.vector.Vectord;
import com.endie.thaumicadditions.shaded.flowpoweredmath.vector.Vectorf;
import com.endie.thaumicadditions.shaded.flowpoweredmath.vector.Vectori;
import com.endie.thaumicadditions.shaded.flowpoweredmath.vector.Vectorl;

public class VectorNi
implements Vectori,
Comparable<VectorNi>,
Serializable,
Cloneable {
    public static VectorNi ZERO_2 = new ImmutableZeroVectorN(0, 0);
    public static VectorNi ZERO_3 = new ImmutableZeroVectorN(0, 0, 0);
    public static VectorNi ZERO_4 = new ImmutableZeroVectorN(0, 0, 0, 0);
    private static final long serialVersionUID = 1;
    private final int[] vec;

    public VectorNi(int size) {
        if (size < 2) {
            throw new IllegalArgumentException("Minimum vector size is 2");
        }
        this.vec = new int[size];
    }

    public VectorNi(Vector2i v) {
        this(v.getX(), v.getY());
    }

    public VectorNi(Vector3i v) {
        this(v.getX(), v.getY(), v.getZ());
    }

    public VectorNi(Vector4i v) {
        this(v.getX(), v.getY(), v.getZ(), v.getW());
    }

    public VectorNi(VectorNi v) {
        this(v.vec);
    }

    public /* varargs */ VectorNi(int ... v) {
        this.vec = (int[])v.clone();
    }

    public int size() {
        return this.vec.length;
    }

    public int get(int comp) {
        return this.vec[comp];
    }

    public void set(int comp, int val) {
        this.vec[comp] = val;
    }

    public void setZero() {
        Arrays.fill(this.vec, 0);
    }

    public VectorNi resize(int size) {
        VectorNi d = new VectorNi(size);
        System.arraycopy(this.vec, 0, d.vec, 0, Math.min(size, this.size()));
        return d;
    }

    public VectorNi add(VectorNi v) {
        return this.add(v.vec);
    }

    public /* varargs */ VectorNi add(int ... v) {
        int size = this.size();
        if (size != v.length) {
            throw new IllegalArgumentException("Vector sizes must be the same");
        }
        VectorNi d = new VectorNi(size);
        for (int comp = 0; comp < size; ++comp) {
            d.vec[comp] = this.vec[comp] + v[comp];
        }
        return d;
    }

    public VectorNi sub(VectorNi v) {
        return this.sub(v.vec);
    }

    public /* varargs */ VectorNi sub(int ... v) {
        int size = this.size();
        if (size != v.length) {
            throw new IllegalArgumentException("Vector sizes must be the same");
        }
        VectorNi d = new VectorNi(size);
        for (int comp = 0; comp < size; ++comp) {
            d.vec[comp] = this.vec[comp] - v[comp];
        }
        return d;
    }

    public VectorNi mul(double a) {
        return this.mul(GenericMath.floor(a));
    }

    @Override
    public VectorNi mul(int a) {
        int size = this.size();
        VectorNi d = new VectorNi(size);
        for (int comp = 0; comp < size; ++comp) {
            d.vec[comp] = this.vec[comp] * a;
        }
        return d;
    }

    public VectorNi mul(VectorNi v) {
        return this.mul(v.vec);
    }

    public /* varargs */ VectorNi mul(int ... v) {
        int size = this.size();
        if (size != v.length) {
            throw new IllegalArgumentException("Vector sizes must be the same");
        }
        VectorNi d = new VectorNi(size);
        for (int comp = 0; comp < size; ++comp) {
            d.vec[comp] = this.vec[comp] * v[comp];
        }
        return d;
    }

    public VectorNi div(double a) {
        return this.div(GenericMath.floor(a));
    }

    @Override
    public VectorNi div(int a) {
        int size = this.size();
        VectorNi d = new VectorNi(size);
        for (int comp = 0; comp < size; ++comp) {
            d.vec[comp] = this.vec[comp] / a;
        }
        return d;
    }

    public VectorNi div(VectorNi v) {
        return this.div(v.vec);
    }

    public /* varargs */ VectorNi div(int ... v) {
        int size = this.size();
        if (size != v.length) {
            throw new IllegalArgumentException("Vector sizes must be the same");
        }
        VectorNi d = new VectorNi(size);
        for (int comp = 0; comp < size; ++comp) {
            d.vec[comp] = this.vec[comp] / v[comp];
        }
        return d;
    }

    public int dot(VectorNi v) {
        return this.dot(v.vec);
    }

    public /* varargs */ int dot(int ... v) {
        int size = this.size();
        if (size != v.length) {
            throw new IllegalArgumentException("Vector sizes must be the same");
        }
        int d = 0;
        for (int comp = 0; comp < size; ++comp) {
            d += this.vec[comp] * v[comp];
        }
        return d;
    }

    public VectorNi project(VectorNi v) {
        return this.project(v.vec);
    }

    public /* varargs */ VectorNi project(int ... v) {
        int size = this.size();
        if (size != v.length) {
            throw new IllegalArgumentException("Vector sizes must be the same");
        }
        int lengthSquared = 0;
        for (int comp = 0; comp < size; ++comp) {
            lengthSquared += v[comp] * v[comp];
        }
        if (lengthSquared == 0) {
            throw new ArithmeticException("Cannot project onto the zero vector");
        }
        float a = (float)this.dot(v) / (float)lengthSquared;
        VectorNi d = new VectorNi(size);
        for (int comp = 0; comp < size; ++comp) {
            d.vec[comp] = GenericMath.floor(a * (float)v[comp]);
        }
        return d;
    }

    public VectorNi pow(double pow) {
        return this.pow(GenericMath.floor(pow));
    }

    @Override
    public VectorNi pow(int power) {
        int size = this.size();
        VectorNi d = new VectorNi(size);
        for (int comp = 0; comp < size; ++comp) {
            d.vec[comp] = GenericMath.floor(Math.pow(this.vec[comp], power));
        }
        return d;
    }

    @Override
    public VectorNi abs() {
        int size = this.size();
        VectorNi d = new VectorNi(size);
        for (int comp = 0; comp < size; ++comp) {
            d.vec[comp] = Math.abs(this.vec[comp]);
        }
        return d;
    }

    @Override
    public VectorNi negate() {
        int size = this.size();
        VectorNi d = new VectorNi(size);
        for (int comp = 0; comp < size; ++comp) {
            d.vec[comp] = - this.vec[comp];
        }
        return d;
    }

    public VectorNi min(VectorNi v) {
        return this.min(v.vec);
    }

    public /* varargs */ VectorNi min(int ... v) {
        int size = this.size();
        if (size != v.length) {
            throw new IllegalArgumentException("Vector sizes must be the same");
        }
        VectorNi d = new VectorNi(size);
        for (int comp = 0; comp < size; ++comp) {
            d.vec[comp] = Math.min(this.vec[comp], v[comp]);
        }
        return d;
    }

    public VectorNi max(VectorNi v) {
        return this.max(v.vec);
    }

    public /* varargs */ VectorNi max(int ... v) {
        int size = this.size();
        if (size != v.length) {
            throw new IllegalArgumentException("Vector sizes must be the same");
        }
        VectorNi d = new VectorNi(size);
        for (int comp = 0; comp < size; ++comp) {
            d.vec[comp] = Math.max(this.vec[comp], v[comp]);
        }
        return d;
    }

    public int distanceSquared(VectorNi v) {
        return this.distanceSquared(v.vec);
    }

    public /* varargs */ int distanceSquared(int ... v) {
        int size = this.size();
        if (size != v.length) {
            throw new IllegalArgumentException("Vector sizes must be the same");
        }
        int d = 0;
        for (int comp = 0; comp < size; ++comp) {
            int delta = this.vec[comp] - v[comp];
            d += delta * delta;
        }
        return d;
    }

    public float distance(VectorNi v) {
        return this.distance(v.vec);
    }

    public /* varargs */ float distance(int ... v) {
        return (float)Math.sqrt(this.distanceSquared(v));
    }

    @Override
    public int lengthSquared() {
        int size = this.size();
        int l = 0;
        for (int comp = 0; comp < size; ++comp) {
            l += this.vec[comp] * this.vec[comp];
        }
        return l;
    }

    @Override
    public float length() {
        return (float)Math.sqrt(this.lengthSquared());
    }

    @Override
    public int getMinAxis() {
        int axis = 0;
        int value = this.vec[axis];
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
        int value = this.vec[axis];
        int size = this.size();
        for (int comp = 1; comp < size; ++comp) {
            if (this.vec[comp] <= value) continue;
            value = this.vec[comp];
            axis = comp;
        }
        return axis;
    }

    public Vector2i toVector2() {
        return new Vector2i(this);
    }

    public Vector3i toVector3() {
        return new Vector3i(this);
    }

    public Vector4i toVector4() {
        return new Vector4i(this);
    }

    @Override
    public int[] toArray() {
        return (int[])this.vec.clone();
    }

    @Override
    public VectorNi toInt() {
        int size = this.size();
        int[] intVec = new int[size];
        for (int comp = 0; comp < size; ++comp) {
            intVec[comp] = this.vec[comp];
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
    public int compareTo(VectorNi v) {
        return this.lengthSquared() - v.lengthSquared();
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof VectorNi)) {
            return false;
        }
        return Arrays.equals(this.vec, ((VectorNi)obj).vec);
    }

    public int hashCode() {
        return 335 + Arrays.hashCode(this.vec);
    }

    public VectorNi clone() {
        return new VectorNi(this);
    }

    public String toString() {
        return Arrays.toString(this.vec).replace('[', '(').replace(']', ')');
    }

    private static class ImmutableZeroVectorN
    extends VectorNi {
        public /* varargs */ ImmutableZeroVectorN(int ... v) {
            super(v);
        }

        @Override
        public void set(int comp, int val) {
            throw new UnsupportedOperationException("You may not alter this vector");
        }
    }

}

