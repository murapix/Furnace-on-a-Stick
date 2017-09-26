package com.teamwizardry.vanillaonastick.gui.crafting_table;

import com.teamwizardry.vanillaonastick.gui.GuiBase;

import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiCraftingTable extends GuiBase
{
	private static final ResourceLocation TEX = new ResourceLocation("textures/gui/container/crafting_table.png");
	
	public GuiCraftingTable(Container container)
	{
		super(container, TEX);
	}
}
