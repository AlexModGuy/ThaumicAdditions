/*
 * Decompiled with CFR 0_123.
 */
package com.endie.thaumicadditions.shaded.flowpoweredmath.imaginary;

import com.endie.thaumicadditions.shaded.flowpoweredmath.imaginary.Imaginaryd;

public interface Imaginaryf {
    public Imaginaryf mul(float var1);

    public Imaginaryf div(float var1);

    public Imaginaryf conjugate();

    public Imaginaryf invert();

    public float length();

    public float lengthSquared();

    public Imaginaryf normalize();

    public Imaginaryf toFloat();

    public Imaginaryd toDouble();
}

