package com.endie.thaumicadditions.inventory.container;

import com.endie.thaumicadditions.InfoTAR;
import com.endie.thaumicadditions.init.ItemsTAR;
import com.endie.thaumicadditions.inventory.SlotTexturable;
import com.endie.thaumicadditions.tiles.TileAuraDisperser;
import com.pengu.hammercore.core.gui.container.ItemTransferHelper.TransferableContainer;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class ContainerAuraDisperser extends TransferableContainer<TileAuraDisperser>
{
	public ContainerAuraDisperser(EntityPlayer player, TileAuraDisperser t)
	{
		super(player, t, 8, 84);
	}
	
	@Override
	protected void addCustomSlots()
	{
		for(int i = 0; i < 9; ++i)
			addSlotToContainer(new SlotTexturable(t.inventory, i, 62 + (i % 3) * 18, 12 + i / 3 * 18).setValidator(s -> s.getItem() == ItemsTAR.SALT_ESSENCE).setBackgroundTexture(new ResourceLocation(InfoTAR.MOD_ID, "textures/slots/salt.png")));
	}
	
	@Override
	protected void addTransfer()
	{
		
	}
}