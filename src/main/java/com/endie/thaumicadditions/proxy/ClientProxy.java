package com.endie.thaumicadditions.proxy;

import com.endie.thaumicadditions.blocks.BlockAbstractEssentiaJar.BlockAbstractJarItem;
import com.endie.thaumicadditions.client.tesr.TESRAbstractJar;
import com.endie.thaumicadditions.client.texture.TextureThaumonomiconBG;
import com.endie.thaumicadditions.tiles.TileAbstractJarFillable;
import com.pengu.hammercore.client.render.item.ItemRenderingHandler;

import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ClientProxy extends CommonProxy
{
	@Override
	public void init()
	{
		// Assign custom texture
		Minecraft.getMinecraft().getTextureManager().loadTickableTexture(TEXTURE_THAUMONOMICON_BG, new TextureThaumonomiconBG());
		
		TESRAbstractJar jarRender = new TESRAbstractJar();
		ClientRegistry.bindTileEntitySpecialRenderer(TileAbstractJarFillable.class, jarRender);
		ItemRenderingHandler.INSTANCE.applyItemRender(jarRender, i -> i instanceof BlockAbstractJarItem);
	}
}