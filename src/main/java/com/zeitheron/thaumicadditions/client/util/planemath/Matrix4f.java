package com.zeitheron.thaumicadditions.client.util.planemath;

import javax.vecmath.Vector4f;

public class Matrix4f
{
	public static final Matrix4f ZERO = new Matrix4f(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f);
	public static final Matrix4f IDENTITY = new Matrix4f();
	private final float m00;
	private final float m01;
	private final float m02;
	private final float m03;
	private final float m10;
	private final float m11;
	private final float m12;
	private final float m13;
	private final float m20;
	private final float m21;
	private final float m22;
	private final float m23;
	private final float m30;
	private final float m31;
	private final float m32;
	private final float m33;
	private volatile transient boolean hashed = false;
	private volatile transient int hashCode = 0;
	
	public Matrix4f()
	{
		this(1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f);
	}
	
	public Matrix4f(Matrix4f m)
	{
		this(m.m00, m.m01, m.m02, m.m03, m.m10, m.m11, m.m12, m.m13, m.m20, m.m21, m.m22, m.m23, m.m30, m.m31, m.m32, m.m33);
	}
	
	public Matrix4f(double m00, double m01, double m02, double m03, double m10, double m11, double m12, double m13, double m20, double m21, double m22, double m23, double m30, double m31, double m32, double m33)
	{
		this((float) m00, (float) m01, (float) m02, (float) m03, (float) m10, (float) m11, (float) m12, (float) m13, (float) m20, (float) m21, (float) m22, (float) m23, (float) m30, (float) m31, (float) m32, (float) m33);
	}
	
	public Matrix4f(float m00, float m01, float m02, float m03, float m10, float m11, float m12, float m13, float m20, float m21, float m22, float m23, float m30, float m31, float m32, float m33)
	{
		this.m00 = m00;
		this.m01 = m01;
		this.m02 = m02;
		this.m03 = m03;
		this.m10 = m10;
		this.m11 = m11;
		this.m12 = m12;
		this.m13 = m13;
		this.m20 = m20;
		this.m21 = m21;
		this.m22 = m22;
		this.m23 = m23;
		this.m30 = m30;
		this.m31 = m31;
		this.m32 = m32;
		this.m33 = m33;
	}
	
	public static Matrix4f from(float m00, float m01, float m02, float m03, float m10, float m11, float m12, float m13, float m20, float m21, float m22, float m23, float m30, float m31, float m32, float m33)
	{
		return m00 == 0.0f && m01 == 0.0f && m02 == 0.0f && m03 == 0.0f && m10 == 0.0f && m11 == 0.0f && m12 == 0.0f && m13 == 0.0f && m20 == 0.0f && m21 == 0.0f && m22 == 0.0f && m23 == 0.0f && m30 == 0.0f && m31 == 0.0f && m32 == 0.0f && m33 == 0.0f ? ZERO : new Matrix4f(m00, m01, m02, m03, m10, m11, m12, m13, m20, m21, m22, m23, m30, m31, m32, m33);
	}
	
	public Vector4f transform(Vector4f v)
	{
		return this.transform(v.getX(), v.getY(), v.getZ(), v.getW());
	}
	
	public Vector4f transform(double x, double y, double z, double w)
	{
		return this.transform((float) x, (float) y, (float) z, (float) w);
	}
	
	public Vector4f transform(float x, float y, float z, float w)
	{
		return new Vector4f(this.m00 * x + this.m01 * y + this.m02 * z + this.m03 * w, this.m10 * x + this.m11 * y + this.m12 * z + this.m13 * w, this.m20 * x + this.m21 * y + this.m22 * z + this.m23 * w, this.m30 * x + this.m31 * y + this.m32 * z + this.m33 * w);
	}
}