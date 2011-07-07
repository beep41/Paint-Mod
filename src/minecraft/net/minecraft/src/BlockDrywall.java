package net.minecraft.src;

public class BlockDrywall extends Block
{
    public BlockDrywall(int i)
    {
        super(i, 134, Material.cloth);
    }
    
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
    
}