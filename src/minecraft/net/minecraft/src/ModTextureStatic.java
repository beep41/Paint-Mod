// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package net.minecraft.src;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

// Referenced classes of package net.minecraft.src:
//            TextureFX

public class ModTextureStatic extends TextureFX
{

    public ModTextureStatic(int i, int j, BufferedImage bufferedimage)
    {
        this(i, 1, j, bufferedimage);
    }

    public ModTextureStatic(int i, int j, int k, BufferedImage bufferedimage)
    {
        super(i);
        pixels = null;
        tileSize = j;
        tileImage = k;
        int l = bufferedimage.getWidth();
        int i1 = bufferedimage.getHeight();
        int j1 = (int)Math.sqrt(imageData.length / 4);
        pixels = new int[j1 * j1];
        if(l != i1 || l != j1)
        {
            BufferedImage bufferedimage1 = new BufferedImage(j1, j1, 6);
            Graphics2D graphics2d = bufferedimage1.createGraphics();
            graphics2d.drawImage(bufferedimage, 0, 0, j1, j1, 0, 0, l, i1, null);
            bufferedimage1.getRGB(0, 0, j1, j1, pixels, 0, j1);
            graphics2d.dispose();
        } else
        {
            bufferedimage.getRGB(0, 0, l, i1, pixels, 0, l);
        }
        update();
    }

    public void update()
    {
        for(int i = 0; i < pixels.length; i++)
        {
            int j = pixels[i] >> 24 & 0xff;
            int k = pixels[i] >> 16 & 0xff;
            int l = pixels[i] >> 8 & 0xff;
            int i1 = pixels[i] >> 0 & 0xff;
            if(anaglyphEnabled)
            {
                int j1 = (k + l + i1) / 3;
                k = l = i1 = j1;
            }
            imageData[i * 4 + 0] = (byte)k;
            imageData[i * 4 + 1] = (byte)l;
            imageData[i * 4 + 2] = (byte)i1;
            imageData[i * 4 + 3] = (byte)j;
        }

        oldanaglyph = anaglyphEnabled;
    }

    public void onTick()
    {
        if(oldanaglyph != anaglyphEnabled)
        {
            update();
        }
    }

    public static BufferedImage scale2x(BufferedImage bufferedimage)
    {
        int j2 = bufferedimage.getWidth();
        int k2 = bufferedimage.getHeight();
        BufferedImage bufferedimage1 = new BufferedImage(j2 * 2, k2 * 2, 2);
        for(int l2 = 0; l2 < k2; l2++)
        {
            for(int i3 = 0; i3 < j2; i3++)
            {
                int i = bufferedimage.getRGB(i3, l2);
                int j1;
                if(l2 == 0)
                {
                    j1 = i;
                } else
                {
                    j1 = bufferedimage.getRGB(i3, l2 - 1);
                }
                int k1;
                if(i3 == 0)
                {
                    k1 = i;
                } else
                {
                    k1 = bufferedimage.getRGB(i3 - 1, l2);
                }
                int l1;
                if(i3 >= j2 - 1)
                {
                    l1 = i;
                } else
                {
                    l1 = bufferedimage.getRGB(i3 + 1, l2);
                }
                int i2;
                if(l2 >= k2 - 1)
                {
                    i2 = i;
                } else
                {
                    i2 = bufferedimage.getRGB(i3, l2 + 1);
                }
                int j;
                int k;
                int l;
                int i1;
                if(j1 != i2 && k1 != l1)
                {
                    j = k1 != j1 ? i : k1;
                    k = j1 != l1 ? i : l1;
                    l = k1 != i2 ? i : k1;
                    i1 = i2 != l1 ? i : l1;
                } else
                {
                    j = i;
                    k = i;
                    l = i;
                    i1 = i;
                }
                bufferedimage1.setRGB(i3 * 2, l2 * 2, j);
                bufferedimage1.setRGB(i3 * 2 + 1, l2 * 2, k);
                bufferedimage1.setRGB(i3 * 2, l2 * 2 + 1, l);
                bufferedimage1.setRGB(i3 * 2 + 1, l2 * 2 + 1, i1);
            }

        }

        return bufferedimage1;
    }

    private boolean oldanaglyph;
    private int pixels[];
}
