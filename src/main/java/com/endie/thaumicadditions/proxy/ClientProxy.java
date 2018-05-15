package com.endie.thaumicadditions.proxy;

import com.endie.thaumicadditions.InfoTAR;
import com.endie.thaumicadditions.TAReconstructed;
import com.endie.thaumicadditions.api.fx.TARParticleTypes;
import com.endie.thaumicadditions.blocks.BlockAbstractEssentiaJar.BlockAbstractJarItem;
import com.endie.thaumicadditions.client.ClientChainReactor;
import com.endie.thaumicadditions.client.isr.ItemRenderJar;
import com.endie.thaumicadditions.client.tesr.TESRAbstractJar;
import com.endie.thaumicadditions.client.tesr.TESRAspectCombiner;
import com.endie.thaumicadditions.client.tesr.TESRAuraCharger;
import com.endie.thaumicadditions.client.tesr.TESRCrystalCrusher;
import com.endie.thaumicadditions.client.texture.TextureThaumonomiconBG;
import com.endie.thaumicadditions.init.BlocksTAR;
import com.endie.thaumicadditions.init.ItemsTAR;
import com.endie.thaumicadditions.tiles.TileAbstractJarFillable;
import com.endie.thaumicadditions.tiles.TileAspectCombiner;
import com.endie.thaumicadditions.tiles.TileAuraCharger;
import com.endie.thaumicadditions.tiles.TileCrystalCrusher;
import com.pengu.hammercore.client.render.item.ItemRenderingHandler;
import com.pengu.hammercore.utils.ColorHelper;
import com.pengu.hammercore.utils.NBTUtils;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleBreaking;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import thaumcraft.common.blocks.essentia.BlockJarItem;

public class ClientProxy extends CommonProxy
{
	@Override
	public void preInit()
	{
		ModelLoader.setCustomStateMapper(BlocksTAR.CRYSTAL_WATER, new StateMap.Builder().ignore(BlockFluidBase.LEVEL).build());
	}
	
	@Override
	public void init()
	{
		MinecraftForge.EVENT_BUS.register(ClientChainReactor.REACTOR);
		
		// Assign custom texture
		Minecraft.getMinecraft().getTextureManager().loadTickableTexture(TEXTURE_THAUMONOMICON_BG, new TextureThaumonomiconBG());
		
		// Adding custom color handlers
		Minecraft.getMinecraft().getItemColors().registerItemColorHandler(ItemsTAR.SALT_ESSENCE::getItemColor, ItemsTAR.SALT_ESSENCE);
		
		// Add custom TESRs
		
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
		
		TESRCrystalCrusher crycr = new TESRCrystalCrusher();
		ClientRegistry.bindTileEntitySpecialRenderer(TileCrystalCrusher.class, crycr);
		ItemRenderingHandler.INSTANCE.setItemRender(Item.getItemFromBlock(BlocksTAR.CRYSTAL_CRUSHER), crycr);
		Minecraft.getMinecraft().getRenderItem().registerItem(Item.getItemFromBlock(BlocksTAR.CRYSTAL_CRUSHER), 0, "chest");
		
		// Fluid state mapping.
		mapFluid(BlocksTAR.CRYSTAL_WATER);
	}
	
	@Override
	public void postInit()
	{
		Minecraft.getMinecraft().effectRenderer.registerParticle(TARParticleTypes.ITEMSTACK_CRACK.getParticleID(), (particleID, worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn, args) -> new ParticleColoredBreaking(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn, new ItemStack(NBTUtils.toNBT(args))));
	}
	
	@Override
	public int getItemColor(ItemStack stack, int layer)
	{
		return Minecraft.getMinecraft().getItemColors().getColorFromItemstack(stack, layer);
	}
	
	private static void mapFluid(BlockFluidBase fluidBlock)
	{
		final Item item = Item.getItemFromBlock(fluidBlock);
		assert item != null;
		ModelBakery.registerItemVariants(item);
		ModelResourceLocation modelResourceLocation = new ModelResourceLocation(InfoTAR.MOD_ID + ":fluid", fluidBlock.getFluid().getName());
		ModelLoader.setCustomMeshDefinition(item, stack -> modelResourceLocation);
		ModelLoader.setCustomStateMapper(fluidBlock, new StateMapperBase()
		{
			@Override
			protected ModelResourceLocation getModelResourceLocation(IBlockState state)
			{
				return modelResourceLocation;
			}
		});
	}
	
	public static class ParticleColoredBreaking extends ParticleBreaking
	{
		protected ParticleColoredBreaking(World worldIn, double posXIn, double posYIn, double posZIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, ItemStack stack)
		{
			super(worldIn, posXIn, posYIn, posZIn, xSpeedIn, ySpeedIn, zSpeedIn, stack.getItem(), stack.getItemDamage());
			int color = TAReconstructed.proxy.getItemColor(stack, 0);
			setRBGColorF(ColorHelper.getRed(color), ColorHelper.getGreen(color), ColorHelper.getBlue(color));
		}
	}
}