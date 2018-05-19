/*
 * Decompiled with CFR 0_123.
 */
package com.endie.thaumicadditions.shaded.flowpoweredmath.vector;

import com.endie.thaumicadditions.shaded.flowpoweredmath.vector.Vectorf;
import com.endie.thaumicadditions.shaded.flowpoweredmath.vector.Vectori;
import com.endie.thaumicadditions.shaded.flowpoweredmath.vector.Vectorl;

public interface Vectord {
    public Vectord mul(double var1);

    public Vectord div(double var1);

    public Vectord pow(double var1);

    public Vectord ceil();

    public Vectord floor();

    public Vectord round();

    public Vectord abs();

    public Vectord negate();

    public double length();

    public double lengthSquared();

    public Vectord normalize();

    public int getMinAxis();

    public int getMaxAxis();

    public double[] toArray();

    public Vectori toInt();

    public Vectorl toLong();

    public Vectorf toFloat();

    public Vectord toDouble();
}

