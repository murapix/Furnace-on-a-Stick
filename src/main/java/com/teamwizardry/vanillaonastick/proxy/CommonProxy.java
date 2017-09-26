package com.teamwizardry.vanillaonastick.proxy;

import com.teamwizardry.vanillaonastick.VanillaOnAStick;
import com.teamwizardry.vanillaonastick.gui.GuiHandler;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

@Mod.EventBusSubscriber
public class CommonProxy
{
	public void preInit(FMLPreInitializationEvent event)
	{}
	
	public void init(FMLInitializationEvent event)
	{
		NetworkRegistry.INSTANCE.registerGuiHandler(VanillaOnAStick.instance, new GuiHandler());
	}
	
	public void postInit(FMLPostInitializationEvent event)
	{}
}
