// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package net.minecraft.src;


// Referenced classes of package net.minecraft.src:
//            Item, EntityDrywall, World, ItemStack, 
//            EntityPlayer

public class ItemDrywall extends Item
{

    public ItemDrywall(int i)
    {
        super(i);
    }

    public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l)
    {
    	/*
        public void onBlockAdded(World world, int i, int j, int k)
        {
            if(world.func_28100_h(i - 1, j, k))
            {
                world.setBlockMetadataWithNotify(i, j, k, 1);
            } else
            if(world.func_28100_h(i + 1, j, k))
            {
                world.setBlockMetadataWithNotify(i, j, k, 2);
            } else
            if(world.func_28100_h(i, j, k - 1))
            {
                world.setBlockMetadataWithNotify(i, j, k, 3);
            } else
            if(world.func_28100_h(i, j, k + 1))
            {
                world.setBlockMetadataWithNotify(i, j, k, 4);
            } else
            if(world.func_28100_h(i, j - 1, k))
            {
                world.setBlockMetadataWithNotify(i, j, k, 5);
            }
            if(world.func_28100_h(i, j + 1, k))
            {
                world.setBlockMetadataWithNotify(i, j, k, 6);
            }
        }
        */
    	
    	
        if(l == 0)
        {
            return false;
        }
        if(l == 1)
        {
            return false;
        }
        byte byte0 = 0;
        if(l == 4)
        {
            byte0 = 1;
        }
        if(l == 3)
        {
            byte0 = 2;
        }
        if(l == 5)
        {
            byte0 = 3;
        }
        EntityDrywall entityDrywall = new EntityDrywall(world, i, j, k, byte0);
        if(entityDrywall.func_410_i())
        {
            if(!world.multiplayerWorld)
            {
                world.entityJoinedWorld(entityDrywall);
            }
            itemstack.stackSize--;
        }
        return true;
    }
}
