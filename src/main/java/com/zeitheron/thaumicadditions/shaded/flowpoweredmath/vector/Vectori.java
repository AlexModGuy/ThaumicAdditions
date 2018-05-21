/*
 * Decompiled with CFR 0_123.
 */
package com.zeitheron.thaumicadditions.shaded.flowpoweredmath.vector;

import com.zeitheron.thaumicadditions.shaded.flowpoweredmath.vector.Vectord;
import com.zeitheron.thaumicadditions.shaded.flowpoweredmath.vector.Vectorf;
import com.zeitheron.thaumicadditions.shaded.flowpoweredmath.vector.Vectorl;

public interface Vectori {
    public Vectori mul(int var1);

    public Vectori div(int var1);

    public Vectori pow(int var1);

    public Vectori abs();

    public Vectori negate();

    public float length();

    public int lengthSquared();

    public int getMinAxis();

    public int getMaxAxis();

    public int[] toArray();

    public Vectori toInt();

    public Vectorl toLong();

    public Vectorf toFloat();

    public Vectord toDouble();
}

