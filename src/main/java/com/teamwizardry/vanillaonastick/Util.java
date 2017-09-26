package com.teamwizardry.vanillaonastick;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.NonNullList;

public class Util
{
	public static int getCookTime(ItemStack stack)
	{
		return 200;
	}

	public static int getItemBurnTime(ItemStack stack)
	{
		return TileEntityFurnace.getItemBurnTime(stack);
	}

	public static boolean isItemFuel(ItemStack stack)
	{
		return TileEntityFurnace.isItemFuel(stack);
	}

	public static boolean isBurning(IInventory inventory)
	{
		return inventory.getField(0) > 0;
	}

	public static boolean canSmelt(NonNullList<ItemStack> items)
	{
		if (items.get(0).isEmpty())
			return false;

		ItemStack result = FurnaceRecipes.instance().getSmeltingResult(items.get(0));
		if (result.isEmpty())
			return false;

		ItemStack output = items.get(2);
		if (output.isEmpty())
			return true;
		if (!output.isItemEqual(result))
			return false;
		if (output.getCount() + result.getCount() <= 64 && output.getCount() + result.getCount() <= output.getMaxStackSize())
			return true;
		return output.getCount() + result.getCount() <= result.getMaxStackSize();
	}
	
	public static void smeltItem(NonNullList<ItemStack> items)
	{
		if (Util.canSmelt(items))
        {
            ItemStack input = items.get(0);
            ItemStack result = FurnaceRecipes.instance().getSmeltingResult(input);
            ItemStack output = items.get(2);

            if (output.isEmpty())
                items.set(2, result.copy());

            else if (output.getItem() == result.getItem())
                output.grow(result.getCount());

            if (input.getItem() == Item.getItemFromBlock(Blocks.SPONGE) && input.getMetadata() == 1 && !items.get(1).isEmpty() && items.get(1).getItem() == Items.BUCKET)
                items.set(1, new ItemStack(Items.WATER_BUCKET));

            input.shrink(1);
        }
	}
	
	public static int getSlotFor(ItemStack stack, NonNullList<ItemStack> inventory)
    {
        for (int i = 0; i < inventory.size(); ++i)
            if (!inventory.get(i).isEmpty() && stackEqualExact(stack, inventory.get(i)))
                return i;
        return -1;
    }
	
	private static boolean stackEqualExact(ItemStack stack1, ItemStack stack2)
    {
        return stack1.getItem() == stack2.getItem() && (!stack1.getHasSubtypes() || stack1.getMetadata() == stack2.getMetadata()) && ItemStack.areItemStackTagsEqual(stack1, stack2);
    }

}
