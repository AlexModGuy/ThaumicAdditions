/*
 * Decompiled with CFR 0_123.
 */
package com.zeitheron.thaumicadditions.shaded.flowpoweredmath.matrix;

import com.zeitheron.thaumicadditions.shaded.flowpoweredmath.matrix.Matrixf;
import com.zeitheron.thaumicadditions.shaded.flowpoweredmath.vector.Vectord;

public interface Matrixd {
    public double get(int var1, int var2);

    public Vectord getRow(int var1);

    public Vectord getColumn(int var1);

    public Matrixd mul(double var1);

    public Matrixd div(double var1);

    public Matrixd pow(double var1);

    public Matrixd ceil();

    public Matrixd floor();

    public Matrixd round();

    public Matrixd abs();

    public Matrixd negate();

    public Matrixd transpose();

    public double trace();

    public double determinant();

    public Matrixd invert();

    public double[] toArray(boolean var1);

    public Matrixf toFloat();

    public Matrixd toDouble();
}

