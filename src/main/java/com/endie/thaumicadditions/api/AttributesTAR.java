package com.endie.thaumicadditions.api;

import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.RangedAttribute;

public class AttributesTAR
{
	public static final IAttribute SOUND_SENSIVITY = new RangedAttribute(null, "generic.soundSensivity", 1, 0, 10).setDescription("Sound Sensivity").setShouldWatch(true);
}