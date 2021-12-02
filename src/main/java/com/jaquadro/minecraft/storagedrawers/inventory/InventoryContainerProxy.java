package com.jaquadro.minecraft.storagedrawers.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class InventoryContainerProxy implements IInventory
{
    private IInventory parent;
    private Container container;

    public InventoryContainerProxy (IInventory parentInventory, Container container) {
        this.parent = parentInventory;
        this.container = container;
    }

    @Override
    public int getSizeInventory () {
        return parent.getSizeInventory();
    }

    @Override
    public ItemStack getStackInSlot (int slot) {
        return parent.getStackInSlot(slot);
    }

    @Override
    public ItemStack decrStackSize (int slot, int count) {
        ItemStack stack = parent.getStackInSlot(slot);
        if (stack == null)
            return null;

        int stackCount = stack.stackSize;
        ItemStack result = parent.decrStackSize(slot, count);

        ItemStack stackAfter = parent.getStackInSlot(slot);
        if (stack != stackAfter || stackCount != stackAfter.stackSize)
            container.onCraftMatrixChanged(this);

        return result;
    }

    @Override
    public ItemStack getStackInSlotOnClosing (int slot) {
        return parent.getStackInSlotOnClosing(slot);
    }

    @Override
    public void setInventorySlotContents (int slot, ItemStack stack) {
        parent.setInventorySlotContents(slot, stack);
        container.onCraftMatrixChanged(this);
    }

    @Override
    public String getInventoryName () {
        return parent.getInventoryName();
    }

    @Override
    public boolean hasCustomInventoryName () {
        return parent.hasCustomInventoryName();
    }

    @Override
    public int getInventoryStackLimit () {
        return parent.getInventoryStackLimit();
    }

    @Override
    public void markDirty () {
        parent.markDirty();
    }

    @Override
    public boolean isUseableByPlayer (EntityPlayer player) {
        return parent.isUseableByPlayer(player);
    }

    @Override
    public void openInventory () {
        parent.openInventory();
    }

    @Override
    public void closeInventory () {
        parent.closeInventory();
    }

    @Override
    public boolean isItemValidForSlot (int slot, ItemStack stack) {
        return parent.isItemValidForSlot(slot, stack);
    }
}
