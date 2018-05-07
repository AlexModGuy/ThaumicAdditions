package com.endie.thaumicadditions.proxy;

import com.endie.thaumicadditions.client.texture.TextureThaumonomiconBG;

import net.minecraft.client.Minecraft;

public class ClientProxy extends CommonProxy
{
	@Override
	public void init()
	{
		// Assign custom texture
		Minecraft.getMinecraft().getTextureManager().loadTickableTexture(TEXTURE_THAUMONOMICON_BG, new TextureThaumonomiconBG());
	}
}