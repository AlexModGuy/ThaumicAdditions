package com.endie.thaumicadditions.proxy;

import com.endie.thaumicadditions.blocks.BlockAbstractEssentiaJar.BlockAbstractJarItem;
import com.endie.thaumicadditions.client.isr.ItemRenderJar;
import com.endie.thaumicadditions.client.tesr.TESRAbstractJar;
import com.endie.thaumicadditions.client.tesr.TESRAspectCombiner;
import com.endie.thaumicadditions.client.tesr.TESRAuraCharger;
import com.endie.thaumicadditions.client.texture.TextureThaumonomiconBG;
import com.endie.thaumicadditions.init.BlocksTAR;
import com.endie.thaumicadditions.tiles.TileAbstractJarFillable;
import com.endie.thaumicadditions.tiles.TileAspectCombiner;
import com.endie.thaumicadditions.tiles.TileAuraCharger;
import com.pengu.hammercore.client.render.item.ItemRenderingHandler;

import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
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
		
		TESRAspectCombiner acom = new TESRAspectCombiner();
		ClientRegistry.bindTileEntitySpecialRenderer(TileAspectCombiner.class, acom);
		ItemRenderingHandler.INSTANCE.setItemRender(Item.getItemFromBlock(BlocksTAR.ASPECT_COMBINER), acom);
		Minecraft.getMinecraft().getRenderItem().registerItem(Item.getItemFromBlock(BlocksTAR.ASPECT_COMBINER), 0, "chest");
		
		TESRAuraCharger cha = new TESRAuraCharger();
		ClientRegistry.bindTileEntitySpecialRenderer(TileAuraCharger.class, cha);
		ItemRenderingHandler.INSTANCE.setItemRender(Item.getItemFromBlock(BlocksTAR.AURA_CHARGER), cha);
		Minecraft.getMinecraft().getRenderItem().registerItem(Item.getItemFromBlock(BlocksTAR.AURA_CHARGER), 0, "chest");
	}
}