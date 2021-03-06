package com.teamwizardry.vanillaonastick.item;

import com.teamwizardry.vanillaonastick.VanillaOnAStick;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemEnderChest extends Item
{
	public ItemEnderChest()
	{
		setRegistryName("enderchestonastick");
		setUnlocalizedName(VanillaOnAStick.MODID + ":enderchestonastick");
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
		if (!world.isRemote)
			player.displayGUIChest(player.getInventoryEnderChest());
		player.playSound(SoundEvents.ENTITY_ENDERMEN_TELEPORT, 1, 1);
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, player.getHeldItem(hand));
	}
	
	@SideOnly(Side.CLIENT)
	public void initModel()
	{
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
	}
}
