// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package net.minecraft.src;


public class MapColor
{

    private MapColor(int i, int j)
    {
        colorIndex = i;
        colorValue = j;
        mapColorArray[i] = this;
    }

    public static final MapColor mapColorArray[] = new MapColor[16];
    public static final MapColor Air_color = new MapColor(0, 0);
    public static final MapColor Grass_color = new MapColor(1, 0x7fb238);
    public static final MapColor Sand_color = new MapColor(2, 0xf7e9a3);
    public static final MapColor Burning_Cloth_color = new MapColor(3, 0xa7a7a7);
    public static final MapColor Tnt_color = new MapColor(4, 0xff0000);
    public static final MapColor Ice_color = new MapColor(5, 0xa0a0ff);
    public static final MapColor field_28206_h = new MapColor(6, 0xa7a7a7);
    public static final MapColor field_28205_i = new MapColor(7, 31744);
    public static final MapColor Background_color = new MapColor(8, 0xffffff);
    public static final MapColor field_28203_k = new MapColor(9, 0xa4a8b8);
    public static final MapColor field_28202_l = new MapColor(10, 0xb76a2f);
    public static final MapColor field_28201_m = new MapColor(11, 0x707070);
    public static final MapColor field_28200_n = new MapColor(12, 0x4040ff);
    public static final MapColor field_28199_o = new MapColor(13, 0x685332);
    public final int colorValue;
    public final int colorIndex;

}
