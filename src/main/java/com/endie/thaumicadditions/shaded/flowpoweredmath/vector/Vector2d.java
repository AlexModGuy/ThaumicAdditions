package com.endie.thaumicadditions.shaded.flowpoweredmath.vector;

import java.io.Serializable;
import java.util.Random;

import com.endie.thaumicadditions.shaded.flowpoweredmath.GenericMath;
import com.endie.thaumicadditions.shaded.flowpoweredmath.HashFunctions;
import com.endie.thaumicadditions.shaded.flowpoweredmath.TrigMath;

public class Vector2d implements Vectord, Comparable<Vector2d>, Serializable, Cloneable
{
	private static final long serialVersionUID = 1;
	public static final Vector2d ZERO = new Vector2d(0.0f, 0.0f);
	public static final Vector2d UNIT_X = new Vector2d(1.0f, 0.0f);
	public static final Vector2d UNIT_Y = new Vector2d(0.0f, 1.0f);
	public static final Vector2d ONE = new Vector2d(1.0f, 1.0f);
	private final double x;
	private final double y;
	private volatile transient boolean hashed = false;
	private volatile transient int hashCode = 0;
	
	public Vector2d()
	{
		this(0.0f, 0.0f);
	}
	
	public Vector2d(Vector2d v)
	{
		this(v.x, v.y);
	}
	
	public Vector2d(Vector3d v)
	{
		this(v.getX(), v.getY());
	}
	
	public Vector2d(Vector4d v)
	{
		this(v.getX(), v.getY());
	}
	
	public Vector2d(VectorNd v)
	{
		this(v.get(0), v.get(1));
	}
	
	public Vector2d(float x, float y)
	{
		this((double) x, (double) y);
	}
	
	public Vector2d(double x, double y)
	{
		this.x = x;
		this.y = y;
	}
	
	public double getX()
	{
		return this.x;
	}
	
	public double getY()
	{
		return this.y;
	}
	
	public int getFloorX()
	{
		return GenericMath.floor(this.x);
	}
	
	public int getFloorY()
	{
		return GenericMath.floor(this.y);
	}
	
	public Vector2d add(Vector2d v)
	{
		return this.add(v.x, v.y);
	}
	
	public Vector2d add(float x, float y)
	{
		return this.add((double) x, (double) y);
	}
	
	public Vector2d add(double x, double y)
	{
		return new Vector2d(this.x + x, this.y + y);
	}
	
	public Vector2d sub(Vector2d v)
	{
		return this.sub(v.x, v.y);
	}
	
	public Vector2d sub(float x, float y)
	{
		return this.sub((double) x, (double) y);
	}
	
	public Vector2d sub(double x, double y)
	{
		return new Vector2d(this.x - x, this.y - y);
	}
	
	public Vector2d mul(float a)
	{
		return this.mul((double) a);
	}
	
	@Override
	public Vector2d mul(double a)
	{
		return this.mul(a, a);
	}
	
	public Vector2d mul(Vector2d v)
	{
		return this.mul(v.x, v.y);
	}
	
	public Vector2d mul(float x, float y)
	{
		return this.mul((double) x, (double) y);
	}
	
	public Vector2d mul(double x, double y)
	{
		return new Vector2d(this.x * x, this.y * y);
	}
	
	public Vector2d div(float a)
	{
		return this.div((double) a);
	}
	
	@Override
	public Vector2d div(double a)
	{
		return this.div(a, a);
	}
	
	public Vector2d div(Vector2d v)
	{
		return this.div(v.x, v.y);
	}
	
	public Vector2d div(float x, float y)
	{
		return this.div((double) x, (double) y);
	}
	
	public Vector2d div(double x, double y)
	{
		return new Vector2d(this.x / x, this.y / y);
	}
	
	public double dot(Vector2d v)
	{
		return this.dot(v.x, v.y);
	}
	
	public double dot(float x, float y)
	{
		return this.dot((double) x, (double) y);
	}
	
	public double dot(double x, double y)
	{
		return this.x * x + this.y * y;
	}
	
	public Vector2d project(Vector2d v)
	{
		return this.project(v.x, v.y);
	}
	
	public Vector2d project(float x, float y)
	{
		return this.project((double) x, (double) y);
	}
	
	public Vector2d project(double x, double y)
	{
		double lengthSquared = x * x + y * y;
		if(Math.abs(lengthSquared) < GenericMath.DBL_EPSILON)
		{
			throw new ArithmeticException("Cannot project onto the zero vector");
		}
		double a = this.dot(x, y) / lengthSquared;
		return new Vector2d(a * x, a * y);
	}
	
	public Vector2d pow(float pow)
	{
		return this.pow((double) pow);
	}
	
	@Override
	public Vector2d pow(double power)
	{
		return new Vector2d(Math.pow(this.x, power), Math.pow(this.y, power));
	}
	
	@Override
	public Vector2d ceil()
	{
		return new Vector2d(Math.ceil(this.x), Math.ceil(this.y));
	}
	
	@Override
	public Vector2d floor()
	{
		return new Vector2d(GenericMath.floor(this.x), GenericMath.floor(this.y));
	}
	
	@Override
	public Vector2d round()
	{
		return new Vector2d(Math.round(this.x), Math.round(this.y));
	}
	
	@Override
	public Vector2d abs()
	{
		return new Vector2d(Math.abs(this.x), Math.abs(this.y));
	}
	
	@Override
	public Vector2d negate()
	{
		return new Vector2d(-this.x, -this.y);
	}
	
	public Vector2d min(Vector2d v)
	{
		return this.min(v.x, v.y);
	}
	
	public Vector2d min(float x, float y)
	{
		return this.min((double) x, (double) y);
	}
	
	public Vector2d min(double x, double y)
	{
		return new Vector2d(Math.min(this.x, x), Math.min(this.y, y));
	}
	
	public Vector2d max(Vector2d v)
	{
		return this.max(v.x, v.y);
	}
	
	public Vector2d max(float x, float y)
	{
		return this.max((double) x, (double) y);
	}
	
	public Vector2d max(double x, double y)
	{
		return new Vector2d(Math.max(this.x, x), Math.max(this.y, y));
	}
	
	public double distanceSquared(Vector2d v)
	{
		return this.distanceSquared(v.x, v.y);
	}
	
	public double distanceSquared(float x, float y)
	{
		return this.distanceSquared((double) x, (double) y);
	}
	
	public double distanceSquared(double x, double y)
	{
		double dx = this.x - x;
		double dy = this.y - y;
		return dx * dx + dy * dy;
	}
	
	public double distance(Vector2d v)
	{
		return this.distance(v.x, v.y);
	}
	
	public double distance(float x, float y)
	{
		return this.distance((double) x, (double) y);
	}
	
	public double distance(double x, double y)
	{
		return Math.sqrt(this.distanceSquared(x, y));
	}
	
	@Override
	public double lengthSquared()
	{
		return this.x * this.x + this.y * this.y;
	}
	
	@Override
	public double length()
	{
		return Math.sqrt(this.lengthSquared());
	}
	
	@Override
	public Vector2d normalize()
	{
		double length = this.length();
		if(Math.abs(length) < GenericMath.DBL_EPSILON)
		{
			throw new ArithmeticException("Cannot normalize the zero vector");
		}
		return new Vector2d(this.x / length, this.y / length);
	}
	
	@Override
	public int getMinAxis()
	{
		return this.x < this.y ? 0 : 1;
	}
	
	@Override
	public int getMaxAxis()
	{
		return this.x > this.y ? 0 : 1;
	}
	
	public Vector3d toVector3()
	{
		return this.toVector3(0.0f);
	}
	
	public Vector3d toVector3(float z)
	{
		return this.toVector3((double) z);
	}
	
	public Vector3d toVector3(double z)
	{
		return new Vector3d(this, z);
	}
	
	public Vector4d toVector4()
	{
		return this.toVector4(0.0f, 0.0f);
	}
	
	public Vector4d toVector4(float z, float w)
	{
		return this.toVector4((double) z, (double) w);
	}
	
	public Vector4d toVector4(double z, double w)
	{
		return new Vector4d(this, z, w);
	}
	
	public VectorNd toVectorN()
	{
		return new VectorNd(this);
	}
	
	@Override
	public double[] toArray()
	{
		return new double[] { this.x, this.y };
	}
	
	@Override
	public Vector2i toInt()
	{
		return new Vector2i(this.x, this.y);
	}
	
	@Override
	public Vector2l toLong()
	{
		return new Vector2l(this.x, this.y);
	}
	
	@Override
	public Vector2f toFloat()
	{
		return new Vector2f(this.x, this.y);
	}
	
	@Override
	public Vector2d toDouble()
	{
		return new Vector2d(this.x, this.y);
	}
	
	@Override
	public int compareTo(Vector2d v)
	{
		return (int) Math.signum(this.lengthSquared() - v.lengthSquared());
	}
	
	public boolean equals(Object o)
	{
		if(this == o)
		{
			return true;
		}
		if(!(o instanceof Vector2d))
		{
			return false;
		}
		Vector2d vector2 = (Vector2d) o;
		if(Double.compare(vector2.x, this.x) != 0)
		{
			return false;
		}
		if(Double.compare(vector2.y, this.y) != 0)
		{
			return false;
		}
		return true;
	}
	
	public int hashCode()
	{
		if(!this.hashed)
		{
			int result = this.x != 0.0 ? HashFunctions.hash(this.x) : 0;
			this.hashCode = 31 * result + (this.y != 0.0 ? HashFunctions.hash(this.y) : 0);
			this.hashed = true;
		}
		return this.hashCode;
	}
	
	public Vector2d clone()
	{
		return new Vector2d(this);
	}
	
	public String toString()
	{
		return "(" + this.x + ", " + this.y + ")";
	}
	
	public static Vector2d from(double n)
	{
		return n == 0.0 ? ZERO : new Vector2d(n, n);
	}
	
	public static Vector2d from(double x, double y)
	{
		return x == 0.0 && y == 0.0 ? ZERO : new Vector2d(x, y);
	}
	
	public static Vector2d createRandomDirection(Random random)
	{
		return Vector2d.createDirectionRad(random.nextDouble() * 6.283185307179586);
	}
	
	public static Vector2d createDirectionDeg(float angle)
	{
		return Vector2d.createDirectionDeg((double) angle);
	}
	
	public static Vector2d createDirectionDeg(double angle)
	{
		return Vector2d.createDirectionRad(Math.toRadians(angle));
	}
	
	public static Vector2d createDirectionRad(float angle)
	{
		return Vector2d.createDirectionRad((double) angle);
	}
	
	public static Vector2d createDirectionRad(double angle)
	{
		return new Vector2d(TrigMath.cos(angle), TrigMath.sin(angle));
	}
}
