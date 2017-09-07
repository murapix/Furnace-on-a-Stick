package com.teamwizardry.furnaceonastick.proxy;

import com.teamwizardry.furnaceonastick.FurnaceOnAStick;
import com.teamwizardry.furnaceonastick.gui.GuiHandler;

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
		NetworkRegistry.INSTANCE.registerGuiHandler(FurnaceOnAStick.instance, new GuiHandler());
	}
	
	public void postInit(FMLPostInitializationEvent event)
	{}
}
