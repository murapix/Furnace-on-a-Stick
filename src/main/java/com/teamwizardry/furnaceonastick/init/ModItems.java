package com.teamwizardry.furnaceonastick.init;

import com.teamwizardry.furnaceonastick.FurnaceOnAStick;
import com.teamwizardry.furnaceonastick.item.ItemFurnaceOnAStick;

import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod.EventBusSubscriber
public class ModItems
{
	@GameRegistry.ObjectHolder("furnaceonastick:furnaceonastick")
	public static ItemFurnaceOnAStick stickFurnace;
	
	@SubscribeEvent	
	public static void registerItems(RegistryEvent.Register<Item> event)
	{
		FurnaceOnAStick.logger.info("Registering Furnace on a Stick");
		event.getRegistry().register(new ItemFurnaceOnAStick());
	}
	
	public static void registerModels()
	{
		stickFurnace.initModel();
	}
}
