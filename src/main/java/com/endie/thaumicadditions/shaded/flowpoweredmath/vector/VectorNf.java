/*
 * Decompiled with CFR 0_123.
 */
package com.endie.thaumicadditions.shaded.flowpoweredmath.vector;

import java.io.Serializable;
import java.util.Arrays;

import com.endie.thaumicadditions.shaded.flowpoweredmath.GenericMath;
import com.endie.thaumicadditions.shaded.flowpoweredmath.vector.Vector2f;
import com.endie.thaumicadditions.shaded.flowpoweredmath.vector.Vector3f;
import com.endie.thaumicadditions.shaded.flowpoweredmath.vector.Vector4f;
import com.endie.thaumicadditions.shaded.flowpoweredmath.vector.VectorNd;
import com.endie.thaumicadditions.shaded.flowpoweredmath.vector.VectorNi;
import com.endie.thaumicadditions.shaded.flowpoweredmath.vector.VectorNl;
import com.endie.thaumicadditions.shaded.flowpoweredmath.vector.Vectord;
import com.endie.thaumicadditions.shaded.flowpoweredmath.vector.Vectorf;
import com.endie.thaumicadditions.shaded.flowpoweredmath.vector.Vectori;
import com.endie.thaumicadditions.shaded.flowpoweredmath.vector.Vectorl;

public class VectorNf
implements Vectorf,
Comparable<VectorNf>,
Serializable,
Cloneable {
    public static VectorNf ZERO_2 = new ImmutableZeroVectorN(0.0f, 0.0f);
    public static VectorNf ZERO_3 = new ImmutableZeroVectorN(0.0f, 0.0f, 0.0f);
    public static VectorNf ZERO_4 = new ImmutableZeroVectorN(0.0f, 0.0f, 0.0f, 0.0f);
    private static final long serialVersionUID = 1;
    private final float[] vec;

    public VectorNf(int size) {
        if (size < 2) {
            throw new IllegalArgumentException("Minimum vector size is 2");
        }
        this.vec = new float[size];
    }

    public VectorNf(Vector2f v) {
        this(v.getX(), v.getY());
    }

    public VectorNf(Vector3f v) {
        this(v.getX(), v.getY(), v.getZ());
    }

    public VectorNf(Vector4f v) {
        this(v.getX(), v.getY(), v.getZ(), v.getW());
    }

    public VectorNf(VectorNf v) {
        this(v.vec);
    }

    public /* varargs */ VectorNf(float ... v) {
        this.vec = (float[])v.clone();
    }

    public int size() {
        return this.vec.length;
    }

    public float get(int comp) {
        return this.vec[comp];
    }

    public int getFloored(int comp) {
        return GenericMath.floor(this.get(comp));
    }

    public void set(int comp, double val) {
        this.set(comp, (float)val);
    }

    public void set(int comp, float val) {
        this.vec[comp] = val;
    }

    public void setZero() {
        Arrays.fill(this.vec, 0.0f);
    }

    public VectorNf resize(int size) {
        VectorNf d = new VectorNf(size);
        System.arraycopy(this.vec, 0, d.vec, 0, Math.min(size, this.size()));
        return d;
    }

    public VectorNf add(VectorNf v) {
        return this.add(v.vec);
    }

    public /* varargs */ VectorNf add(float ... v) {
        int size = this.size();
        if (size != v.length) {
            throw new IllegalArgumentException("Vector sizes must be the same");
        }
        VectorNf d = new VectorNf(size);
        for (int comp = 0; comp < size; ++comp) {
            d.vec[comp] = this.vec[comp] + v[comp];
        }
        return d;
    }

    public VectorNf sub(VectorNf v) {
        return this.sub(v.vec);
    }

    public /* varargs */ VectorNf sub(float ... v) {
        int size = this.size();
        if (size != v.length) {
            throw new IllegalArgumentException("Vector sizes must be the same");
        }
        VectorNf d = new VectorNf(size);
        for (int comp = 0; comp < size; ++comp) {
            d.vec[comp] = this.vec[comp] - v[comp];
        }
        return d;
    }

    public VectorNf mul(double a) {
        return this.mul((float)a);
    }

    @Override
    public VectorNf mul(float a) {
        int size = this.size();
        VectorNf d = new VectorNf(size);
        for (int comp = 0; comp < size; ++comp) {
            d.vec[comp] = this.vec[comp] * a;
        }
        return d;
    }

    public VectorNf mul(VectorNf v) {
        return this.mul(v.vec);
    }

    public /* varargs */ VectorNf mul(float ... v) {
        int size = this.size();
        if (size != v.length) {
            throw new IllegalArgumentException("Vector sizes must be the same");
        }
        VectorNf d = new VectorNf(size);
        for (int comp = 0; comp < size; ++comp) {
            d.vec[comp] = this.vec[comp] * v[comp];
        }
        return d;
    }

    public VectorNf div(double a) {
        return this.div((float)a);
    }

    @Override
    public VectorNf div(float a) {
        int size = this.size();
        VectorNf d = new VectorNf(size);
        for (int comp = 0; comp < size; ++comp) {
            d.vec[comp] = this.vec[comp] / a;
        }
        return d;
    }

    public VectorNf div(VectorNf v) {
        return this.div(v.vec);
    }

    public /* varargs */ VectorNf div(float ... v) {
        int size = this.size();
        if (size != v.length) {
            throw new IllegalArgumentException("Vector sizes must be the same");
        }
        VectorNf d = new VectorNf(size);
        for (int comp = 0; comp < size; ++comp) {
            d.vec[comp] = this.vec[comp] / v[comp];
        }
        return d;
    }

    public float dot(VectorNf v) {
        return this.dot(v.vec);
    }

    public /* varargs */ float dot(float ... v) {
        int size = this.size();
        if (size != v.length) {
            throw new IllegalArgumentException("Vector sizes must be the same");
        }
        float d = 0.0f;
        for (int comp = 0; comp < size; ++comp) {
            d += this.vec[comp] * v[comp];
        }
        return d;
    }

    public VectorNf project(VectorNf v) {
        return this.project(v.vec);
    }

    public /* varargs */ VectorNf project(float ... v) {
        int size = this.size();
        if (size != v.length) {
            throw new IllegalArgumentException("Vector sizes must be the same");
        }
        float lengthSquared = 0.0f;
        for (int comp = 0; comp < size; ++comp) {
            lengthSquared += v[comp] * v[comp];
        }
        if (Math.abs(lengthSquared) < GenericMath.FLT_EPSILON) {
            throw new ArithmeticException("Cannot project onto the zero vector");
        }
        float a = this.dot(v) / lengthSquared;
        VectorNf d = new VectorNf(size);
        for (int comp = 0; comp < size; ++comp) {
            d.vec[comp] = a * v[comp];
        }
        return d;
    }

    public VectorNf pow(double pow) {
        return this.pow((float)pow);
    }

    @Override
    public VectorNf pow(float power) {
        int size = this.size();
        VectorNf d = new VectorNf(size);
        for (int comp = 0; comp < size; ++comp) {
            d.vec[comp] = (float)Math.pow(this.vec[comp], power);
        }
        return d;
    }

    @Override
    public VectorNf ceil() {
        int size = this.size();
        VectorNf d = new VectorNf(size);
        for (int comp = 0; comp < size; ++comp) {
            d.vec[comp] = (float)Math.ceil(this.vec[comp]);
        }
        return d;
    }

    @Override
    public VectorNf floor() {
        int size = this.size();
        VectorNf d = new VectorNf(size);
        for (int comp = 0; comp < size; ++comp) {
            d.vec[comp] = GenericMath.floor(this.vec[comp]);
        }
        return d;
    }

    @Override
    public VectorNf round() {
        int size = this.size();
        VectorNf d = new VectorNf(size);
        for (int comp = 0; comp < size; ++comp) {
            d.vec[comp] = Math.round(this.vec[comp]);
        }
        return d;
    }

    @Override
    public VectorNf abs() {
        int size = this.size();
        VectorNf d = new VectorNf(size);
        for (int comp = 0; comp < size; ++comp) {
            d.vec[comp] = Math.abs(this.vec[comp]);
        }
        return d;
    }

    @Override
    public VectorNf negate() {
        int size = this.size();
        VectorNf d = new VectorNf(size);
        for (int comp = 0; comp < size; ++comp) {
            d.vec[comp] = - this.vec[comp];
        }
        return d;
    }

    public VectorNf min(VectorNf v) {
        return this.min(v.vec);
    }

    public /* varargs */ VectorNf min(float ... v) {
        int size = this.size();
        if (size != v.length) {
            throw new IllegalArgumentException("Vector sizes must be the same");
        }
        VectorNf d = new VectorNf(size);
        for (int comp = 0; comp < size; ++comp) {
            d.vec[comp] = Math.min(this.vec[comp], v[comp]);
        }
        return d;
    }

    public VectorNf max(VectorNf v) {
        return this.max(v.vec);
    }

    public /* varargs */ VectorNf max(float ... v) {
        int size = this.size();
        if (size != v.length) {
            throw new IllegalArgumentException("Vector sizes must be the same");
        }
        VectorNf d = new VectorNf(size);
        for (int comp = 0; comp < size; ++comp) {
            d.vec[comp] = Math.max(this.vec[comp], v[comp]);
        }
        return d;
    }

    public float distanceSquared(VectorNf v) {
        return this.distanceSquared(v.vec);
    }

    public /* varargs */ float distanceSquared(float ... v) {
        int size = this.size();
        if (size != v.length) {
            throw new IllegalArgumentException("Vector sizes must be the same");
        }
        float d = 0.0f;
        for (int comp = 0; comp < size; ++comp) {
            float delta = this.vec[comp] - v[comp];
            d += delta * delta;
        }
        return d;
    }

    public float distance(VectorNf v) {
        return this.distance(v.vec);
    }

    public /* varargs */ float distance(float ... v) {
        return (float)Math.sqrt(this.distanceSquared(v));
    }

    @Override
    public float lengthSquared() {
        int size = this.size();
        float l = 0.0f;
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
    public VectorNf normalize() {
        float length = this.length();
        if (Math.abs(length) < GenericMath.FLT_EPSILON) {
            throw new ArithmeticException("Cannot normalize the zero vector");
        }
        int size = this.size();
        VectorNf d = new VectorNf(size);
        for (int comp = 0; comp < size; ++comp) {
            d.vec[comp] = this.vec[comp] / length;
        }
        return d;
    }

    @Override
    public int getMinAxis() {
        int axis = 0;
        float value = this.vec[axis];
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
        float value = this.vec[axis];
        int size = this.size();
        for (int comp = 1; comp < size; ++comp) {
            if (this.vec[comp] <= value) continue;
            value = this.vec[comp];
            axis = comp;
        }
        return axis;
    }

    public Vector2f toVector2() {
        return new Vector2f(this);
    }

    public Vector3f toVector3() {
        return new Vector3f(this);
    }

    public Vector4f toVector4() {
        return new Vector4f(this);
    }

    @Override
    public float[] toArray() {
        return (float[])this.vec.clone();
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
    public int compareTo(VectorNf v) {
        return (int)Math.signum(this.lengthSquared() - v.lengthSquared());
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof VectorNf)) {
            return false;
        }
        return Arrays.equals(this.vec, ((VectorNf)obj).vec);
    }

    public int hashCode() {
        return 335 + Arrays.hashCode(this.vec);
    }

    public VectorNf clone() {
        return new VectorNf(this);
    }

    public String toString() {
        return Arrays.toString(this.vec).replace('[', '(').replace(']', ')');
    }

    private static class ImmutableZeroVectorN
    extends VectorNf {
        public /* varargs */ ImmutableZeroVectorN(float ... v) {
            super(v);
        }

        @Override
        public void set(int comp, float val) {
            throw new UnsupportedOperationException("You may not alter this vector");
        }
    }

}

