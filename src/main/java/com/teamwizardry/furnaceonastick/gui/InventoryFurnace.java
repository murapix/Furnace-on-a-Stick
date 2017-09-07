package com.teamwizardry.furnaceonastick.gui;

import com.teamwizardry.furnaceonastick.Util;
import com.teamwizardry.furnaceonastick.init.ModItems;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

public class InventoryFurnace implements IInventory
{
	public NonNullList<ItemStack> inventory = NonNullList.<ItemStack>withSize(3, ItemStack.EMPTY);
	
	public int burnTime;
	public int currentItemBurnTime;
	public int cookTime;
	public int totalCookTime;
	
	public InventoryFurnace()
	{}
	
	public InventoryFurnace(NBTTagCompound compound)
	{
		readFromNBT(compound);
	}
	
	@Override
	public String getName()
	{
		return "container.furnace";
	}

	@Override
	public boolean hasCustomName()
	{
		return false;
	}

	@Override
	public ITextComponent getDisplayName()
	{
		return new TextComponentTranslation(getName());
	}

	@Override
	public int getSizeInventory()
	{
		return inventory.size();
	}

	@Override
	public boolean isEmpty()
	{
		for (ItemStack stack : inventory)
			if (!stack.isEmpty())
				return false;
		return true;
	}

	@Override
	public ItemStack getStackInSlot(int index)
	{
		return inventory.get(index);
	}

	@Override
	public ItemStack decrStackSize(int index, int count)
	{
		return ItemStackHelper.getAndSplit(inventory, index, count);
	}

	@Override
	public ItemStack removeStackFromSlot(int index)
	{
		return ItemStackHelper.getAndRemove(inventory, index);
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack)
	{
		ItemStack item = inventory.get(index);
		boolean flag = !stack.isEmpty() && stack.isItemEqual(item) && ItemStack.areItemStackTagsEqual(stack, item);
		inventory.set(index, stack);
		
		if (stack.getCount() > getInventoryStackLimit())
			stack.setCount(getInventoryStackLimit());
		
		if (index == 0 && !flag)
		{
			totalCookTime = Util.getCookTime(stack);
			cookTime = 0;
			markDirty();
		}
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}

	@Override
	public void markDirty()
	{
		
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer player)
	{
		ItemStack active = player.getActiveItemStack();
		return active != null && active.getItem() == ModItems.stickFurnace;
	}

	@Override
	public void openInventory(EntityPlayer player)
	{
		
	}

	@Override
	public void closeInventory(EntityPlayer player)
	{
		
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack)
	{
		return false;
	}

	@Override
	public int getField(int id)
	{
		switch (id)
		{
			case 0:
				return burnTime;
			case 1:
				return currentItemBurnTime;
			case 2:
				return cookTime;
			case 3:
				return totalCookTime;
			default:
				return 0;
		}
	}

	@Override
	public void setField(int id, int value)
	{
		switch (id)
		{
			case 0:
				burnTime = value;
				break;
			case 1:
				currentItemBurnTime = value;
				break;
			case 2:
				cookTime = value;
				break;
			case 3:
				totalCookTime = value;
		}
	}

	@Override
	public int getFieldCount()
	{
		return 4;
	}

	@Override
	public void clear()
	{
		inventory.clear();
	}
	
	public boolean isBurning()
	{
		return burnTime > 0;
	}
	
	public void readFromNBT(NBTTagCompound compound)
	{
		this.inventory = NonNullList.<ItemStack>withSize(this.getSizeInventory(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(compound, this.inventory);
        this.burnTime = compound.getInteger("BurnTime");
        this.cookTime = compound.getInteger("CookTime");
        this.totalCookTime = compound.getInteger("CookTimeTotal");
        this.currentItemBurnTime = Util.getItemBurnTime((ItemStack)this.inventory.get(1));
	}
	
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		compound.setInteger("BurnTime", burnTime);
		compound.setInteger("CookTime", cookTime);
		compound.setInteger("CookTimeTotal", totalCookTime);
		ItemStackHelper.saveAllItems(compound, inventory);
		return compound;
	}
}
