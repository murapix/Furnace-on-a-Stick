package com.teamwizardry.furnaceonastick.gui;

import com.teamwizardry.furnaceonastick.FurnaceOnAStick;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class GuiHandler implements IGuiHandler
{
	public static void init()
	{
		NetworkRegistry.INSTANCE.registerGuiHandler(FurnaceOnAStick.instance, new GuiHandler());
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		switch (GuiTypes.values()[ID])
		{
			case FURNACE:
				return new ContainerFurnace(player);
			default:
				return null;
		}
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		switch (GuiTypes.values()[ID])
		{
			case FURNACE:
				return new GuiFurnace(player, new ContainerFurnace(player));
			default:
				return null;
		}
	}
	
	public enum GuiTypes
	{
		FURNACE;
	}
}
