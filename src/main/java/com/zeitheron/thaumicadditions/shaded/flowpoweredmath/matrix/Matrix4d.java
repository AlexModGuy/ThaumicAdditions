/*
 * Decompiled with CFR 0_123.
 */
package com.zeitheron.thaumicadditions.shaded.flowpoweredmath.matrix;

import java.io.Serializable;

import com.zeitheron.thaumicadditions.shaded.flowpoweredmath.GenericMath;
import com.zeitheron.thaumicadditions.shaded.flowpoweredmath.HashFunctions;
import com.zeitheron.thaumicadditions.shaded.flowpoweredmath.TrigMath;
import com.zeitheron.thaumicadditions.shaded.flowpoweredmath.imaginary.Complexd;
import com.zeitheron.thaumicadditions.shaded.flowpoweredmath.imaginary.Quaterniond;
import com.zeitheron.thaumicadditions.shaded.flowpoweredmath.matrix.Matrix2d;
import com.zeitheron.thaumicadditions.shaded.flowpoweredmath.matrix.Matrix3d;
import com.zeitheron.thaumicadditions.shaded.flowpoweredmath.matrix.Matrix4f;
import com.zeitheron.thaumicadditions.shaded.flowpoweredmath.matrix.MatrixNd;
import com.zeitheron.thaumicadditions.shaded.flowpoweredmath.matrix.Matrixd;
import com.zeitheron.thaumicadditions.shaded.flowpoweredmath.matrix.Matrixf;
import com.zeitheron.thaumicadditions.shaded.flowpoweredmath.vector.Vector3d;
import com.zeitheron.thaumicadditions.shaded.flowpoweredmath.vector.Vector4d;
import com.zeitheron.thaumicadditions.shaded.flowpoweredmath.vector.Vectord;

public class Matrix4d
implements Matrixd,
Serializable,
Cloneable {
    private static final long serialVersionUID = 1;
    public static final Matrix4d ZERO = new Matrix4d(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f);
    public static final Matrix4d IDENTITY = new Matrix4d();
    private final double m00;
    private final double m01;
    private final double m02;
    private final double m03;
    private final double m10;
    private final double m11;
    private final double m12;
    private final double m13;
    private final double m20;
    private final double m21;
    private final double m22;
    private final double m23;
    private final double m30;
    private final double m31;
    private final double m32;
    private final double m33;
    private volatile transient boolean hashed = false;
    private volatile transient int hashCode = 0;

    public Matrix4d() {
        this(1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f);
    }

    public Matrix4d(Matrix2d m) {
        this(m.get(0, 0), m.get(0, 1), 0.0, 0.0, m.get(1, 0), m.get(1, 1), 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
    }

    public Matrix4d(Matrix3d m) {
        this(m.get(0, 0), m.get(0, 1), m.get(0, 2), 0.0, m.get(1, 0), m.get(1, 1), m.get(1, 2), 0.0, m.get(2, 0), m.get(2, 1), m.get(2, 2), 0.0, 0.0, 0.0, 0.0, 0.0);
    }

    public Matrix4d(Matrix4d m) {
        this(m.m00, m.m01, m.m02, m.m03, m.m10, m.m11, m.m12, m.m13, m.m20, m.m21, m.m22, m.m23, m.m30, m.m31, m.m32, m.m33);
    }

    public Matrix4d(MatrixNd m) {
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
            if (m.size() > 3) {
                this.m03 = m.get(0, 3);
                this.m13 = m.get(1, 3);
                this.m23 = m.get(2, 3);
                this.m30 = m.get(3, 0);
                this.m31 = m.get(3, 1);
                this.m32 = m.get(3, 2);
                this.m33 = m.get(3, 3);
            } else {
                this.m03 = 0.0;
                this.m13 = 0.0;
                this.m23 = 0.0;
                this.m30 = 0.0;
                this.m31 = 0.0;
                this.m32 = 0.0;
                this.m33 = 0.0;
            }
        } else {
            this.m02 = 0.0;
            this.m12 = 0.0;
            this.m20 = 0.0;
            this.m21 = 0.0;
            this.m22 = 0.0;
            this.m03 = 0.0;
            this.m13 = 0.0;
            this.m23 = 0.0;
            this.m30 = 0.0;
            this.m31 = 0.0;
            this.m32 = 0.0;
            this.m33 = 0.0;
        }
    }

    public Matrix4d(float m00, float m01, float m02, float m03, float m10, float m11, float m12, float m13, float m20, float m21, float m22, float m23, float m30, float m31, float m32, float m33) {
        this((double)m00, (double)m01, (double)m02, (double)m03, (double)m10, (double)m11, (double)m12, (double)m13, (double)m20, (double)m21, (double)m22, (double)m23, (double)m30, (double)m31, (double)m32, (double)m33);
    }

    public Matrix4d(double m00, double m01, double m02, double m03, double m10, double m11, double m12, double m13, double m20, double m21, double m22, double m23, double m30, double m31, double m32, double m33) {
        this.m00 = m00;
        this.m01 = m01;
        this.m02 = m02;
        this.m03 = m03;
        this.m10 = m10;
        this.m11 = m11;
        this.m12 = m12;
        this.m13 = m13;
        this.m20 = m20;
        this.m21 = m21;
        this.m22 = m22;
        this.m23 = m23;
        this.m30 = m30;
        this.m31 = m31;
        this.m32 = m32;
        this.m33 = m33;
    }

    @Override
    public double get(int row, int col) {
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
                    case 3: {
                        return this.m03;
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
                    case 3: {
                        return this.m13;
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
                    case 3: {
                        return this.m23;
                    }
                }
            }
            case 3: {
                switch (col) {
                    case 0: {
                        return this.m30;
                    }
                    case 1: {
                        return this.m31;
                    }
                    case 2: {
                        return this.m32;
                    }
                    case 3: {
                        return this.m33;
                    }
                }
            }
        }
        throw new IllegalArgumentException((row < 0 || row > 2 ? "row must be greater than zero and smaller than 3. " : "") + (col < 0 || col > 2 ? "col must be greater than zero and smaller than 3." : ""));
    }

    @Override
    public Vector4d getRow(int row) {
        return new Vector4d(this.get(row, 0), this.get(row, 1), this.get(row, 2), this.get(row, 3));
    }

    @Override
    public Vector4d getColumn(int col) {
        return new Vector4d(this.get(0, col), this.get(1, col), this.get(2, col), this.get(3, col));
    }

    public Matrix4d add(Matrix4d m) {
        return new Matrix4d(this.m00 + m.m00, this.m01 + m.m01, this.m02 + m.m02, this.m03 + m.m03, this.m10 + m.m10, this.m11 + m.m11, this.m12 + m.m12, this.m13 + m.m13, this.m20 + m.m20, this.m21 + m.m21, this.m22 + m.m22, this.m23 + m.m23, this.m30 + m.m30, this.m31 + m.m31, this.m32 + m.m32, this.m33 + m.m33);
    }

    public Matrix4d sub(Matrix4d m) {
        return new Matrix4d(this.m00 - m.m00, this.m01 - m.m01, this.m02 - m.m02, this.m03 - m.m03, this.m10 - m.m10, this.m11 - m.m11, this.m12 - m.m12, this.m13 - m.m13, this.m20 - m.m20, this.m21 - m.m21, this.m22 - m.m22, this.m23 - m.m23, this.m30 - m.m30, this.m31 - m.m31, this.m32 - m.m32, this.m33 - m.m33);
    }

    public Matrix4d mul(float a) {
        return this.mul((double)a);
    }

    @Override
    public Matrix4d mul(double a) {
        return new Matrix4d(this.m00 * a, this.m01 * a, this.m02 * a, this.m03 * a, this.m10 * a, this.m11 * a, this.m12 * a, this.m13 * a, this.m20 * a, this.m21 * a, this.m22 * a, this.m23 * a, this.m30 * a, this.m31 * a, this.m32 * a, this.m33 * a);
    }

    public Matrix4d mul(Matrix4d m) {
        return new Matrix4d(this.m00 * m.m00 + this.m01 * m.m10 + this.m02 * m.m20 + this.m03 * m.m30, this.m00 * m.m01 + this.m01 * m.m11 + this.m02 * m.m21 + this.m03 * m.m31, this.m00 * m.m02 + this.m01 * m.m12 + this.m02 * m.m22 + this.m03 * m.m32, this.m00 * m.m03 + this.m01 * m.m13 + this.m02 * m.m23 + this.m03 * m.m33, this.m10 * m.m00 + this.m11 * m.m10 + this.m12 * m.m20 + this.m13 * m.m30, this.m10 * m.m01 + this.m11 * m.m11 + this.m12 * m.m21 + this.m13 * m.m31, this.m10 * m.m02 + this.m11 * m.m12 + this.m12 * m.m22 + this.m13 * m.m32, this.m10 * m.m03 + this.m11 * m.m13 + this.m12 * m.m23 + this.m13 * m.m33, this.m20 * m.m00 + this.m21 * m.m10 + this.m22 * m.m20 + this.m23 * m.m30, this.m20 * m.m01 + this.m21 * m.m11 + this.m22 * m.m21 + this.m23 * m.m31, this.m20 * m.m02 + this.m21 * m.m12 + this.m22 * m.m22 + this.m23 * m.m32, this.m20 * m.m03 + this.m21 * m.m13 + this.m22 * m.m23 + this.m23 * m.m33, this.m30 * m.m00 + this.m31 * m.m10 + this.m32 * m.m20 + this.m33 * m.m30, this.m30 * m.m01 + this.m31 * m.m11 + this.m32 * m.m21 + this.m33 * m.m31, this.m30 * m.m02 + this.m31 * m.m12 + this.m32 * m.m22 + this.m33 * m.m32, this.m30 * m.m03 + this.m31 * m.m13 + this.m32 * m.m23 + this.m33 * m.m33);
    }

    public Matrix4d div(float a) {
        return this.div((double)a);
    }

    @Override
    public Matrix4d div(double a) {
        return new Matrix4d(this.m00 / a, this.m01 / a, this.m02 / a, this.m03 / a, this.m10 / a, this.m11 / a, this.m12 / a, this.m13 / a, this.m20 / a, this.m21 / a, this.m22 / a, this.m23 / a, this.m30 / a, this.m31 / a, this.m32 / a, this.m33 / a);
    }

    public Matrix4d div(Matrix4d m) {
        return this.mul(m.invert());
    }

    public Matrix4d pow(float pow) {
        return this.pow((double)pow);
    }

    @Override
    public Matrix4d pow(double pow) {
        return new Matrix4d(Math.pow(this.m00, pow), Math.pow(this.m01, pow), Math.pow(this.m02, pow), Math.pow(this.m03, pow), Math.pow(this.m10, pow), Math.pow(this.m11, pow), Math.pow(this.m12, pow), Math.pow(this.m13, pow), Math.pow(this.m20, pow), Math.pow(this.m21, pow), Math.pow(this.m22, pow), Math.pow(this.m23, pow), Math.pow(this.m30, pow), Math.pow(this.m31, pow), Math.pow(this.m32, pow), Math.pow(this.m33, pow));
    }

    public Matrix4d translate(Vector3d v) {
        return this.translate(v.getX(), v.getY(), v.getZ());
    }

    public Matrix4d translate(float x, float y, float z) {
        return this.translate((double)x, (double)y, (double)z);
    }

    public Matrix4d translate(double x, double y, double z) {
        return Matrix4d.createTranslation(x, y, z).mul(this);
    }

    public Matrix4d scale(float scale) {
        return this.scale((double)scale);
    }

    public Matrix4d scale(double scale) {
        return this.scale(scale, scale, scale, scale);
    }

    public Matrix4d scale(Vector4d v) {
        return this.scale(v.getX(), v.getY(), v.getZ(), v.getW());
    }

    public Matrix4d scale(float x, float y, float z, float w) {
        return this.scale((double)x, (double)y, (double)z, (double)w);
    }

    public Matrix4d scale(double x, double y, double z, double w) {
        return Matrix4d.createScaling(x, y, z, w).mul(this);
    }

    public Matrix4d rotate(Complexd rot) {
        return Matrix4d.createRotation(rot).mul(this);
    }

    public Matrix4d rotate(Quaterniond rot) {
        return Matrix4d.createRotation(rot).mul(this);
    }

    public Vector4d transform(Vector4d v) {
        return this.transform(v.getX(), v.getY(), v.getZ(), v.getW());
    }

    public Vector4d transform(float x, float y, float z, float w) {
        return this.transform((double)x, (double)y, (double)z, (double)w);
    }

    public Vector4d transform(double x, double y, double z, double w) {
        return new Vector4d(this.m00 * x + this.m01 * y + this.m02 * z + this.m03 * w, this.m10 * x + this.m11 * y + this.m12 * z + this.m13 * w, this.m20 * x + this.m21 * y + this.m22 * z + this.m23 * w, this.m30 * x + this.m31 * y + this.m32 * z + this.m33 * w);
    }

    @Override
    public Matrix4d floor() {
        return new Matrix4d(GenericMath.floor(this.m00), GenericMath.floor(this.m01), GenericMath.floor(this.m02), GenericMath.floor(this.m03), GenericMath.floor(this.m10), GenericMath.floor(this.m11), GenericMath.floor(this.m12), GenericMath.floor(this.m13), GenericMath.floor(this.m20), GenericMath.floor(this.m21), GenericMath.floor(this.m22), GenericMath.floor(this.m23), GenericMath.floor(this.m30), GenericMath.floor(this.m31), GenericMath.floor(this.m32), GenericMath.floor(this.m33));
    }

    @Override
    public Matrix4d ceil() {
        return new Matrix4d(Math.ceil(this.m00), Math.ceil(this.m01), Math.ceil(this.m02), Math.ceil(this.m03), Math.ceil(this.m10), Math.ceil(this.m11), Math.ceil(this.m12), Math.ceil(this.m13), Math.ceil(this.m20), Math.ceil(this.m21), Math.ceil(this.m22), Math.ceil(this.m23), Math.ceil(this.m30), Math.ceil(this.m31), Math.ceil(this.m32), Math.ceil(this.m33));
    }

    @Override
    public Matrix4d round() {
        return new Matrix4d(Math.round(this.m00), Math.round(this.m01), Math.round(this.m02), Math.round(this.m03), Math.round(this.m10), Math.round(this.m11), Math.round(this.m12), Math.round(this.m13), Math.round(this.m20), Math.round(this.m21), Math.round(this.m22), Math.round(this.m23), Math.round(this.m30), Math.round(this.m31), Math.round(this.m32), Math.round(this.m33));
    }

    @Override
    public Matrix4d abs() {
        return new Matrix4d(Math.abs(this.m00), Math.abs(this.m01), Math.abs(this.m02), Math.abs(this.m03), Math.abs(this.m10), Math.abs(this.m11), Math.abs(this.m12), Math.abs(this.m13), Math.abs(this.m20), Math.abs(this.m21), Math.abs(this.m22), Math.abs(this.m23), Math.abs(this.m30), Math.abs(this.m31), Math.abs(this.m32), Math.abs(this.m33));
    }

    @Override
    public Matrix4d negate() {
        return new Matrix4d(- this.m00, - this.m01, - this.m02, - this.m03, - this.m10, - this.m11, - this.m12, - this.m13, - this.m20, - this.m21, - this.m22, - this.m23, - this.m30, - this.m31, - this.m32, - this.m33);
    }

    @Override
    public Matrix4d transpose() {
        return new Matrix4d(this.m00, this.m10, this.m20, this.m30, this.m01, this.m11, this.m21, this.m31, this.m02, this.m12, this.m22, this.m32, this.m03, this.m13, this.m23, this.m33);
    }

    @Override
    public double trace() {
        return this.m00 + this.m11 + this.m22 + this.m33;
    }

    @Override
    public double determinant() {
        return this.m00 * (this.m11 * this.m22 * this.m33 + this.m21 * this.m32 * this.m13 + this.m31 * this.m12 * this.m23 - this.m31 * this.m22 * this.m13 - this.m11 * this.m32 * this.m23 - this.m21 * this.m12 * this.m33) - this.m10 * (this.m01 * this.m22 * this.m33 + this.m21 * this.m32 * this.m03 + this.m31 * this.m02 * this.m23 - this.m31 * this.m22 * this.m03 - this.m01 * this.m32 * this.m23 - this.m21 * this.m02 * this.m33) + this.m20 * (this.m01 * this.m12 * this.m33 + this.m11 * this.m32 * this.m03 + this.m31 * this.m02 * this.m13 - this.m31 * this.m12 * this.m03 - this.m01 * this.m32 * this.m13 - this.m11 * this.m02 * this.m33) - this.m30 * (this.m01 * this.m12 * this.m23 + this.m11 * this.m22 * this.m03 + this.m21 * this.m02 * this.m13 - this.m21 * this.m12 * this.m03 - this.m01 * this.m22 * this.m13 - this.m11 * this.m02 * this.m23);
    }

    @Override
    public Matrix4d invert() {
        double det = this.determinant();
        if (Math.abs(det) < GenericMath.DBL_EPSILON) {
            throw new ArithmeticException("Cannot inverse a matrix with a zero determinant");
        }
        return new Matrix4d(Matrix4d.det3(this.m11, this.m21, this.m31, this.m12, this.m22, this.m32, this.m13, this.m23, this.m33) / det, (- Matrix4d.det3(this.m01, this.m21, this.m31, this.m02, this.m22, this.m32, this.m03, this.m23, this.m33)) / det, Matrix4d.det3(this.m01, this.m11, this.m31, this.m02, this.m12, this.m32, this.m03, this.m13, this.m33) / det, (- Matrix4d.det3(this.m01, this.m11, this.m21, this.m02, this.m12, this.m22, this.m03, this.m13, this.m23)) / det, (- Matrix4d.det3(this.m10, this.m20, this.m30, this.m12, this.m22, this.m32, this.m13, this.m23, this.m33)) / det, Matrix4d.det3(this.m00, this.m20, this.m30, this.m02, this.m22, this.m32, this.m03, this.m23, this.m33) / det, (- Matrix4d.det3(this.m00, this.m10, this.m30, this.m02, this.m12, this.m32, this.m03, this.m13, this.m33)) / det, Matrix4d.det3(this.m00, this.m10, this.m20, this.m02, this.m12, this.m22, this.m03, this.m13, this.m23) / det, Matrix4d.det3(this.m10, this.m20, this.m30, this.m11, this.m21, this.m31, this.m13, this.m23, this.m33) / det, (- Matrix4d.det3(this.m00, this.m20, this.m30, this.m01, this.m21, this.m31, this.m03, this.m23, this.m33)) / det, Matrix4d.det3(this.m00, this.m10, this.m30, this.m01, this.m11, this.m31, this.m03, this.m13, this.m33) / det, (- Matrix4d.det3(this.m00, this.m10, this.m20, this.m01, this.m11, this.m21, this.m03, this.m13, this.m23)) / det, (- Matrix4d.det3(this.m10, this.m20, this.m30, this.m11, this.m21, this.m31, this.m12, this.m22, this.m32)) / det, Matrix4d.det3(this.m00, this.m20, this.m30, this.m01, this.m21, this.m31, this.m02, this.m22, this.m32) / det, (- Matrix4d.det3(this.m00, this.m10, this.m30, this.m01, this.m11, this.m31, this.m02, this.m12, this.m32)) / det, Matrix4d.det3(this.m00, this.m10, this.m20, this.m01, this.m11, this.m21, this.m02, this.m12, this.m22) / det);
    }

    public Matrix2d toMatrix2() {
        return new Matrix2d(this);
    }

    public Matrix3d toMatrix3() {
        return new Matrix3d(this);
    }

    public MatrixNd toMatrixN() {
        return new MatrixNd(this);
    }

    public double[] toArray() {
        return this.toArray(false);
    }

    @Override
    public double[] toArray(boolean columnMajor) {
        if (columnMajor) {
            return new double[]{this.m00, this.m10, this.m20, this.m30, this.m01, this.m11, this.m21, this.m31, this.m02, this.m12, this.m22, this.m32, this.m03, this.m13, this.m23, this.m33};
        }
        return new double[]{this.m00, this.m01, this.m02, this.m03, this.m10, this.m11, this.m12, this.m13, this.m20, this.m21, this.m22, this.m23, this.m30, this.m31, this.m32, this.m33};
    }

    @Override
    public Matrix4f toFloat() {
        return new Matrix4f(this.m00, this.m01, this.m02, this.m03, this.m10, this.m11, this.m12, this.m13, this.m20, this.m21, this.m22, this.m23, this.m30, this.m31, this.m32, this.m33);
    }

    @Override
    public Matrix4d toDouble() {
        return new Matrix4d(this.m00, this.m01, this.m02, this.m03, this.m10, this.m11, this.m12, this.m13, this.m20, this.m21, this.m22, this.m23, this.m30, this.m31, this.m32, this.m33);
    }

    public String toString() {
        return "" + this.m00 + " " + this.m01 + " " + this.m02 + " " + this.m03 + "\n" + this.m10 + " " + this.m11 + " " + this.m12 + " " + this.m13 + "\n" + this.m20 + " " + this.m21 + " " + this.m22 + " " + this.m23 + "\n" + this.m30 + " " + this.m31 + " " + this.m32 + " " + this.m33 + "\n";
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Matrix4d)) {
            return false;
        }
        Matrix4d matrix4 = (Matrix4d)o;
        if (Double.compare(matrix4.m00, this.m00) != 0) {
            return false;
        }
        if (Double.compare(matrix4.m01, this.m01) != 0) {
            return false;
        }
        if (Double.compare(matrix4.m02, this.m02) != 0) {
            return false;
        }
        if (Double.compare(matrix4.m03, this.m03) != 0) {
            return false;
        }
        if (Double.compare(matrix4.m10, this.m10) != 0) {
            return false;
        }
        if (Double.compare(matrix4.m11, this.m11) != 0) {
            return false;
        }
        if (Double.compare(matrix4.m12, this.m12) != 0) {
            return false;
        }
        if (Double.compare(matrix4.m13, this.m13) != 0) {
            return false;
        }
        if (Double.compare(matrix4.m20, this.m20) != 0) {
            return false;
        }
        if (Double.compare(matrix4.m21, this.m21) != 0) {
            return false;
        }
        if (Double.compare(matrix4.m22, this.m22) != 0) {
            return false;
        }
        if (Double.compare(matrix4.m23, this.m23) != 0) {
            return false;
        }
        if (Double.compare(matrix4.m30, this.m30) != 0) {
            return false;
        }
        if (Double.compare(matrix4.m31, this.m31) != 0) {
            return false;
        }
        if (Double.compare(matrix4.m32, this.m32) != 0) {
            return false;
        }
        if (Double.compare(matrix4.m33, this.m33) != 0) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        if (!this.hashed) {
            int result = this.m00 != 0.0 ? HashFunctions.hash(this.m00) : 0;
            result = 31 * result + (this.m01 != 0.0 ? HashFunctions.hash(this.m01) : 0);
            result = 31 * result + (this.m02 != 0.0 ? HashFunctions.hash(this.m02) : 0);
            result = 31 * result + (this.m03 != 0.0 ? HashFunctions.hash(this.m03) : 0);
            result = 31 * result + (this.m10 != 0.0 ? HashFunctions.hash(this.m10) : 0);
            result = 31 * result + (this.m11 != 0.0 ? HashFunctions.hash(this.m11) : 0);
            result = 31 * result + (this.m12 != 0.0 ? HashFunctions.hash(this.m12) : 0);
            result = 31 * result + (this.m13 != 0.0 ? HashFunctions.hash(this.m13) : 0);
            result = 31 * result + (this.m20 != 0.0 ? HashFunctions.hash(this.m20) : 0);
            result = 31 * result + (this.m21 != 0.0 ? HashFunctions.hash(this.m21) : 0);
            result = 31 * result + (this.m22 != 0.0 ? HashFunctions.hash(this.m22) : 0);
            result = 31 * result + (this.m23 != 0.0 ? HashFunctions.hash(this.m23) : 0);
            result = 31 * result + (this.m30 != 0.0 ? HashFunctions.hash(this.m30) : 0);
            result = 31 * result + (this.m31 != 0.0 ? HashFunctions.hash(this.m31) : 0);
            result = 31 * result + (this.m32 != 0.0 ? HashFunctions.hash(this.m32) : 0);
            this.hashCode = 31 * result + (this.m33 != 0.0 ? HashFunctions.hash(this.m33) : 0);
            this.hashed = true;
        }
        return this.hashCode;
    }

    public Matrix4d clone() {
        return new Matrix4d(this);
    }

    public static Matrix4d from(double n) {
        return n == 0.0 ? ZERO : new Matrix4d(n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n);
    }

    public static Matrix4d from(double m00, double m01, double m02, double m03, double m10, double m11, double m12, double m13, double m20, double m21, double m22, double m23, double m30, double m31, double m32, double m33) {
        return m00 == 0.0 && m01 == 0.0 && m02 == 0.0 && m03 == 0.0 && m10 == 0.0 && m11 == 0.0 && m12 == 0.0 && m13 == 0.0 && m20 == 0.0 && m21 == 0.0 && m22 == 0.0 && m23 == 0.0 && m30 == 0.0 && m31 == 0.0 && m32 == 0.0 && m33 == 0.0 ? ZERO : new Matrix4d(m00, m01, m02, m03, m10, m11, m12, m13, m20, m21, m22, m23, m30, m31, m32, m33);
    }

    public static Matrix4d fromDiagonal(double m00, double m11, double m22, double m33) {
        return m00 == 0.0 && m11 == 0.0 && m22 == 0.0 && m33 == 0.0 ? ZERO : new Matrix4d(m00, 0.0, 0.0, 0.0, 0.0, m11, 0.0, 0.0, 0.0, 0.0, m22, 0.0, 0.0, 0.0, 0.0, m33);
    }

    public static Matrix4d createScaling(float scale) {
        return Matrix4d.createScaling((double)scale);
    }

    public static Matrix4d createScaling(double scale) {
        return Matrix4d.createScaling(scale, scale, scale, scale);
    }

    public static Matrix4d createScaling(Vector4d v) {
        return Matrix4d.createScaling(v.getX(), v.getY(), v.getZ(), v.getW());
    }

    public static Matrix4d createScaling(float x, float y, float z, float w) {
        return Matrix4d.createScaling((double)x, (double)y, (double)z, (double)w);
    }

    public static Matrix4d createScaling(double x, double y, double z, double w) {
        return new Matrix4d(x, 0.0, 0.0, 0.0, 0.0, y, 0.0, 0.0, 0.0, 0.0, z, 0.0, 0.0, 0.0, 0.0, w);
    }

    public static Matrix4d createTranslation(Vector3d v) {
        return Matrix4d.createTranslation(v.getX(), v.getY(), v.getZ());
    }

    public static Matrix4d createTranslation(float x, float y, float z) {
        return Matrix4d.createTranslation((double)x, (double)y, (double)z);
    }

    public static Matrix4d createTranslation(double x, double y, double z) {
        return new Matrix4d(1.0, 0.0, 0.0, x, 0.0, 1.0, 0.0, y, 0.0, 0.0, 1.0, z, 0.0, 0.0, 0.0, 1.0);
    }

    public static Matrix4d createRotation(Complexd rot) {
        rot = rot.normalize();
        return new Matrix4d(rot.getX(), - rot.getY(), 0.0, 0.0, rot.getY(), rot.getX(), 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 1.0);
    }

    public static Matrix4d createRotation(Quaterniond rot) {
        rot = rot.normalize();
        return new Matrix4d(1.0 - 2.0 * rot.getY() * rot.getY() - 2.0 * rot.getZ() * rot.getZ(), 2.0 * rot.getX() * rot.getY() - 2.0 * rot.getW() * rot.getZ(), 2.0 * rot.getX() * rot.getZ() + 2.0 * rot.getW() * rot.getY(), 0.0, 2.0 * rot.getX() * rot.getY() + 2.0 * rot.getW() * rot.getZ(), 1.0 - 2.0 * rot.getX() * rot.getX() - 2.0 * rot.getZ() * rot.getZ(), 2.0 * rot.getY() * rot.getZ() - 2.0 * rot.getW() * rot.getX(), 0.0, 2.0 * rot.getX() * rot.getZ() - 2.0 * rot.getW() * rot.getY(), 2.0 * rot.getY() * rot.getZ() + 2.0 * rot.getX() * rot.getW(), 1.0 - 2.0 * rot.getX() * rot.getX() - 2.0 * rot.getY() * rot.getY(), 0.0, 0.0, 0.0, 0.0, 1.0);
    }

    public static Matrix4d createLookAt(Vector3d eye, Vector3d at, Vector3d up) {
        Vector3d f = at.sub(eye).normalize();
        Vector3d s = f.cross(up).normalize();
        Vector3d u = s.cross(f);
        Matrix4d mat = new Matrix4d(s.getX(), s.getY(), s.getZ(), 0.0, u.getX(), u.getY(), u.getZ(), 0.0, - f.getX(), - f.getY(), - f.getZ(), 0.0, 0.0, 0.0, 0.0, 1.0);
        return mat.translate(eye.negate());
    }

    public static Matrix4d createPerspective(float fov, float aspect, float near, float far) {
        return Matrix4d.createPerspective((double)fov, (double)aspect, (double)near, (double)far);
    }

    public static Matrix4d createPerspective(double fov, double aspect, double near, double far) {
        double scale = 1.0f / TrigMath.tan(fov * 0.008726646259971648);
        return new Matrix4d(scale / aspect, 0.0, 0.0, 0.0, 0.0, scale, 0.0, 0.0, 0.0, 0.0, (far + near) / (near - far), 2.0 * far * near / (near - far), 0.0, 0.0, -1.0, 0.0);
    }

    public static Matrix4d createOrthographic(float right, float left, float top, float bottom, float near, float far) {
        return Matrix4d.createOrthographic((double)right, (double)left, (double)top, (double)bottom, (double)near, (double)far);
    }

    public static Matrix4d createOrthographic(double right, double left, double top, double bottom, double near, double far) {
        return new Matrix4d(2.0 / (right - left), 0.0, 0.0, (- right + left) / (right - left), 0.0, 2.0 / (top - bottom), 0.0, (- top + bottom) / (top - bottom), 0.0, 0.0, -2.0 / (far - near), (- far + near) / (far - near), 0.0, 0.0, 0.0, 1.0);
    }

    private static double det3(double m00, double m01, double m02, double m10, double m11, double m12, double m20, double m21, double m22) {
        return m00 * (m11 * m22 - m12 * m21) - m01 * (m10 * m22 - m12 * m20) + m02 * (m10 * m21 - m11 * m20);
    }
}

