package com.teamwizardry.vanillaonastick.gui.crafting_table;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ContainerWorkbench;

public class ContainerCraftingTable extends ContainerWorkbench
{
	public ContainerCraftingTable(EntityPlayer player)
	{
		super(player.inventory, player.world, player.getPosition());
	}

	@Override
	public boolean canInteractWith(EntityPlayer player)
	{
		return true;
	}
}
