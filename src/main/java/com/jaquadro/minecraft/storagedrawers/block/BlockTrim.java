package com.jaquadro.minecraft.storagedrawers.block;

import com.jaquadro.minecraft.storagedrawers.StorageDrawers;
import com.jaquadro.minecraft.storagedrawers.api.storage.INetworked;
import com.jaquadro.minecraft.storagedrawers.core.ModCreativeTabs;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockWood;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class BlockTrim extends Block implements INetworked
{
    @SideOnly(Side.CLIENT)
    protected IIcon[] iconTrim;

    public BlockTrim (String name) {
        super(Material.wood);

        setBlockName(name);
        setCreativeTab(ModCreativeTabs.tabStorageDrawers);
        setHardness(5f);
        setStepSound(Block.soundTypeWood);
    }

    @Override
    public int damageDropped (int meta) {
        return meta;
    }

    @Override
    public boolean removedByPlayer (World world, EntityPlayer player, int x, int y, int z, boolean willHarvest) {
        if (willHarvest)
            return true;

        return super.removedByPlayer(world, player, x, y, z, willHarvest);
    }

    @Override
    public void harvestBlock (World world, EntityPlayer player, int x, int y, int z, int meta) {
        super.harvestBlock(world, player, x, y, z, meta);
        world.setBlockToAir(x, y, z);
    }

    protected ItemStack getMainDrop (World world, int x, int y, int z, int metadata) {
        return new ItemStack(Item.getItemFromBlock(this), 1, metadata);
    }

    @Override
    public ArrayList<ItemStack> getDrops (World world, int x, int y, int z, int metadata, int fortune) {
        ItemStack dropStack = getMainDrop(world, x, y, z, metadata);

        ArrayList<ItemStack> drops = new ArrayList<ItemStack>();
        drops.add(dropStack);

        return drops;
    }

    @Override
    public void getSubBlocks (Item item, CreativeTabs creativeTabs, List list) {
        list.add(new ItemStack(item, 1, 0));

        if (StorageDrawers.config.cache.creativeTabVanillaWoods) {
            for (int i = 1; i < BlockWood.field_150096_a.length; i++)
                list.add(new ItemStack(item, 1, i));
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon (int side, int meta) {
        meta = meta % iconTrim.length;
        return iconTrim[meta];
    }

    @Override
    public void registerBlockIcons (IIconRegister register) {
        String[] subtex = BlockWood.field_150096_a;

        iconTrim = new IIcon[subtex.length];
        for (int i = 0; i < subtex.length; i++)
            iconTrim[i] = register.registerIcon(StorageDrawers.MOD_ID + ":drawers_" + subtex[i] + "_side");
    }
}
