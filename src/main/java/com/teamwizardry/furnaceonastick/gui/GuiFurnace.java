package com.teamwizardry.furnaceonastick.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiFurnace extends GuiContainer
{
	private static final ResourceLocation TEX = new ResourceLocation("textures/gui/container/furnace.png");
	private final InventoryPlayer playerInv;
	private final InventoryFurnace furnaceInv;
	private final ContainerFurnace furnace;

	public GuiFurnace(EntityPlayer player, ContainerFurnace furnace)
	{
		super(furnace);
		this.playerInv = player.inventory;
		this.furnaceInv = (InventoryFurnace) furnace.furnace;
		this.furnace = furnace;
		this.xSize = 176;
		this.ySize = 166;
	}
	
	@Override
	public void updateScreen()
	{
		super.updateScreen();
		furnace.tick();
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		this.drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		this.renderHoveredToolTip(mouseX, mouseY);
	}

	@Override
	public void drawGuiContainerForegroundLayer(int x, int y)
	{
		String s = furnaceInv.getDisplayName().getUnformattedText();
		fontRenderer.drawString(s, xSize / 2 - fontRenderer.getStringWidth(s) / 2, 6, 4210752);
		fontRenderer.drawString(playerInv.getDisplayName().getUnformattedText(), 8, ySize - 96 + 2, 4210752);
	}

	@Override
	public void drawGuiContainerBackgroundLayer(float f, int x, int y)
	{
		GlStateManager.color(1, 1, 1, 1);
		mc.getTextureManager().bindTexture(TEX);
		int i = (width - xSize) / 2;
		int j = (height - ySize) / 2;
		drawTexturedModalRect(i, j, 0, 0, xSize, ySize);

		if (TileEntityFurnace.isBurning(furnaceInv))
		{
			int k = getBurnLeftScaled(13);
			drawTexturedModalRect(i + 56, j + 36 + 12 - k, 176, 12 - k, 14, k + 1);
		}

		int l = getCookProgressScaled(24);
		drawTexturedModalRect(i + 79, j + 34, 176, 14, l + 1, 16);
	}

	private int getCookProgressScaled(int pixels)
	{
		int i = furnaceInv.getField(2);
		int j = furnaceInv.getField(3);
		return j != 0 && i != 0 ? i * pixels / j : 0;
	}

	private int getBurnLeftScaled(int pixels)
	{
		int i = furnaceInv.getField(1);
		if (i == 0) i = 200;
		return furnaceInv.getField(0) * pixels / i;
	}
}
