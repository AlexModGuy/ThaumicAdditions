package com.zeitheron.thaumicadditions.api.fx;

import com.pengu.hammercore.client.ParticleHelper;

import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.text.TextFormatting;

public class TARParticleTypes
{
	public static final EnumParticleTypes ITEMSTACK_CRACK = ParticleHelper.newParticleType("TAR_ITEMSTACK_CRACK", TextFormatting.DARK_RED + "itemstack_crack" + TextFormatting.RESET, false, 0);
	public static final EnumParticleTypes POLLUTION = ParticleHelper.newParticleType("TAR_POLLUTION", "tc.pollution", false, 0);
	public static final EnumParticleTypes COLOR_CLOUD = ParticleHelper.newParticleType("TAR_COLOR_CLOUD", "tar.color_cloud", false, 0);
}