package com.zeitheron.thaumicadditions.shaded.flowpoweredmath.matrix;

import java.io.Serializable;

import com.zeitheron.thaumicadditions.shaded.flowpoweredmath.GenericMath;
import com.zeitheron.thaumicadditions.shaded.flowpoweredmath.HashFunctions;
import com.zeitheron.thaumicadditions.shaded.flowpoweredmath.TrigMath;
import com.zeitheron.thaumicadditions.shaded.flowpoweredmath.imaginary.Complexf;
import com.zeitheron.thaumicadditions.shaded.flowpoweredmath.imaginary.Quaternionf;
import com.zeitheron.thaumicadditions.shaded.flowpoweredmath.vector.Vector3f;
import com.zeitheron.thaumicadditions.shaded.flowpoweredmath.vector.Vector4f;

public class Matrix4f implements Matrixf, Serializable, Cloneable
{
	private static final long serialVersionUID = 1;
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
	
	public Matrix4f(Matrix2f m)
	{
		this(m.get(0, 0), m.get(0, 1), 0.0f, 0.0f, m.get(1, 0), m.get(1, 1), 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f);
	}
	
	public Matrix4f(Matrix3f m)
	{
		this(m.get(0, 0), m.get(0, 1), m.get(0, 2), 0.0f, m.get(1, 0), m.get(1, 1), m.get(1, 2), 0.0f, m.get(2, 0), m.get(2, 1), m.get(2, 2), 0.0f, 0.0f, 0.0f, 0.0f, 0.0f);
	}
	
	public Matrix4f(Matrix4f m)
	{
		this(m.m00, m.m01, m.m02, m.m03, m.m10, m.m11, m.m12, m.m13, m.m20, m.m21, m.m22, m.m23, m.m30, m.m31, m.m32, m.m33);
	}
	
	public Matrix4f(MatrixNf m)
	{
		this.m00 = m.get(0, 0);
		this.m01 = m.get(0, 1);
		this.m10 = m.get(1, 0);
		this.m11 = m.get(1, 1);
		if(m.size() > 2)
		{
			this.m02 = m.get(0, 2);
			this.m12 = m.get(1, 2);
			this.m20 = m.get(2, 0);
			this.m21 = m.get(2, 1);
			this.m22 = m.get(2, 2);
			if(m.size() > 3)
			{
				this.m03 = m.get(0, 3);
				this.m13 = m.get(1, 3);
				this.m23 = m.get(2, 3);
				this.m30 = m.get(3, 0);
				this.m31 = m.get(3, 1);
				this.m32 = m.get(3, 2);
				this.m33 = m.get(3, 3);
			} else
			{
				this.m03 = 0.0f;
				this.m13 = 0.0f;
				this.m23 = 0.0f;
				this.m30 = 0.0f;
				this.m31 = 0.0f;
				this.m32 = 0.0f;
				this.m33 = 0.0f;
			}
		} else
		{
			this.m02 = 0.0f;
			this.m12 = 0.0f;
			this.m20 = 0.0f;
			this.m21 = 0.0f;
			this.m22 = 0.0f;
			this.m03 = 0.0f;
			this.m13 = 0.0f;
			this.m23 = 0.0f;
			this.m30 = 0.0f;
			this.m31 = 0.0f;
			this.m32 = 0.0f;
			this.m33 = 0.0f;
		}
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
	
	@Override
	public float get(int row, int col)
	{
		switch(row)
		{
		case 0:
		{
			switch(col)
			{
			case 0:
			{
				return this.m00;
			}
			case 1:
			{
				return this.m01;
			}
			case 2:
			{
				return this.m02;
			}
			case 3:
			{
				return this.m03;
			}
			}
		}
		case 1:
		{
			switch(col)
			{
			case 0:
			{
				return this.m10;
			}
			case 1:
			{
				return this.m11;
			}
			case 2:
			{
				return this.m12;
			}
			case 3:
			{
				return this.m13;
			}
			}
		}
		case 2:
		{
			switch(col)
			{
			case 0:
			{
				return this.m20;
			}
			case 1:
			{
				return this.m21;
			}
			case 2:
			{
				return this.m22;
			}
			case 3:
			{
				return this.m23;
			}
			}
		}
		case 3:
		{
			switch(col)
			{
			case 0:
			{
				return this.m30;
			}
			case 1:
			{
				return this.m31;
			}
			case 2:
			{
				return this.m32;
			}
			case 3:
			{
				return this.m33;
			}
			}
		}
		}
		throw new IllegalArgumentException((row < 0 || row > 2 ? "row must be greater than zero and smaller than 3. " : "") + (col < 0 || col > 2 ? "col must be greater than zero and smaller than 3." : ""));
	}
	
	@Override
	public Vector4f getRow(int row)
	{
		return new Vector4f(this.get(row, 0), this.get(row, 1), this.get(row, 2), this.get(row, 3));
	}
	
	@Override
	public Vector4f getColumn(int col)
	{
		return new Vector4f(this.get(0, col), this.get(1, col), this.get(2, col), this.get(3, col));
	}
	
	public Matrix4f add(Matrix4f m)
	{
		return new Matrix4f(this.m00 + m.m00, this.m01 + m.m01, this.m02 + m.m02, this.m03 + m.m03, this.m10 + m.m10, this.m11 + m.m11, this.m12 + m.m12, this.m13 + m.m13, this.m20 + m.m20, this.m21 + m.m21, this.m22 + m.m22, this.m23 + m.m23, this.m30 + m.m30, this.m31 + m.m31, this.m32 + m.m32, this.m33 + m.m33);
	}
	
	public Matrix4f sub(Matrix4f m)
	{
		return new Matrix4f(this.m00 - m.m00, this.m01 - m.m01, this.m02 - m.m02, this.m03 - m.m03, this.m10 - m.m10, this.m11 - m.m11, this.m12 - m.m12, this.m13 - m.m13, this.m20 - m.m20, this.m21 - m.m21, this.m22 - m.m22, this.m23 - m.m23, this.m30 - m.m30, this.m31 - m.m31, this.m32 - m.m32, this.m33 - m.m33);
	}
	
	public Matrix4f mul(double a)
	{
		return this.mul((float) a);
	}
	
	@Override
	public Matrix4f mul(float a)
	{
		return new Matrix4f(this.m00 * a, this.m01 * a, this.m02 * a, this.m03 * a, this.m10 * a, this.m11 * a, this.m12 * a, this.m13 * a, this.m20 * a, this.m21 * a, this.m22 * a, this.m23 * a, this.m30 * a, this.m31 * a, this.m32 * a, this.m33 * a);
	}
	
	public Matrix4f mul(Matrix4f m)
	{
		return new Matrix4f(this.m00 * m.m00 + this.m01 * m.m10 + this.m02 * m.m20 + this.m03 * m.m30, this.m00 * m.m01 + this.m01 * m.m11 + this.m02 * m.m21 + this.m03 * m.m31, this.m00 * m.m02 + this.m01 * m.m12 + this.m02 * m.m22 + this.m03 * m.m32, this.m00 * m.m03 + this.m01 * m.m13 + this.m02 * m.m23 + this.m03 * m.m33, this.m10 * m.m00 + this.m11 * m.m10 + this.m12 * m.m20 + this.m13 * m.m30, this.m10 * m.m01 + this.m11 * m.m11 + this.m12 * m.m21 + this.m13 * m.m31, this.m10 * m.m02 + this.m11 * m.m12 + this.m12 * m.m22 + this.m13 * m.m32, this.m10 * m.m03 + this.m11 * m.m13 + this.m12 * m.m23 + this.m13 * m.m33, this.m20 * m.m00 + this.m21 * m.m10 + this.m22 * m.m20 + this.m23 * m.m30, this.m20 * m.m01 + this.m21 * m.m11 + this.m22 * m.m21 + this.m23 * m.m31, this.m20 * m.m02 + this.m21 * m.m12 + this.m22 * m.m22 + this.m23 * m.m32, this.m20 * m.m03 + this.m21 * m.m13 + this.m22 * m.m23 + this.m23 * m.m33, this.m30 * m.m00 + this.m31 * m.m10 + this.m32 * m.m20 + this.m33 * m.m30, this.m30 * m.m01 + this.m31 * m.m11 + this.m32 * m.m21 + this.m33 * m.m31, this.m30 * m.m02 + this.m31 * m.m12 + this.m32 * m.m22 + this.m33 * m.m32, this.m30 * m.m03 + this.m31 * m.m13 + this.m32 * m.m23 + this.m33 * m.m33);
	}
	
	public Matrix4f div(double a)
	{
		return this.div((float) a);
	}
	
	@Override
	public Matrix4f div(float a)
	{
		return new Matrix4f(this.m00 / a, this.m01 / a, this.m02 / a, this.m03 / a, this.m10 / a, this.m11 / a, this.m12 / a, this.m13 / a, this.m20 / a, this.m21 / a, this.m22 / a, this.m23 / a, this.m30 / a, this.m31 / a, this.m32 / a, this.m33 / a);
	}
	
	public Matrix4f div(Matrix4f m)
	{
		return this.mul(m.invert());
	}
	
	public Matrix4f pow(double pow)
	{
		return this.pow((float) pow);
	}
	
	@Override
	public Matrix4f pow(float pow)
	{
		return new Matrix4f(Math.pow(this.m00, pow), Math.pow(this.m01, pow), Math.pow(this.m02, pow), Math.pow(this.m03, pow), Math.pow(this.m10, pow), Math.pow(this.m11, pow), Math.pow(this.m12, pow), Math.pow(this.m13, pow), Math.pow(this.m20, pow), Math.pow(this.m21, pow), Math.pow(this.m22, pow), Math.pow(this.m23, pow), Math.pow(this.m30, pow), Math.pow(this.m31, pow), Math.pow(this.m32, pow), Math.pow(this.m33, pow));
	}
	
	public Matrix4f translate(Vector3f v)
	{
		return this.translate(v.getX(), v.getY(), v.getZ());
	}
	
	public Matrix4f translate(double x, double y, double z)
	{
		return this.translate((float) x, (float) y, (float) z);
	}
	
	public Matrix4f translate(float x, float y, float z)
	{
		return Matrix4f.createTranslation(x, y, z).mul(this);
	}
	
	public Matrix4f scale(double scale)
	{
		return this.scale((float) scale);
	}
	
	public Matrix4f scale(float scale)
	{
		return this.scale(scale, scale, scale, scale);
	}
	
	public Matrix4f scale(Vector4f v)
	{
		return this.scale(v.getX(), v.getY(), v.getZ(), v.getW());
	}
	
	public Matrix4f scale(double x, double y, double z, double w)
	{
		return this.scale((float) x, (float) y, (float) z, (float) w);
	}
	
	public Matrix4f scale(float x, float y, float z, float w)
	{
		return Matrix4f.createScaling(x, y, z, w).mul(this);
	}
	
	public Matrix4f rotate(Complexf rot)
	{
		return Matrix4f.createRotation(rot).mul(this);
	}
	
	public Matrix4f rotate(Quaternionf rot)
	{
		return Matrix4f.createRotation(rot).mul(this);
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
	
	@Override
	public Matrix4f floor()
	{
		return new Matrix4f(GenericMath.floor(this.m00), GenericMath.floor(this.m01), GenericMath.floor(this.m02), GenericMath.floor(this.m03), GenericMath.floor(this.m10), GenericMath.floor(this.m11), GenericMath.floor(this.m12), GenericMath.floor(this.m13), GenericMath.floor(this.m20), GenericMath.floor(this.m21), GenericMath.floor(this.m22), GenericMath.floor(this.m23), GenericMath.floor(this.m30), GenericMath.floor(this.m31), GenericMath.floor(this.m32), GenericMath.floor(this.m33));
	}
	
	@Override
	public Matrix4f ceil()
	{
		return new Matrix4f(Math.ceil(this.m00), Math.ceil(this.m01), Math.ceil(this.m02), Math.ceil(this.m03), Math.ceil(this.m10), Math.ceil(this.m11), Math.ceil(this.m12), Math.ceil(this.m13), Math.ceil(this.m20), Math.ceil(this.m21), Math.ceil(this.m22), Math.ceil(this.m23), Math.ceil(this.m30), Math.ceil(this.m31), Math.ceil(this.m32), Math.ceil(this.m33));
	}
	
	@Override
	public Matrix4f round()
	{
		return new Matrix4f(Math.round(this.m00), Math.round(this.m01), Math.round(this.m02), Math.round(this.m03), Math.round(this.m10), Math.round(this.m11), Math.round(this.m12), Math.round(this.m13), Math.round(this.m20), Math.round(this.m21), Math.round(this.m22), Math.round(this.m23), Math.round(this.m30), Math.round(this.m31), Math.round(this.m32), Math.round(this.m33));
	}
	
	@Override
	public Matrix4f abs()
	{
		return new Matrix4f(Math.abs(this.m00), Math.abs(this.m01), Math.abs(this.m02), Math.abs(this.m03), Math.abs(this.m10), Math.abs(this.m11), Math.abs(this.m12), Math.abs(this.m13), Math.abs(this.m20), Math.abs(this.m21), Math.abs(this.m22), Math.abs(this.m23), Math.abs(this.m30), Math.abs(this.m31), Math.abs(this.m32), Math.abs(this.m33));
	}
	
	@Override
	public Matrix4f negate()
	{
		return new Matrix4f(-this.m00, -this.m01, -this.m02, -this.m03, -this.m10, -this.m11, -this.m12, -this.m13, -this.m20, -this.m21, -this.m22, -this.m23, -this.m30, -this.m31, -this.m32, -this.m33);
	}
	
	@Override
	public Matrix4f transpose()
	{
		return new Matrix4f(this.m00, this.m10, this.m20, this.m30, this.m01, this.m11, this.m21, this.m31, this.m02, this.m12, this.m22, this.m32, this.m03, this.m13, this.m23, this.m33);
	}
	
	@Override
	public float trace()
	{
		return this.m00 + this.m11 + this.m22 + this.m33;
	}
	
	@Override
	public float determinant()
	{
		return this.m00 * (this.m11 * this.m22 * this.m33 + this.m21 * this.m32 * this.m13 + this.m31 * this.m12 * this.m23 - this.m31 * this.m22 * this.m13 - this.m11 * this.m32 * this.m23 - this.m21 * this.m12 * this.m33) - this.m10 * (this.m01 * this.m22 * this.m33 + this.m21 * this.m32 * this.m03 + this.m31 * this.m02 * this.m23 - this.m31 * this.m22 * this.m03 - this.m01 * this.m32 * this.m23 - this.m21 * this.m02 * this.m33) + this.m20 * (this.m01 * this.m12 * this.m33 + this.m11 * this.m32 * this.m03 + this.m31 * this.m02 * this.m13 - this.m31 * this.m12 * this.m03 - this.m01 * this.m32 * this.m13 - this.m11 * this.m02 * this.m33) - this.m30 * (this.m01 * this.m12 * this.m23 + this.m11 * this.m22 * this.m03 + this.m21 * this.m02 * this.m13 - this.m21 * this.m12 * this.m03 - this.m01 * this.m22 * this.m13 - this.m11 * this.m02 * this.m23);
	}
	
	@Override
	public Matrix4f invert()
	{
		float det = this.determinant();
		if(Math.abs(det) < GenericMath.FLT_EPSILON)
		{
			throw new ArithmeticException("Cannot inverse a matrix with a zero determinant");
		}
		return new Matrix4f(Matrix4f.det3(this.m11, this.m21, this.m31, this.m12, this.m22, this.m32, this.m13, this.m23, this.m33) / det, (-Matrix4f.det3(this.m01, this.m21, this.m31, this.m02, this.m22, this.m32, this.m03, this.m23, this.m33)) / det, Matrix4f.det3(this.m01, this.m11, this.m31, this.m02, this.m12, this.m32, this.m03, this.m13, this.m33) / det, (-Matrix4f.det3(this.m01, this.m11, this.m21, this.m02, this.m12, this.m22, this.m03, this.m13, this.m23)) / det, (-Matrix4f.det3(this.m10, this.m20, this.m30, this.m12, this.m22, this.m32, this.m13, this.m23, this.m33)) / det, Matrix4f.det3(this.m00, this.m20, this.m30, this.m02, this.m22, this.m32, this.m03, this.m23, this.m33) / det, (-Matrix4f.det3(this.m00, this.m10, this.m30, this.m02, this.m12, this.m32, this.m03, this.m13, this.m33)) / det, Matrix4f.det3(this.m00, this.m10, this.m20, this.m02, this.m12, this.m22, this.m03, this.m13, this.m23) / det, Matrix4f.det3(this.m10, this.m20, this.m30, this.m11, this.m21, this.m31, this.m13, this.m23, this.m33) / det, (-Matrix4f.det3(this.m00, this.m20, this.m30, this.m01, this.m21, this.m31, this.m03, this.m23, this.m33)) / det, Matrix4f.det3(this.m00, this.m10, this.m30, this.m01, this.m11, this.m31, this.m03, this.m13, this.m33) / det, (-Matrix4f.det3(this.m00, this.m10, this.m20, this.m01, this.m11, this.m21, this.m03, this.m13, this.m23)) / det, (-Matrix4f.det3(this.m10, this.m20, this.m30, this.m11, this.m21, this.m31, this.m12, this.m22, this.m32)) / det, Matrix4f.det3(this.m00, this.m20, this.m30, this.m01, this.m21, this.m31, this.m02, this.m22, this.m32) / det, (-Matrix4f.det3(this.m00, this.m10, this.m30, this.m01, this.m11, this.m31, this.m02, this.m12, this.m32)) / det, Matrix4f.det3(this.m00, this.m10, this.m20, this.m01, this.m11, this.m21, this.m02, this.m12, this.m22) / det);
	}
	
	public Matrix2f toMatrix2()
	{
		return new Matrix2f(this);
	}
	
	public Matrix3f toMatrix3()
	{
		return new Matrix3f(this);
	}
	
	public MatrixNf toMatrixN()
	{
		return new MatrixNf(this);
	}
	
	public float[] toArray()
	{
		return this.toArray(false);
	}
	
	@Override
	public float[] toArray(boolean columnMajor)
	{
		if(columnMajor)
		{
			return new float[] { this.m00, this.m10, this.m20, this.m30, this.m01, this.m11, this.m21, this.m31, this.m02, this.m12, this.m22, this.m32, this.m03, this.m13, this.m23, this.m33 };
		}
		return new float[] { this.m00, this.m01, this.m02, this.m03, this.m10, this.m11, this.m12, this.m13, this.m20, this.m21, this.m22, this.m23, this.m30, this.m31, this.m32, this.m33 };
	}
	
	@Override
	public Matrix4f toFloat()
	{
		return new Matrix4f(this.m00, this.m01, this.m02, this.m03, this.m10, this.m11, this.m12, this.m13, this.m20, this.m21, this.m22, this.m23, this.m30, this.m31, this.m32, this.m33);
	}
	
	@Override
	public Matrix4d toDouble()
	{
		return new Matrix4d(this.m00, this.m01, this.m02, this.m03, this.m10, this.m11, this.m12, this.m13, this.m20, this.m21, this.m22, this.m23, this.m30, this.m31, this.m32, this.m33);
	}
	
	public String toString()
	{
		return "" + this.m00 + " " + this.m01 + " " + this.m02 + " " + this.m03 + "\n" + this.m10 + " " + this.m11 + " " + this.m12 + " " + this.m13 + "\n" + this.m20 + " " + this.m21 + " " + this.m22 + " " + this.m23 + "\n" + this.m30 + " " + this.m31 + " " + this.m32 + " " + this.m33 + "\n";
	}
	
	public boolean equals(Object o)
	{
		if(this == o)
		{
			return true;
		}
		if(!(o instanceof Matrix4f))
		{
			return false;
		}
		Matrix4f matrix4 = (Matrix4f) o;
		if(Float.compare(matrix4.m00, this.m00) != 0)
		{
			return false;
		}
		if(Float.compare(matrix4.m01, this.m01) != 0)
		{
			return false;
		}
		if(Float.compare(matrix4.m02, this.m02) != 0)
		{
			return false;
		}
		if(Float.compare(matrix4.m03, this.m03) != 0)
		{
			return false;
		}
		if(Float.compare(matrix4.m10, this.m10) != 0)
		{
			return false;
		}
		if(Float.compare(matrix4.m11, this.m11) != 0)
		{
			return false;
		}
		if(Float.compare(matrix4.m12, this.m12) != 0)
		{
			return false;
		}
		if(Float.compare(matrix4.m13, this.m13) != 0)
		{
			return false;
		}
		if(Float.compare(matrix4.m20, this.m20) != 0)
		{
			return false;
		}
		if(Float.compare(matrix4.m21, this.m21) != 0)
		{
			return false;
		}
		if(Float.compare(matrix4.m22, this.m22) != 0)
		{
			return false;
		}
		if(Float.compare(matrix4.m23, this.m23) != 0)
		{
			return false;
		}
		if(Float.compare(matrix4.m30, this.m30) != 0)
		{
			return false;
		}
		if(Float.compare(matrix4.m31, this.m31) != 0)
		{
			return false;
		}
		if(Float.compare(matrix4.m32, this.m32) != 0)
		{
			return false;
		}
		if(Float.compare(matrix4.m33, this.m33) != 0)
		{
			return false;
		}
		return true;
	}
	
	public int hashCode()
	{
		if(!this.hashed)
		{
			int result = this.m00 != 0.0f ? HashFunctions.hash(this.m00) : 0;
			result = 31 * result + (this.m01 != 0.0f ? HashFunctions.hash(this.m01) : 0);
			result = 31 * result + (this.m02 != 0.0f ? HashFunctions.hash(this.m02) : 0);
			result = 31 * result + (this.m03 != 0.0f ? HashFunctions.hash(this.m03) : 0);
			result = 31 * result + (this.m10 != 0.0f ? HashFunctions.hash(this.m10) : 0);
			result = 31 * result + (this.m11 != 0.0f ? HashFunctions.hash(this.m11) : 0);
			result = 31 * result + (this.m12 != 0.0f ? HashFunctions.hash(this.m12) : 0);
			result = 31 * result + (this.m13 != 0.0f ? HashFunctions.hash(this.m13) : 0);
			result = 31 * result + (this.m20 != 0.0f ? HashFunctions.hash(this.m20) : 0);
			result = 31 * result + (this.m21 != 0.0f ? HashFunctions.hash(this.m21) : 0);
			result = 31 * result + (this.m22 != 0.0f ? HashFunctions.hash(this.m22) : 0);
			result = 31 * result + (this.m23 != 0.0f ? HashFunctions.hash(this.m23) : 0);
			result = 31 * result + (this.m30 != 0.0f ? HashFunctions.hash(this.m30) : 0);
			result = 31 * result + (this.m31 != 0.0f ? HashFunctions.hash(this.m31) : 0);
			result = 31 * result + (this.m32 != 0.0f ? HashFunctions.hash(this.m32) : 0);
			this.hashCode = 31 * result + (this.m33 != 0.0f ? HashFunctions.hash(this.m33) : 0);
			this.hashed = true;
		}
		return this.hashCode;
	}
	
	public Matrix4f clone()
	{
		return new Matrix4f(this);
	}
	
	public static Matrix4f from(float n)
	{
		return n == 0.0f ? ZERO : new Matrix4f(n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n);
	}
	
	public static Matrix4f from(float m00, float m01, float m02, float m03, float m10, float m11, float m12, float m13, float m20, float m21, float m22, float m23, float m30, float m31, float m32, float m33)
	{
		return m00 == 0.0f && m01 == 0.0f && m02 == 0.0f && m03 == 0.0f && m10 == 0.0f && m11 == 0.0f && m12 == 0.0f && m13 == 0.0f && m20 == 0.0f && m21 == 0.0f && m22 == 0.0f && m23 == 0.0f && m30 == 0.0f && m31 == 0.0f && m32 == 0.0f && m33 == 0.0f ? ZERO : new Matrix4f(m00, m01, m02, m03, m10, m11, m12, m13, m20, m21, m22, m23, m30, m31, m32, m33);
	}
	
	public static Matrix4f fromDiagonal(float m00, float m11, float m22, float m33)
	{
		return m00 == 0.0f && m11 == 0.0f && m22 == 0.0f && m33 == 0.0f ? ZERO : new Matrix4f(m00, 0.0f, 0.0f, 0.0f, 0.0f, m11, 0.0f, 0.0f, 0.0f, 0.0f, m22, 0.0f, 0.0f, 0.0f, 0.0f, m33);
	}
	
	public static Matrix4f createScaling(double scale)
	{
		return Matrix4f.createScaling((float) scale);
	}
	
	public static Matrix4f createScaling(float scale)
	{
		return Matrix4f.createScaling(scale, scale, scale, scale);
	}
	
	public static Matrix4f createScaling(Vector4f v)
	{
		return Matrix4f.createScaling(v.getX(), v.getY(), v.getZ(), v.getW());
	}
	
	public static Matrix4f createScaling(double x, double y, double z, double w)
	{
		return Matrix4f.createScaling((float) x, (float) y, (float) z, (float) w);
	}
	
	public static Matrix4f createScaling(float x, float y, float z, float w)
	{
		return new Matrix4f(x, 0.0f, 0.0f, 0.0f, 0.0f, y, 0.0f, 0.0f, 0.0f, 0.0f, z, 0.0f, 0.0f, 0.0f, 0.0f, w);
	}
	
	public static Matrix4f createTranslation(Vector3f v)
	{
		return Matrix4f.createTranslation(v.getX(), v.getY(), v.getZ());
	}
	
	public static Matrix4f createTranslation(double x, double y, double z)
	{
		return Matrix4f.createTranslation((float) x, (float) y, (float) z);
	}
	
	public static Matrix4f createTranslation(float x, float y, float z)
	{
		return new Matrix4f(1.0f, 0.0f, 0.0f, x, 0.0f, 1.0f, 0.0f, y, 0.0f, 0.0f, 1.0f, z, 0.0f, 0.0f, 0.0f, 1.0f);
	}
	
	public static Matrix4f createRotation(Complexf rot)
	{
		rot = rot.normalize();
		return new Matrix4f(rot.getX(), -rot.getY(), 0.0f, 0.0f, rot.getY(), rot.getX(), 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f);
	}
	
	public static Matrix4f createRotation(Quaternionf rot)
	{
		rot = rot.normalize();
		return new Matrix4f(1.0f - 2.0f * rot.getY() * rot.getY() - 2.0f * rot.getZ() * rot.getZ(), 2.0f * rot.getX() * rot.getY() - 2.0f * rot.getW() * rot.getZ(), 2.0f * rot.getX() * rot.getZ() + 2.0f * rot.getW() * rot.getY(), 0.0f, 2.0f * rot.getX() * rot.getY() + 2.0f * rot.getW() * rot.getZ(), 1.0f - 2.0f * rot.getX() * rot.getX() - 2.0f * rot.getZ() * rot.getZ(), 2.0f * rot.getY() * rot.getZ() - 2.0f * rot.getW() * rot.getX(), 0.0f, 2.0f * rot.getX() * rot.getZ() - 2.0f * rot.getW() * rot.getY(), 2.0f * rot.getY() * rot.getZ() + 2.0f * rot.getX() * rot.getW(), 1.0f - 2.0f * rot.getX() * rot.getX() - 2.0f * rot.getY() * rot.getY(), 0.0f, 0.0f, 0.0f, 0.0f, 1.0f);
	}
	
	public static Matrix4f createLookAt(Vector3f eye, Vector3f at, Vector3f up)
	{
		Vector3f f = at.sub(eye).normalize();
		Vector3f s = f.cross(up).normalize();
		Vector3f u = s.cross(f);
		Matrix4f mat = new Matrix4f(s.getX(), s.getY(), s.getZ(), 0.0f, u.getX(), u.getY(), u.getZ(), 0.0f, -f.getX(), -f.getY(), -f.getZ(), 0.0f, 0.0f, 0.0f, 0.0f, 1.0f);
		return mat.translate(eye.negate());
	}
	
	public static Matrix4f createPerspective(double fov, double aspect, double near, double far)
	{
		return Matrix4f.createPerspective((float) fov, (float) aspect, (float) near, (float) far);
	}
	
	public static Matrix4f createPerspective(float fov, float aspect, float near, float far)
	{
		float scale = 1.0f / TrigMath.tan(fov * 0.008726646f);
		return new Matrix4f(scale / aspect, 0.0f, 0.0f, 0.0f, 0.0f, scale, 0.0f, 0.0f, 0.0f, 0.0f, (far + near) / (near - far), 2.0f * far * near / (near - far), 0.0f, 0.0f, -1.0f, 0.0f);
	}
	
	public static Matrix4f createOrthographic(double right, double left, double top, double bottom, double near, double far)
	{
		return Matrix4f.createOrthographic((float) right, (float) left, (float) top, (float) bottom, (float) near, (float) far);
	}
	
	public static Matrix4f createOrthographic(float right, float left, float top, float bottom, float near, float far)
	{
		return new Matrix4f(2.0f / (right - left), 0.0f, 0.0f, (-right + left) / (right - left), 0.0f, 2.0f / (top - bottom), 0.0f, (-top + bottom) / (top - bottom), 0.0f, 0.0f, -2.0f / (far - near), (-far + near) / (far - near), 0.0f, 0.0f, 0.0f, 1.0f);
	}
	
	private static float det3(float m00, float m01, float m02, float m10, float m11, float m12, float m20, float m21, float m22)
	{
		return m00 * (m11 * m22 - m12 * m21) - m01 * (m10 * m22 - m12 * m20) + m02 * (m10 * m21 - m11 * m20);
	}
}
