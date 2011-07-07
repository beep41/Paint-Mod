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
        materialMapColor = mapcolor;
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

    private Material func_28127_i()
    {
        field_28128_D = true;
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

    public Material func_27284_f()
    {
        field_27285_A = true;
        return this;
    }

    public boolean func_27283_g()
    {
        return field_27285_A;
    }

    public boolean func_28126_h()
    {
        if(field_28128_D)
        {
            return false;
        } else
        {
            return getIsSolid();
        }
    }

    public static final Material air;
    public static final Material grassMaterial;
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
    public static final Material field_4262_q;
    public static final Material ice;
    public static final Material snow;
    public static final Material builtSnow;
    public static final Material cactus;
    public static final Material clay;
    public static final Material pumpkin;
    public static final Material portal;
    public static final Material cakeMaterial;
    private boolean canBurn;
    private boolean field_27285_A;
    private boolean field_28128_D;
    public final MapColor materialMapColor;

    static 
    {
        air = new MaterialTransparent(MapColor.Air_color);
        grassMaterial = new Material(MapColor.Grass_color);
        ground = new Material(MapColor.field_28202_l);
        wood = (new Material(MapColor.field_28199_o)).setBurning();
        rock = new Material(MapColor.field_28201_m);
        iron = new Material(MapColor.field_28206_h);
        water = new MaterialLiquid(MapColor.field_28200_n);
        lava = new MaterialLiquid(MapColor.Tnt_color);
        leaves = (new Material(MapColor.field_28205_i)).setBurning().func_28127_i();
        plants = new MaterialLogic(MapColor.field_28205_i);
        sponge = new Material(MapColor.Burning_Cloth_color);
        cloth = (new Material(MapColor.Burning_Cloth_color)).setBurning();
        fire = new MaterialTransparent(MapColor.Air_color);
        sand = new Material(MapColor.Sand_color);
        circuits = new MaterialLogic(MapColor.Air_color);
        glass = (new Material(MapColor.Air_color)).func_28127_i();
        tnt = (new Material(MapColor.Tnt_color)).setBurning().func_28127_i();
        field_4262_q = new Material(MapColor.field_28205_i);
        ice = (new Material(MapColor.Ice_color)).func_28127_i();
        snow = (new MaterialLogic(MapColor.Background_color)).func_27284_f().func_28127_i();
        builtSnow = new Material(MapColor.Background_color);
        cactus = (new Material(MapColor.field_28205_i)).func_28127_i();
        clay = new Material(MapColor.field_28203_k);
        pumpkin = new Material(MapColor.field_28205_i);
        portal = new MaterialPortal(MapColor.Air_color);
        cakeMaterial = new Material(MapColor.Air_color);
    }
}
