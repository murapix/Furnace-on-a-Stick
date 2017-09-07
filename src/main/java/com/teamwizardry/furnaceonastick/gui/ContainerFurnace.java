package com.teamwizardry.furnaceonastick.gui;

import com.teamwizardry.furnaceonastick.Util;
import com.teamwizardry.furnaceonastick.init.ModItems;
import com.teamwizardry.furnaceonastick.item.ItemFurnaceOnAStick;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnaceFuel;
import net.minecraft.inventory.SlotFurnaceOutput;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.math.MathHelper;

public class ContainerFurnace extends Container
{
	public final IInventory furnace;
	private int heldSlot;

	public ContainerFurnace(EntityPlayer player)
	{
		InventoryPlayer playerInventory = player.inventory;
		ItemStack stickFurnace = player.getHeldItemMainhand();
		heldSlot = player.inventory.currentItem;
		if (stickFurnace.getItem() != ModItems.stickFurnace)
		{
			stickFurnace = player.getHeldItemOffhand();
			heldSlot = -1;
		}
		if (stickFurnace.getItem() != ModItems.stickFurnace)
			throw new NullPointerException("No Furnace on a Stick held");
		NBTTagCompound itemCompound = stickFurnace.getTagCompound();
		NBTTagCompound furnaceCompound = itemCompound.getCompoundTag("FurnaceData");
		furnace = new InventoryFurnace(furnaceCompound);
		addSlotToContainer(new Slot(furnace, 0, 56, 17));
		addSlotToContainer(new SlotFurnaceFuel(furnace, 1, 56, 53));
		addSlotToContainer(new SlotFurnaceOutput(player, furnace, 2, 116, 35));

		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 9; j++)
				addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));

		for (int i = 0; i < 9; i++)
			if (i != heldSlot) addSlotToContainer(new Slot(playerInventory, i, 8 + i * 18, 142));

		if (heldSlot != -1) addSlotToContainer(new SlotUntouchable(playerInventory, heldSlot, 8 + heldSlot * 18, 142));
	}
	
	public ContainerFurnace(EntityPlayer player, ItemStack stack)
	{
		InventoryPlayer playerInventory = player.inventory;
		if (stack.getItem() != ModItems.stickFurnace)
			throw new NullPointerException("No Furnace on a Stick held");
		
		heldSlot = Util.getSlotFor(stack, playerInventory.mainInventory);
		NBTTagCompound itemCompound = stack.getTagCompound();
		NBTTagCompound furnaceCompound = itemCompound.getCompoundTag("FurnaceData");
		furnace = new InventoryFurnace(furnaceCompound);
		addSlotToContainer(new Slot(furnace, 0, 56, 17));
		addSlotToContainer(new SlotFurnaceFuel(furnace, 1, 56, 53));
		addSlotToContainer(new SlotFurnaceOutput(player, furnace, 2, 116, 35));

		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 9; j++)
				addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));

		for (int i = 0; i < 9; i++)
			addSlotToContainer(new Slot(playerInventory, i, 8 + i * 18, 142));
	}

	@Override
	public void onContainerClosed(EntityPlayer player)
	{
		NBTTagCompound compound = ((InventoryFurnace) furnace).writeToNBT(new NBTTagCompound());
		ItemStack stack = player.inventory.getStackInSlot(heldSlot < 0 ? 40 : heldSlot);
		if (stack.getItem() instanceof ItemFurnaceOnAStick)
		{
			if (!stack.hasTagCompound()) stack.setTagCompound(new NBTTagCompound());
			stack.getTagCompound().setTag("FurnaceData", compound);
		}
	}

//	@Override
//	public void detectAndSendChanges()
//	{
//		super.detectAndSendChanges();
//
//		for (int i = 0; i < listeners.size(); ++i)
//		{
//			IContainerListener icontainerlistener = listeners.get(i);
//
//			if (cookTime != furnace.getField(2))
//				icontainerlistener.sendWindowProperty(this, 2, furnace.getField(2));
//
//			if (burnTime != furnace.getField(0))
//				icontainerlistener.sendWindowProperty(this, 0, furnace.getField(0));
//
//			if (currentItemBurnTime != furnace.getField(1))
//				icontainerlistener.sendWindowProperty(this, 1, furnace.getField(1));
//
//			if (totalCookTime != furnace.getField(3))
//				icontainerlistener.sendWindowProperty(this, 3, furnace.getField(3));
//		}
//
//		cookTime = furnace.getField(2);
//		burnTime = furnace.getField(0);
//		currentItemBurnTime = furnace.getField(1);
//		totalCookTime = furnace.getField(3);
//	}

	@Override
	public void addListener(IContainerListener listener)
	{
		super.addListener(listener);
		listener.sendAllWindowProperties(this, furnace);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
	{
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = inventorySlots.get(index);

		if (slot != null && slot.getHasStack())
		{
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (index == 2)
			{
				if (!mergeItemStack(itemstack1, 3, 39, true)) { return ItemStack.EMPTY; }

				slot.onSlotChange(itemstack1, itemstack);
			}
			else if (index != 1 && index != 0)
			{
				if (!FurnaceRecipes.instance().getSmeltingResult(itemstack1).isEmpty())
				{
					if (!mergeItemStack(itemstack1, 0, 1, false)) { return ItemStack.EMPTY; }
				}
				else if (TileEntityFurnace.isItemFuel(itemstack1))
				{
					if (!mergeItemStack(itemstack1, 1, 2, false)) { return ItemStack.EMPTY; }
				}
				else if (index >= 3 && index < 30)
				{
					if (!mergeItemStack(itemstack1, 30, 39, false)) { return ItemStack.EMPTY; }
				}
				else if (index >= 30 && index < 39
						&& !mergeItemStack(itemstack1, 3, 30, false)) { return ItemStack.EMPTY; }
			}
			else if (!mergeItemStack(itemstack1, 3, 39, false)) { return ItemStack.EMPTY; }

			if (itemstack1.isEmpty())
			{
				slot.putStack(ItemStack.EMPTY);
			}
			else
			{
				slot.onSlotChanged();
			}

			if (itemstack1.getCount() == itemstack.getCount()) { return ItemStack.EMPTY; }

			slot.onTake(playerIn, itemstack1);
		}

		return itemstack;
	}

	@Override
	public boolean canInteractWith(EntityPlayer player)
	{
		return true;
	}
	
	public void tick()
	{
		InventoryFurnace furnace = ((InventoryFurnace) this.furnace);

		if (furnace.isBurning())
			furnace.burnTime--;

		ItemStack itemstack = furnace.inventory.get(1);

		if (furnace.isBurning() || !itemstack.isEmpty() && !furnace.inventory.get(0).isEmpty())
		{
			if (!furnace.isBurning() && Util.canSmelt(furnace.inventory))
			{
				furnace.burnTime = Util.getItemBurnTime(itemstack);
				furnace.currentItemBurnTime = furnace.burnTime;

				if (furnace.isBurning())
				{
					if (!itemstack.isEmpty())
					{
						Item item = itemstack.getItem();
						itemstack.shrink(1);

						if (itemstack.isEmpty())
						{
							ItemStack item1 = item.getContainerItem(itemstack);
							furnace.inventory.set(1, item1);
						}
					}
				}
			}

			if (furnace.isBurning() && Util.canSmelt(furnace.inventory))
			{
				furnace.cookTime++;

				if (furnace.cookTime == furnace.totalCookTime)
				{
					furnace.cookTime = 0;
					furnace.totalCookTime = Util.getCookTime(furnace.inventory.get(0));
					Util.smeltItem(furnace.inventory);
				}
			}
			else furnace.cookTime = 0;
		}
		else if (!furnace.isBurning() && furnace.cookTime > 0)
			furnace.cookTime = MathHelper.clamp(furnace.cookTime - 2, 0, furnace.totalCookTime);
	}
}
