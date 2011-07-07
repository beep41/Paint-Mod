// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package net.minecraft.src;

import java.io.IOException;
import java.util.*;

// Referenced classes of package net.minecraft.src:
//            IChunkProvider, EmptyChunk, ChunkCoordIntPair, Chunk, 
//            IChunkLoader, World, ModLoader, IProgressUpdate

public class ChunkProvider
    implements IChunkProvider
{

    public ChunkProvider(World world, IChunkLoader ichunkloader, IChunkProvider ichunkprovider)
    {
        field_28065_a = new HashSet();
        field_28068_e = new HashMap();
        field_28067_f = new ArrayList();
        field_28064_b = new EmptyChunk(world, new byte[32768], 0, 0);
        field_28066_g = world;
        field_28069_d = ichunkloader;
        field_28070_c = ichunkprovider;
    }

    public boolean chunkExists(int i, int j)
    {
        return field_28068_e.containsKey(Integer.valueOf(ChunkCoordIntPair.chunkXZ2Int(i, j)));
    }

    public Chunk prepareChunk(int i, int j)
    {
        int k = ChunkCoordIntPair.chunkXZ2Int(i, j);
        field_28065_a.remove(Integer.valueOf(k));
        Chunk chunk = (Chunk)field_28068_e.get(Integer.valueOf(k));
        if(chunk == null)
        {
            chunk = func_28061_d(i, j);
            if(chunk == null)
            {
                if(field_28070_c == null)
                {
                    chunk = field_28064_b;
                } else
                {
                    chunk = field_28070_c.provideChunk(i, j);
                }
            }
            field_28068_e.put(Integer.valueOf(k), chunk);
            field_28067_f.add(chunk);
            if(chunk != null)
            {
                chunk.func_4143_d();
                chunk.onChunkLoad();
            }
            if(!chunk.isTerrainPopulated && chunkExists(i + 1, j + 1) && chunkExists(i, j + 1) && chunkExists(i + 1, j))
            {
                populate(this, i, j);
            }
            if(chunkExists(i - 1, j) && !provideChunk(i - 1, j).isTerrainPopulated && chunkExists(i - 1, j + 1) && chunkExists(i, j + 1) && chunkExists(i - 1, j))
            {
                populate(this, i - 1, j);
            }
            if(chunkExists(i, j - 1) && !provideChunk(i, j - 1).isTerrainPopulated && chunkExists(i + 1, j - 1) && chunkExists(i, j - 1) && chunkExists(i + 1, j))
            {
                populate(this, i, j - 1);
            }
            if(chunkExists(i - 1, j - 1) && !provideChunk(i - 1, j - 1).isTerrainPopulated && chunkExists(i - 1, j - 1) && chunkExists(i, j - 1) && chunkExists(i - 1, j))
            {
                populate(this, i - 1, j - 1);
            }
        }
        return chunk;
    }

    public Chunk provideChunk(int i, int j)
    {
        Chunk chunk = (Chunk)field_28068_e.get(Integer.valueOf(ChunkCoordIntPair.chunkXZ2Int(i, j)));
        if(chunk == null)
        {
            return prepareChunk(i, j);
        } else
        {
            return chunk;
        }
    }

    private Chunk func_28061_d(int i, int j)
    {
        if(field_28069_d == null)
        {
            return null;
        }
        try
        {
            Chunk chunk = field_28069_d.loadChunk(field_28066_g, i, j);
            if(chunk != null)
            {
                chunk.lastSaveTime = field_28066_g.getWorldTime();
            }
            return chunk;
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
        }
        return null;
    }

    private void func_28063_a(Chunk chunk)
    {
        if(field_28069_d == null)
        {
            return;
        }
        try
        {
            field_28069_d.saveExtraChunkData(field_28066_g, chunk);
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
        }
    }

    private void func_28062_b(Chunk chunk)
    {
        if(field_28069_d == null)
        {
            return;
        }
        try
        {
            chunk.lastSaveTime = field_28066_g.getWorldTime();
            field_28069_d.saveChunk(field_28066_g, chunk);
        }
        catch(IOException ioexception)
        {
            ioexception.printStackTrace();
        }
    }

    public void populate(IChunkProvider ichunkprovider, int i, int j)
    {
        Chunk chunk = provideChunk(i, j);
        if(!chunk.isTerrainPopulated)
        {
            chunk.isTerrainPopulated = true;
            if(field_28070_c != null)
            {
                field_28070_c.populate(ichunkprovider, i, j);
                ModLoader.PopulateChunk(field_28070_c, i << 4, j << 4, field_28066_g);
                chunk.setChunkModified();
            }
        }
    }

    public boolean saveChunks(boolean flag, IProgressUpdate iprogressupdate)
    {
        int i = 0;
        for(int j = 0; j < field_28067_f.size(); j++)
        {
            Chunk chunk = (Chunk)field_28067_f.get(j);
            if(flag && !chunk.neverSave)
            {
                func_28063_a(chunk);
            }
            if(chunk.needsSaving(flag))
            {
                func_28062_b(chunk);
                chunk.isModified = false;
                if(++i == 24 && !flag)
                {
                    return false;
                }
            }
        }

        if(flag)
        {
            if(field_28069_d == null)
            {
                return true;
            }
            field_28069_d.saveExtraData();
        }
        return true;
    }

    public boolean func_532_a()
    {
        for(int i = 0; i < 100; i++)
        {
            if(!field_28065_a.isEmpty())
            {
                Integer integer = (Integer)field_28065_a.iterator().next();
                Chunk chunk = (Chunk)field_28068_e.get(integer);
                chunk.onChunkUnload();
                func_28062_b(chunk);
                func_28063_a(chunk);
                field_28065_a.remove(integer);
                field_28068_e.remove(integer);
                field_28067_f.remove(chunk);
            }
        }

        if(field_28069_d != null)
        {
            field_28069_d.func_814_a();
        }
        return field_28070_c.func_532_a();
    }

    public boolean func_536_b()
    {
        return true;
    }

    public String makeString()
    {
        return (new StringBuilder("ServerChunkCache: ")).append(field_28068_e.size()).append(" Drop: ").append(field_28065_a.size()).toString();
    }

    private Set field_28065_a;
    private Chunk field_28064_b;
    private IChunkProvider field_28070_c;
    private IChunkLoader field_28069_d;
    private Map field_28068_e;
    private List field_28067_f;
    private World field_28066_g;
}
