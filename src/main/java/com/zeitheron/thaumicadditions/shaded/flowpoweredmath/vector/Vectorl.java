/*
 * Decompiled with CFR 0_123.
 */
package com.zeitheron.thaumicadditions.shaded.flowpoweredmath.vector;

import com.zeitheron.thaumicadditions.shaded.flowpoweredmath.vector.Vectord;
import com.zeitheron.thaumicadditions.shaded.flowpoweredmath.vector.Vectorf;
import com.zeitheron.thaumicadditions.shaded.flowpoweredmath.vector.Vectori;

public interface Vectorl {
    public Vectorl mul(long var1);

    public Vectorl div(long var1);

    public Vectorl pow(long var1);

    public Vectorl abs();

    public Vectorl negate();

    public double length();

    public long lengthSquared();

    public int getMinAxis();

    public int getMaxAxis();

    public long[] toArray();

    public Vectori toInt();

    public Vectorl toLong();

    public Vectorf toFloat();

    public Vectord toDouble();
}

