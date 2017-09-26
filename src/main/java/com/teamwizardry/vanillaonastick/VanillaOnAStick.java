package com.teamwizardry.vanillaonastick;

import org.apache.logging.log4j.Logger;

import com.teamwizardry.vanillaonastick.proxy.CommonProxy;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = VanillaOnAStick.MODID, name = VanillaOnAStick.MODNAME, version = VanillaOnAStick.VERSION, useMetadata = true)
public class VanillaOnAStick
{
	public static final String MODID = "vanillaonastick";
	public static final String MODNAME = "Vanilla On A Stick";
	public static final String VERSION = "1.0";
	public static final String CLIENT = "com.teamwizardry.vanillaonastick.proxy.ClientProxy";
	public static final String SERVER = "com.teamwizardry.vanillaonastick.proxy.ServerProxy";
	
	@SidedProxy(clientSide = CLIENT, serverSide = SERVER)
	public static CommonProxy proxy;
	
	@Mod.Instance
	public static VanillaOnAStick instance;
	
	public static Logger logger;
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		logger = event.getModLog();
		proxy.preInit(event);
	}
	
	@Mod.EventHandler
	public void init(FMLInitializationEvent event)
	{
		proxy.init(event);
	}
	
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		proxy.postInit(event);
	}
}
