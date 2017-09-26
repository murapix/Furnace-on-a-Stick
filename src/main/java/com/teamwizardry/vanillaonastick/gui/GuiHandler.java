package com.teamwizardry.vanillaonastick.gui;

import com.teamwizardry.vanillaonastick.VanillaOnAStick;
import com.teamwizardry.vanillaonastick.gui.crafting_table.ContainerCraftingTable;
import com.teamwizardry.vanillaonastick.gui.crafting_table.GuiCraftingTable;
import com.teamwizardry.vanillaonastick.gui.furnace.ContainerFurnace;
import com.teamwizardry.vanillaonastick.gui.furnace.GuiFurnace;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class GuiHandler implements IGuiHandler
{
	public static void init()
	{
		NetworkRegistry.INSTANCE.registerGuiHandler(VanillaOnAStick.instance, new GuiHandler());
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		switch (GuiTypes.values()[ID])
		{
			case FURNACE:
				return new ContainerFurnace(player);
			case CRAFTING_TABLE:
				return new ContainerCraftingTable(player);
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
			case CRAFTING_TABLE:
				return new GuiCraftingTable(new ContainerCraftingTable(player));
			default:
				return null;
		}
	}
	
	public enum GuiTypes
	{
		FURNACE,
		CRAFTING_TABLE;
	}
}
