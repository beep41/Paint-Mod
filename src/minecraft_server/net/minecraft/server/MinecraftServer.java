// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package net.minecraft.server;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.ChunkCoordinates;
import net.minecraft.src.ChunkProviderServer;
import net.minecraft.src.ConsoleCommandHandler;
import net.minecraft.src.ConsoleLogManager;
import net.minecraft.src.ConvertProgressUpdater;
import net.minecraft.src.EntityTracker;
import net.minecraft.src.ICommandListener;
import net.minecraft.src.ISaveFormat;
import net.minecraft.src.IUpdatePlayerListBox;
import net.minecraft.src.NetworkListenThread;
import net.minecraft.src.Packet4UpdateTime;
import net.minecraft.src.PropertyManager;
import net.minecraft.src.SaveConverterMcRegion;
import net.minecraft.src.SaveOldDir;
import net.minecraft.src.ServerCommand;
import net.minecraft.src.ServerConfigurationManager;
import net.minecraft.src.ServerGUI;
import net.minecraft.src.StatList;
import net.minecraft.src.ThreadCommandReader;
import net.minecraft.src.ThreadServerApplication;
import net.minecraft.src.ThreadSleepForever;
import net.minecraft.src.Vec3D;
import net.minecraft.src.WorldManager;
import net.minecraft.src.WorldProvider;
import net.minecraft.src.WorldServer;
import net.minecraft.src.WorldServerMulti;

public class MinecraftServer
    implements Runnable, ICommandListener
{

    public MinecraftServer()
    {
        serverRunning = true;
        serverStopped = false;
        deathTime = 0;
        field_9010_p = new ArrayList();
        commands = Collections.synchronizedList(new ArrayList());
        entityTracker = new EntityTracker[2];
        new ThreadSleepForever(this);
    }

    private boolean startServer()
        throws UnknownHostException
    {
        commandHandler = new ConsoleCommandHandler(this);
        ThreadCommandReader threadcommandreader = new ThreadCommandReader(this);
        threadcommandreader.setDaemon(true);
        threadcommandreader.start();
        ConsoleLogManager.init();
        logger.info("Starting minecraft server version Beta 1.7.3");
        if(Runtime.getRuntime().maxMemory() / 1024L / 1024L < 512L)
        {
            logger.warning("**** NOT ENOUGH RAM!");
            logger.warning("To start the server with more ram, launch it as \"java -Xmx1024M -Xms1024M -jar minecraft_server.jar\"");
        }
        logger.info("Loading properties");
        propertyManagerObj = new PropertyManager(new File("server.properties"));
        String s = propertyManagerObj.getStringProperty("server-ip", "");
        onlineMode = propertyManagerObj.getBooleanProperty("online-mode", true);
        spawnPeacefulMobs = propertyManagerObj.getBooleanProperty("spawn-animals", true);
        pvpOn = propertyManagerObj.getBooleanProperty("pvp", true);
        allowFlight = propertyManagerObj.getBooleanProperty("allow-flight", false);
        InetAddress inetaddress = null;
        if(s.length() > 0)
        {
            inetaddress = InetAddress.getByName(s);
        }
        int i = propertyManagerObj.getIntProperty("server-port", 25565);
        logger.info((new StringBuilder()).append("Starting Minecraft server on ").append(s.length() != 0 ? s : "*").append(":").append(i).toString());
        try
        {
            networkServer = new NetworkListenThread(this, inetaddress, i);
        }
        catch(IOException ioexception)
        {
            logger.warning("**** FAILED TO BIND TO PORT!");
            logger.log(Level.WARNING, (new StringBuilder()).append("The exception was: ").append(ioexception.toString()).toString());
            logger.warning("Perhaps a server is already running on that port?");
            return false;
        }
        if(!onlineMode)
        {
            logger.warning("**** SERVER IS RUNNING IN OFFLINE/INSECURE MODE!");
            logger.warning("The server will make no attempt to authenticate usernames. Beware.");
            logger.warning("While this makes the game possible to play without internet access, it also opens up the ability for hackers to connect with any username they choose.");
            logger.warning("To change this, set \"online-mode\" to \"true\" in the server.settings file.");
        }
        configManager = new ServerConfigurationManager(this);
        entityTracker[0] = new EntityTracker(this, 0);
        entityTracker[1] = new EntityTracker(this, -1);
        long l = System.nanoTime();
        String s1 = propertyManagerObj.getStringProperty("level-name", "world");
        String s2 = propertyManagerObj.getStringProperty("level-seed", "");
        long l1 = (new Random()).nextLong();
        if(s2.length() > 0)
        {
            try
            {
                l1 = Long.parseLong(s2);
            }
            catch(NumberFormatException numberformatexception)
            {
                l1 = s2.hashCode();
            }
        }
        logger.info((new StringBuilder()).append("Preparing level \"").append(s1).append("\"").toString());
        initWorld(new SaveConverterMcRegion(new File(".")), s1, l1);
        logger.info((new StringBuilder()).append("Done (").append(System.nanoTime() - l).append("ns)! For help, type \"help\" or \"?\"").toString());
        return true;
    }

    private void initWorld(ISaveFormat isaveformat, String s, long l)
    {
        if(isaveformat.isOldSaveType(s))
        {
            logger.info("Converting map!");
            isaveformat.converMapToMCRegion(s, new ConvertProgressUpdater(this));
        }
        worldMngr = new WorldServer[2];
        SaveOldDir saveolddir = new SaveOldDir(new File("."), s, true);
        for(int i = 0; i < worldMngr.length; i++)
        {
            if(i == 0)
            {
                worldMngr[i] = new WorldServer(this, saveolddir, s, i != 0 ? -1 : 0, l);
            } else
            {
                worldMngr[i] = new WorldServerMulti(this, saveolddir, s, i != 0 ? -1 : 0, l, worldMngr[0]);
            }
            worldMngr[i].addWorldAccess(new WorldManager(this, worldMngr[i]));
            worldMngr[i].difficultySetting = propertyManagerObj.getBooleanProperty("spawn-monsters", true) ? 1 : 0;
            worldMngr[i].setAllowedSpawnTypes(propertyManagerObj.getBooleanProperty("spawn-monsters", true), spawnPeacefulMobs);
            configManager.setPlayerManager(worldMngr);
        }

        char c = '\304';
        long l1 = System.currentTimeMillis();
label0:
        for(int j = 0; j < worldMngr.length; j++)
        {
            logger.info((new StringBuilder()).append("Preparing start region for level ").append(j).toString());
            if(j != 0 && !propertyManagerObj.getBooleanProperty("allow-nether", true))
            {
                continue;
            }
            WorldServer worldserver = worldMngr[j];
            ChunkCoordinates chunkcoordinates = worldserver.getSpawnPoint();
            int k = -c;
            do
            {
                if(k > c || !serverRunning)
                {
                    continue label0;
                }
                for(int i1 = -c; i1 <= c && serverRunning; i1 += 16)
                {
                    long l2 = System.currentTimeMillis();
                    if(l2 < l1)
                    {
                        l1 = l2;
                    }
                    if(l2 > l1 + 1000L)
                    {
                        int j1 = (c * 2 + 1) * (c * 2 + 1);
                        int k1 = (k + c) * (c * 2 + 1) + (i1 + 1);
                        outputPercentRemaining("Preparing spawn area", (k1 * 100) / j1);
                        l1 = l2;
                    }
                    worldserver.chunkProviderServer.loadChunk(chunkcoordinates.posX + k >> 4, chunkcoordinates.posZ + i1 >> 4);
                    while(worldserver.func_6156_d() && serverRunning) ;
                }

                k += 16;
            } while(true);
        }

        clearCurrentTask();
    }

    private void outputPercentRemaining(String s, int i)
    {
        currentTask = s;
        percentDone = i;
        logger.info((new StringBuilder()).append(s).append(": ").append(i).append("%").toString());
    }

    private void clearCurrentTask()
    {
        currentTask = null;
        percentDone = 0;
    }

    private void saveServerWorld()
    {
        logger.info("Saving chunks");
        for(int i = 0; i < worldMngr.length; i++)
        {
            WorldServer worldserver = worldMngr[i];
            worldserver.saveWorld(true, null);
            worldserver.func_30006_w();
        }

    }

    private void stopServer()
    {
        logger.info("Stopping server");
        if(configManager != null)
        {
            configManager.savePlayerStates();
        }
        for(int i = 0; i < worldMngr.length; i++)
        {
            WorldServer worldserver = worldMngr[i];
            if(worldserver != null)
            {
                saveServerWorld();
            }
        }

    }

    public void initiateShutdown()
    {
        serverRunning = false;
    }

    public void run()
    {
        try
        {
            if(startServer())
            {
                long l = System.currentTimeMillis();
                long l1 = 0L;
                while(serverRunning) 
                {
                    long l2 = System.currentTimeMillis();
                    long l3 = l2 - l;
                    if(l3 > 2000L)
                    {
                        logger.warning("Can't keep up! Did the system time change, or is the server overloaded?");
                        l3 = 2000L;
                    }
                    if(l3 < 0L)
                    {
                        logger.warning("Time ran backwards! Did the system time change?");
                        l3 = 0L;
                    }
                    l1 += l3;
                    l = l2;
                    if(worldMngr[0].isAllPlayersFullyAsleep())
                    {
                        doTick();
                        l1 = 0L;
                    } else
                    {
                        while(l1 > 50L) 
                        {
                            l1 -= 50L;
                            doTick();
                        }
                    }
                    Thread.sleep(1L);
                }
            } else
            {
                while(serverRunning) 
                {
                    commandLineParser();
                    try
                    {
                        Thread.sleep(10L);
                    }
                    catch(InterruptedException interruptedexception)
                    {
                        interruptedexception.printStackTrace();
                    }
                }
            }
        }
        catch(Throwable throwable1)
        {
            throwable1.printStackTrace();
            logger.log(Level.SEVERE, "Unexpected exception", throwable1);
            while(serverRunning) 
            {
                commandLineParser();
                try
                {
                    Thread.sleep(10L);
                }
                catch(InterruptedException interruptedexception1)
                {
                    interruptedexception1.printStackTrace();
                }
            }
        }
        finally
        {
            try
            {
                stopServer();
                serverStopped = true;
            }
            catch(Throwable throwable2)
            {
                throwable2.printStackTrace();
            }
            finally
            {
                System.exit(0);
            }
        }
    }

    private void doTick()
    {
        ArrayList arraylist = new ArrayList();
        for(Iterator iterator = field_6037_b.keySet().iterator(); iterator.hasNext();)
        {
            String s = (String)iterator.next();
            int i1 = ((Integer)field_6037_b.get(s)).intValue();
            if(i1 > 0)
            {
                field_6037_b.put(s, Integer.valueOf(i1 - 1));
            } else
            {
                arraylist.add(s);
            }
        }

        for(int i = 0; i < arraylist.size(); i++)
        {
            field_6037_b.remove(arraylist.get(i));
        }

        AxisAlignedBB.clearBoundingBoxPool();
        Vec3D.initialize();
        deathTime++;
        for(int j = 0; j < worldMngr.length; j++)
        {
            if(j != 0 && !propertyManagerObj.getBooleanProperty("allow-nether", true))
            {
                continue;
            }
            WorldServer worldserver = worldMngr[j];
            if(deathTime % 20 == 0)
            {
                configManager.sendPacketToAllPlayersInDimension(new Packet4UpdateTime(worldserver.getWorldTime()), worldserver.worldProvider.worldType);
            }
            worldserver.tick();
            while(worldserver.func_6156_d()) ;
            worldserver.updateEntities();
        }

        networkServer.handleNetworkListenThread();
        configManager.onTick();
        for(int k = 0; k < entityTracker.length; k++)
        {
            entityTracker[k].updateTrackedEntities();
        }

        for(int l = 0; l < field_9010_p.size(); l++)
        {
            ((IUpdatePlayerListBox)field_9010_p.get(l)).update();
        }

        try
        {
            commandLineParser();
        }
        catch(Exception exception)
        {
            logger.log(Level.WARNING, "Unexpected exception while parsing console command", exception);
        }
    }

    public void addCommand(String s, ICommandListener icommandlistener)
    {
        commands.add(new ServerCommand(s, icommandlistener));
    }

    public void commandLineParser()
    {
        ServerCommand servercommand;
        for(; commands.size() > 0; commandHandler.handleCommand(servercommand))
        {
            servercommand = (ServerCommand)commands.remove(0);
        }

    }

    public void func_6022_a(IUpdatePlayerListBox iupdateplayerlistbox)
    {
        field_9010_p.add(iupdateplayerlistbox);
    }

    public static void main(String args[])
    {
        StatList.func_27092_a();
        try
        {
            MinecraftServer minecraftserver = new MinecraftServer();
            if(!java.awt.GraphicsEnvironment.isHeadless() && (args.length <= 0 || !args[0].equals("nogui")))
            {
                ServerGUI.initGui(minecraftserver);
            }
            (new ThreadServerApplication("Server thread", minecraftserver)).start();
        }
        catch(Exception exception)
        {
            logger.log(Level.SEVERE, "Failed to start the minecraft server", exception);
        }
    }

    public File getFile(String s)
    {
        return new File(s);
    }

    public void log(String s)
    {
        logger.info(s);
    }

    public void logWarning(String s)
    {
        logger.warning(s);
    }

    public String getUsername()
    {
        return "CONSOLE";
    }

    public WorldServer getWorldManager(int i)
    {
        if(i == -1)
        {
            return worldMngr[1];
        } else
        {
            return worldMngr[0];
        }
    }

    public EntityTracker getEntityTracker(int i)
    {
        if(i == -1)
        {
            return entityTracker[1];
        } else
        {
            return entityTracker[0];
        }
    }

    public static boolean isServerRunning(MinecraftServer minecraftserver)
    {
        return minecraftserver.serverRunning;
    }

    public static Logger logger = Logger.getLogger("Minecraft");
    public static HashMap field_6037_b = new HashMap();
    public NetworkListenThread networkServer;
    public PropertyManager propertyManagerObj;
    public WorldServer worldMngr[];
    public ServerConfigurationManager configManager;
    private ConsoleCommandHandler commandHandler;
    private boolean serverRunning;
    public boolean serverStopped;
    int deathTime;
    public String currentTask;
    public int percentDone;
    private List field_9010_p;
    private List commands;
    public EntityTracker entityTracker[];
    public boolean onlineMode;
    public boolean spawnPeacefulMobs;
    public boolean pvpOn;
    public boolean allowFlight;

}
