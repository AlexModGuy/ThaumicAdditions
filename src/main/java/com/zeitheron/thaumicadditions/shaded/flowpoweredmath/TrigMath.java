package com.zeitheron.thaumicadditions.shaded.flowpoweredmath;

public class TrigMath
{
	public static final double PI = 3.141592653589793;
	public static final double SQUARED_PI = 9.869604401089358;
	public static final double HALF_PI = 1.5707963267948966;
	public static final double QUARTER_PI = 0.7853981633974483;
	public static final double TWO_PI = 6.283185307179586;
	public static final double THREE_PI_HALVES = 4.71238898038469;
	public static final double DEG_TO_RAD = 0.017453292519943295;
	public static final double HALF_DEG_TO_RAD = 0.008726646259971648;
	public static final double RAD_TO_DEG = 57.29577951308232;
	public static final double SQRT_OF_TWO = Math.sqrt(2.0);
	public static final double HALF_SQRT_OF_TWO = SQRT_OF_TWO / 2.0;
	private static final int SIN_BITS = 22;
	private static final int SIN_SIZE = 4194304;
	private static final int SIN_MASK = 4194303;
	private static final float[] SIN_TABLE = new float[4194304];
	private static final double SIN_CONVERSION_FACTOR = 667544.214430109;
	private static final int COS_OFFSET = 1048576;
	private static final double sq2p1 = 2.414213562373095;
	private static final double sq2m1 = 0.41421356237309503;
	private static final double p4 = 16.15364129822302;
	private static final double p3 = 268.42548195503974;
	private static final double p2 = 1153.029351540485;
	private static final double p1 = 1780.406316433197;
	private static final double p0 = 896.7859740366387;
	private static final double q4 = 58.95697050844462;
	private static final double q3 = 536.2653740312153;
	private static final double q2 = 1666.7838148816338;
	private static final double q1 = 2079.33497444541;
	private static final double q0 = 896.7859740366387;
	
	private TrigMath()
	{
	}
	
	public static float sin(double angle)
	{
		return TrigMath.sinRaw(GenericMath.floor(angle * SIN_CONVERSION_FACTOR));
	}
	
	public static float cos(double angle)
	{
		return TrigMath.cosRaw(GenericMath.floor(angle * SIN_CONVERSION_FACTOR));
	}
	
	public static float tan(double angle)
	{
		int idx = GenericMath.floor(angle * SIN_CONVERSION_FACTOR);
		return TrigMath.sinRaw(idx) / TrigMath.cosRaw(idx);
	}
	
	public static float csc(double angle)
	{
		return 1.0f / TrigMath.sin(angle);
	}
	
	public static float sec(double angle)
	{
		return 1.0f / TrigMath.cos(angle);
	}
	
	public static float cot(double angle)
	{
		int idx = GenericMath.floor(angle * SIN_CONVERSION_FACTOR);
		return TrigMath.cosRaw(idx) / TrigMath.sinRaw(idx);
	}
	
	public static double asin(double value)
	{
		if(value > 1.0)
		{
			return Double.NaN;
		}
		if(value < 0.0)
		{
			return -TrigMath.asin(-value);
		}
		double temp = Math.sqrt(1.0 - value * value);
		if(value > 0.7)
		{
			return 1.5707963267948966 - TrigMath.msatan(temp / value);
		}
		return TrigMath.msatan(value / temp);
	}
	
	public static double acos(double value)
	{
		if(value > 1.0 || value < -1.0)
		{
			return Double.NaN;
		}
		return 1.5707963267948966 - TrigMath.asin(value);
	}
	
	public static double atan(double value)
	{
		if(value > 0.0)
		{
			return TrigMath.msatan(value);
		}
		return -TrigMath.msatan(-value);
	}
	
	public static double atan2(double y, double x)
	{
		if(y + x == y)
		{
			return y >= 0.0 ? 1.5707963267948966 : -1.5707963267948966;
		}
		y = TrigMath.atan(y / x);
		if(x < 0.0)
		{
			if(y <= 0.0)
			{
				return y + 3.141592653589793;
			}
			return y - 3.141592653589793;
		}
		return y;
	}
	
	public static double acsc(double value)
	{
		if(value == 0.0)
		{
			return Double.NaN;
		}
		return TrigMath.asin(1.0 / value);
	}
	
	public static double asec(double value)
	{
		if(value == 0.0)
		{
			return Double.NaN;
		}
		return TrigMath.acos(1.0 / value);
	}
	
	public static double acot(double value)
	{
		if(value == 0.0)
		{
			return Double.NaN;
		}
		if(value > 0.0)
		{
			return TrigMath.atan(1.0 / value);
		}
		return TrigMath.atan(1.0 / value) + 3.141592653589793;
	}
	
	private static float sinRaw(int idx)
	{
		return SIN_TABLE[idx & 4194303];
	}
	
	private static float cosRaw(int idx)
	{
		return SIN_TABLE[idx + 1048576 & 4194303];
	}
	
	private static double mxatan(double arg)
	{
		double argsq = arg * arg;
		double value = (((16.15364129822302 * argsq + 268.42548195503974) * argsq + 1153.029351540485) * argsq + 1780.406316433197) * argsq + 896.7859740366387;
		return (value /= ((((argsq + 58.95697050844462) * argsq + 536.2653740312153) * argsq + 1666.7838148816338) * argsq + 2079.33497444541) * argsq + 896.7859740366387) * arg;
	}
	
	private static double msatan(double arg)
	{
		if(arg < 0.41421356237309503)
		{
			return TrigMath.mxatan(arg);
		}
		if(arg > 2.414213562373095)
		{
			return 1.5707963267948966 - TrigMath.mxatan(1.0 / arg);
		}
		return 0.7853981633974483 + TrigMath.mxatan((arg - 1.0) / (arg + 1.0));
	}
	
	static
	{
		for(int i = 0; i < 4194304; ++i)
		{
			TrigMath.SIN_TABLE[i] = (float) Math.sin((double) i * 6.283185307179586 / 4194304.0);
		}
	}
}
