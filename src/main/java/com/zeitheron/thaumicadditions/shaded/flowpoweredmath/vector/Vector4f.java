/* Decompiled with CFR 0_123. */
package com.zeitheron.thaumicadditions.shaded.flowpoweredmath.vector;

import java.io.Serializable;

import com.zeitheron.thaumicadditions.shaded.flowpoweredmath.GenericMath;
import com.zeitheron.thaumicadditions.shaded.flowpoweredmath.HashFunctions;

public class Vector4f implements Vectorf, Comparable<Vector4f>, Serializable, Cloneable
{
	private static final long serialVersionUID = 1;
	public static final Vector4f ZERO = new Vector4f(0.0f, 0.0f, 0.0f, 0.0f);
	public static final Vector4f UNIT_X = new Vector4f(1.0f, 0.0f, 0.0f, 0.0f);
	public static final Vector4f UNIT_Y = new Vector4f(0.0f, 1.0f, 0.0f, 0.0f);
	public static final Vector4f UNIT_Z = new Vector4f(0.0f, 0.0f, 1.0f, 0.0f);
	public static final Vector4f UNIT_W = new Vector4f(0.0f, 0.0f, 0.0f, 1.0f);
	public static final Vector4f ONE = new Vector4f(1.0f, 1.0f, 1.0f, 1.0f);
	private final float x;
	private final float y;
	private final float z;
	private final float w;
	private volatile transient boolean hashed = false;
	private volatile transient int hashCode = 0;
	
	public Vector4f()
	{
		this(0.0f, 0.0f, 0.0f, 0.0f);
	}
	
	public Vector4f(Vector2f v)
	{
		this(v, 0.0f, 0.0f);
	}
	
	public Vector4f(Vector2f v, double z, double w)
	{
		this(v, (float) z, (float) w);
	}
	
	public Vector4f(Vector2f v, float z, float w)
	{
		this(v.getX(), v.getY(), z, w);
	}
	
	public Vector4f(Vector3f v)
	{
		this(v, 0.0f);
	}
	
	public Vector4f(Vector3f v, double w)
	{
		this(v, (float) w);
	}
	
	public Vector4f(Vector3f v, float w)
	{
		this(v.getX(), v.getY(), v.getZ(), w);
	}
	
	public Vector4f(Vector4f v)
	{
		this(v.x, v.y, v.z, v.w);
	}
	
	public Vector4f(VectorNf v)
	{
		this(v.get(0), v.get(1), v.size() > 2 ? v.get(2) : 0.0f, v.size() > 3 ? v.get(3) : 0.0f);
	}
	
	public Vector4f(double x, double y, double z, double w)
	{
		this((float) x, (float) y, (float) z, (float) w);
	}
	
	public Vector4f(float x, float y, float z, float w)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}
	
	public float getX()
	{
		return this.x;
	}
	
	public float getY()
	{
		return this.y;
	}
	
	public float getZ()
	{
		return this.z;
	}
	
	public float getW()
	{
		return this.w;
	}
	
	public int getFloorX()
	{
		return GenericMath.floor(this.x);
	}
	
	public int getFloorY()
	{
		return GenericMath.floor(this.y);
	}
	
	public int getFloorZ()
	{
		return GenericMath.floor(this.z);
	}
	
	public int getFloorW()
	{
		return GenericMath.floor(this.w);
	}
	
	public Vector4f add(Vector4f v)
	{
		return this.add(v.x, v.y, v.z, v.w);
	}
	
	public Vector4f add(double x, double y, double z, double w)
	{
		return this.add((float) x, (float) y, (float) z, (float) w);
	}
	
	public Vector4f add(float x, float y, float z, float w)
	{
		return new Vector4f(this.x + x, this.y + y, this.z + z, this.w + w);
	}
	
	public Vector4f sub(Vector4f v)
	{
		return this.sub(v.x, v.y, v.z, v.w);
	}
	
	public Vector4f sub(double x, double y, double z, double w)
	{
		return this.sub((float) x, (float) y, (float) z, (float) w);
	}
	
	public Vector4f sub(float x, float y, float z, float w)
	{
		return new Vector4f(this.x - x, this.y - y, this.z - z, this.w - w);
	}
	
	public Vector4f mul(double a)
	{
		return this.mul((float) a);
	}
	
	@Override
	public Vector4f mul(float a)
	{
		return this.mul(a, a, a, a);
	}
	
	public Vector4f mul(Vector4f v)
	{
		return this.mul(v.x, v.y, v.z, v.w);
	}
	
	public Vector4f mul(double x, double y, double z, double w)
	{
		return this.mul((float) x, (float) y, (float) z, (float) w);
	}
	
	public Vector4f mul(float x, float y, float z, float w)
	{
		return new Vector4f(this.x * x, this.y * y, this.z * z, this.w * w);
	}
	
	public Vector4f div(double a)
	{
		return this.div((float) a);
	}
	
	@Override
	public Vector4f div(float a)
	{
		return this.div(a, a, a, a);
	}
	
	public Vector4f div(Vector4f v)
	{
		return this.div(v.x, v.y, v.z, v.w);
	}
	
	public Vector4f div(double x, double y, double z, double w)
	{
		return this.div((float) x, (float) y, (float) z, (float) w);
	}
	
	public Vector4f div(float x, float y, float z, float w)
	{
		return new Vector4f(this.x / x, this.y / y, this.z / z, this.w / w);
	}
	
	public float dot(Vector4f v)
	{
		return this.dot(v.x, v.y, v.z, v.w);
	}
	
	public float dot(double x, double y, double z, double w)
	{
		return this.dot((float) x, (float) y, (float) z, (float) w);
	}
	
	public float dot(float x, float y, float z, float w)
	{
		return this.x * x + this.y * y + this.z * z + this.w * w;
	}
	
	public Vector4f project(Vector4f v)
	{
		return this.project(v.x, v.y, v.z, v.w);
	}
	
	public Vector4f project(double x, double y, double z, double w)
	{
		return this.project((float) x, (float) y, (float) z, (float) w);
	}
	
	public Vector4f project(float x, float y, float z, float w)
	{
		float lengthSquared = x * x + y * y + z * z + w * w;
		if(Math.abs(lengthSquared) < GenericMath.FLT_EPSILON)
		{
			throw new ArithmeticException("Cannot project onto the zero vector");
		}
		float a = this.dot(x, y, z, w) / lengthSquared;
		return new Vector4f(a * x, a * y, a * z, a * w);
	}
	
	public Vector4f pow(double pow)
	{
		return this.pow((float) pow);
	}
	
	@Override
	public Vector4f pow(float power)
	{
		return new Vector4f(Math.pow(this.x, power), Math.pow(this.y, power), Math.pow(this.z, power), Math.pow(this.w, power));
	}
	
	@Override
	public Vector4f ceil()
	{
		return new Vector4f(Math.ceil(this.x), Math.ceil(this.y), Math.ceil(this.z), Math.ceil(this.w));
	}
	
	@Override
	public Vector4f floor()
	{
		return new Vector4f(GenericMath.floor(this.x), GenericMath.floor(this.y), GenericMath.floor(this.z), GenericMath.floor(this.w));
	}
	
	@Override
	public Vector4f round()
	{
		return new Vector4f(Math.round(this.x), Math.round(this.y), Math.round(this.z), Math.round(this.w));
	}
	
	@Override
	public Vector4f abs()
	{
		return new Vector4f(Math.abs(this.x), Math.abs(this.y), Math.abs(this.z), Math.abs(this.w));
	}
	
	@Override
	public Vector4f negate()
	{
		return new Vector4f(-this.x, -this.y, -this.z, -this.w);
	}
	
	public Vector4f min(Vector4f v)
	{
		return this.min(v.x, v.y, v.z, v.w);
	}
	
	public Vector4f min(double x, double y, double z, double w)
	{
		return this.min((float) x, (float) y, (float) z, (float) w);
	}
	
	public Vector4f min(float x, float y, float z, float w)
	{
		return new Vector4f(Math.min(this.x, x), Math.min(this.y, y), Math.min(this.z, z), Math.min(this.w, w));
	}
	
	public Vector4f max(Vector4f v)
	{
		return this.max(v.x, v.y, v.z, v.w);
	}
	
	public Vector4f max(double x, double y, double z, double w)
	{
		return this.max((float) x, (float) y, (float) z, (float) w);
	}
	
	public Vector4f max(float x, float y, float z, float w)
	{
		return new Vector4f(Math.max(this.x, x), Math.max(this.y, y), Math.max(this.z, z), Math.max(this.w, w));
	}
	
	public float distanceSquared(Vector4f v)
	{
		return this.distanceSquared(v.x, v.y, v.z, v.w);
	}
	
	public float distanceSquared(double x, double y, double z, double w)
	{
		return this.distanceSquared((float) x, (float) y, (float) z, (float) w);
	}
	
	public float distanceSquared(float x, float y, float z, float w)
	{
		float dx = this.x - x;
		float dy = this.y - y;
		float dz = this.z - z;
		float dw = this.w - w;
		return dx * dx + dy * dy + dz * dz + dw * dw;
	}
	
	public float distance(Vector4f v)
	{
		return this.distance(v.x, v.y, v.z, v.w);
	}
	
	public float distance(double x, double y, double z, double w)
	{
		return this.distance((float) x, (float) y, (float) z, (float) w);
	}
	
	public float distance(float x, float y, float z, float w)
	{
		return (float) Math.sqrt(this.distanceSquared(x, y, z, w));
	}
	
	@Override
	public float lengthSquared()
	{
		return this.x * this.x + this.y * this.y + this.z * this.z + this.w * this.w;
	}
	
	@Override
	public float length()
	{
		return (float) Math.sqrt(this.lengthSquared());
	}
	
	@Override
	public Vector4f normalize()
	{
		float length = this.length();
		if(Math.abs(length) < GenericMath.FLT_EPSILON)
		{
			throw new ArithmeticException("Cannot normalize the zero vector");
		}
		return new Vector4f(this.x / length, this.y / length, this.z / length, this.w / length);
	}
	
	@Override
	public int getMinAxis()
	{
		float value = this.x;
		int axis = 0;
		if(this.y < value)
		{
			value = this.y;
			axis = 1;
		}
		if(this.z < value)
		{
			value = this.z;
			axis = 2;
		}
		if(this.w < value)
		{
			axis = 3;
		}
		return axis;
	}
	
	@Override
	public int getMaxAxis()
	{
		float value = this.x;
		int axis = 0;
		if(this.y > value)
		{
			value = this.y;
			axis = 1;
		}
		if(this.z > value)
		{
			value = this.z;
			axis = 2;
		}
		if(this.w > value)
		{
			axis = 3;
		}
		return axis;
	}
	
	public Vector2f toVector2()
	{
		return new Vector2f(this);
	}
	
	public Vector3f toVector3()
	{
		return new Vector3f(this);
	}
	
	public VectorNf toVectorN()
	{
		return new VectorNf(this);
	}
	
	@Override
	public float[] toArray()
	{
		return new float[] { this.x, this.y, this.z, this.w };
	}
	
	@Override
	public Vector4i toInt()
	{
		return new Vector4i(this.x, this.y, this.z, this.w);
	}
	
	@Override
	public Vector4l toLong()
	{
		return new Vector4l(this.x, this.y, this.z, this.w);
	}
	
	@Override
	public Vector4f toFloat()
	{
		return new Vector4f(this.x, this.y, this.z, this.w);
	}
	
	@Override
	public Vector4d toDouble()
	{
		return new Vector4d(this.x, this.y, this.z, this.w);
	}
	
	@Override
	public int compareTo(Vector4f v)
	{
		return (int) Math.signum(this.lengthSquared() - v.lengthSquared());
	}
	
	public boolean equals(Object o)
	{
		if(this == o)
		{
			return true;
		}
		if(!(o instanceof Vector4f))
		{
			return false;
		}
		Vector4f vector4 = (Vector4f) o;
		if(Float.compare(vector4.w, this.w) != 0)
		{
			return false;
		}
		if(Float.compare(vector4.x, this.x) != 0)
		{
			return false;
		}
		if(Float.compare(vector4.y, this.y) != 0)
		{
			return false;
		}
		if(Float.compare(vector4.z, this.z) != 0)
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
			result = 31 * result + (this.y != 0.0f ? HashFunctions.hash(this.y) : 0);
			result = 31 * result + (this.z != 0.0f ? HashFunctions.hash(this.z) : 0);
			this.hashCode = 31 * result + (this.w != 0.0f ? HashFunctions.hash(this.w) : 0);
			this.hashed = true;
		}
		return this.hashCode;
	}
	
	public Vector4f clone()
	{
		return new Vector4f(this);
	}
	
	public String toString()
	{
		return "(" + this.x + ", " + this.y + ", " + this.z + ", " + this.w + ")";
	}
	
	public static Vector4f from(float n)
	{
		return n == 0.0f ? ZERO : new Vector4f(n, n, n, n);
	}
	
	public static Vector4f from(float x, float y, float z, float w)
	{
		return x == 0.0f && y == 0.0f && z == 0.0f && w == 0.0f ? ZERO : new Vector4f(x, y, z, w);
	}
}
