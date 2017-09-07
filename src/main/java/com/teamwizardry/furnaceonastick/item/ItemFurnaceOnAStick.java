package com.teamwizardry.furnaceonastick.item;

import java.util.List;

import com.teamwizardry.furnaceonastick.FurnaceOnAStick;
import com.teamwizardry.furnaceonastick.gui.ContainerFurnace;
import com.teamwizardry.furnaceonastick.gui.GuiHandler;
import com.teamwizardry.furnaceonastick.gui.InventoryFurnace;
import com.teamwizardry.furnaceonastick.init.ModItems;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemFurnaceOnAStick extends Item
{
	public ItemFurnaceOnAStick()
	{
		setRegistryName("furnaceonastick");
		setUnlocalizedName(FurnaceOnAStick.MODID + ":furnaceonastick");
		setMaxStackSize(1);
		setCreativeTab(CreativeTabs.TOOLS);
	}
	
	@Override
	public boolean shouldCauseReequipAnimation(ItemStack oldItem, ItemStack newItem, boolean slotChanged)
	{
		if (ItemStack.areItemsEqual(oldItem, newItem))
			return false;
		return slotChanged;
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
	{
		NBTTagCompound compound = player.getHeldItem(hand).getTagCompound();
		if (compound == null)
		{
			compound = new NBTTagCompound();
			player.getHeldItem(hand).setTagCompound(compound);
		}
		if (!compound.hasKey("FurnaceData"))
			compound.setTag("FurnaceData", new InventoryFurnace().writeToNBT(new NBTTagCompound()));
		if (!world.isRemote)
			player.openGui(FurnaceOnAStick.instance, GuiHandler.GuiTypes.FURNACE.ordinal(), world, (int) player.posX, (int) player.posY, (int) player.posZ);
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, player.getHeldItem(hand));
	}
	
	@Override
	public void onUpdate(ItemStack stack, World world, Entity player, int slot, boolean isSelected)
	{
		if (!(player instanceof EntityPlayer))
			return;
		
		NBTTagCompound compound = stack.getTagCompound();
		if (compound == null)
		{
			compound = new NBTTagCompound();
			stack.setTagCompound(compound);
		}
		if (compound.getCompoundTag("FurnaceData") == null)
			compound.setTag("FurnaceData", new InventoryFurnace().writeToNBT(new NBTTagCompound()));
		
		if (isSelected && player instanceof EntityPlayerMP && ((EntityPlayerMP) player).openContainer instanceof ContainerFurnace)
				((ContainerFurnace) ((EntityPlayerMP) player).openContainer).tick();
		else
		{
			ContainerFurnace furnace = new ContainerFurnace((EntityPlayer) player, stack);
			furnace.tick();
			furnace.onContainerClosed((EntityPlayer) player);
		}
	}
	
	@SideOnly(Side.CLIENT)
	public void initModel()
	{
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
	}
	
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag)
	{
		if (stack.getItem() != ModItems.stickFurnace)
			return;
		if (!stack.hasTagCompound())
			return;
		if (!stack.getTagCompound().hasKey("FurnaceData"))
			return;
		NBTTagCompound compound = stack.getTagCompound().getCompoundTag("FurnaceData");
		NonNullList<ItemStack> inventory = NonNullList.<ItemStack>withSize(3, ItemStack.EMPTY);
		ItemStackHelper.loadAllItems(compound, inventory);
		if (!inventory.get(0).isEmpty())
			tooltip.add("Item Slot: " + inventory.get(0).getDisplayName() + " x" + inventory.get(0).getCount());
		if (!inventory.get(1).isEmpty())
			tooltip.add("Fuel Slot: " + inventory.get(1).getDisplayName() + " x" + inventory.get(1).getCount());
		if (!inventory.get(2).isEmpty())
			tooltip.add("Output Slot: " + inventory.get(2).getDisplayName() + " x" + inventory.get(2).getCount());
		tooltip.add("Burn Time: " + compound.getInteger("BurnTime"));
		tooltip.add("Cook Time: " + compound.getInteger("CookTime"));
	}
}
