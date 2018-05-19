/*
 * Decompiled with CFR 0_123.
 */
package com.endie.thaumicadditions.shaded.flowpoweredmath.vector;

import com.endie.thaumicadditions.shaded.flowpoweredmath.vector.Vectord;
import com.endie.thaumicadditions.shaded.flowpoweredmath.vector.Vectori;
import com.endie.thaumicadditions.shaded.flowpoweredmath.vector.Vectorl;

public interface Vectorf {
    public Vectorf mul(float var1);

    public Vectorf div(float var1);

    public Vectorf pow(float var1);

    public Vectorf ceil();

    public Vectorf floor();

    public Vectorf round();

    public Vectorf abs();

    public Vectorf negate();

    public float length();

    public float lengthSquared();

    public Vectorf normalize();

    public int getMinAxis();

    public int getMaxAxis();

    public float[] toArray();

    public Vectori toInt();

    public Vectorl toLong();

    public Vectorf toFloat();

    public Vectord toDouble();
}

