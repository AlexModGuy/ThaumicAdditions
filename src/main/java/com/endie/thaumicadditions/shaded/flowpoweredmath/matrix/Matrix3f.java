/*
 * Decompiled with CFR 0_123.
 */
package com.endie.thaumicadditions.shaded.flowpoweredmath.matrix;

import java.io.Serializable;

import com.endie.thaumicadditions.shaded.flowpoweredmath.GenericMath;
import com.endie.thaumicadditions.shaded.flowpoweredmath.HashFunctions;
import com.endie.thaumicadditions.shaded.flowpoweredmath.imaginary.Complexf;
import com.endie.thaumicadditions.shaded.flowpoweredmath.imaginary.Quaternionf;
import com.endie.thaumicadditions.shaded.flowpoweredmath.matrix.Matrix2f;
import com.endie.thaumicadditions.shaded.flowpoweredmath.matrix.Matrix3d;
import com.endie.thaumicadditions.shaded.flowpoweredmath.matrix.Matrix4f;
import com.endie.thaumicadditions.shaded.flowpoweredmath.matrix.MatrixNf;
import com.endie.thaumicadditions.shaded.flowpoweredmath.matrix.Matrixd;
import com.endie.thaumicadditions.shaded.flowpoweredmath.matrix.Matrixf;
import com.endie.thaumicadditions.shaded.flowpoweredmath.vector.Vector2f;
import com.endie.thaumicadditions.shaded.flowpoweredmath.vector.Vector3f;
import com.endie.thaumicadditions.shaded.flowpoweredmath.vector.Vectorf;

public class Matrix3f
implements Matrixf,
Serializable,
Cloneable {
    private static final long serialVersionUID = 1;
    public static final Matrix3f ZERO = new Matrix3f(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f);
    public static final Matrix3f IDENTITY = new Matrix3f();
    private final float m00;
    private final float m01;
    private final float m02;
    private final float m10;
    private final float m11;
    private final float m12;
    private final float m20;
    private final float m21;
    private final float m22;
    private volatile transient boolean hashed = false;
    private volatile transient int hashCode = 0;

    public Matrix3f() {
        this(1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f);
    }

    public Matrix3f(Matrix2f m) {
        this(m.get(0, 0), m.get(0, 1), 0.0f, m.get(1, 0), m.get(1, 1), 0.0f, 0.0f, 0.0f, 0.0f);
    }

    public Matrix3f(Matrix3f m) {
        this(m.m00, m.m01, m.m02, m.m10, m.m11, m.m12, m.m20, m.m21, m.m22);
    }

    public Matrix3f(Matrix4f m) {
        this(m.get(0, 0), m.get(0, 1), m.get(0, 2), m.get(1, 0), m.get(1, 1), m.get(1, 2), m.get(2, 0), m.get(2, 1), m.get(2, 2));
    }

    public Matrix3f(MatrixNf m) {
        this.m00 = m.get(0, 0);
        this.m01 = m.get(0, 1);
        this.m10 = m.get(1, 0);
        this.m11 = m.get(1, 1);
        if (m.size() > 2) {
            this.m02 = m.get(0, 2);
            this.m12 = m.get(1, 2);
            this.m20 = m.get(2, 0);
            this.m21 = m.get(2, 1);
            this.m22 = m.get(2, 2);
        } else {
            this.m02 = 0.0f;
            this.m12 = 0.0f;
            this.m20 = 0.0f;
            this.m21 = 0.0f;
            this.m22 = 0.0f;
        }
    }

    public Matrix3f(double m00, double m01, double m02, double m10, double m11, double m12, double m20, double m21, double m22) {
        this((float)m00, (float)m01, (float)m02, (float)m10, (float)m11, (float)m12, (float)m20, (float)m21, (float)m22);
    }

    public Matrix3f(float m00, float m01, float m02, float m10, float m11, float m12, float m20, float m21, float m22) {
        this.m00 = m00;
        this.m01 = m01;
        this.m02 = m02;
        this.m10 = m10;
        this.m11 = m11;
        this.m12 = m12;
        this.m20 = m20;
        this.m21 = m21;
        this.m22 = m22;
    }

    @Override
    public float get(int row, int col) {
        switch (row) {
            case 0: {
                switch (col) {
                    case 0: {
                        return this.m00;
                    }
                    case 1: {
                        return this.m01;
                    }
                    case 2: {
                        return this.m02;
                    }
                }
            }
            case 1: {
                switch (col) {
                    case 0: {
                        return this.m10;
                    }
                    case 1: {
                        return this.m11;
                    }
                    case 2: {
                        return this.m12;
                    }
                }
            }
            case 2: {
                switch (col) {
                    case 0: {
                        return this.m20;
                    }
                    case 1: {
                        return this.m21;
                    }
                    case 2: {
                        return this.m22;
                    }
                }
            }
        }
        throw new IllegalArgumentException((row < 0 || row > 2 ? "row must be greater than zero and smaller than 3. " : "") + (col < 0 || col > 2 ? "col must be greater than zero and smaller than 3." : ""));
    }

    @Override
    public Vector3f getRow(int row) {
        return new Vector3f(this.get(row, 0), this.get(row, 1), this.get(row, 2));
    }

    @Override
    public Vector3f getColumn(int col) {
        return new Vector3f(this.get(0, col), this.get(1, col), this.get(2, col));
    }

    public Matrix3f add(Matrix3f m) {
        return new Matrix3f(this.m00 + m.m00, this.m01 + m.m01, this.m02 + m.m02, this.m10 + m.m10, this.m11 + m.m11, this.m12 + m.m12, this.m20 + m.m20, this.m21 + m.m21, this.m22 + m.m22);
    }

    public Matrix3f sub(Matrix3f m) {
        return new Matrix3f(this.m00 - m.m00, this.m01 - m.m01, this.m02 - m.m02, this.m10 - m.m10, this.m11 - m.m11, this.m12 - m.m12, this.m20 - m.m20, this.m21 - m.m21, this.m22 - m.m22);
    }

    public Matrix3f mul(double a) {
        return this.mul((float)a);
    }

    @Override
    public Matrix3f mul(float a) {
        return new Matrix3f(this.m00 * a, this.m01 * a, this.m02 * a, this.m10 * a, this.m11 * a, this.m12 * a, this.m20 * a, this.m21 * a, this.m22 * a);
    }

    public Matrix3f mul(Matrix3f m) {
        return new Matrix3f(this.m00 * m.m00 + this.m01 * m.m10 + this.m02 * m.m20, this.m00 * m.m01 + this.m01 * m.m11 + this.m02 * m.m21, this.m00 * m.m02 + this.m01 * m.m12 + this.m02 * m.m22, this.m10 * m.m00 + this.m11 * m.m10 + this.m12 * m.m20, this.m10 * m.m01 + this.m11 * m.m11 + this.m12 * m.m21, this.m10 * m.m02 + this.m11 * m.m12 + this.m12 * m.m22, this.m20 * m.m00 + this.m21 * m.m10 + this.m22 * m.m20, this.m20 * m.m01 + this.m21 * m.m11 + this.m22 * m.m21, this.m20 * m.m02 + this.m21 * m.m12 + this.m22 * m.m22);
    }

    public Matrix3f div(double a) {
        return this.div((float)a);
    }

    @Override
    public Matrix3f div(float a) {
        return new Matrix3f(this.m00 / a, this.m01 / a, this.m02 / a, this.m10 / a, this.m11 / a, this.m12 / a, this.m20 / a, this.m21 / a, this.m22 / a);
    }

    public Matrix3f div(Matrix3f m) {
        return this.mul(m.invert());
    }

    public Matrix3f pow(double pow) {
        return this.pow((float)pow);
    }

    @Override
    public Matrix3f pow(float pow) {
        return new Matrix3f(Math.pow(this.m00, pow), Math.pow(this.m01, pow), Math.pow(this.m02, pow), Math.pow(this.m10, pow), Math.pow(this.m11, pow), Math.pow(this.m12, pow), Math.pow(this.m20, pow), Math.pow(this.m21, pow), Math.pow(this.m22, pow));
    }

    public Matrix3f translate(Vector2f v) {
        return this.translate(v.getX(), v.getY());
    }

    public Matrix3f translate(double x, double y) {
        return this.translate((float)x, (float)y);
    }

    public Matrix3f translate(float x, float y) {
        return Matrix3f.createTranslation(x, y).mul(this);
    }

    public Matrix3f scale(double scale) {
        return this.scale((float)scale);
    }

    public Matrix3f scale(float scale) {
        return this.scale(scale, scale, scale);
    }

    public Matrix3f scale(Vector3f v) {
        return this.scale(v.getX(), v.getY(), v.getZ());
    }

    public Matrix3f scale(double x, double y, double z) {
        return this.scale((float)x, (float)y, (float)z);
    }

    public Matrix3f scale(float x, float y, float z) {
        return Matrix3f.createScaling(x, y, z).mul(this);
    }

    public Matrix3f rotate(Complexf rot) {
        return Matrix3f.createRotation(rot).mul(this);
    }

    public Matrix3f rotate(Quaternionf rot) {
        return Matrix3f.createRotation(rot).mul(this);
    }

    public Vector3f transform(Vector3f v) {
        return this.transform(v.getX(), v.getY(), v.getZ());
    }

    public Vector3f transform(double x, double y, double z) {
        return this.transform((float)x, (float)y, (float)z);
    }

    public Vector3f transform(float x, float y, float z) {
        return new Vector3f(this.m00 * x + this.m01 * y + this.m02 * z, this.m10 * x + this.m11 * y + this.m12 * z, this.m20 * x + this.m21 * y + this.m22 * z);
    }

    @Override
    public Matrix3f floor() {
        return new Matrix3f(GenericMath.floor(this.m00), GenericMath.floor(this.m01), GenericMath.floor(this.m02), GenericMath.floor(this.m10), GenericMath.floor(this.m11), GenericMath.floor(this.m12), GenericMath.floor(this.m20), GenericMath.floor(this.m21), GenericMath.floor(this.m22));
    }

    @Override
    public Matrix3f ceil() {
        return new Matrix3f(Math.ceil(this.m00), Math.ceil(this.m01), Math.ceil(this.m02), Math.ceil(this.m10), Math.ceil(this.m11), Math.ceil(this.m12), Math.ceil(this.m20), Math.ceil(this.m21), Math.ceil(this.m22));
    }

    @Override
    public Matrix3f round() {
        return new Matrix3f(Math.round(this.m00), Math.round(this.m01), Math.round(this.m02), Math.round(this.m10), Math.round(this.m11), Math.round(this.m12), Math.round(this.m20), Math.round(this.m21), Math.round(this.m22));
    }

    @Override
    public Matrix3f abs() {
        return new Matrix3f(Math.abs(this.m00), Math.abs(this.m01), Math.abs(this.m02), Math.abs(this.m10), Math.abs(this.m11), Math.abs(this.m12), Math.abs(this.m20), Math.abs(this.m21), Math.abs(this.m22));
    }

    @Override
    public Matrix3f negate() {
        return new Matrix3f(- this.m00, - this.m01, - this.m02, - this.m10, - this.m11, - this.m12, - this.m20, - this.m21, - this.m22);
    }

    @Override
    public Matrix3f transpose() {
        return new Matrix3f(this.m00, this.m10, this.m20, this.m01, this.m11, this.m21, this.m02, this.m12, this.m22);
    }

    @Override
    public float trace() {
        return this.m00 + this.m11 + this.m22;
    }

    @Override
    public float determinant() {
        return this.m00 * (this.m11 * this.m22 - this.m12 * this.m21) - this.m01 * (this.m10 * this.m22 - this.m12 * this.m20) + this.m02 * (this.m10 * this.m21 - this.m11 * this.m20);
    }

    @Override
    public Matrix3f invert() {
        float det = this.determinant();
        if (Math.abs(det) < GenericMath.FLT_EPSILON) {
            throw new ArithmeticException("Cannot inverse a matrix with a zero determinant");
        }
        return new Matrix3f((this.m11 * this.m22 - this.m21 * this.m12) / det, (- this.m01 * this.m22 - this.m21 * this.m02) / det, (this.m01 * this.m12 - this.m02 * this.m11) / det, (- this.m10 * this.m22 - this.m20 * this.m12) / det, (this.m00 * this.m22 - this.m20 * this.m02) / det, (- this.m00 * this.m12 - this.m10 * this.m02) / det, (this.m10 * this.m21 - this.m20 * this.m11) / det, (- this.m00 * this.m21 - this.m20 * this.m01) / det, (this.m00 * this.m11 - this.m01 * this.m10) / det);
    }

    public Matrix2f toMatrix2() {
        return new Matrix2f(this);
    }

    public Matrix4f toMatrix4() {
        return new Matrix4f(this);
    }

    public MatrixNf toMatrixN() {
        return new MatrixNf(this);
    }

    public float[] toArray() {
        return this.toArray(false);
    }

    @Override
    public float[] toArray(boolean columnMajor) {
        if (columnMajor) {
            return new float[]{this.m00, this.m10, this.m20, this.m01, this.m11, this.m21, this.m02, this.m12, this.m22};
        }
        return new float[]{this.m00, this.m01, this.m02, this.m10, this.m11, this.m12, this.m20, this.m21, this.m22};
    }

    @Override
    public Matrix3f toFloat() {
        return new Matrix3f(this.m00, this.m01, this.m02, this.m10, this.m11, this.m12, this.m20, this.m21, this.m22);
    }

    @Override
    public Matrix3d toDouble() {
        return new Matrix3d(this.m00, this.m01, this.m02, this.m10, this.m11, this.m12, this.m20, this.m21, this.m22);
    }

    public String toString() {
        return "" + this.m00 + " " + this.m01 + " " + this.m02 + "\n" + this.m10 + " " + this.m11 + " " + this.m12 + "\n" + this.m20 + " " + this.m21 + " " + this.m22 + "\n";
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Matrix3f)) {
            return false;
        }
        Matrix3f matrix3 = (Matrix3f)o;
        if (Float.compare(matrix3.m00, this.m00) != 0) {
            return false;
        }
        if (Float.compare(matrix3.m01, this.m01) != 0) {
            return false;
        }
        if (Float.compare(matrix3.m02, this.m02) != 0) {
            return false;
        }
        if (Float.compare(matrix3.m10, this.m10) != 0) {
            return false;
        }
        if (Float.compare(matrix3.m11, this.m11) != 0) {
            return false;
        }
        if (Float.compare(matrix3.m12, this.m12) != 0) {
            return false;
        }
        if (Float.compare(matrix3.m20, this.m20) != 0) {
            return false;
        }
        if (Float.compare(matrix3.m21, this.m21) != 0) {
            return false;
        }
        if (Float.compare(matrix3.m22, this.m22) != 0) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        if (!this.hashed) {
            int result = this.m00 != 0.0f ? HashFunctions.hash(this.m00) : 0;
            result = 31 * result + (this.m01 != 0.0f ? HashFunctions.hash(this.m01) : 0);
            result = 31 * result + (this.m02 != 0.0f ? HashFunctions.hash(this.m02) : 0);
            result = 31 * result + (this.m10 != 0.0f ? HashFunctions.hash(this.m10) : 0);
            result = 31 * result + (this.m11 != 0.0f ? HashFunctions.hash(this.m11) : 0);
            result = 31 * result + (this.m12 != 0.0f ? HashFunctions.hash(this.m12) : 0);
            result = 31 * result + (this.m20 != 0.0f ? HashFunctions.hash(this.m20) : 0);
            result = 31 * result + (this.m21 != 0.0f ? HashFunctions.hash(this.m21) : 0);
            this.hashCode = 31 * result + (this.m22 != 0.0f ? HashFunctions.hash(this.m22) : 0);
            this.hashed = true;
        }
        return this.hashCode;
    }

    public Matrix3f clone() {
        return new Matrix3f(this);
    }

    public static Matrix3f from(float n) {
        return n == 0.0f ? ZERO : new Matrix3f(n, n, n, n, n, n, n, n, n);
    }

    public static Matrix3f from(float m00, float m01, float m02, float m10, float m11, float m12, float m20, float m21, float m22) {
        return m00 == 0.0f && m01 == 0.0f && m02 == 0.0f && m10 == 0.0f && m11 == 0.0f && m12 == 0.0f && m20 == 0.0f && m21 == 0.0f && m22 == 0.0f ? ZERO : new Matrix3f(m00, m01, m02, m10, m11, m12, m20, m21, m22);
    }

    public static Matrix3f fromDiagonal(float m00, float m11, float m22) {
        return m00 == 0.0f && m11 == 0.0f && m22 == 0.0f ? ZERO : new Matrix3f(m00, 0.0f, 0.0f, 0.0f, m11, 0.0f, 0.0f, 0.0f, m22);
    }

    public static Matrix3f createScaling(double scale) {
        return Matrix3f.createScaling((float)scale);
    }

    public static Matrix3f createScaling(float scale) {
        return Matrix3f.createScaling(scale, scale, scale);
    }

    public static Matrix3f createScaling(Vector3f v) {
        return Matrix3f.createScaling(v.getX(), v.getY(), v.getZ());
    }

    public static Matrix3f createScaling(double x, double y, double z) {
        return Matrix3f.createScaling((float)x, (float)y, (float)z);
    }

    public static Matrix3f createScaling(float x, float y, float z) {
        return new Matrix3f(x, 0.0f, 0.0f, 0.0f, y, 0.0f, 0.0f, 0.0f, z);
    }

    public static Matrix3f createTranslation(Vector2f v) {
        return Matrix3f.createTranslation(v.getX(), v.getY());
    }

    public static Matrix3f createTranslation(double x, double y) {
        return Matrix3f.createTranslation((float)x, (float)y);
    }

    public static Matrix3f createTranslation(float x, float y) {
        return new Matrix3f(1.0f, 0.0f, x, 0.0f, 1.0f, y, 0.0f, 0.0f, 1.0f);
    }

    public static Matrix3f createRotation(Complexf rot) {
        rot = rot.normalize();
        return new Matrix3f(rot.getX(), - rot.getY(), 0.0f, rot.getY(), rot.getX(), 0.0f, 0.0f, 0.0f, 1.0f);
    }

    public static Matrix3f createRotation(Quaternionf rot) {
        rot = rot.normalize();
        return new Matrix3f(1.0f - 2.0f * rot.getY() * rot.getY() - 2.0f * rot.getZ() * rot.getZ(), 2.0f * rot.getX() * rot.getY() - 2.0f * rot.getW() * rot.getZ(), 2.0f * rot.getX() * rot.getZ() + 2.0f * rot.getW() * rot.getY(), 2.0f * rot.getX() * rot.getY() + 2.0f * rot.getW() * rot.getZ(), 1.0f - 2.0f * rot.getX() * rot.getX() - 2.0f * rot.getZ() * rot.getZ(), 2.0f * rot.getY() * rot.getZ() - 2.0f * rot.getW() * rot.getX(), 2.0f * rot.getX() * rot.getZ() - 2.0f * rot.getW() * rot.getY(), 2.0f * rot.getY() * rot.getZ() + 2.0f * rot.getX() * rot.getW(), 1.0f - 2.0f * rot.getX() * rot.getX() - 2.0f * rot.getY() * rot.getY());
    }
}

