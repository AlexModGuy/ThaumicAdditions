package com.zeitheron.thaumicadditions.shaded.flowpoweredmath.matrix;

import java.io.Serializable;

import com.zeitheron.thaumicadditions.shaded.flowpoweredmath.GenericMath;
import com.zeitheron.thaumicadditions.shaded.flowpoweredmath.HashFunctions;
import com.zeitheron.thaumicadditions.shaded.flowpoweredmath.imaginary.Complexd;
import com.zeitheron.thaumicadditions.shaded.flowpoweredmath.vector.Vector2d;

public class Matrix2d implements Matrixd, Serializable, Cloneable
{
	private static final long serialVersionUID = 1;
	public static final Matrix2d ZERO = new Matrix2d(0.0f, 0.0f, 0.0f, 0.0f);
	public static final Matrix2d IDENTITY = new Matrix2d();
	private final double m00;
	private final double m01;
	private final double m10;
	private final double m11;
	private volatile transient boolean hashed = false;
	private volatile transient int hashCode = 0;
	
	public Matrix2d()
	{
		this(1.0f, 0.0f, 0.0f, 1.0f);
	}
	
	public Matrix2d(Matrix2d m)
	{
		this(m.m00, m.m01, m.m10, m.m11);
	}
	
	public Matrix2d(Matrix3d m)
	{
		this(m.get(0, 0), m.get(0, 1), m.get(1, 0), m.get(1, 1));
	}
	
	public Matrix2d(Matrix4d m)
	{
		this(m.get(0, 0), m.get(0, 1), m.get(1, 0), m.get(1, 1));
	}
	
	public Matrix2d(MatrixNd m)
	{
		this(m.get(0, 0), m.get(0, 1), m.get(1, 0), m.get(1, 1));
	}
	
	public Matrix2d(float m00, float m01, float m10, float m11)
	{
		this((double) m00, (double) m01, (double) m10, (double) m11);
	}
	
	public Matrix2d(double m00, double m01, double m10, double m11)
	{
		this.m00 = m00;
		this.m01 = m01;
		this.m10 = m10;
		this.m11 = m11;
	}
	
	@Override
	public double get(int row, int col)
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
			}
		}
		}
		throw new IllegalArgumentException((row < 0 || row > 1 ? "row must be greater than zero and smaller than 2. " : "") + (col < 0 || col > 1 ? "col must be greater than zero and smaller than 2." : ""));
	}
	
	@Override
	public Vector2d getRow(int row)
	{
		return new Vector2d(this.get(row, 0), this.get(row, 1));
	}
	
	@Override
	public Vector2d getColumn(int col)
	{
		return new Vector2d(this.get(0, col), this.get(1, col));
	}
	
	public Matrix2d add(Matrix2d m)
	{
		return new Matrix2d(this.m00 + m.m00, this.m01 + m.m01, this.m10 + m.m10, this.m11 + m.m11);
	}
	
	public Matrix2d sub(Matrix2d m)
	{
		return new Matrix2d(this.m00 - m.m00, this.m01 - m.m01, this.m10 - m.m10, this.m11 - m.m11);
	}
	
	public Matrix2d mul(float a)
	{
		return this.mul((double) a);
	}
	
	@Override
	public Matrix2d mul(double a)
	{
		return new Matrix2d(this.m00 * a, this.m01 * a, this.m10 * a, this.m11 * a);
	}
	
	public Matrix2d mul(Matrix2d m)
	{
		return new Matrix2d(this.m00 * m.m00 + this.m01 * m.m10, this.m00 * m.m01 + this.m01 * m.m11, this.m10 * m.m00 + this.m11 * m.m10, this.m10 * m.m01 + this.m11 * m.m11);
	}
	
	public Matrix2d div(float a)
	{
		return this.div((double) a);
	}
	
	@Override
	public Matrix2d div(double a)
	{
		return new Matrix2d(this.m00 / a, this.m01 / a, this.m10 / a, this.m11 / a);
	}
	
	public Matrix2d div(Matrix2d m)
	{
		return this.mul(m.invert());
	}
	
	public Matrix2d pow(float pow)
	{
		return this.pow((double) pow);
	}
	
	@Override
	public Matrix2d pow(double pow)
	{
		return new Matrix2d(Math.pow(this.m00, pow), Math.pow(this.m01, pow), Math.pow(this.m10, pow), Math.pow(this.m11, pow));
	}
	
	public Matrix2d translate(float x)
	{
		return this.translate((double) x);
	}
	
	public Matrix2d translate(double x)
	{
		return Matrix2d.createTranslation(x).mul(this);
	}
	
	public Matrix2d scale(float scale)
	{
		return this.scale((double) scale);
	}
	
	public Matrix2d scale(double scale)
	{
		return this.scale(scale, scale);
	}
	
	public Matrix2d scale(Vector2d v)
	{
		return this.scale(v.getX(), v.getY());
	}
	
	public Matrix2d scale(float x, float y)
	{
		return this.scale((double) x, (double) y);
	}
	
	public Matrix2d scale(double x, double y)
	{
		return Matrix2d.createScaling(x, y).mul(this);
	}
	
	public Matrix2d rotate(Complexd rot)
	{
		return Matrix2d.createRotation(rot).mul(this);
	}
	
	public Vector2d transform(Vector2d v)
	{
		return this.transform(v.getX(), v.getY());
	}
	
	public Vector2d transform(float x, float y)
	{
		return this.transform((double) x, (double) y);
	}
	
	public Vector2d transform(double x, double y)
	{
		return new Vector2d(this.m00 * x + this.m01 * y, this.m10 * x + this.m11 * y);
	}
	
	@Override
	public Matrix2d floor()
	{
		return new Matrix2d(GenericMath.floor(this.m00), GenericMath.floor(this.m01), GenericMath.floor(this.m10), GenericMath.floor(this.m11));
	}
	
	@Override
	public Matrix2d ceil()
	{
		return new Matrix2d(Math.ceil(this.m00), Math.ceil(this.m01), Math.ceil(this.m10), Math.ceil(this.m11));
	}
	
	@Override
	public Matrix2d round()
	{
		return new Matrix2d(Math.round(this.m00), Math.round(this.m01), Math.round(this.m10), Math.round(this.m11));
	}
	
	@Override
	public Matrix2d abs()
	{
		return new Matrix2d(Math.abs(this.m00), Math.abs(this.m01), Math.abs(this.m10), Math.abs(this.m11));
	}
	
	@Override
	public Matrix2d negate()
	{
		return new Matrix2d(-this.m00, -this.m01, -this.m10, -this.m11);
	}
	
	@Override
	public Matrix2d transpose()
	{
		return new Matrix2d(this.m00, this.m10, this.m01, this.m11);
	}
	
	@Override
	public double trace()
	{
		return this.m00 + this.m11;
	}
	
	@Override
	public double determinant()
	{
		return this.m00 * this.m11 - this.m01 * this.m10;
	}
	
	@Override
	public Matrix2d invert()
	{
		double det = this.determinant();
		if(Math.abs(det) < GenericMath.DBL_EPSILON)
		{
			throw new ArithmeticException("Cannot inverse a matrix with a zero determinant");
		}
		return new Matrix2d(this.m11 / det, (-this.m01) / det, (-this.m10) / det, this.m00 / det);
	}
	
	public Matrix3d toMatrix3()
	{
		return new Matrix3d(this);
	}
	
	public Matrix4d toMatrix4()
	{
		return new Matrix4d(this);
	}
	
	public MatrixNd toMatrixN()
	{
		return new MatrixNd(this);
	}
	
	public double[] toArray()
	{
		return this.toArray(false);
	}
	
	@Override
	public double[] toArray(boolean columnMajor)
	{
		if(columnMajor)
		{
			return new double[] { this.m00, this.m10, this.m01, this.m11 };
		}
		return new double[] { this.m00, this.m01, this.m10, this.m11 };
	}
	
	@Override
	public Matrix2f toFloat()
	{
		return new Matrix2f(this.m00, this.m01, this.m10, this.m11);
	}
	
	@Override
	public Matrix2d toDouble()
	{
		return new Matrix2d(this.m00, this.m01, this.m10, this.m11);
	}
	
	public String toString()
	{
		return "" + this.m00 + " " + this.m01 + "\n" + this.m10 + " " + this.m11;
	}
	
	public boolean equals(Object o)
	{
		if(this == o)
		{
			return true;
		}
		if(!(o instanceof Matrix2d))
		{
			return false;
		}
		Matrix2d matrix2 = (Matrix2d) o;
		if(Double.compare(matrix2.m00, this.m00) != 0)
		{
			return false;
		}
		if(Double.compare(matrix2.m01, this.m01) != 0)
		{
			return false;
		}
		if(Double.compare(matrix2.m10, this.m10) != 0)
		{
			return false;
		}
		if(Double.compare(matrix2.m11, this.m11) != 0)
		{
			return false;
		}
		return true;
	}
	
	public int hashCode()
	{
		if(!this.hashed)
		{
			int result = this.m00 != 0.0 ? HashFunctions.hash(this.m00) : 0;
			result = 31 * result + (this.m01 != 0.0 ? HashFunctions.hash(this.m01) : 0);
			result = 31 * result + (this.m10 != 0.0 ? HashFunctions.hash(this.m10) : 0);
			this.hashCode = 31 * result + (this.m11 != 0.0 ? HashFunctions.hash(this.m11) : 0);
			this.hashed = true;
		}
		return this.hashCode;
	}
	
	public Matrix2d clone()
	{
		return new Matrix2d(this);
	}
	
	public static Matrix2d from(double n)
	{
		return n == 0.0 ? ZERO : new Matrix2d(n, n, n, n);
	}
	
	public static Matrix2d from(double m00, double m01, double m10, double m11)
	{
		return m00 == 0.0 && m01 == 0.0 && m10 == 0.0 && m11 == 0.0 ? ZERO : new Matrix2d(m00, m01, m10, m11);
	}
	
	public static Matrix2d fromDiagonal(double m00, double m11)
	{
		return m00 == 0.0 && m11 == 0.0 ? ZERO : new Matrix2d(m00, 0.0, 0.0, m11);
	}
	
	public static Matrix2d createScaling(float scale)
	{
		return Matrix2d.createScaling((double) scale);
	}
	
	public static Matrix2d createScaling(double scale)
	{
		return Matrix2d.createScaling(scale, scale);
	}
	
	public static Matrix2d createScaling(Vector2d v)
	{
		return Matrix2d.createScaling(v.getX(), v.getY());
	}
	
	public static Matrix2d createScaling(float x, float y)
	{
		return Matrix2d.createScaling((double) x, (double) y);
	}
	
	public static Matrix2d createScaling(double x, double y)
	{
		return new Matrix2d(x, 0.0, 0.0, y);
	}
	
	public static Matrix2d createTranslation(float x)
	{
		return Matrix2d.createTranslation((double) x);
	}
	
	public static Matrix2d createTranslation(double x)
	{
		return new Matrix2d(1.0, x, 0.0, 1.0);
	}
	
	public static Matrix2d createRotation(Complexd rot)
	{
		rot = rot.normalize();
		return new Matrix2d(rot.getX(), -rot.getY(), rot.getY(), rot.getX());
	}
}
