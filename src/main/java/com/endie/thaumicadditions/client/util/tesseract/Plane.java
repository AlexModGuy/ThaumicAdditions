package com.endie.thaumicadditions.client.util.tesseract;

import org.lwjgl.opengl.GL11;

import com.endie.thaumicadditions.shaded.flowpoweredmath.TrigMath;
import com.endie.thaumicadditions.shaded.flowpoweredmath.matrix.Matrix4f;
import com.endie.thaumicadditions.shaded.flowpoweredmath.vector.Vector3f;
import com.endie.thaumicadditions.shaded.flowpoweredmath.vector.Vector4f;
import com.pengu.hammercore.utils.ColorHelper;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Plane
{
	Vector4f[] vectors;
	
	public Plane(Vector4f vec1, Vector4f vec2, Vector4f vec3, Vector4f vec4)
	{
		this.vectors = new Vector4f[] { vec1, vec2, vec3, vec4 };
	}
	
	@SideOnly(value = Side.CLIENT)
	public void draw(int color, double radian)
	{
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder worldRenderer = tessellator.getBuffer();
		worldRenderer.begin(GL11.GL_LINE_LOOP, DefaultVertexFormats.POSITION_TEX_COLOR);
		Plane.project(worldRenderer, Plane.rotYW(this.vectors[0], radian), 0, 0, color);
		Plane.project(worldRenderer, Plane.rotYW(this.vectors[1], radian), 0, 1, color);
		Plane.project(worldRenderer, Plane.rotYW(this.vectors[2], radian), 1, 1, color);
		Plane.project(worldRenderer, Plane.rotYW(this.vectors[3], radian), 1, 0, color);
		tessellator.draw();
	}
	
	private static Vector4f rotXW(Vector4f v, double angle)
	{
		return Matrix4f.from(TrigMath.cos(angle), 0.0f, 0.0f, TrigMath.sin(angle), 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, -TrigMath.sin(angle), 0.0f, 0.0f, TrigMath.cos(angle)).transform(v);
	}
	
	private static Vector4f rotZW(Vector4f v, double angle)
	{
		return Matrix4f.from(1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, TrigMath.cos(angle), -TrigMath.sin(angle), 0.0f, 0.0f, TrigMath.sin(angle), TrigMath.cos(angle)).transform(v);
	}
	
	private static Vector4f rotYW(Vector4f v, double angle)
	{
		return Matrix4f.from(1.0f, 0.0f, 0.0f, 0.0f, 0.0f, TrigMath.cos(angle), 0.0f, TrigMath.sin(angle), 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, -TrigMath.sin(angle), 0.0f, TrigMath.cos(angle)).transform(v);
	}
	
	private static Vector4f rotXY(Vector4f v, double angle)
	{
		return Matrix4f.from(TrigMath.cos(angle), -TrigMath.sin(angle), 0.0f, 0.0f, TrigMath.sin(angle), TrigMath.cos(angle), 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f).transform(v);
	}
	
	@SideOnly(value = Side.CLIENT)
	private static void project(BufferBuilder buffer, Vector4f vector, int u, int v, int color)
	{
		double scalar = 1.0 / (double) (vector.getW() + 1.0f);
		Vector3f vector1 = vector.toVector3().mul(scalar);
		buffer.pos(vector1.getX(), vector1.getY(), vector1.getZ()).tex(u, v).color(ColorHelper.getRed(color), ColorHelper.getGreen(color), ColorHelper.getBlue(color), ColorHelper.getAlpha(color)).endVertex();
	}
}