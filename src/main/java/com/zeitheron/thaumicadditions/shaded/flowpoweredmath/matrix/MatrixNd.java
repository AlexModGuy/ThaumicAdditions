/*
 * Decompiled with CFR 0_123.
 */
package com.zeitheron.thaumicadditions.shaded.flowpoweredmath.matrix;

import java.io.Serializable;
import java.util.Arrays;

import com.zeitheron.thaumicadditions.shaded.flowpoweredmath.GenericMath;
import com.zeitheron.thaumicadditions.shaded.flowpoweredmath.TrigMath;
import com.zeitheron.thaumicadditions.shaded.flowpoweredmath.imaginary.Complexd;
import com.zeitheron.thaumicadditions.shaded.flowpoweredmath.imaginary.Quaterniond;
import com.zeitheron.thaumicadditions.shaded.flowpoweredmath.matrix.Matrix2d;
import com.zeitheron.thaumicadditions.shaded.flowpoweredmath.matrix.Matrix3d;
import com.zeitheron.thaumicadditions.shaded.flowpoweredmath.matrix.Matrix4d;
import com.zeitheron.thaumicadditions.shaded.flowpoweredmath.matrix.MatrixNf;
import com.zeitheron.thaumicadditions.shaded.flowpoweredmath.matrix.Matrixd;
import com.zeitheron.thaumicadditions.shaded.flowpoweredmath.matrix.Matrixf;
import com.zeitheron.thaumicadditions.shaded.flowpoweredmath.vector.Vector3d;
import com.zeitheron.thaumicadditions.shaded.flowpoweredmath.vector.VectorNd;
import com.zeitheron.thaumicadditions.shaded.flowpoweredmath.vector.Vectord;

public class MatrixNd
implements Matrixd,
Serializable,
Cloneable {
    private static final long serialVersionUID = 1;
    public static final MatrixNd IDENTITY_2 = new ImmutableIdentityMatrixN(2);
    public static final MatrixNd IDENTITY_3 = new ImmutableIdentityMatrixN(3);
    public static final MatrixNd IDENTITY_4 = new ImmutableIdentityMatrixN(4);
    private final double[][] mat;

    public MatrixNd(int size) {
        if (size < 2) {
            throw new IllegalArgumentException("Minimum matrix size is 2");
        }
        this.mat = new double[size][size];
        this.setIdentity();
    }

    public MatrixNd(Matrix2d m) {
        this.mat = new double[][]{{m.get(0, 0), m.get(0, 1)}, {m.get(1, 0), m.get(1, 1)}};
    }

    public MatrixNd(Matrix3d m) {
        this.mat = new double[][]{{m.get(0, 0), m.get(0, 1), m.get(0, 2)}, {m.get(1, 0), m.get(1, 1), m.get(1, 2)}, {m.get(2, 0), m.get(2, 1), m.get(2, 2)}};
    }

    public MatrixNd(Matrix4d m) {
        this.mat = new double[][]{{m.get(0, 0), m.get(0, 1), m.get(0, 2), m.get(0, 3)}, {m.get(1, 0), m.get(1, 1), m.get(1, 2), m.get(1, 3)}, {m.get(2, 0), m.get(2, 1), m.get(2, 2), m.get(2, 3)}, {m.get(3, 0), m.get(3, 1), m.get(3, 2), m.get(3, 3)}};
    }

    public /* varargs */ MatrixNd(double ... m) {
        if (m.length < 4) {
            throw new IllegalArgumentException("Minimum matrix size is 2");
        }
        int size = (int)Math.ceil(Math.sqrt(m.length));
        this.mat = new double[size][size];
        for (int row = 0; row < size; ++row) {
            for (int col = 0; col < size; ++col) {
                int index = col + row * size;
                this.mat[row][col] = index < m.length ? m[index] : 0.0;
            }
        }
    }

    public MatrixNd(MatrixNd m) {
        this.mat = MatrixNd.deepClone(m.mat);
    }

    public int size() {
        return this.mat.length;
    }

    @Override
    public double get(int row, int col) {
        return this.mat[row][col];
    }

    @Override
    public VectorNd getRow(int row) {
        int size = this.size();
        VectorNd d = new VectorNd(size);
        for (int col = 0; col < size; ++col) {
            d.set(col, this.get(row, col));
        }
        return d;
    }

    @Override
    public VectorNd getColumn(int col) {
        int size = this.size();
        VectorNd d = new VectorNd(size);
        for (int row = 0; row < size; ++row) {
            d.set(row, this.get(row, col));
        }
        return d;
    }

    public void set(int row, int col, float val) {
        this.set(row, col, (double)val);
    }

    public void set(int row, int col, double val) {
        this.mat[row][col] = val;
    }

    public final void setIdentity() {
        int size = this.size();
        for (int row = 0; row < size; ++row) {
            for (int col = 0; col < size; ++col) {
                this.mat[row][col] = row == col ? 1.0 : 0.0;
            }
        }
    }

    public void setZero() {
        int size = this.size();
        for (int row = 0; row < size; ++row) {
            Arrays.fill(this.mat[row], 0.0);
        }
    }

    public MatrixNd resize(int size) {
        MatrixNd d = new MatrixNd(size);
        for (int rowCol = this.size(); rowCol < size; ++rowCol) {
            d.set(rowCol, rowCol, 0.0f);
        }
        size = Math.min(size, this.size());
        for (int row = 0; row < size; ++row) {
            System.arraycopy(this.mat[row], 0, d.mat[row], 0, size);
        }
        return d;
    }

    public MatrixNd add(MatrixNd m) {
        int size = this.size();
        if (size != m.size()) {
            throw new IllegalArgumentException("Matrix sizes must be the same");
        }
        MatrixNd d = new MatrixNd(size);
        for (int row = 0; row < size; ++row) {
            for (int col = 0; col < size; ++col) {
                d.mat[row][col] = this.mat[row][col] + m.mat[row][col];
            }
        }
        return d;
    }

    public MatrixNd sub(MatrixNd m) {
        int size = this.size();
        if (size != m.size()) {
            throw new IllegalArgumentException("Matrix sizes must be the same");
        }
        MatrixNd d = new MatrixNd(size);
        for (int row = 0; row < size; ++row) {
            for (int col = 0; col < size; ++col) {
                d.mat[row][col] = this.mat[row][col] - m.mat[row][col];
            }
        }
        return d;
    }

    public MatrixNd mul(float a) {
        return this.mul((double)a);
    }

    @Override
    public MatrixNd mul(double a) {
        int size = this.size();
        MatrixNd d = new MatrixNd(size);
        for (int row = 0; row < size; ++row) {
            for (int col = 0; col < size; ++col) {
                d.mat[row][col] = this.mat[row][col] * a;
            }
        }
        return d;
    }

    public MatrixNd mul(MatrixNd m) {
        int size = this.size();
        if (size != m.size()) {
            throw new IllegalArgumentException("Matrix sizes must be the same");
        }
        MatrixNd d = new MatrixNd(size);
        for (int row = 0; row < size; ++row) {
            for (int col = 0; col < size; ++col) {
                double dot = 0.0;
                for (int i = 0; i < size; ++i) {
                    dot += this.mat[row][i] * m.mat[i][col];
                }
                d.mat[row][col] = dot;
            }
        }
        return d;
    }

    public MatrixNd div(float a) {
        return this.div((double)a);
    }

    @Override
    public MatrixNd div(double a) {
        int size = this.size();
        MatrixNd d = new MatrixNd(size);
        for (int row = 0; row < size; ++row) {
            for (int col = 0; col < size; ++col) {
                d.mat[row][col] = this.mat[row][col] / a;
            }
        }
        return d;
    }

    public MatrixNd div(MatrixNd m) {
        return this.mul(m.invert());
    }

    public MatrixNd pow(float pow) {
        return this.pow((double)pow);
    }

    @Override
    public MatrixNd pow(double pow) {
        int size = this.size();
        MatrixNd d = new MatrixNd(size);
        for (int row = 0; row < size; ++row) {
            for (int col = 0; col < size; ++col) {
                d.mat[row][col] = Math.pow(this.mat[row][col], pow);
            }
        }
        return d;
    }

    public MatrixNd translate(VectorNd v) {
        return this.translate(v.toArray());
    }

    public /* varargs */ MatrixNd translate(double ... v) {
        return MatrixNd.createTranslation(v).mul(this);
    }

    public MatrixNd scale(VectorNd v) {
        return this.scale(v.toArray());
    }

    public /* varargs */ MatrixNd scale(double ... v) {
        return MatrixNd.createScaling(v).mul(this);
    }

    public MatrixNd rotate(Complexd rot) {
        return MatrixNd.createRotation(this.size(), rot).mul(this);
    }

    public MatrixNd rotate(Quaterniond rot) {
        return MatrixNd.createRotation(this.size(), rot).mul(this);
    }

    public VectorNd transform(VectorNd v) {
        return this.transform(v.toArray());
    }

    public /* varargs */ VectorNd transform(double ... vec) {
        int size = this.size();
        if (size != vec.length) {
            throw new IllegalArgumentException("Matrix and vector sizes must be the same");
        }
        VectorNd d = new VectorNd(size);
        for (int row = 0; row < size; ++row) {
            double dot = 0.0;
            for (int col = 0; col < size; ++col) {
                dot += this.mat[row][col] * vec[col];
            }
            d.set(row, dot);
        }
        return d;
    }

    @Override
    public MatrixNd floor() {
        int size = this.size();
        MatrixNd d = new MatrixNd(size);
        for (int row = 0; row < size; ++row) {
            for (int col = 0; col < size; ++col) {
                d.mat[row][col] = GenericMath.floor(this.mat[row][col]);
            }
        }
        return d;
    }

    @Override
    public MatrixNd ceil() {
        int size = this.size();
        MatrixNd d = new MatrixNd(size);
        for (int row = 0; row < size; ++row) {
            for (int col = 0; col < size; ++col) {
                d.mat[row][col] = Math.ceil(this.mat[row][col]);
            }
        }
        return d;
    }

    @Override
    public MatrixNd round() {
        int size = this.size();
        MatrixNd d = new MatrixNd(size);
        for (int row = 0; row < size; ++row) {
            for (int col = 0; col < size; ++col) {
                d.mat[row][col] = Math.round(this.mat[row][col]);
            }
        }
        return d;
    }

    @Override
    public MatrixNd abs() {
        int size = this.size();
        MatrixNd d = new MatrixNd(size);
        for (int row = 0; row < size; ++row) {
            for (int col = 0; col < size; ++col) {
                d.mat[row][col] = Math.abs(this.mat[row][col]);
            }
        }
        return d;
    }

    @Override
    public MatrixNd negate() {
        int size = this.size();
        MatrixNd d = new MatrixNd(size);
        for (int row = 0; row < size; ++row) {
            for (int col = 0; col < size; ++col) {
                d.mat[row][col] = - this.mat[row][col];
            }
        }
        return d;
    }

    @Override
    public MatrixNd transpose() {
        int size = this.size();
        MatrixNd d = new MatrixNd(size);
        for (int row = 0; row < size; ++row) {
            for (int col = 0; col < size; ++col) {
                d.mat[row][col] = this.mat[col][row];
            }
        }
        return d;
    }

    @Override
    public double trace() {
        int size = this.size();
        double trace = 0.0;
        for (int rowCol = 0; rowCol < size; ++rowCol) {
            trace += this.mat[rowCol][rowCol];
        }
        return trace;
    }

    @Override
    public double determinant() {
        int i;
        double det;
        int size = this.size();
        double[][] m = MatrixNd.deepClone(this.mat);
        for (i = 0; i < size - 1; ++i) {
            for (int col = i + 1; col < size; ++col) {
                det = m[i][i] < GenericMath.DBL_EPSILON ? 0.0 : m[i][col] / m[i][i];
                for (int row = i; row < size; ++row) {
                    double[] arrd = m[row];
                    int n = col;
                    arrd[n] = arrd[n] - det * m[row][i];
                }
            }
        }
        det = 1.0;
        for (i = 0; i < size; ++i) {
            det *= m[i][i];
        }
        return det;
    }

    @Override
    public MatrixNd invert() {
        if (Math.abs(this.determinant()) < GenericMath.DBL_EPSILON) {
            throw new ArithmeticException("Cannot inverse a matrix with a zero determinant");
        }
        int size = this.size();
        AugmentedMatrixN augMat = new AugmentedMatrixN(this);
        int augmentedSize = augMat.getAugmentedSize();
        for (int i = 0; i < size; ++i) {
            for (int row = 0; row < size; ++row) {
                if (i == row) continue;
                double ratio = augMat.get(row, i) / augMat.get(i, i);
                for (int col = 0; col < augmentedSize; ++col) {
                    augMat.set(row, col, augMat.get(row, col) - ratio * augMat.get(i, col));
                }
            }
        }
        for (int row = 0; row < size; ++row) {
            double div = augMat.get(row, row);
            for (int col = 0; col < augmentedSize; ++col) {
                augMat.set(row, col, augMat.get(row, col) / div);
            }
        }
        return augMat.getAugmentation();
    }

    public Matrix2d toMatrix2() {
        return new Matrix2d(this);
    }

    public Matrix3d toMatrix3() {
        return new Matrix3d(this);
    }

    public Matrix4d toMatrix4() {
        return new Matrix4d(this);
    }

    public double[] toArray() {
        return this.toArray(false);
    }

    @Override
    public MatrixNf toFloat() {
        int size = this.size();
        float[] m = new float[size * size];
        for (int row = 0; row < size; ++row) {
            for (int col = 0; col < size; ++col) {
                m[col + row * size] = (float)this.get(row, col);
            }
        }
        return new MatrixNf(m);
    }

    @Override
    public MatrixNd toDouble() {
        int size = this.size();
        double[] m = new double[size * size];
        for (int row = 0; row < size; ++row) {
            for (int col = 0; col < size; ++col) {
                m[col + row * size] = this.get(row, col);
            }
        }
        return new MatrixNd(m);
    }

    @Override
    public double[] toArray(boolean columnMajor) {
        double[] array;
        int size = this.size();
        array = new double[size * size];
        if (columnMajor) {
            for (int col = 0; col < size; ++col) {
                for (int row = 0; row < size; ++row) {
                    array[row + col * size] = this.mat[row][col];
                }
            }
        } else {
            for (int row = 0; row < size; ++row) {
                System.arraycopy(this.mat[row], 0, array, row * size, size);
            }
        }
        return array;
    }

    public String toString() {
        int size = this.size();
        StringBuilder builder = new StringBuilder();
        for (int row = 0; row < size; ++row) {
            for (int col = 0; col < size; ++col) {
                builder.append(this.mat[row][col]);
                if (col >= size - 1) continue;
                builder.append(' ');
            }
            if (row >= size - 1) continue;
            builder.append('\n');
        }
        return builder.toString();
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof MatrixNd)) {
            return false;
        }
        return Arrays.deepEquals((Object[])this.mat, (Object[])((MatrixNd)obj).mat);
    }

    public int hashCode() {
        return 395 + Arrays.deepHashCode((Object[])this.mat);
    }

    public MatrixNd clone() {
        return new MatrixNd(this);
    }

    public static MatrixNd createScaling(VectorNd v) {
        return MatrixNd.createScaling(v.toArray());
    }

    public static /* varargs */ MatrixNd createScaling(double ... vec) {
        int size = vec.length;
        MatrixNd m = new MatrixNd(size);
        for (int rowCol = 0; rowCol < size; ++rowCol) {
            m.set(rowCol, rowCol, vec[rowCol]);
        }
        return m;
    }

    public static MatrixNd createTranslation(VectorNd v) {
        return MatrixNd.createTranslation(v.toArray());
    }

    public static /* varargs */ MatrixNd createTranslation(double ... vec) {
        int size = vec.length;
        MatrixNd m = new MatrixNd(size + 1);
        for (int row = 0; row < size; ++row) {
            m.set(row, size, vec[row]);
        }
        return m;
    }

    public static MatrixNd createRotation(int size, Complexd rot) {
        if (size < 2) {
            throw new IllegalArgumentException("Minimum matrix size is 2");
        }
        MatrixNd m = new MatrixNd(size);
        rot = rot.normalize();
        m.set(0, 0, rot.getX());
        m.set(0, 1, - rot.getY());
        m.set(1, 0, rot.getY());
        m.set(1, 1, rot.getX());
        return m;
    }

    public static MatrixNd createRotation(int size, Quaterniond rot) {
        if (size < 3) {
            throw new IllegalArgumentException("Minimum matrix size is 3");
        }
        MatrixNd m = new MatrixNd(size);
        rot = rot.normalize();
        m.set(0, 0, 1.0 - 2.0 * rot.getY() * rot.getY() - 2.0 * rot.getZ() * rot.getZ());
        m.set(0, 1, 2.0 * rot.getX() * rot.getY() - 2.0 * rot.getW() * rot.getZ());
        m.set(0, 2, 2.0 * rot.getX() * rot.getZ() + 2.0 * rot.getW() * rot.getY());
        m.set(1, 0, 2.0 * rot.getX() * rot.getY() + 2.0 * rot.getW() * rot.getZ());
        m.set(1, 1, 1.0 - 2.0 * rot.getX() * rot.getX() - 2.0 * rot.getZ() * rot.getZ());
        m.set(1, 2, 2.0 * rot.getY() * rot.getZ() - 2.0 * rot.getW() * rot.getX());
        m.set(2, 0, 2.0 * rot.getX() * rot.getZ() - 2.0 * rot.getW() * rot.getY());
        m.set(2, 1, 2.0 * rot.getY() * rot.getZ() + 2.0 * rot.getX() * rot.getW());
        m.set(2, 2, 1.0 - 2.0 * rot.getX() * rot.getX() - 2.0 * rot.getY() * rot.getY());
        return m;
    }

    public static MatrixNd createLookAt(int size, Vector3d eye, Vector3d at, Vector3d up) {
        if (size < 4) {
            throw new IllegalArgumentException("Minimum matrix size is 4");
        }
        Vector3d f = at.sub(eye).normalize();
        up = up.normalize();
        Vector3d s = f.cross(up).normalize();
        Vector3d u = s.cross(f).normalize();
        MatrixNd mat = new MatrixNd(size);
        mat.set(0, 0, s.getX());
        mat.set(0, 1, s.getY());
        mat.set(0, 2, s.getZ());
        mat.set(1, 0, u.getX());
        mat.set(1, 1, u.getY());
        mat.set(1, 2, u.getZ());
        mat.set(2, 0, - f.getX());
        mat.set(2, 1, - f.getY());
        mat.set(2, 2, - f.getZ());
        return mat.translate(eye.mul(-1.0f).toVectorN());
    }

    public static MatrixNd createPerspective(int size, float fov, float aspect, float near, float far) {
        return MatrixNd.createPerspective(size, (double)fov, (double)aspect, (double)near, (double)far);
    }

    public static MatrixNd createPerspective(int size, double fov, double aspect, double near, double far) {
        if (size < 4) {
            throw new IllegalArgumentException("Minimum matrix size is 4");
        }
        MatrixNd perspective = new MatrixNd(size);
        double scale = 1.0f / TrigMath.tan(fov * 0.008726646259971648);
        perspective.set(0, 0, scale / aspect);
        perspective.set(1, 1, scale);
        perspective.set(2, 2, (far + near) / (near - far));
        perspective.set(2, 3, 2.0 * far * near / (near - far));
        perspective.set(3, 2, -1.0f);
        perspective.set(3, 3, 0.0f);
        return perspective;
    }

    public static MatrixNd createOrthographic(int size, float right, float left, float top, float bottom, float near, float far) {
        return MatrixNd.createOrthographic(size, (double)right, (double)left, (double)top, (double)bottom, (double)near, (double)far);
    }

    public static MatrixNd createOrthographic(int size, double right, double left, double top, double bottom, double near, double far) {
        if (size < 4) {
            throw new IllegalArgumentException("Minimum matrix size is 4");
        }
        MatrixNd orthographic = new MatrixNd(size);
        orthographic.set(0, 0, 2.0 / (right - left));
        orthographic.set(1, 1, 2.0 / (top - bottom));
        orthographic.set(2, 2, -2.0 / (far - near));
        orthographic.set(0, 3, (- right + left) / (right - left));
        orthographic.set(1, 3, (- top + bottom) / (top - bottom));
        orthographic.set(2, 3, (- far + near) / (far - near));
        return orthographic;
    }

    private static double[][] deepClone(double[][] array) {
        int size = array.length;
        double[][] clone = (double[][])array.clone();
        for (int i = 0; i < size; ++i) {
            clone[i] = (double[])array[i].clone();
        }
        return clone;
    }

    private static class AugmentedMatrixN {
        private final MatrixNd mat;
        private final MatrixNd aug;
        private final int size;

        private AugmentedMatrixN(MatrixNd mat) {
            this.mat = mat.clone();
            this.size = mat.size();
            this.aug = new MatrixNd(this.size);
        }

        private MatrixNd getAugmentation() {
            return this.aug;
        }

        private int getAugmentedSize() {
            return this.size * 2;
        }

        private double get(int row, int col) {
            if (col < this.size) {
                return this.mat.get(row, col);
            }
            return this.aug.get(row, col - this.size);
        }

        private void set(int row, int col, double val) {
            if (col < this.size) {
                this.mat.set(row, col, val);
            } else {
                this.aug.set(row, col - this.size, val);
            }
        }
    }

    private static class ImmutableIdentityMatrixN
    extends MatrixNd {
        public ImmutableIdentityMatrixN(int size) {
            super(size);
        }

        @Override
        public void set(int row, int col, double val) {
            throw new UnsupportedOperationException("You may not alter this matrix");
        }

        @Override
        public void setZero() {
            throw new UnsupportedOperationException("You may not alter this matrix");
        }
    }

}

