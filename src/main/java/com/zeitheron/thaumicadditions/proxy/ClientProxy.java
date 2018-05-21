package com.zeitheron.thaumicadditions.proxy;

import com.pengu.hammercore.client.render.item.ItemRenderingHandler;
import com.pengu.hammercore.common.blocks.base.iBlockHorizontal;
import com.pengu.hammercore.common.blocks.base.iBlockOrientable;
import com.pengu.hammercore.utils.ColorHelper;
import com.pengu.hammercore.utils.NBTUtils;
import com.zeitheron.thaumicadditions.InfoTAR;
import com.zeitheron.thaumicadditions.SProt;
import com.zeitheron.thaumicadditions.TAReconstructed;
import com.zeitheron.thaumicadditions.api.fx.TARParticleTypes;
import com.zeitheron.thaumicadditions.blocks.BlockAbstractEssentiaJar.BlockAbstractJarItem;
import com.zeitheron.thaumicadditions.client.ClientChainReactor;
import com.zeitheron.thaumicadditions.client.FXHandlerClient;
import com.zeitheron.thaumicadditions.client.isr.ItemRenderJar;
import com.zeitheron.thaumicadditions.client.tesr.TESRAspectCombiner;
import com.zeitheron.thaumicadditions.client.tesr.TESRAuraCharger;
import com.zeitheron.thaumicadditions.client.tesr.TESRAuraDisperser;
import com.zeitheron.thaumicadditions.client.tesr.TESRCrystalBore;
import com.zeitheron.thaumicadditions.client.tesr.TESRCrystalCrusher;
import com.zeitheron.thaumicadditions.client.texture.TextureThaumonomiconBG;
import com.zeitheron.thaumicadditions.init.BlocksTAR;
import com.zeitheron.thaumicadditions.init.ItemsTAR;
import com.zeitheron.thaumicadditions.tiles.TileAspectCombiner;
import com.zeitheron.thaumicadditions.tiles.TileAuraCharger;
import com.zeitheron.thaumicadditions.tiles.TileAuraDisperser;
import com.zeitheron.thaumicadditions.tiles.TileCrystalBore;
import com.zeitheron.thaumicadditions.tiles.TileCrystalCrusher;

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
import thaumcraft.client.fx.ParticleEngine;
import thaumcraft.client.fx.particles.FXGeneric;
import thaumcraft.common.blocks.essentia.BlockJarItem;

public class ClientProxy extends CommonProxy
{
	public static final String BUILD = "DEV_BUILD";
	
	@Override
	public void construct()
	{
		if(BUILD.contains("TRUSTED"))
		{
			TAReconstructed.LOG.warn("You are running trusted build " + InfoTAR.MOD_VERSION + "@" + SProt.BUILD_COPY + ". We must check that everything is OK before continuing!");
			String err = SProt.playErr();
			if(err != null)
			{
				TAReconstructed.LOG.error("Uh oh! Zeitheron detected that you can't play with this test build!");
				throw new RuntimeException(err);
			}
			TAReconstructed.LOG.info("Zeitheron checked everything, you are allowed ;)");
		} else if(BUILD.contains("DEV"))
			TAReconstructed.LOG.info("Welcome to Minecraft, my dear developer!");
		else
			TAReconstructed.LOG.info("You are running general public build " + InfoTAR.MOD_VERSION + ". You're allright! ;)");
	}
	
	@Override
	public void preInit()
	{
		ModelLoader.setCustomStateMapper(BlocksTAR.CRYSTAL_WATER, new StateMap.Builder().ignore(BlockFluidBase.LEVEL).build());
		ModelLoader.setCustomStateMapper(BlocksTAR.ASPECT_COMBINER, new StateMap.Builder().ignore(iBlockHorizontal.FACING).build());
		ModelLoader.setCustomStateMapper(BlocksTAR.CRYSTAL_BORE, new StateMap.Builder().ignore(iBlockOrientable.FACING).build());
	}
	
	@Override
	public void init()
	{
		MinecraftForge.EVENT_BUS.register(ClientChainReactor.REACTOR);
		
		// Assign custom texture
		Minecraft.getMinecraft().getTextureManager().loadTickableTexture(TEXTURE_THAUMONOMICON_BG, new TextureThaumonomiconBG());
		
		// Adding custom color handlers
		Minecraft.getMinecraft().getItemColors().registerItemColorHandler(ItemsTAR.SALT_ESSENCE::getItemColor, ItemsTAR.SALT_ESSENCE);
		Minecraft.getMinecraft().getItemColors().registerItemColorHandler(ItemsTAR.ENTITY_CELL::getColor, ItemsTAR.ENTITY_CELL);
		
		// Add custom TESRs
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileAuraDisperser.class, new TESRAuraDisperser());
		
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
		
		TESRCrystalBore crybo = new TESRCrystalBore();
		ClientRegistry.bindTileEntitySpecialRenderer(TileCrystalBore.class, crybo);
		ItemRenderingHandler.INSTANCE.setItemRender(Item.getItemFromBlock(BlocksTAR.CRYSTAL_BORE), crybo);
		Minecraft.getMinecraft().getRenderItem().registerItem(Item.getItemFromBlock(BlocksTAR.CRYSTAL_BORE), 0, "chest");
		
		// Fluid state mapping.
		mapFluid(BlocksTAR.CRYSTAL_WATER);
	}
	
	@Override
	public void postInit()
	{
		Minecraft.getMinecraft().effectRenderer.registerParticle(TARParticleTypes.ITEMSTACK_CRACK.getParticleID(), (particleID, worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn, args) -> new ParticleColoredBreaking(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn, new ItemStack(NBTUtils.toNBT(args))));
		
		Minecraft.getMinecraft().effectRenderer.registerParticle(TARParticleTypes.POLLUTION.getParticleID(), (particleID, w, x, y, z, x2, y2, z2, args) ->
		{
			FXGeneric fb = new FXGeneric(w, x, y, z, (w.rand.nextFloat() - w.rand.nextFloat()) * 0.005, 0.02, (w.rand.nextFloat() - w.rand.nextFloat()) * 0.005);
			fb.setMaxAge(400 + w.rand.nextInt(100));
			fb.setRBGColorF(1F, .3F, .9F);
			fb.setAlphaF(.5F, 0);
			fb.setGridSize(16);
			fb.setParticles(56, 1, 1);
			fb.setScale(2, 5);
			fb.setLayer(1);
			fb.setSlowDown(1);
			fb.setWind(0.001);
			fb.setRotationSpeed(w.rand.nextFloat(), w.rand.nextBoolean() ? -1 : 1);
			ParticleEngine.addEffect(w, fb);
			return null;
		});
		
		Minecraft.getMinecraft().effectRenderer.registerParticle(TARParticleTypes.COLOR_CLOUD.getParticleID(), (particleID, worldIn, x, y, z, x2, y2, z2, args) ->
		{
			int red = args.length > 1 ? args[0] : 255;
			int green = args.length > 2 ? args[1] : 255;
			int blue = args.length > 3 ? args[2] : 255;
			int alpha = args.length > 4 ? args[3] : 0;
			int a = 200 + worldIn.rand.nextInt(100);
			FXGeneric fb = new FXGeneric(worldIn, x, y, z, (x2 - x) / (a * .9), (y2 - y) / (a * .9), (z2 - z) / (a * .9));
			fb.setMaxAge(a);
			fb.setRBGColorF(red / 255F, green / 255F, blue / 255F);
			fb.setAlphaF(alpha == 0 ? .3F : alpha / 255F, 0);
			fb.setGridSize(16);
			fb.setParticles(56, 1, 1);
			fb.setScale(2, 5);
			fb.setLayer(0);
			fb.setSlowDown(1);
			fb.setNoClip(args.length > 5 ? args[4] > 0 : false);
			fb.setRotationSpeed(worldIn.rand.nextFloat(), worldIn.rand.nextBoolean() ? -1 : 1);
			ParticleEngine.addEffect(worldIn, fb);
			return null;
		});
	}
	
	@Override
	public int getItemColor(ItemStack stack, int layer)
	{
		return Minecraft.getMinecraft().getItemColors().getColorFromItemstack(stack, layer);
	}
	
	@Override
	protected FXHandler createFX()
	{
		return new FXHandlerClient();
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