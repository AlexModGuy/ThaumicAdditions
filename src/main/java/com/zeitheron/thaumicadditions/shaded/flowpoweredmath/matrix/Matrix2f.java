/*
 * Decompiled with CFR 0_123.
 */
package com.zeitheron.thaumicadditions.shaded.flowpoweredmath.matrix;

import java.io.Serializable;

import com.zeitheron.thaumicadditions.shaded.flowpoweredmath.GenericMath;
import com.zeitheron.thaumicadditions.shaded.flowpoweredmath.HashFunctions;
import com.zeitheron.thaumicadditions.shaded.flowpoweredmath.imaginary.Complexf;
import com.zeitheron.thaumicadditions.shaded.flowpoweredmath.matrix.Matrix2d;
import com.zeitheron.thaumicadditions.shaded.flowpoweredmath.matrix.Matrix3f;
import com.zeitheron.thaumicadditions.shaded.flowpoweredmath.matrix.Matrix4f;
import com.zeitheron.thaumicadditions.shaded.flowpoweredmath.matrix.MatrixNf;
import com.zeitheron.thaumicadditions.shaded.flowpoweredmath.matrix.Matrixd;
import com.zeitheron.thaumicadditions.shaded.flowpoweredmath.matrix.Matrixf;
import com.zeitheron.thaumicadditions.shaded.flowpoweredmath.vector.Vector2f;
import com.zeitheron.thaumicadditions.shaded.flowpoweredmath.vector.Vectorf;

public class Matrix2f
implements Matrixf,
Serializable,
Cloneable {
    private static final long serialVersionUID = 1;
    public static final Matrix2f ZERO = new Matrix2f(0.0f, 0.0f, 0.0f, 0.0f);
    public static final Matrix2f IDENTITY = new Matrix2f();
    private final float m00;
    private final float m01;
    private final float m10;
    private final float m11;
    private volatile transient boolean hashed = false;
    private volatile transient int hashCode = 0;

    public Matrix2f() {
        this(1.0f, 0.0f, 0.0f, 1.0f);
    }

    public Matrix2f(Matrix2f m) {
        this(m.m00, m.m01, m.m10, m.m11);
    }

    public Matrix2f(Matrix3f m) {
        this(m.get(0, 0), m.get(0, 1), m.get(1, 0), m.get(1, 1));
    }

    public Matrix2f(Matrix4f m) {
        this(m.get(0, 0), m.get(0, 1), m.get(1, 0), m.get(1, 1));
    }

    public Matrix2f(MatrixNf m) {
        this(m.get(0, 0), m.get(0, 1), m.get(1, 0), m.get(1, 1));
    }

    public Matrix2f(double m00, double m01, double m10, double m11) {
        this((float)m00, (float)m01, (float)m10, (float)m11);
    }

    public Matrix2f(float m00, float m01, float m10, float m11) {
        this.m00 = m00;
        this.m01 = m01;
        this.m10 = m10;
        this.m11 = m11;
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
                }
            }
        }
        throw new IllegalArgumentException((row < 0 || row > 1 ? "row must be greater than zero and smaller than 2. " : "") + (col < 0 || col > 1 ? "col must be greater than zero and smaller than 2." : ""));
    }

    @Override
    public Vector2f getRow(int row) {
        return new Vector2f(this.get(row, 0), this.get(row, 1));
    }

    @Override
    public Vector2f getColumn(int col) {
        return new Vector2f(this.get(0, col), this.get(1, col));
    }

    public Matrix2f add(Matrix2f m) {
        return new Matrix2f(this.m00 + m.m00, this.m01 + m.m01, this.m10 + m.m10, this.m11 + m.m11);
    }

    public Matrix2f sub(Matrix2f m) {
        return new Matrix2f(this.m00 - m.m00, this.m01 - m.m01, this.m10 - m.m10, this.m11 - m.m11);
    }

    public Matrix2f mul(double a) {
        return this.mul((float)a);
    }

    @Override
    public Matrix2f mul(float a) {
        return new Matrix2f(this.m00 * a, this.m01 * a, this.m10 * a, this.m11 * a);
    }

    public Matrix2f mul(Matrix2f m) {
        return new Matrix2f(this.m00 * m.m00 + this.m01 * m.m10, this.m00 * m.m01 + this.m01 * m.m11, this.m10 * m.m00 + this.m11 * m.m10, this.m10 * m.m01 + this.m11 * m.m11);
    }

    public Matrix2f div(double a) {
        return this.div((float)a);
    }

    @Override
    public Matrix2f div(float a) {
        return new Matrix2f(this.m00 / a, this.m01 / a, this.m10 / a, this.m11 / a);
    }

    public Matrix2f div(Matrix2f m) {
        return this.mul(m.invert());
    }

    public Matrix2f pow(double pow) {
        return this.pow((float)pow);
    }

    @Override
    public Matrix2f pow(float pow) {
        return new Matrix2f(Math.pow(this.m00, pow), Math.pow(this.m01, pow), Math.pow(this.m10, pow), Math.pow(this.m11, pow));
    }

    public Matrix2f translate(double x) {
        return this.translate((float)x);
    }

    public Matrix2f translate(float x) {
        return Matrix2f.createTranslation(x).mul(this);
    }

    public Matrix2f scale(double scale) {
        return this.scale((float)scale);
    }

    public Matrix2f scale(float scale) {
        return this.scale(scale, scale);
    }

    public Matrix2f scale(Vector2f v) {
        return this.scale(v.getX(), v.getY());
    }

    public Matrix2f scale(double x, double y) {
        return this.scale((float)x, (float)y);
    }

    public Matrix2f scale(float x, float y) {
        return Matrix2f.createScaling(x, y).mul(this);
    }

    public Matrix2f rotate(Complexf rot) {
        return Matrix2f.createRotation(rot).mul(this);
    }

    public Vector2f transform(Vector2f v) {
        return this.transform(v.getX(), v.getY());
    }

    public Vector2f transform(double x, double y) {
        return this.transform((float)x, (float)y);
    }

    public Vector2f transform(float x, float y) {
        return new Vector2f(this.m00 * x + this.m01 * y, this.m10 * x + this.m11 * y);
    }

    @Override
    public Matrix2f floor() {
        return new Matrix2f(GenericMath.floor(this.m00), GenericMath.floor(this.m01), GenericMath.floor(this.m10), GenericMath.floor(this.m11));
    }

    @Override
    public Matrix2f ceil() {
        return new Matrix2f(Math.ceil(this.m00), Math.ceil(this.m01), Math.ceil(this.m10), Math.ceil(this.m11));
    }

    @Override
    public Matrix2f round() {
        return new Matrix2f(Math.round(this.m00), Math.round(this.m01), Math.round(this.m10), Math.round(this.m11));
    }

    @Override
    public Matrix2f abs() {
        return new Matrix2f(Math.abs(this.m00), Math.abs(this.m01), Math.abs(this.m10), Math.abs(this.m11));
    }

    @Override
    public Matrix2f negate() {
        return new Matrix2f(- this.m00, - this.m01, - this.m10, - this.m11);
    }

    @Override
    public Matrix2f transpose() {
        return new Matrix2f(this.m00, this.m10, this.m01, this.m11);
    }

    @Override
    public float trace() {
        return this.m00 + this.m11;
    }

    @Override
    public float determinant() {
        return this.m00 * this.m11 - this.m01 * this.m10;
    }

    @Override
    public Matrix2f invert() {
        float det = this.determinant();
        if (Math.abs(det) < GenericMath.FLT_EPSILON) {
            throw new ArithmeticException("Cannot inverse a matrix with a zero determinant");
        }
        return new Matrix2f(this.m11 / det, (- this.m01) / det, (- this.m10) / det, this.m00 / det);
    }

    public Matrix3f toMatrix3() {
        return new Matrix3f(this);
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
            return new float[]{this.m00, this.m10, this.m01, this.m11};
        }
        return new float[]{this.m00, this.m01, this.m10, this.m11};
    }

    @Override
    public Matrix2f toFloat() {
        return new Matrix2f(this.m00, this.m01, this.m10, this.m11);
    }

    @Override
    public Matrix2d toDouble() {
        return new Matrix2d(this.m00, this.m01, this.m10, this.m11);
    }

    public String toString() {
        return "" + this.m00 + " " + this.m01 + "\n" + this.m10 + " " + this.m11;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Matrix2f)) {
            return false;
        }
        Matrix2f matrix2 = (Matrix2f)o;
        if (Float.compare(matrix2.m00, this.m00) != 0) {
            return false;
        }
        if (Float.compare(matrix2.m01, this.m01) != 0) {
            return false;
        }
        if (Float.compare(matrix2.m10, this.m10) != 0) {
            return false;
        }
        if (Float.compare(matrix2.m11, this.m11) != 0) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        if (!this.hashed) {
            int result = this.m00 != 0.0f ? HashFunctions.hash(this.m00) : 0;
            result = 31 * result + (this.m01 != 0.0f ? HashFunctions.hash(this.m01) : 0);
            result = 31 * result + (this.m10 != 0.0f ? HashFunctions.hash(this.m10) : 0);
            this.hashCode = 31 * result + (this.m11 != 0.0f ? HashFunctions.hash(this.m11) : 0);
            this.hashed = true;
        }
        return this.hashCode;
    }

    public Matrix2f clone() {
        return new Matrix2f(this);
    }

    public static Matrix2f from(float n) {
        return n == 0.0f ? ZERO : new Matrix2f(n, n, n, n);
    }

    public static Matrix2f from(float m00, float m01, float m10, float m11) {
        return m00 == 0.0f && m01 == 0.0f && m10 == 0.0f && m11 == 0.0f ? ZERO : new Matrix2f(m00, m01, m10, m11);
    }

    public static Matrix2f fromDiagonal(float m00, float m11) {
        return m00 == 0.0f && m11 == 0.0f ? ZERO : new Matrix2f(m00, 0.0f, 0.0f, m11);
    }

    public static Matrix2f createScaling(double scale) {
        return Matrix2f.createScaling((float)scale);
    }

    public static Matrix2f createScaling(float scale) {
        return Matrix2f.createScaling(scale, scale);
    }

    public static Matrix2f createScaling(Vector2f v) {
        return Matrix2f.createScaling(v.getX(), v.getY());
    }

    public static Matrix2f createScaling(double x, double y) {
        return Matrix2f.createScaling((float)x, (float)y);
    }

    public static Matrix2f createScaling(float x, float y) {
        return new Matrix2f(x, 0.0f, 0.0f, y);
    }

    public static Matrix2f createTranslation(double x) {
        return Matrix2f.createTranslation((float)x);
    }

    public static Matrix2f createTranslation(float x) {
        return new Matrix2f(1.0f, x, 0.0f, 1.0f);
    }

    public static Matrix2f createRotation(Complexf rot) {
        rot = rot.normalize();
        return new Matrix2f(rot.getX(), - rot.getY(), rot.getY(), rot.getX());
    }
}

