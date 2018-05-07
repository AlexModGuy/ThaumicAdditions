package com.endie.thaumicadditions.client.texture;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.endie.thaumicadditions.InfoTAR;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.ITickableTextureObject;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import thaumcraft.client.gui.GuiResearchBrowser;

public class TextureThaumonomiconBG extends AbstractTexture implements ITickableTextureObject
{
	private BufferedImage baseTex;
	public final BufferedImage texture = new BufferedImage(1024, 1024, BufferedImage.TYPE_INT_RGB);
	public boolean blur = true, clamp;
	
	@Override
	public void loadTexture(IResourceManager resourceManager) throws IOException
	{
		baseTex = ImageIO.read(resourceManager.getResource(new ResourceLocation(InfoTAR.MOD_ID, "textures/gui/thaumonomicon_back.jpg")).getInputStream());
		
		deleteGlTexture();
		TextureUtil.uploadTextureImageAllocate(getGlTextureId(), this.baseTex, blur, clamp);
	}
	
	private boolean hasChanged;
	private int x;
	
	@Override
	public void tick()
	{
		GuiScreen gui = Minecraft.getMinecraft().currentScreen;
		if(!(gui instanceof GuiResearchBrowser))
			return;
		
		x++;
		int time = 10;
		if(x % time == 0)
			makeOffset(x / time);
		if(x >= baseTex.getWidth() * time)
			x = 0;
		if(hasChanged)
		{
			hasChanged = false;
			deleteGlTexture();
			TextureUtil.uploadTextureImageAllocate(getGlTextureId(), this.texture, blur, clamp);
		}
	}
	
	private void makeOffset(int x)
	{
		Graphics2D g = texture.createGraphics();
		g.drawImage(baseTex, x, 0, null);
		g.drawImage(baseTex, x - baseTex.getWidth(), 0, null);
		g.dispose();
		hasChanged = true;
	}
}