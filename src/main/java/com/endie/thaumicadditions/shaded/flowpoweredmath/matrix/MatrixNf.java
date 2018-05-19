/*
 * Decompiled with CFR 0_123.
 */
package com.endie.thaumicadditions.shaded.flowpoweredmath.matrix;

import java.io.Serializable;
import java.util.Arrays;

import com.endie.thaumicadditions.shaded.flowpoweredmath.GenericMath;
import com.endie.thaumicadditions.shaded.flowpoweredmath.TrigMath;
import com.endie.thaumicadditions.shaded.flowpoweredmath.imaginary.Complexf;
import com.endie.thaumicadditions.shaded.flowpoweredmath.imaginary.Quaternionf;
import com.endie.thaumicadditions.shaded.flowpoweredmath.matrix.Matrix2f;
import com.endie.thaumicadditions.shaded.flowpoweredmath.matrix.Matrix3f;
import com.endie.thaumicadditions.shaded.flowpoweredmath.matrix.Matrix4f;
import com.endie.thaumicadditions.shaded.flowpoweredmath.matrix.MatrixNd;
import com.endie.thaumicadditions.shaded.flowpoweredmath.matrix.Matrixd;
import com.endie.thaumicadditions.shaded.flowpoweredmath.matrix.Matrixf;
import com.endie.thaumicadditions.shaded.flowpoweredmath.vector.Vector3f;
import com.endie.thaumicadditions.shaded.flowpoweredmath.vector.VectorNf;
import com.endie.thaumicadditions.shaded.flowpoweredmath.vector.Vectorf;

public class MatrixNf
implements Matrixf,
Serializable,
Cloneable {
    private static final long serialVersionUID = 1;
    public static final MatrixNf IDENTITY_2 = new ImmutableIdentityMatrixN(2);
    public static final MatrixNf IDENTITY_3 = new ImmutableIdentityMatrixN(3);
    public static final MatrixNf IDENTITY_4 = new ImmutableIdentityMatrixN(4);
    private final float[][] mat;

    public MatrixNf(int size) {
        if (size < 2) {
            throw new IllegalArgumentException("Minimum matrix size is 2");
        }
        this.mat = new float[size][size];
        this.setIdentity();
    }

    public MatrixNf(Matrix2f m) {
        this.mat = new float[][]{{m.get(0, 0), m.get(0, 1)}, {m.get(1, 0), m.get(1, 1)}};
    }

    public MatrixNf(Matrix3f m) {
        this.mat = new float[][]{{m.get(0, 0), m.get(0, 1), m.get(0, 2)}, {m.get(1, 0), m.get(1, 1), m.get(1, 2)}, {m.get(2, 0), m.get(2, 1), m.get(2, 2)}};
    }

    public MatrixNf(Matrix4f m) {
        this.mat = new float[][]{{m.get(0, 0), m.get(0, 1), m.get(0, 2), m.get(0, 3)}, {m.get(1, 0), m.get(1, 1), m.get(1, 2), m.get(1, 3)}, {m.get(2, 0), m.get(2, 1), m.get(2, 2), m.get(2, 3)}, {m.get(3, 0), m.get(3, 1), m.get(3, 2), m.get(3, 3)}};
    }

    public /* varargs */ MatrixNf(float ... m) {
        if (m.length < 4) {
            throw new IllegalArgumentException("Minimum matrix size is 2");
        }
        int size = (int)Math.ceil(Math.sqrt(m.length));
        this.mat = new float[size][size];
        for (int row = 0; row < size; ++row) {
            for (int col = 0; col < size; ++col) {
                int index = col + row * size;
                this.mat[row][col] = index < m.length ? m[index] : 0.0f;
            }
        }
    }

    public MatrixNf(MatrixNf m) {
        this.mat = MatrixNf.deepClone(m.mat);
    }

    public int size() {
        return this.mat.length;
    }

    @Override
    public float get(int row, int col) {
        return this.mat[row][col];
    }

    @Override
    public VectorNf getRow(int row) {
        int size = this.size();
        VectorNf d = new VectorNf(size);
        for (int col = 0; col < size; ++col) {
            d.set(col, this.get(row, col));
        }
        return d;
    }

    @Override
    public VectorNf getColumn(int col) {
        int size = this.size();
        VectorNf d = new VectorNf(size);
        for (int row = 0; row < size; ++row) {
            d.set(row, this.get(row, col));
        }
        return d;
    }

    public void set(int row, int col, double val) {
        this.set(row, col, (float)val);
    }

    public void set(int row, int col, float val) {
        this.mat[row][col] = val;
    }

    public final void setIdentity() {
        int size = this.size();
        for (int row = 0; row < size; ++row) {
            for (int col = 0; col < size; ++col) {
                this.mat[row][col] = row == col ? 1.0f : 0.0f;
            }
        }
    }

    public void setZero() {
        int size = this.size();
        for (int row = 0; row < size; ++row) {
            Arrays.fill(this.mat[row], 0.0f);
        }
    }

    public MatrixNf resize(int size) {
        MatrixNf d = new MatrixNf(size);
        for (int rowCol = this.size(); rowCol < size; ++rowCol) {
            d.set(rowCol, rowCol, 0.0f);
        }
        size = Math.min(size, this.size());
        for (int row = 0; row < size; ++row) {
            System.arraycopy(this.mat[row], 0, d.mat[row], 0, size);
        }
        return d;
    }

    public MatrixNf add(MatrixNf m) {
        int size = this.size();
        if (size != m.size()) {
            throw new IllegalArgumentException("Matrix sizes must be the same");
        }
        MatrixNf d = new MatrixNf(size);
        for (int row = 0; row < size; ++row) {
            for (int col = 0; col < size; ++col) {
                d.mat[row][col] = this.mat[row][col] + m.mat[row][col];
            }
        }
        return d;
    }

    public MatrixNf sub(MatrixNf m) {
        int size = this.size();
        if (size != m.size()) {
            throw new IllegalArgumentException("Matrix sizes must be the same");
        }
        MatrixNf d = new MatrixNf(size);
        for (int row = 0; row < size; ++row) {
            for (int col = 0; col < size; ++col) {
                d.mat[row][col] = this.mat[row][col] - m.mat[row][col];
            }
        }
        return d;
    }

    public MatrixNf mul(double a) {
        return this.mul((float)a);
    }

    @Override
    public MatrixNf mul(float a) {
        int size = this.size();
        MatrixNf d = new MatrixNf(size);
        for (int row = 0; row < size; ++row) {
            for (int col = 0; col < size; ++col) {
                d.mat[row][col] = this.mat[row][col] * a;
            }
        }
        return d;
    }

    public MatrixNf mul(MatrixNf m) {
        int size = this.size();
        if (size != m.size()) {
            throw new IllegalArgumentException("Matrix sizes must be the same");
        }
        MatrixNf d = new MatrixNf(size);
        for (int row = 0; row < size; ++row) {
            for (int col = 0; col < size; ++col) {
                float dot = 0.0f;
                for (int i = 0; i < size; ++i) {
                    dot += this.mat[row][i] * m.mat[i][col];
                }
                d.mat[row][col] = dot;
            }
        }
        return d;
    }

    public MatrixNf div(double a) {
        return this.div((float)a);
    }

    @Override
    public MatrixNf div(float a) {
        int size = this.size();
        MatrixNf d = new MatrixNf(size);
        for (int row = 0; row < size; ++row) {
            for (int col = 0; col < size; ++col) {
                d.mat[row][col] = this.mat[row][col] / a;
            }
        }
        return d;
    }

    public MatrixNf div(MatrixNf m) {
        return this.mul(m.invert());
    }

    public MatrixNf pow(double pow) {
        return this.pow((float)pow);
    }

    @Override
    public MatrixNf pow(float pow) {
        int size = this.size();
        MatrixNf d = new MatrixNf(size);
        for (int row = 0; row < size; ++row) {
            for (int col = 0; col < size; ++col) {
                d.mat[row][col] = (float)Math.pow(this.mat[row][col], pow);
            }
        }
        return d;
    }

    public MatrixNf translate(VectorNf v) {
        return this.translate(v.toArray());
    }

    public /* varargs */ MatrixNf translate(float ... v) {
        return MatrixNf.createTranslation(v).mul(this);
    }

    public MatrixNf scale(VectorNf v) {
        return this.scale(v.toArray());
    }

    public /* varargs */ MatrixNf scale(float ... v) {
        return MatrixNf.createScaling(v).mul(this);
    }

    public MatrixNf rotate(Complexf rot) {
        return MatrixNf.createRotation(this.size(), rot).mul(this);
    }

    public MatrixNf rotate(Quaternionf rot) {
        return MatrixNf.createRotation(this.size(), rot).mul(this);
    }

    public VectorNf transform(VectorNf v) {
        return this.transform(v.toArray());
    }

    public /* varargs */ VectorNf transform(float ... vec) {
        int size = this.size();
        if (size != vec.length) {
            throw new IllegalArgumentException("Matrix and vector sizes must be the same");
        }
        VectorNf d = new VectorNf(size);
        for (int row = 0; row < size; ++row) {
            float dot = 0.0f;
            for (int col = 0; col < size; ++col) {
                dot += this.mat[row][col] * vec[col];
            }
            d.set(row, dot);
        }
        return d;
    }

    @Override
    public MatrixNf floor() {
        int size = this.size();
        MatrixNf d = new MatrixNf(size);
        for (int row = 0; row < size; ++row) {
            for (int col = 0; col < size; ++col) {
                d.mat[row][col] = GenericMath.floor(this.mat[row][col]);
            }
        }
        return d;
    }

    @Override
    public MatrixNf ceil() {
        int size = this.size();
        MatrixNf d = new MatrixNf(size);
        for (int row = 0; row < size; ++row) {
            for (int col = 0; col < size; ++col) {
                d.mat[row][col] = (float)Math.ceil(this.mat[row][col]);
            }
        }
        return d;
    }

    @Override
    public MatrixNf round() {
        int size = this.size();
        MatrixNf d = new MatrixNf(size);
        for (int row = 0; row < size; ++row) {
            for (int col = 0; col < size; ++col) {
                d.mat[row][col] = Math.round(this.mat[row][col]);
            }
        }
        return d;
    }

    @Override
    public MatrixNf abs() {
        int size = this.size();
        MatrixNf d = new MatrixNf(size);
        for (int row = 0; row < size; ++row) {
            for (int col = 0; col < size; ++col) {
                d.mat[row][col] = Math.abs(this.mat[row][col]);
            }
        }
        return d;
    }

    @Override
    public MatrixNf negate() {
        int size = this.size();
        MatrixNf d = new MatrixNf(size);
        for (int row = 0; row < size; ++row) {
            for (int col = 0; col < size; ++col) {
                d.mat[row][col] = - this.mat[row][col];
            }
        }
        return d;
    }

    @Override
    public MatrixNf transpose() {
        int size = this.size();
        MatrixNf d = new MatrixNf(size);
        for (int row = 0; row < size; ++row) {
            for (int col = 0; col < size; ++col) {
                d.mat[row][col] = this.mat[col][row];
            }
        }
        return d;
    }

    @Override
    public float trace() {
        int size = this.size();
        float trace = 0.0f;
        for (int rowCol = 0; rowCol < size; ++rowCol) {
            trace += this.mat[rowCol][rowCol];
        }
        return trace;
    }

    @Override
    public float determinant() {
        int i;
        float det;
        int size = this.size();
        float[][] m = MatrixNf.deepClone(this.mat);
        for (i = 0; i < size - 1; ++i) {
            for (int col = i + 1; col < size; ++col) {
                det = m[i][i] < GenericMath.FLT_EPSILON ? 0.0f : m[i][col] / m[i][i];
                for (int row = i; row < size; ++row) {
                    float[] arrf = m[row];
                    int n = col;
                    arrf[n] = arrf[n] - det * m[row][i];
                }
            }
        }
        det = 1.0f;
        for (i = 0; i < size; ++i) {
            det *= m[i][i];
        }
        return det;
    }

    @Override
    public MatrixNf invert() {
        if (Math.abs(this.determinant()) < GenericMath.FLT_EPSILON) {
            throw new ArithmeticException("Cannot inverse a matrix with a zero determinant");
        }
        int size = this.size();
        AugmentedMatrixN augMat = new AugmentedMatrixN(this);
        int augmentedSize = augMat.getAugmentedSize();
        for (int i = 0; i < size; ++i) {
            for (int row = 0; row < size; ++row) {
                if (i == row) continue;
                float ratio = augMat.get(row, i) / augMat.get(i, i);
                for (int col = 0; col < augmentedSize; ++col) {
                    augMat.set(row, col, augMat.get(row, col) - ratio * augMat.get(i, col));
                }
            }
        }
        for (int row = 0; row < size; ++row) {
            float div = augMat.get(row, row);
            for (int col = 0; col < augmentedSize; ++col) {
                augMat.set(row, col, augMat.get(row, col) / div);
            }
        }
        return augMat.getAugmentation();
    }

    public Matrix2f toMatrix2() {
        return new Matrix2f(this);
    }

    public Matrix3f toMatrix3() {
        return new Matrix3f(this);
    }

    public Matrix4f toMatrix4() {
        return new Matrix4f(this);
    }

    public float[] toArray() {
        return this.toArray(false);
    }

    @Override
    public MatrixNf toFloat() {
        int size = this.size();
        float[] m = new float[size * size];
        for (int row = 0; row < size; ++row) {
            for (int col = 0; col < size; ++col) {
                m[col + row * size] = this.get(row, col);
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
    public float[] toArray(boolean columnMajor) {
        float[] array;
        int size = this.size();
        array = new float[size * size];
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
        if (!(obj instanceof MatrixNf)) {
            return false;
        }
        return Arrays.deepEquals((Object[])this.mat, (Object[])((MatrixNf)obj).mat);
    }

    public int hashCode() {
        return 395 + Arrays.deepHashCode((Object[])this.mat);
    }

    public MatrixNf clone() {
        return new MatrixNf(this);
    }

    public static MatrixNf createScaling(VectorNf v) {
        return MatrixNf.createScaling(v.toArray());
    }

    public static /* varargs */ MatrixNf createScaling(float ... vec) {
        int size = vec.length;
        MatrixNf m = new MatrixNf(size);
        for (int rowCol = 0; rowCol < size; ++rowCol) {
            m.set(rowCol, rowCol, vec[rowCol]);
        }
        return m;
    }

    public static MatrixNf createTranslation(VectorNf v) {
        return MatrixNf.createTranslation(v.toArray());
    }

    public static /* varargs */ MatrixNf createTranslation(float ... vec) {
        int size = vec.length;
        MatrixNf m = new MatrixNf(size + 1);
        for (int row = 0; row < size; ++row) {
            m.set(row, size, vec[row]);
        }
        return m;
    }

    public static MatrixNf createRotation(int size, Complexf rot) {
        if (size < 2) {
            throw new IllegalArgumentException("Minimum matrix size is 2");
        }
        MatrixNf m = new MatrixNf(size);
        rot = rot.normalize();
        m.set(0, 0, rot.getX());
        m.set(0, 1, - rot.getY());
        m.set(1, 0, rot.getY());
        m.set(1, 1, rot.getX());
        return m;
    }

    public static MatrixNf createRotation(int size, Quaternionf rot) {
        if (size < 3) {
            throw new IllegalArgumentException("Minimum matrix size is 3");
        }
        MatrixNf m = new MatrixNf(size);
        rot = rot.normalize();
        m.set(0, 0, 1.0f - 2.0f * rot.getY() * rot.getY() - 2.0f * rot.getZ() * rot.getZ());
        m.set(0, 1, 2.0f * rot.getX() * rot.getY() - 2.0f * rot.getW() * rot.getZ());
        m.set(0, 2, 2.0f * rot.getX() * rot.getZ() + 2.0f * rot.getW() * rot.getY());
        m.set(1, 0, 2.0f * rot.getX() * rot.getY() + 2.0f * rot.getW() * rot.getZ());
        m.set(1, 1, 1.0f - 2.0f * rot.getX() * rot.getX() - 2.0f * rot.getZ() * rot.getZ());
        m.set(1, 2, 2.0f * rot.getY() * rot.getZ() - 2.0f * rot.getW() * rot.getX());
        m.set(2, 0, 2.0f * rot.getX() * rot.getZ() - 2.0f * rot.getW() * rot.getY());
        m.set(2, 1, 2.0f * rot.getY() * rot.getZ() + 2.0f * rot.getX() * rot.getW());
        m.set(2, 2, 1.0f - 2.0f * rot.getX() * rot.getX() - 2.0f * rot.getY() * rot.getY());
        return m;
    }

    public static MatrixNf createLookAt(int size, Vector3f eye, Vector3f at, Vector3f up) {
        if (size < 4) {
            throw new IllegalArgumentException("Minimum matrix size is 4");
        }
        Vector3f f = at.sub(eye).normalize();
        up = up.normalize();
        Vector3f s = f.cross(up).normalize();
        Vector3f u = s.cross(f).normalize();
        MatrixNf mat = new MatrixNf(size);
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

    public static MatrixNf createPerspective(int size, double fov, double aspect, double near, double far) {
        return MatrixNf.createPerspective(size, (float)fov, (float)aspect, (float)near, (float)far);
    }

    public static MatrixNf createPerspective(int size, float fov, float aspect, float near, float far) {
        if (size < 4) {
            throw new IllegalArgumentException("Minimum matrix size is 4");
        }
        MatrixNf perspective = new MatrixNf(size);
        float scale = 1.0f / TrigMath.tan(fov * 0.008726646f);
        perspective.set(0, 0, scale / aspect);
        perspective.set(1, 1, scale);
        perspective.set(2, 2, (far + near) / (near - far));
        perspective.set(2, 3, 2.0f * far * near / (near - far));
        perspective.set(3, 2, -1.0f);
        perspective.set(3, 3, 0.0f);
        return perspective;
    }

    public static MatrixNf createOrthographic(int size, double right, double left, double top, double bottom, double near, double far) {
        return MatrixNf.createOrthographic(size, (float)right, (float)left, (float)top, (float)bottom, (float)near, (float)far);
    }

    public static MatrixNf createOrthographic(int size, float right, float left, float top, float bottom, float near, float far) {
        if (size < 4) {
            throw new IllegalArgumentException("Minimum matrix size is 4");
        }
        MatrixNf orthographic = new MatrixNf(size);
        orthographic.set(0, 0, 2.0f / (right - left));
        orthographic.set(1, 1, 2.0f / (top - bottom));
        orthographic.set(2, 2, -2.0f / (far - near));
        orthographic.set(0, 3, (- right + left) / (right - left));
        orthographic.set(1, 3, (- top + bottom) / (top - bottom));
        orthographic.set(2, 3, (- far + near) / (far - near));
        return orthographic;
    }

    private static float[][] deepClone(float[][] array) {
        int size = array.length;
        float[][] clone = (float[][])array.clone();
        for (int i = 0; i < size; ++i) {
            clone[i] = (float[])array[i].clone();
        }
        return clone;
    }

    private static class AugmentedMatrixN {
        private final MatrixNf mat;
        private final MatrixNf aug;
        private final int size;

        private AugmentedMatrixN(MatrixNf mat) {
            this.mat = mat.clone();
            this.size = mat.size();
            this.aug = new MatrixNf(this.size);
        }

        private MatrixNf getAugmentation() {
            return this.aug;
        }

        private int getAugmentedSize() {
            return this.size * 2;
        }

        private float get(int row, int col) {
            if (col < this.size) {
                return this.mat.get(row, col);
            }
            return this.aug.get(row, col - this.size);
        }

        private void set(int row, int col, float val) {
            if (col < this.size) {
                this.mat.set(row, col, val);
            } else {
                this.aug.set(row, col - this.size, val);
            }
        }
    }

    private static class ImmutableIdentityMatrixN
    extends MatrixNf {
        public ImmutableIdentityMatrixN(int size) {
            super(size);
        }

        @Override
        public void set(int row, int col, float val) {
            throw new UnsupportedOperationException("You may not alter this matrix");
        }

        @Override
        public void setZero() {
            throw new UnsupportedOperationException("You may not alter this matrix");
        }
    }

}

