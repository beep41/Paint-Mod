// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package net.minecraft.src;


// Referenced classes of package net.minecraft.src:
//            MaterialTransparent, MapColor, MaterialLiquid, MaterialLogic, 
//            MaterialPortal

public class Material
{

    public Material(MapColor mapcolor)
    {
        field_28131_A = mapcolor;
    }

    public boolean getIsLiquid()
    {
        return false;
    }

    public boolean isSolid()
    {
        return true;
    }

    public boolean getCanBlockGrass()
    {
        return true;
    }

    public boolean getIsSolid()
    {
        return true;
    }

    private Material func_28129_i()
    {
        field_28130_D = true;
        return this;
    }

    private Material setBurning()
    {
        canBurn = true;
        return this;
    }

    public boolean getBurning()
    {
        return canBurn;
    }

    public Material func_27089_f()
    {
        field_27091_A = true;
        return this;
    }

    public boolean func_27090_g()
    {
        return field_27091_A;
    }

    public boolean func_28128_h()
    {
        if(field_28130_D)
        {
            return false;
        } else
        {
            return getIsSolid();
        }
    }

    public static final Material air;
    public static final Material field_28132_b;
    public static final Material ground;
    public static final Material wood;
    public static final Material rock;
    public static final Material iron;
    public static final Material water;
    public static final Material lava;
    public static final Material leaves;
    public static final Material plants;
    public static final Material sponge;
    public static final Material cloth;
    public static final Material fire;
    public static final Material sand;
    public static final Material circuits;
    public static final Material glass;
    public static final Material tnt;
    public static final Material field_4215_q;
    public static final Material ice;
    public static final Material snow;
    public static final Material builtSnow;
    public static final Material cactus;
    public static final Material clay;
    public static final Material pumpkin;
    public static final Material portal;
    public static final Material cakeMaterial;
    private boolean canBurn;
    private boolean field_27091_A;
    private boolean field_28130_D;
    public final MapColor field_28131_A;

    static 
    {
        air = new MaterialTransparent(MapColor.field_28199_b);
        field_28132_b = new Material(MapColor.field_28198_c);
        ground = new Material(MapColor.field_28189_l);
        wood = (new Material(MapColor.field_28186_o)).setBurning();
        rock = new Material(MapColor.field_28188_m);
        iron = new Material(MapColor.field_28193_h);
        water = new MaterialLiquid(MapColor.field_28187_n);
        lava = new MaterialLiquid(MapColor.field_28195_f);
        leaves = (new Material(MapColor.field_28192_i)).setBurning().func_28129_i();
        plants = new MaterialLogic(MapColor.field_28192_i);
        sponge = new Material(MapColor.field_28196_e);
        cloth = (new Material(MapColor.field_28196_e)).setBurning();
        fire = new MaterialTransparent(MapColor.field_28199_b);
        sand = new Material(MapColor.field_28197_d);
        circuits = new MaterialLogic(MapColor.field_28199_b);
        glass = (new Material(MapColor.field_28199_b)).func_28129_i();
        tnt = (new Material(MapColor.field_28195_f)).setBurning().func_28129_i();
        field_4215_q = new Material(MapColor.field_28192_i);
        ice = (new Material(MapColor.field_28194_g)).func_28129_i();
        snow = (new MaterialLogic(MapColor.field_28191_j)).func_27089_f().func_28129_i();
        builtSnow = new Material(MapColor.field_28191_j);
        cactus = (new Material(MapColor.field_28192_i)).func_28129_i();
        clay = new Material(MapColor.field_28190_k);
        pumpkin = new Material(MapColor.field_28192_i);
        portal = new MaterialPortal(MapColor.field_28199_b);
        cakeMaterial = new Material(MapColor.field_28199_b);
    }
}
