package com.endie.thaumicadditions.shaded.flowpoweredmath.vector;

import java.io.Serializable;

import com.endie.thaumicadditions.shaded.flowpoweredmath.GenericMath;
import com.endie.thaumicadditions.shaded.flowpoweredmath.HashFunctions;

public class Vector2i implements Vectori, Comparable<Vector2i>, Serializable, Cloneable
{
	private static final long serialVersionUID = 1;
	public static final Vector2i ZERO = new Vector2i(0, 0);
	public static final Vector2i UNIT_X = new Vector2i(1, 0);
	public static final Vector2i UNIT_Y = new Vector2i(0, 1);
	public static final Vector2i ONE = new Vector2i(1, 1);
	private final int x;
	private final int y;
	private volatile transient boolean hashed = false;
	private volatile transient int hashCode = 0;
	
	public Vector2i()
	{
		this(0, 0);
	}
	
	public Vector2i(Vector2i v)
	{
		this(v.x, v.y);
	}
	
	public Vector2i(Vector3i v)
	{
		this(v.getX(), v.getY());
	}
	
	public Vector2i(Vector4i v)
	{
		this(v.getX(), v.getY());
	}
	
	public Vector2i(VectorNi v)
	{
		this(v.get(0), v.get(1));
	}
	
	public Vector2i(double x, double y)
	{
		this(GenericMath.floor(x), GenericMath.floor(y));
	}
	
	public Vector2i(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	public int getX()
	{
		return this.x;
	}
	
	public int getY()
	{
		return this.y;
	}
	
	public Vector2i add(Vector2i v)
	{
		return this.add(v.x, v.y);
	}
	
	public Vector2i add(double x, double y)
	{
		return this.add(GenericMath.floor(x), GenericMath.floor(y));
	}
	
	public Vector2i add(int x, int y)
	{
		return new Vector2i(this.x + x, this.y + y);
	}
	
	public Vector2i sub(Vector2i v)
	{
		return this.sub(v.x, v.y);
	}
	
	public Vector2i sub(double x, double y)
	{
		return this.sub(GenericMath.floor(x), GenericMath.floor(y));
	}
	
	public Vector2i sub(int x, int y)
	{
		return new Vector2i(this.x - x, this.y - y);
	}
	
	public Vector2i mul(double a)
	{
		return this.mul(GenericMath.floor(a));
	}
	
	@Override
	public Vector2i mul(int a)
	{
		return this.mul(a, a);
	}
	
	public Vector2i mul(Vector2i v)
	{
		return this.mul(v.x, v.y);
	}
	
	public Vector2i mul(double x, double y)
	{
		return this.mul(GenericMath.floor(x), GenericMath.floor(y));
	}
	
	public Vector2i mul(int x, int y)
	{
		return new Vector2i(this.x * x, this.y * y);
	}
	
	public Vector2i div(double a)
	{
		return this.div(GenericMath.floor(a));
	}
	
	@Override
	public Vector2i div(int a)
	{
		return this.div(a, a);
	}
	
	public Vector2i div(Vector2i v)
	{
		return this.div(v.x, v.y);
	}
	
	public Vector2i div(double x, double y)
	{
		return this.div(GenericMath.floor(x), GenericMath.floor(y));
	}
	
	public Vector2i div(int x, int y)
	{
		return new Vector2i(this.x / x, this.y / y);
	}
	
	public int dot(Vector2i v)
	{
		return this.dot(v.x, v.y);
	}
	
	public int dot(double x, double y)
	{
		return this.dot(GenericMath.floor(x), GenericMath.floor(y));
	}
	
	public int dot(int x, int y)
	{
		return this.x * x + this.y * y;
	}
	
	public Vector2i project(Vector2i v)
	{
		return this.project(v.x, v.y);
	}
	
	public Vector2i project(double x, double y)
	{
		return this.project(GenericMath.floor(x), GenericMath.floor(y));
	}
	
	public Vector2i project(int x, int y)
	{
		int lengthSquared = x * x + y * y;
		if(lengthSquared == 0)
		{
			throw new ArithmeticException("Cannot project onto the zero vector");
		}
		float a = (float) this.dot(x, y) / (float) lengthSquared;
		return new Vector2i(a * (float) x, a * (float) y);
	}
	
	public Vector2i pow(double pow)
	{
		return this.pow(GenericMath.floor(pow));
	}
	
	@Override
	public Vector2i pow(int power)
	{
		return new Vector2i(Math.pow(this.x, power), Math.pow(this.y, power));
	}
	
	@Override
	public Vector2i abs()
	{
		return new Vector2i(Math.abs(this.x), Math.abs(this.y));
	}
	
	@Override
	public Vector2i negate()
	{
		return new Vector2i(-this.x, -this.y);
	}
	
	public Vector2i min(Vector2i v)
	{
		return this.min(v.x, v.y);
	}
	
	public Vector2i min(double x, double y)
	{
		return this.min(GenericMath.floor(x), GenericMath.floor(y));
	}
	
	public Vector2i min(int x, int y)
	{
		return new Vector2i(Math.min(this.x, x), Math.min(this.y, y));
	}
	
	public Vector2i max(Vector2i v)
	{
		return this.max(v.x, v.y);
	}
	
	public Vector2i max(double x, double y)
	{
		return this.max(GenericMath.floor(x), GenericMath.floor(y));
	}
	
	public Vector2i max(int x, int y)
	{
		return new Vector2i(Math.max(this.x, x), Math.max(this.y, y));
	}
	
	public int distanceSquared(Vector2i v)
	{
		return this.distanceSquared(v.x, v.y);
	}
	
	public int distanceSquared(double x, double y)
	{
		return this.distanceSquared(GenericMath.floor(x), GenericMath.floor(y));
	}
	
	public int distanceSquared(int x, int y)
	{
		int dx = this.x - x;
		int dy = this.y - y;
		return dx * dx + dy * dy;
	}
	
	public float distance(Vector2i v)
	{
		return this.distance(v.x, v.y);
	}
	
	public float distance(double x, double y)
	{
		return this.distance(GenericMath.floor(x), GenericMath.floor(y));
	}
	
	public float distance(int x, int y)
	{
		return (float) Math.sqrt(this.distanceSquared(x, y));
	}
	
	@Override
	public int lengthSquared()
	{
		return this.x * this.x + this.y * this.y;
	}
	
	@Override
	public float length()
	{
		return (float) Math.sqrt(this.lengthSquared());
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
	
	public Vector3i toVector3()
	{
		return this.toVector3(0);
	}
	
	public Vector3i toVector3(double z)
	{
		return this.toVector3(GenericMath.floor(z));
	}
	
	public Vector3i toVector3(int z)
	{
		return new Vector3i(this, z);
	}
	
	public Vector4i toVector4()
	{
		return this.toVector4(0, 0);
	}
	
	public Vector4i toVector4(double z, double w)
	{
		return this.toVector4(GenericMath.floor(z), GenericMath.floor(w));
	}
	
	public Vector4i toVector4(int z, int w)
	{
		return new Vector4i(this, z, w);
	}
	
	public VectorNi toVectorN()
	{
		return new VectorNi(this);
	}
	
	@Override
	public int[] toArray()
	{
		return new int[] { this.x, this.y };
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
	public int compareTo(Vector2i v)
	{
		return this.lengthSquared() - v.lengthSquared();
	}
	
	public boolean equals(Object o)
	{
		if(this == o)
		{
			return true;
		}
		if(!(o instanceof Vector2i))
		{
			return false;
		}
		Vector2i vector2 = (Vector2i) o;
		if(vector2.x != this.x)
		{
			return false;
		}
		if(vector2.y != this.y)
		{
			return false;
		}
		return true;
	}
	
	public int hashCode()
	{
		if(!this.hashed)
		{
			int result = (float) this.x != 0.0f ? HashFunctions.hash(this.x) : 0;
			this.hashCode = 31 * result + ((float) this.y != 0.0f ? HashFunctions.hash(this.y) : 0);
			this.hashed = true;
		}
		return this.hashCode;
	}
	
	public Vector2i clone()
	{
		return new Vector2i(this);
	}
	
	public String toString()
	{
		return "(" + this.x + ", " + this.y + ")";
	}
	
	public static Vector2i from(int n)
	{
		return n == 0 ? ZERO : new Vector2i(n, n);
	}
	
	public static Vector2i from(int x, int y)
	{
		return x == 0 && y == 0 ? ZERO : new Vector2i(x, y);
	}
}
