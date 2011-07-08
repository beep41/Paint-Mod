// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package net.minecraft.src;

import java.util.Map;
import java.util.Random;
import net.minecraft.client.Minecraft;

// Referenced classes of package net.minecraft.src:
//            World, ItemStack, KeyBinding, GuiScreen, 
//            RenderBlocks, Block, IBlockAccess, EntityPlayer

public abstract class BaseMod
{

    public BaseMod()
    {
    }

    public int AddFuel(int i)
    {
        return 0;
    }

    public void AddRenderer(Map map)
    {
    }

    public boolean DispenseEntity(World world, double d, double d1, double d2, 
            int i, int j, ItemStack itemstack)
    {
        return false;
    }

    public void GenerateNether(World world, Random random, int i, int j)
    {
    }

    public void GenerateSurface(World world, Random random, int i, int j)
    {
    }

    public void KeyboardEvent(KeyBinding keybinding)
    {
    }

    public void ModsLoaded()
    {
    }

    public boolean OnTickInGame(Minecraft minecraft)
    {
        return false;
    }

    public boolean OnTickInGUI(Minecraft minecraft, GuiScreen guiscreen)
    {
        return false;
    }

    public void RegisterAnimation(Minecraft minecraft)
    {
    }

    public void RenderInvBlock(RenderBlocks renderblocks, Block block, int i, int j)
    {
    }

    public boolean RenderWorldBlock(RenderBlocks renderblocks, IBlockAccess iblockaccess, int i, int j, int k, Block block, int l)
    {
        return false;
    }

    public void TakenFromCrafting(EntityPlayer entityplayer, ItemStack itemstack)
    {
    }

    public void TakenFromFurnace(EntityPlayer entityplayer, ItemStack itemstack)
    {
    }

    public void OnItemPickup(EntityPlayer entityplayer, ItemStack itemstack)
    {
    }

    public String toString()
    {
        return (new StringBuilder(String.valueOf(getClass().getName()))).append(" ").append(Version()).toString();
    }

    public abstract String Version();
}
