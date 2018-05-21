/* Decompiled with CFR 0_123. */
package com.zeitheron.thaumicadditions.shaded.flowpoweredmath.matrix;

import com.zeitheron.thaumicadditions.shaded.flowpoweredmath.vector.Vectorf;

public interface Matrixf
{
	public float get(int var1, int var2);
	
	public Vectorf getRow(int var1);
	
	public Vectorf getColumn(int var1);
	
	public Matrixf mul(float var1);
	
	public Matrixf div(float var1);
	
	public Matrixf pow(float var1);
	
	public Matrixf ceil();
	
	public Matrixf floor();
	
	public Matrixf round();
	
	public Matrixf abs();
	
	public Matrixf negate();
	
	public Matrixf transpose();
	
	public float trace();
	
	public float determinant();
	
	public Matrixf invert();
	
	public float[] toArray(boolean var1);
	
	public Matrixf toFloat();
	
	public Matrixd toDouble();
}
