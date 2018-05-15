package com.endie.thaumicadditions.effect;

import java.util.UUID;

import com.endie.thaumicadditions.api.AttributesTAR;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AbstractAttributeMap;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;

public class PotionSoundSensivity extends PotionBaseTAR
{
	public static final UUID SENS = UUID.fromString("506ae2cb-95d7-4347-9625-5ea935930e3e");
	
	public PotionSoundSensivity()
	{
		super(false, 0xFFAA00, "sonus", 0, 0);
	}
	
	@Override
	public void applyAttributesModifiersToEntity(EntityLivingBase entityLivingBaseIn, AbstractAttributeMap attributeMapIn, int amplifier)
	{
		IAttributeInstance attr = attributeMapIn.getAttributeInstance(AttributesTAR.SOUND_SENSIVITY);
		
		float amt = 1;
		
		if(amplifier > 20)
			amt = .01F;
		else if(amplifier > 8 && entityLivingBaseIn.getRNG().nextBoolean())
			amt = entityLivingBaseIn.getRNG().nextFloat() * .5F;
		else
			amt += amplifier / 8F;
		
		attr.removeModifier(SENS);
		attr.applyModifier(new AttributeModifier(SENS, "Sonus Potion", amt - 1, 2));
	}
	
	@Override
	public void removeAttributesModifiersFromEntity(EntityLivingBase entityLivingBaseIn, AbstractAttributeMap attributeMapIn, int amplifier)
	{
		IAttributeInstance attr = attributeMapIn.getAttributeInstance(AttributesTAR.SOUND_SENSIVITY);
		attr.removeModifier(SENS);
	}
}