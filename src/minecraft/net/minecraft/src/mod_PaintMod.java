package net.minecraft.src;

import java.util.Map;

public class mod_PaintMod extends BaseMod
{
	public String Version()
	{
		return "PaintMod 0.1";
	}
	
	public mod_PaintMod()
	{
		ModLoader.AddName(drywall, "Drywall");
		
        ModLoader.AddRecipe(new ItemStack(mod_PaintMod.drywall, 1), new Object[] {
            "##", "##", Character.valueOf('#'), Block.dirt
        });

	}
	
	public static Item drywall;
	
	static {
		drywall = (new ItemDrywall(65)).setIconCoord(10, 1).setItemName("Drywall");
	}
	
    public void AddRenderer(Map map)
    {
    	map.put(net.minecraft.src.EntityDrywall.class, new RenderDrywall());
    }
	
}