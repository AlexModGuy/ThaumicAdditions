package com.endie.thaumicadditions.shaded.flowpoweredmath.vector;

import java.io.Serializable;
import java.util.Random;

import com.endie.thaumicadditions.shaded.flowpoweredmath.GenericMath;
import com.endie.thaumicadditions.shaded.flowpoweredmath.HashFunctions;
import com.endie.thaumicadditions.shaded.flowpoweredmath.TrigMath;

public class Vector2f implements Vectorf, Comparable<Vector2f>, Serializable, Cloneable
{
	private static final long serialVersionUID = 1;
	public static final Vector2f ZERO = new Vector2f(0.0f, 0.0f);
	public static final Vector2f UNIT_X = new Vector2f(1.0f, 0.0f);
	public static final Vector2f UNIT_Y = new Vector2f(0.0f, 1.0f);
	public static final Vector2f ONE = new Vector2f(1.0f, 1.0f);
	private final float x;
	private final float y;
	private volatile transient boolean hashed = false;
	private volatile transient int hashCode = 0;
	
	public Vector2f()
	{
		this(0.0f, 0.0f);
	}
	
	public Vector2f(Vector2f v)
	{
		this(v.x, v.y);
	}
	
	public Vector2f(Vector3f v)
	{
		this(v.getX(), v.getY());
	}
	
	public Vector2f(Vector4f v)
	{
		this(v.getX(), v.getY());
	}
	
	public Vector2f(VectorNf v)
	{
		this(v.get(0), v.get(1));
	}
	
	public Vector2f(double x, double y)
	{
		this((float) x, (float) y);
	}
	
	public Vector2f(float x, float y)
	{
		this.x = x;
		this.y = y;
	}
	
	public float getX()
	{
		return this.x;
	}
	
	public float getY()
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
	
	public Vector2f add(Vector2f v)
	{
		return this.add(v.x, v.y);
	}
	
	public Vector2f add(double x, double y)
	{
		return this.add((float) x, (float) y);
	}
	
	public Vector2f add(float x, float y)
	{
		return new Vector2f(this.x + x, this.y + y);
	}
	
	public Vector2f sub(Vector2f v)
	{
		return this.sub(v.x, v.y);
	}
	
	public Vector2f sub(double x, double y)
	{
		return this.sub((float) x, (float) y);
	}
	
	public Vector2f sub(float x, float y)
	{
		return new Vector2f(this.x - x, this.y - y);
	}
	
	public Vector2f mul(double a)
	{
		return this.mul((float) a);
	}
	
	@Override
	public Vector2f mul(float a)
	{
		return this.mul(a, a);
	}
	
	public Vector2f mul(Vector2f v)
	{
		return this.mul(v.x, v.y);
	}
	
	public Vector2f mul(double x, double y)
	{
		return this.mul((float) x, (float) y);
	}
	
	public Vector2f mul(float x, float y)
	{
		return new Vector2f(this.x * x, this.y * y);
	}
	
	public Vector2f div(double a)
	{
		return this.div((float) a);
	}
	
	@Override
	public Vector2f div(float a)
	{
		return this.div(a, a);
	}
	
	public Vector2f div(Vector2f v)
	{
		return this.div(v.x, v.y);
	}
	
	public Vector2f div(double x, double y)
	{
		return this.div((float) x, (float) y);
	}
	
	public Vector2f div(float x, float y)
	{
		return new Vector2f(this.x / x, this.y / y);
	}
	
	public float dot(Vector2f v)
	{
		return this.dot(v.x, v.y);
	}
	
	public float dot(double x, double y)
	{
		return this.dot((float) x, (float) y);
	}
	
	public float dot(float x, float y)
	{
		return this.x * x + this.y * y;
	}
	
	public Vector2f project(Vector2f v)
	{
		return this.project(v.x, v.y);
	}
	
	public Vector2f project(double x, double y)
	{
		return this.project((float) x, (float) y);
	}
	
	public Vector2f project(float x, float y)
	{
		float lengthSquared = x * x + y * y;
		if(Math.abs(lengthSquared) < GenericMath.FLT_EPSILON)
		{
			throw new ArithmeticException("Cannot project onto the zero vector");
		}
		float a = this.dot(x, y) / lengthSquared;
		return new Vector2f(a * x, a * y);
	}
	
	public Vector2f pow(double pow)
	{
		return this.pow((float) pow);
	}
	
	@Override
	public Vector2f pow(float power)
	{
		return new Vector2f(Math.pow(this.x, power), Math.pow(this.y, power));
	}
	
	@Override
	public Vector2f ceil()
	{
		return new Vector2f(Math.ceil(this.x), Math.ceil(this.y));
	}
	
	@Override
	public Vector2f floor()
	{
		return new Vector2f(GenericMath.floor(this.x), GenericMath.floor(this.y));
	}
	
	@Override
	public Vector2f round()
	{
		return new Vector2f(Math.round(this.x), Math.round(this.y));
	}
	
	@Override
	public Vector2f abs()
	{
		return new Vector2f(Math.abs(this.x), Math.abs(this.y));
	}
	
	@Override
	public Vector2f negate()
	{
		return new Vector2f(-this.x, -this.y);
	}
	
	public Vector2f min(Vector2f v)
	{
		return this.min(v.x, v.y);
	}
	
	public Vector2f min(double x, double y)
	{
		return this.min((float) x, (float) y);
	}
	
	public Vector2f min(float x, float y)
	{
		return new Vector2f(Math.min(this.x, x), Math.min(this.y, y));
	}
	
	public Vector2f max(Vector2f v)
	{
		return this.max(v.x, v.y);
	}
	
	public Vector2f max(double x, double y)
	{
		return this.max((float) x, (float) y);
	}
	
	public Vector2f max(float x, float y)
	{
		return new Vector2f(Math.max(this.x, x), Math.max(this.y, y));
	}
	
	public float distanceSquared(Vector2f v)
	{
		return this.distanceSquared(v.x, v.y);
	}
	
	public float distanceSquared(double x, double y)
	{
		return this.distanceSquared((float) x, (float) y);
	}
	
	public float distanceSquared(float x, float y)
	{
		float dx = this.x - x;
		float dy = this.y - y;
		return dx * dx + dy * dy;
	}
	
	public float distance(Vector2f v)
	{
		return this.distance(v.x, v.y);
	}
	
	public float distance(double x, double y)
	{
		return this.distance((float) x, (float) y);
	}
	
	public float distance(float x, float y)
	{
		return (float) Math.sqrt(this.distanceSquared(x, y));
	}
	
	@Override
	public float lengthSquared()
	{
		return this.x * this.x + this.y * this.y;
	}
	
	@Override
	public float length()
	{
		return (float) Math.sqrt(this.lengthSquared());
	}
	
	@Override
	public Vector2f normalize()
	{
		float length = this.length();
		if(Math.abs(length) < GenericMath.FLT_EPSILON)
		{
			throw new ArithmeticException("Cannot normalize the zero vector");
		}
		return new Vector2f(this.x / length, this.y / length);
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
	
	public Vector3f toVector3()
	{
		return this.toVector3(0.0f);
	}
	
	public Vector3f toVector3(double z)
	{
		return this.toVector3((float) z);
	}
	
	public Vector3f toVector3(float z)
	{
		return new Vector3f(this, z);
	}
	
	public Vector4f toVector4()
	{
		return this.toVector4(0.0f, 0.0f);
	}
	
	public Vector4f toVector4(double z, double w)
	{
		return this.toVector4((float) z, (float) w);
	}
	
	public Vector4f toVector4(float z, float w)
	{
		return new Vector4f(this, z, w);
	}
	
	public VectorNf toVectorN()
	{
		return new VectorNf(this);
	}
	
	@Override
	public float[] toArray()
	{
		return new float[] { this.x, this.y };
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
	public int compareTo(Vector2f v)
	{
		return (int) Math.signum(this.lengthSquared() - v.lengthSquared());
	}
	
	public boolean equals(Object o)
	{
		if(this == o)
		{
			return true;
		}
		if(!(o instanceof Vector2f))
		{
			return false;
		}
		Vector2f vector2 = (Vector2f) o;
		if(Float.compare(vector2.x, this.x) != 0)
		{
			return false;
		}
		if(Float.compare(vector2.y, this.y) != 0)
		{
			return false;
		}
		return true;
	}
	
	public int hashCode()
	{
		if(!this.hashed)
		{
			int result = this.x != 0.0f ? HashFunctions.hash(this.x) : 0;
			this.hashCode = 31 * result + (this.y != 0.0f ? HashFunctions.hash(this.y) : 0);
			this.hashed = true;
		}
		return this.hashCode;
	}
	
	public Vector2f clone()
	{
		return new Vector2f(this);
	}
	
	public String toString()
	{
		return "(" + this.x + ", " + this.y + ")";
	}
	
	public static Vector2f from(float n)
	{
		return n == 0.0f ? ZERO : new Vector2f(n, n);
	}
	
	public static Vector2f from(float x, float y)
	{
		return x == 0.0f && y == 0.0f ? ZERO : new Vector2f(x, y);
	}
	
	public static Vector2f createRandomDirection(Random random)
	{
		return Vector2f.createDirectionRad(random.nextFloat() * 6.2831855f);
	}
	
	public static Vector2f createDirectionDeg(double angle)
	{
		return Vector2f.createDirectionDeg((float) angle);
	}
	
	public static Vector2f createDirectionDeg(float angle)
	{
		return Vector2f.createDirectionRad((float) Math.toRadians(angle));
	}
	
	public static Vector2f createDirectionRad(double angle)
	{
		return Vector2f.createDirectionRad((float) angle);
	}
	
	public static Vector2f createDirectionRad(float angle)
	{
		return new Vector2f(TrigMath.cos(angle), TrigMath.sin(angle));
	}
}
