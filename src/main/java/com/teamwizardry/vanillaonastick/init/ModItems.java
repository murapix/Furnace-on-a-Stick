package com.teamwizardry.vanillaonastick.init;

import com.teamwizardry.vanillaonastick.VanillaOnAStick;
import com.teamwizardry.vanillaonastick.item.ItemCraftingTable;
import com.teamwizardry.vanillaonastick.item.ItemEnderChest;
import com.teamwizardry.vanillaonastick.item.ItemFurnace;

import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber
public class ModItems
{
	@GameRegistry.ObjectHolder("vanillaonastick:furnaceonastick")
	public static ItemFurnace stickFurnace;
	
	@GameRegistry.ObjectHolder("vanillaonastick:craftingtableonastick")
	public static ItemCraftingTable stickCraftingTable;
	
	@GameRegistry.ObjectHolder("vanillaonastick:enderchestonastick")
	public static ItemEnderChest stickEnderChest;
	
//	@GameRegistry.ObjectHolder("vanillaonastick:anvilonastick")
//	@GameRegistry.ObjectHolder("vanillaonastick:bedonastick")
//	@GameRegistry.ObjectHolder("vanillaonastick:brewingstandonastick")
//	@GameRegistry.ObjectHolder("vanillaonastick:enchantingtableonastick")
//	@GameRegistry.ObjectHolder("vanillaonastick:jukeboxonastick")
	
	@SubscribeEvent	
	public static void registerItems(RegistryEvent.Register<Item> event)
	{
		VanillaOnAStick.logger.info("Registering Vanilla on a Stick");
		IForgeRegistry<Item> r = event.getRegistry();
		r.register(new ItemFurnace());
		r.register(new ItemCraftingTable());
		r.register(new ItemEnderChest());
	}
	
	public static void registerModels()
	{
		stickFurnace.initModel();
		stickCraftingTable.initModel();
		stickEnderChest.initModel();
	}
}
