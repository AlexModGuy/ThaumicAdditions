package com.endie.thaumicadditions.proxy;

import com.endie.thaumicadditions.blocks.BlockAbstractEssentiaJar.BlockAbstractJarItem;
import com.endie.thaumicadditions.client.isr.ItemRenderJar;
import com.endie.thaumicadditions.client.tesr.TESRAbstractJar;
import com.endie.thaumicadditions.client.texture.TextureThaumonomiconBG;
import com.endie.thaumicadditions.tiles.TileAbstractJarFillable;
import com.pengu.hammercore.client.render.item.ItemRenderingHandler;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import thaumcraft.common.blocks.essentia.BlockJarItem;

public class ClientProxy extends CommonProxy
{
	@Override
	public void init()
	{
		// Assign custom texture
		Minecraft.getMinecraft().getTextureManager().loadTickableTexture(TEXTURE_THAUMONOMICON_BG, new TextureThaumonomiconBG());
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileAbstractJarFillable.class, new TESRAbstractJar());
		ItemRenderingHandler.INSTANCE.applyItemRender(new ItemRenderJar(), i -> i instanceof BlockAbstractJarItem || i instanceof BlockJarItem);
	}
}