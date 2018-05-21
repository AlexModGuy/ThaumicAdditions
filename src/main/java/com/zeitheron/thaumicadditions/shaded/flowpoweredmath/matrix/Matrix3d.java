/*
 * Decompiled with CFR 0_123.
 */
package com.zeitheron.thaumicadditions.shaded.flowpoweredmath.matrix;

import java.io.Serializable;

import com.zeitheron.thaumicadditions.shaded.flowpoweredmath.GenericMath;
import com.zeitheron.thaumicadditions.shaded.flowpoweredmath.HashFunctions;
import com.zeitheron.thaumicadditions.shaded.flowpoweredmath.imaginary.Complexd;
import com.zeitheron.thaumicadditions.shaded.flowpoweredmath.imaginary.Quaterniond;
import com.zeitheron.thaumicadditions.shaded.flowpoweredmath.matrix.Matrix2d;
import com.zeitheron.thaumicadditions.shaded.flowpoweredmath.matrix.Matrix3f;
import com.zeitheron.thaumicadditions.shaded.flowpoweredmath.matrix.Matrix4d;
import com.zeitheron.thaumicadditions.shaded.flowpoweredmath.matrix.MatrixNd;
import com.zeitheron.thaumicadditions.shaded.flowpoweredmath.matrix.Matrixd;
import com.zeitheron.thaumicadditions.shaded.flowpoweredmath.matrix.Matrixf;
import com.zeitheron.thaumicadditions.shaded.flowpoweredmath.vector.Vector2d;
import com.zeitheron.thaumicadditions.shaded.flowpoweredmath.vector.Vector3d;
import com.zeitheron.thaumicadditions.shaded.flowpoweredmath.vector.Vectord;

public class Matrix3d
implements Matrixd,
Serializable,
Cloneable {
    private static final long serialVersionUID = 1;
    public static final Matrix3d ZERO = new Matrix3d(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f);
    public static final Matrix3d IDENTITY = new Matrix3d();
    private final double m00;
    private final double m01;
    private final double m02;
    private final double m10;
    private final double m11;
    private final double m12;
    private final double m20;
    private final double m21;
    private final double m22;
    private volatile transient boolean hashed = false;
    private volatile transient int hashCode = 0;

    public Matrix3d() {
        this(1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f);
    }

    public Matrix3d(Matrix2d m) {
        this(m.get(0, 0), m.get(0, 1), 0.0, m.get(1, 0), m.get(1, 1), 0.0, 0.0, 0.0, 0.0);
    }

    public Matrix3d(Matrix3d m) {
        this(m.m00, m.m01, m.m02, m.m10, m.m11, m.m12, m.m20, m.m21, m.m22);
    }

    public Matrix3d(Matrix4d m) {
        this(m.get(0, 0), m.get(0, 1), m.get(0, 2), m.get(1, 0), m.get(1, 1), m.get(1, 2), m.get(2, 0), m.get(2, 1), m.get(2, 2));
    }

    public Matrix3d(MatrixNd m) {
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
            this.m02 = 0.0;
            this.m12 = 0.0;
            this.m20 = 0.0;
            this.m21 = 0.0;
            this.m22 = 0.0;
        }
    }

    public Matrix3d(float m00, float m01, float m02, float m10, float m11, float m12, float m20, float m21, float m22) {
        this((double)m00, (double)m01, (double)m02, (double)m10, (double)m11, (double)m12, (double)m20, (double)m21, (double)m22);
    }

    public Matrix3d(double m00, double m01, double m02, double m10, double m11, double m12, double m20, double m21, double m22) {
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
    public Vector3d getRow(int row) {
        return new Vector3d(this.get(row, 0), this.get(row, 1), this.get(row, 2));
    }

    @Override
    public Vector3d getColumn(int col) {
        return new Vector3d(this.get(0, col), this.get(1, col), this.get(2, col));
    }

    public Matrix3d add(Matrix3d m) {
        return new Matrix3d(this.m00 + m.m00, this.m01 + m.m01, this.m02 + m.m02, this.m10 + m.m10, this.m11 + m.m11, this.m12 + m.m12, this.m20 + m.m20, this.m21 + m.m21, this.m22 + m.m22);
    }

    public Matrix3d sub(Matrix3d m) {
        return new Matrix3d(this.m00 - m.m00, this.m01 - m.m01, this.m02 - m.m02, this.m10 - m.m10, this.m11 - m.m11, this.m12 - m.m12, this.m20 - m.m20, this.m21 - m.m21, this.m22 - m.m22);
    }

    public Matrix3d mul(float a) {
        return this.mul((double)a);
    }

    @Override
    public Matrix3d mul(double a) {
        return new Matrix3d(this.m00 * a, this.m01 * a, this.m02 * a, this.m10 * a, this.m11 * a, this.m12 * a, this.m20 * a, this.m21 * a, this.m22 * a);
    }

    public Matrix3d mul(Matrix3d m) {
        return new Matrix3d(this.m00 * m.m00 + this.m01 * m.m10 + this.m02 * m.m20, this.m00 * m.m01 + this.m01 * m.m11 + this.m02 * m.m21, this.m00 * m.m02 + this.m01 * m.m12 + this.m02 * m.m22, this.m10 * m.m00 + this.m11 * m.m10 + this.m12 * m.m20, this.m10 * m.m01 + this.m11 * m.m11 + this.m12 * m.m21, this.m10 * m.m02 + this.m11 * m.m12 + this.m12 * m.m22, this.m20 * m.m00 + this.m21 * m.m10 + this.m22 * m.m20, this.m20 * m.m01 + this.m21 * m.m11 + this.m22 * m.m21, this.m20 * m.m02 + this.m21 * m.m12 + this.m22 * m.m22);
    }

    public Matrix3d div(float a) {
        return this.div((double)a);
    }

    @Override
    public Matrix3d div(double a) {
        return new Matrix3d(this.m00 / a, this.m01 / a, this.m02 / a, this.m10 / a, this.m11 / a, this.m12 / a, this.m20 / a, this.m21 / a, this.m22 / a);
    }

    public Matrix3d div(Matrix3d m) {
        return this.mul(m.invert());
    }

    public Matrix3d pow(float pow) {
        return this.pow((double)pow);
    }

    @Override
    public Matrix3d pow(double pow) {
        return new Matrix3d(Math.pow(this.m00, pow), Math.pow(this.m01, pow), Math.pow(this.m02, pow), Math.pow(this.m10, pow), Math.pow(this.m11, pow), Math.pow(this.m12, pow), Math.pow(this.m20, pow), Math.pow(this.m21, pow), Math.pow(this.m22, pow));
    }

    public Matrix3d translate(Vector2d v) {
        return this.translate(v.getX(), v.getY());
    }

    public Matrix3d translate(float x, float y) {
        return this.translate((double)x, (double)y);
    }

    public Matrix3d translate(double x, double y) {
        return Matrix3d.createTranslation(x, y).mul(this);
    }

    public Matrix3d scale(float scale) {
        return this.scale((double)scale);
    }

    public Matrix3d scale(double scale) {
        return this.scale(scale, scale, scale);
    }

    public Matrix3d scale(Vector3d v) {
        return this.scale(v.getX(), v.getY(), v.getZ());
    }

    public Matrix3d scale(float x, float y, float z) {
        return this.scale((double)x, (double)y, (double)z);
    }

    public Matrix3d scale(double x, double y, double z) {
        return Matrix3d.createScaling(x, y, z).mul(this);
    }

    public Matrix3d rotate(Complexd rot) {
        return Matrix3d.createRotation(rot).mul(this);
    }

    public Matrix3d rotate(Quaterniond rot) {
        return Matrix3d.createRotation(rot).mul(this);
    }

    public Vector3d transform(Vector3d v) {
        return this.transform(v.getX(), v.getY(), v.getZ());
    }

    public Vector3d transform(float x, float y, float z) {
        return this.transform((double)x, (double)y, (double)z);
    }

    public Vector3d transform(double x, double y, double z) {
        return new Vector3d(this.m00 * x + this.m01 * y + this.m02 * z, this.m10 * x + this.m11 * y + this.m12 * z, this.m20 * x + this.m21 * y + this.m22 * z);
    }

    @Override
    public Matrix3d floor() {
        return new Matrix3d(GenericMath.floor(this.m00), GenericMath.floor(this.m01), GenericMath.floor(this.m02), GenericMath.floor(this.m10), GenericMath.floor(this.m11), GenericMath.floor(this.m12), GenericMath.floor(this.m20), GenericMath.floor(this.m21), GenericMath.floor(this.m22));
    }

    @Override
    public Matrix3d ceil() {
        return new Matrix3d(Math.ceil(this.m00), Math.ceil(this.m01), Math.ceil(this.m02), Math.ceil(this.m10), Math.ceil(this.m11), Math.ceil(this.m12), Math.ceil(this.m20), Math.ceil(this.m21), Math.ceil(this.m22));
    }

    @Override
    public Matrix3d round() {
        return new Matrix3d(Math.round(this.m00), Math.round(this.m01), Math.round(this.m02), Math.round(this.m10), Math.round(this.m11), Math.round(this.m12), Math.round(this.m20), Math.round(this.m21), Math.round(this.m22));
    }

    @Override
    public Matrix3d abs() {
        return new Matrix3d(Math.abs(this.m00), Math.abs(this.m01), Math.abs(this.m02), Math.abs(this.m10), Math.abs(this.m11), Math.abs(this.m12), Math.abs(this.m20), Math.abs(this.m21), Math.abs(this.m22));
    }

    @Override
    public Matrix3d negate() {
        return new Matrix3d(- this.m00, - this.m01, - this.m02, - this.m10, - this.m11, - this.m12, - this.m20, - this.m21, - this.m22);
    }

    @Override
    public Matrix3d transpose() {
        return new Matrix3d(this.m00, this.m10, this.m20, this.m01, this.m11, this.m21, this.m02, this.m12, this.m22);
    }

    @Override
    public double trace() {
        return this.m00 + this.m11 + this.m22;
    }

    @Override
    public double determinant() {
        return this.m00 * (this.m11 * this.m22 - this.m12 * this.m21) - this.m01 * (this.m10 * this.m22 - this.m12 * this.m20) + this.m02 * (this.m10 * this.m21 - this.m11 * this.m20);
    }

    @Override
    public Matrix3d invert() {
        double det = this.determinant();
        if (Math.abs(det) < GenericMath.DBL_EPSILON) {
            throw new ArithmeticException("Cannot inverse a matrix with a zero determinant");
        }
        return new Matrix3d((this.m11 * this.m22 - this.m21 * this.m12) / det, (- this.m01 * this.m22 - this.m21 * this.m02) / det, (this.m01 * this.m12 - this.m02 * this.m11) / det, (- this.m10 * this.m22 - this.m20 * this.m12) / det, (this.m00 * this.m22 - this.m20 * this.m02) / det, (- this.m00 * this.m12 - this.m10 * this.m02) / det, (this.m10 * this.m21 - this.m20 * this.m11) / det, (- this.m00 * this.m21 - this.m20 * this.m01) / det, (this.m00 * this.m11 - this.m01 * this.m10) / det);
    }

    public Matrix2d toMatrix2() {
        return new Matrix2d(this);
    }

    public Matrix4d toMatrix4() {
        return new Matrix4d(this);
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
            return new double[]{this.m00, this.m10, this.m20, this.m01, this.m11, this.m21, this.m02, this.m12, this.m22};
        }
        return new double[]{this.m00, this.m01, this.m02, this.m10, this.m11, this.m12, this.m20, this.m21, this.m22};
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
        if (!(o instanceof Matrix3d)) {
            return false;
        }
        Matrix3d matrix3 = (Matrix3d)o;
        if (Double.compare(matrix3.m00, this.m00) != 0) {
            return false;
        }
        if (Double.compare(matrix3.m01, this.m01) != 0) {
            return false;
        }
        if (Double.compare(matrix3.m02, this.m02) != 0) {
            return false;
        }
        if (Double.compare(matrix3.m10, this.m10) != 0) {
            return false;
        }
        if (Double.compare(matrix3.m11, this.m11) != 0) {
            return false;
        }
        if (Double.compare(matrix3.m12, this.m12) != 0) {
            return false;
        }
        if (Double.compare(matrix3.m20, this.m20) != 0) {
            return false;
        }
        if (Double.compare(matrix3.m21, this.m21) != 0) {
            return false;
        }
        if (Double.compare(matrix3.m22, this.m22) != 0) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        if (!this.hashed) {
            int result = this.m00 != 0.0 ? HashFunctions.hash(this.m00) : 0;
            result = 31 * result + (this.m01 != 0.0 ? HashFunctions.hash(this.m01) : 0);
            result = 31 * result + (this.m02 != 0.0 ? HashFunctions.hash(this.m02) : 0);
            result = 31 * result + (this.m10 != 0.0 ? HashFunctions.hash(this.m10) : 0);
            result = 31 * result + (this.m11 != 0.0 ? HashFunctions.hash(this.m11) : 0);
            result = 31 * result + (this.m12 != 0.0 ? HashFunctions.hash(this.m12) : 0);
            result = 31 * result + (this.m20 != 0.0 ? HashFunctions.hash(this.m20) : 0);
            result = 31 * result + (this.m21 != 0.0 ? HashFunctions.hash(this.m21) : 0);
            this.hashCode = 31 * result + (this.m22 != 0.0 ? HashFunctions.hash(this.m22) : 0);
            this.hashed = true;
        }
        return this.hashCode;
    }

    public Matrix3d clone() {
        return new Matrix3d(this);
    }

    public static Matrix3d from(double n) {
        return n == 0.0 ? ZERO : new Matrix3d(n, n, n, n, n, n, n, n, n);
    }

    public static Matrix3d from(double m00, double m01, double m02, double m10, double m11, double m12, double m20, double m21, double m22) {
        return m00 == 0.0 && m01 == 0.0 && m02 == 0.0 && m10 == 0.0 && m11 == 0.0 && m12 == 0.0 && m20 == 0.0 && m21 == 0.0 && m22 == 0.0 ? ZERO : new Matrix3d(m00, m01, m02, m10, m11, m12, m20, m21, m22);
    }

    public static Matrix3d fromDiagonal(double m00, double m11, double m22) {
        return m00 == 0.0 && m11 == 0.0 && m22 == 0.0 ? ZERO : new Matrix3d(m00, 0.0, 0.0, 0.0, m11, 0.0, 0.0, 0.0, m22);
    }

    public static Matrix3d createScaling(float scale) {
        return Matrix3d.createScaling((double)scale);
    }

    public static Matrix3d createScaling(double scale) {
        return Matrix3d.createScaling(scale, scale, scale);
    }

    public static Matrix3d createScaling(Vector3d v) {
        return Matrix3d.createScaling(v.getX(), v.getY(), v.getZ());
    }

    public static Matrix3d createScaling(float x, float y, float z) {
        return Matrix3d.createScaling((double)x, (double)y, (double)z);
    }

    public static Matrix3d createScaling(double x, double y, double z) {
        return new Matrix3d(x, 0.0, 0.0, 0.0, y, 0.0, 0.0, 0.0, z);
    }

    public static Matrix3d createTranslation(Vector2d v) {
        return Matrix3d.createTranslation(v.getX(), v.getY());
    }

    public static Matrix3d createTranslation(float x, float y) {
        return Matrix3d.createTranslation((double)x, (double)y);
    }

    public static Matrix3d createTranslation(double x, double y) {
        return new Matrix3d(1.0, 0.0, x, 0.0, 1.0, y, 0.0, 0.0, 1.0);
    }

    public static Matrix3d createRotation(Complexd rot) {
        rot = rot.normalize();
        return new Matrix3d(rot.getX(), - rot.getY(), 0.0, rot.getY(), rot.getX(), 0.0, 0.0, 0.0, 1.0);
    }

    public static Matrix3d createRotation(Quaterniond rot) {
        rot = rot.normalize();
        return new Matrix3d(1.0 - 2.0 * rot.getY() * rot.getY() - 2.0 * rot.getZ() * rot.getZ(), 2.0 * rot.getX() * rot.getY() - 2.0 * rot.getW() * rot.getZ(), 2.0 * rot.getX() * rot.getZ() + 2.0 * rot.getW() * rot.getY(), 2.0 * rot.getX() * rot.getY() + 2.0 * rot.getW() * rot.getZ(), 1.0 - 2.0 * rot.getX() * rot.getX() - 2.0 * rot.getZ() * rot.getZ(), 2.0 * rot.getY() * rot.getZ() - 2.0 * rot.getW() * rot.getX(), 2.0 * rot.getX() * rot.getZ() - 2.0 * rot.getW() * rot.getY(), 2.0 * rot.getY() * rot.getZ() + 2.0 * rot.getX() * rot.getW(), 1.0 - 2.0 * rot.getX() * rot.getX() - 2.0 * rot.getY() * rot.getY());
    }
}

