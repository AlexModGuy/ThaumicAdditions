package com.endie.thaumicadditions.init;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import com.endie.thaumicadditions.effect.PotionSoundSensivity;
import com.pengu.hammercore.utils.OnetimeCaller;

import net.minecraft.potion.Potion;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

public class PotionsTAR
{
	public static final PotionSoundSensivity SOUND_SENSIVITY = new PotionSoundSensivity();
	
	public static final OnetimeCaller register = new OnetimeCaller(PotionsTAR::$register);
	private static void $register()
	{
		IForgeRegistry<Potion> reg = GameRegistry.findRegistry(Potion.class);
		
		for(Field f : PotionsTAR.class.getDeclaredFields())
		{
			f.setAccessible(true);
			if(Potion.class.isAssignableFrom(f.getType()) && Modifier.isStatic(f.getModifiers()))
				try
				{
					reg.register((Potion) f.get(null));
				} catch(IllegalArgumentException | IllegalAccessException e)
				{
					e.printStackTrace();
				}
		}
	}
}