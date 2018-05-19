/*
 * Decompiled with CFR 0_123.
 */
package com.endie.thaumicadditions.shaded.flowpoweredmath.imaginary;

import com.endie.thaumicadditions.shaded.flowpoweredmath.imaginary.Imaginaryf;

public interface Imaginaryd {
    public Imaginaryd mul(double var1);

    public Imaginaryd div(double var1);

    public Imaginaryd conjugate();

    public Imaginaryd invert();

    public double length();

    public double lengthSquared();

    public Imaginaryd normalize();

    public Imaginaryf toFloat();

    public Imaginaryd toDouble();
}

