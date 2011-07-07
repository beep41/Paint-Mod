// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package net.minecraft.src;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

// Referenced classes of package net.minecraft.src:
//            TextureFX

public class ModTextureAnimation extends TextureFX
{

    public ModTextureAnimation(int i, int j, BufferedImage bufferedimage, int k)
    {
        this(i, 1, j, bufferedimage, k);
    }

    public ModTextureAnimation(int i, int j, int k, BufferedImage bufferedimage, int l)
    {
        super(i);
        index = 0;
        ticks = 0;
        tileSize = j;
        tileImage = k;
        tickRate = l;
        ticks = l;
        int i1 = bufferedimage.getWidth();
        int j1 = bufferedimage.getHeight();
        int k1 = (int)Math.floor(j1 / i1);
        if(k1 <= 0)
        {
            throw new IllegalArgumentException("source has no complete images");
        }
        int l1 = (int)Math.sqrt(imageData.length / 4);
        images = new byte[k1][];
        if(i1 != l1)
        {
            BufferedImage bufferedimage1 = new BufferedImage(l1, l1 * k1, 6);
            Graphics2D graphics2d = bufferedimage1.createGraphics();
            graphics2d.drawImage(bufferedimage, 0, 0, l1, l1 * k1, 0, 0, i1, j1, null);
            graphics2d.dispose();
            bufferedimage = bufferedimage1;
        }
        for(int i2 = 0; i2 < k1; i2++)
        {
            int ai[] = new int[l1 * l1];
            bufferedimage.getRGB(0, l1 * i2, l1, l1, ai, 0, l1);
            images[i2] = new byte[l1 * l1 * 4];
            for(int j2 = 0; j2 < ai.length; j2++)
            {
                int k2 = ai[j2] >> 24 & 0xff;
                int l2 = ai[j2] >> 16 & 0xff;
                int i3 = ai[j2] >> 8 & 0xff;
                int j3 = ai[j2] >> 0 & 0xff;
                images[i2][j2 * 4 + 0] = (byte)l2;
                images[i2][j2 * 4 + 1] = (byte)i3;
                images[i2][j2 * 4 + 2] = (byte)j3;
                images[i2][j2 * 4 + 3] = (byte)k2;
            }

        }

    }

    public void onTick()
    {
        if(ticks >= tickRate)
        {
            index++;
            if(index >= images.length)
            {
                index = 0;
            }
            imageData = images[index];
            ticks = 0;
        }
        ticks++;
    }

    private final int tickRate;
    private final byte images[][];
    private int index;
    private int ticks;
}
