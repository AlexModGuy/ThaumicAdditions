package com.endie.thaumicadditions.shaded.flowpoweredmath;

import java.awt.Color;
import java.security.SecureRandom;
import java.util.Random;

import com.endie.thaumicadditions.shaded.flowpoweredmath.imaginary.Quaterniond;
import com.endie.thaumicadditions.shaded.flowpoweredmath.imaginary.Quaternionf;
import com.endie.thaumicadditions.shaded.flowpoweredmath.vector.Vector2d;
import com.endie.thaumicadditions.shaded.flowpoweredmath.vector.Vector2f;
import com.endie.thaumicadditions.shaded.flowpoweredmath.vector.Vector3d;
import com.endie.thaumicadditions.shaded.flowpoweredmath.vector.Vector3f;
import com.endie.thaumicadditions.shaded.flowpoweredmath.vector.Vector4d;
import com.endie.thaumicadditions.shaded.flowpoweredmath.vector.Vector4f;
import com.endie.thaumicadditions.shaded.flowpoweredmath.vector.VectorNd;
import com.endie.thaumicadditions.shaded.flowpoweredmath.vector.VectorNf;

public class GenericMath
{
	public static final double DBL_EPSILON = Double.longBitsToDouble(4372995238176751616L);
	public static final float FLT_EPSILON = Float.intBitsToFloat(872415232);
	private static final ThreadLocal<Random> THREAD_LOCAL_RANDOM = new ThreadLocal<Random>()
	{
		private final Random random = new SecureRandom();
		
		@Override
		protected Random initialValue()
		{
			Random random = this.random;
			synchronized(random)
			{
				return new Random(this.random.nextLong());
			}
		}
	};
	
	private GenericMath()
	{
	}
	
	public static float getDegreeDifference(float angle1, float angle2)
	{
		return Math.abs(GenericMath.wrapAngleDeg(angle1 - angle2));
	}
	
	public static double getRadianDifference(double radian1, double radian2)
	{
		return Math.abs(GenericMath.wrapAngleRad(radian1 - radian2));
	}
	
	public static float wrapAngleDeg(float angle)
	{
		if((angle %= 360.0f) <= -180.0f)
		{
			return angle + 360.0f;
		}
		if(angle > 180.0f)
		{
			return angle - 360.0f;
		}
		return angle;
	}
	
	public static double wrapAngleRad(double angle)
	{
		if((angle %= 6.283185307179586) <= -3.141592653589793)
			return angle + 6.283185307179586;
		if(angle > 3.141592653589793)
			return angle - 6.283185307179586;
		return angle;
	}
	
	public static float wrapAnglePitchDeg(float angle)
	{
		if((angle = GenericMath.wrapAngleDeg(angle)) < -90.0f)
			return -90.0f;
		if(angle > 90.0f)
			return 90.0f;
		return angle;
	}
	
	public static byte wrapByte(int value)
	{
		if((value %= 256) < 0)
		{
			value += 256;
		}
		return (byte) value;
	}
	
	public static double round(double input, int decimals)
	{
		double p = Math.pow(10.0, decimals);
		return (double) Math.round(input * p) / p;
	}
	
	public static double lerp(double a, double b, double percent)
	{
		return (1.0 - percent) * a + percent * b;
	}
	
	public static float lerp(float a, float b, float percent)
	{
		return (1.0f - percent) * a + percent * b;
	}
	
	public static int lerp(int a, int b, int percent)
	{
		return (1 - percent) * a + percent * b;
	}
	
	public static Vector3f lerp(Vector3f a, Vector3f b, float percent)
	{
		return a.mul(1.0f - percent).add(b.mul(percent));
	}
	
	public static Vector3d lerp(Vector3d a, Vector3d b, double percent)
	{
		return a.mul(1.0 - percent).add(b.mul(percent));
	}
	
	public static Vector2f lerp(Vector2f a, Vector2f b, float percent)
	{
		return a.mul(1.0f - percent).add(b.mul(percent));
	}
	
	public static Vector2d lerp(Vector2d a, Vector2d b, double percent)
	{
		return a.mul(1.0 - percent).add(b.mul(percent));
	}
	
	public static double lerp(double x, double x1, double x2, double q0, double q1)
	{
		return (x2 - x) / (x2 - x1) * q0 + (x - x1) / (x2 - x1) * q1;
	}
	
	public static Color lerp(Color a, Color b, float percent)
	{
		int red = (int) GenericMath.lerp((float) a.getRed(), (float) b.getRed(), percent);
		int blue = (int) GenericMath.lerp((float) a.getBlue(), (float) b.getBlue(), percent);
		int green = (int) GenericMath.lerp((float) a.getGreen(), (float) b.getGreen(), percent);
		int alpha = (int) GenericMath.lerp((float) a.getAlpha(), (float) b.getAlpha(), percent);
		return new Color(red, green, blue, alpha);
	}
	
	public static Quaternionf slerp(Quaternionf a, Quaternionf b, float percent)
	{
		float inverted;
		float cosineTheta = a.dot(b);
		if(cosineTheta < 0.0f)
		{
			cosineTheta = -cosineTheta;
			inverted = -1.0f;
		} else
		{
			inverted = 1.0f;
		}
		if(1.0f - cosineTheta < FLT_EPSILON)
		{
			return a.mul(1.0f - percent).add(b.mul(percent * inverted));
		}
		float theta = (float) TrigMath.acos(cosineTheta);
		float sineTheta = TrigMath.sin(theta);
		float coefficient1 = TrigMath.sin((1.0f - percent) * theta) / sineTheta;
		float coefficient2 = TrigMath.sin(percent * theta) / sineTheta * inverted;
		return a.mul(coefficient1).add(b.mul(coefficient2));
	}
	
	public static Quaterniond slerp(Quaterniond a, Quaterniond b, double percent)
	{
		double inverted;
		double cosineTheta = a.dot(b);
		if(cosineTheta < 0.0)
		{
			cosineTheta = -cosineTheta;
			inverted = -1.0;
		} else
		{
			inverted = 1.0;
		}
		if(1.0 - cosineTheta < DBL_EPSILON)
		{
			return a.mul(1.0 - percent).add(b.mul(percent * inverted));
		}
		double theta = TrigMath.acos(cosineTheta);
		double sineTheta = TrigMath.sin(theta);
		double coefficient1 = (double) TrigMath.sin((1.0 - percent) * theta) / sineTheta;
		double coefficient2 = (double) TrigMath.sin(percent * theta) / sineTheta * inverted;
		return a.mul(coefficient1).add(b.mul(coefficient2));
	}
	
	public static Quaternionf lerp(Quaternionf a, Quaternionf b, float percent)
	{
		return a.mul(1.0f - percent).add(b.mul(percent));
	}
	
	public static Quaterniond lerp(Quaterniond a, Quaterniond b, double percent)
	{
		return a.mul(1.0 - percent).add(b.mul(percent));
	}
	
	public static double biLerp(double x, double y, double q00, double q01, double q10, double q11, double x1, double x2, double y1, double y2)
	{
		double q0 = GenericMath.lerp(x, x1, x2, q00, q10);
		double q1 = GenericMath.lerp(x, x1, x2, q01, q11);
		return GenericMath.lerp(y, y1, y2, q0, q1);
	}
	
	public static double triLerp(double x, double y, double z, double q000, double q001, double q010, double q011, double q100, double q101, double q110, double q111, double x1, double x2, double y1, double y2, double z1, double z2)
	{
		double q00 = GenericMath.lerp(x, x1, x2, q000, q100);
		double q01 = GenericMath.lerp(x, x1, x2, q010, q110);
		double q10 = GenericMath.lerp(x, x1, x2, q001, q101);
		double q11 = GenericMath.lerp(x, x1, x2, q011, q111);
		double q0 = GenericMath.lerp(y, y1, y2, q00, q10);
		double q1 = GenericMath.lerp(y, y1, y2, q01, q11);
		return GenericMath.lerp(z, z1, z2, q0, q1);
	}
	
	public static Color blend(Color a, Color b)
	{
		return GenericMath.lerp(a, b, (float) a.getAlpha() / 255.0f);
	}
	
	public static double clamp(double value, double low, double high)
	{
		if(value < low)
		{
			return low;
		}
		if(value > high)
		{
			return high;
		}
		return value;
	}
	
	public static int clamp(int value, int low, int high)
	{
		if(value < low)
		{
			return low;
		}
		if(value > high)
		{
			return high;
		}
		return value;
	}
	
	public static double inverseSqrt(double a)
	{
		double halfA = 0.5 * a;
		a = Double.longBitsToDouble(6910469410427058090L - (Double.doubleToRawLongBits(a) >> 1));
		return a * (1.5 - halfA * a * a);
	}
	
	public static double sqrt(double a)
	{
		return a * GenericMath.inverseSqrt(a);
	}
	
	public static int floor(double a)
	{
		int y = (int) a;
		if(a < (double) y)
		{
			return y - 1;
		}
		return y;
	}
	
	public static int floor(float a)
	{
		int y = (int) a;
		if(a < (float) y)
		{
			return y - 1;
		}
		return y;
	}
	
	public static long floorl(double a)
	{
		long y = (long) a;
		if(a < (double) y)
		{
			return y - 1;
		}
		return y;
	}
	
	public static long floorl(float a)
	{
		long y = (long) a;
		if(a < (float) y)
		{
			return y - 1;
		}
		return y;
	}
	
	public static byte max(byte value1, byte value2)
	{
		return value1 > value2 ? value1 : value2;
	}
	
	public static int roundUpPow2(int a)
	{
		if(a <= 0)
		{
			return 1;
		}
		if(a > 1073741824)
		{
			throw new IllegalArgumentException("Rounding " + a + " to the next highest power of two would exceed the int range");
		}
		--a;
		a |= a >> 1;
		a |= a >> 2;
		a |= a >> 4;
		a |= a >> 8;
		a |= a >> 16;
		return ++a;
	}
	
	public static long roundUpPow2(long a)
	{
		if(a <= 0)
		{
			return 1;
		}
		if(a > 0x4000000000000000L)
		{
			throw new IllegalArgumentException("Rounding " + a + " to the next highest power of two would exceed the int range");
		}
		--a;
		a |= a >> 1;
		a |= a >> 2;
		a |= a >> 4;
		a |= a >> 8;
		a |= a >> 16;
		a |= a >> 32;
		return ++a;
	}
	
	public static Float castFloat(Object o)
	{
		if(o == null)
		{
			return null;
		}
		if(o instanceof Number)
		{
			return Float.valueOf(((Number) o).floatValue());
		}
		try
		{
			return Float.valueOf(o.toString());
		} catch(NumberFormatException e)
		{
			return null;
		}
	}
	
	public static Byte castByte(Object o)
	{
		if(o == null)
		{
			return null;
		}
		if(o instanceof Number)
		{
			return Byte.valueOf(((Number) o).byteValue());
		}
		try
		{
			return Byte.valueOf(o.toString());
		} catch(NumberFormatException e)
		{
			return null;
		}
	}
	
	public static Short castShort(Object o)
	{
		if(o == null)
		{
			return null;
		}
		if(o instanceof Number)
		{
			return ((Number) o).shortValue();
		}
		try
		{
			return Short.valueOf(o.toString());
		} catch(NumberFormatException e)
		{
			return null;
		}
	}
	
	public static Integer castInt(Object o)
	{
		if(o == null)
		{
			return null;
		}
		if(o instanceof Number)
		{
			return ((Number) o).intValue();
		}
		try
		{
			return Integer.valueOf(o.toString());
		} catch(NumberFormatException e)
		{
			return null;
		}
	}
	
	public static Double castDouble(Object o)
	{
		if(o == null)
		{
			return null;
		}
		if(o instanceof Number)
		{
			return ((Number) o).doubleValue();
		}
		try
		{
			return Double.valueOf(o.toString());
		} catch(NumberFormatException e)
		{
			return null;
		}
	}
	
	public static Long castLong(Object o)
	{
		if(o == null)
		{
			return null;
		}
		if(o instanceof Number)
		{
			return ((Number) o).longValue();
		}
		try
		{
			return Long.valueOf(o.toString());
		} catch(NumberFormatException e)
		{
			return null;
		}
	}
	
	public static Boolean castBoolean(Object o)
	{
		if(o == null)
		{
			return null;
		}
		if(o instanceof Boolean)
		{
			return (Boolean) o;
		}
		if(o instanceof String)
		{
			try
			{
				return Boolean.parseBoolean((String) o);
			} catch(IllegalArgumentException e)
			{
				return null;
			}
		}
		return null;
	}
	
	public static /* varargs */ int mean(int... values)
	{
		int sum = 0;
		for(int v : values)
		{
			sum += v;
		}
		return sum / values.length;
	}
	
	public static /* varargs */ double mean(double... values)
	{
		double sum = 0.0;
		for(double v : values)
		{
			sum += v;
		}
		return sum / (double) values.length;
	}
	
	public static String decToHex(int dec, int minDigits)
	{
		String ret = Integer.toHexString(dec);
		while(ret.length() < minDigits)
		{
			ret = "" + '0' + ret;
		}
		return ret;
	}
	
	public static int mod(int a, int div)
	{
		int remainder = a % div;
		return remainder < 0 ? remainder + div : remainder;
	}
	
	public static float mod(float a, float div)
	{
		float remainder = a % div;
		return remainder < 0.0f ? remainder + div : remainder;
	}
	
	public static double mod(double a, double div)
	{
		double remainder = a % div;
		return remainder < 0.0 ? remainder + div : remainder;
	}
	
	public static Random getRandom()
	{
		return THREAD_LOCAL_RANDOM.get();
	}
	
	public static boolean isPowerOfTwo(int num)
	{
		return num > 0 && (num & num - 1) == 0;
	}
	
	public static int multiplyToShift(int a)
	{
		if(a < 1)
		{
			throw new IllegalArgumentException("Multiplicand must be at least 1");
		}
		int shift = 31 - Integer.numberOfLeadingZeros(a);
		if(1 << shift != a)
		{
			throw new IllegalArgumentException("Multiplicand must be a power of 2");
		}
		return shift;
	}
	
	public static Vector2f normalizeSafe(Vector2f v)
	{
		try
		{
			return v.normalize();
		} catch(ArithmeticException ex)
		{
			return Vector2f.ZERO;
		}
	}
	
	public static Vector2d normalizeSafe(Vector2d v)
	{
		try
		{
			return v.normalize();
		} catch(ArithmeticException ex)
		{
			return Vector2d.ZERO;
		}
	}
	
	public static Vector3f normalizeSafe(Vector3f v)
	{
		try
		{
			return v.normalize();
		} catch(ArithmeticException ex)
		{
			return Vector3f.ZERO;
		}
	}
	
	public static Vector3d normalizeSafe(Vector3d v)
	{
		try
		{
			return v.normalize();
		} catch(ArithmeticException ex)
		{
			return Vector3d.ZERO;
		}
	}
	
	public static Vector4f normalizeSafe(Vector4f v)
	{
		try
		{
			return v.normalize();
		} catch(ArithmeticException ex)
		{
			return Vector4f.ZERO;
		}
	}
	
	public static Vector4d normalizeSafe(Vector4d v)
	{
		try
		{
			return v.normalize();
		} catch(ArithmeticException ex)
		{
			return Vector4d.ZERO;
		}
	}
	
	public static VectorNf normalizeSafe(VectorNf v)
	{
		try
		{
			return v.normalize();
		} catch(ArithmeticException ex)
		{
			return new VectorNf(v.size());
		}
	}
	
	public static VectorNd normalizeSafe(VectorNd v)
	{
		try
		{
			return v.normalize();
		} catch(ArithmeticException ex)
		{
			return new VectorNd(v.size());
		}
	}
	
}
