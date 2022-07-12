package net.goldmc.cosmicmining;


import com.github.retrooper.packetevents.PacketEvents;
import io.github.retrooper.packetevents.factory.spigot.SpigotPacketEventsBuilder;
import net.goldmc.cosmicmining.Commands.CreateXpBooster;
import net.goldmc.cosmicmining.Commands.setLevel;
import net.goldmc.cosmicmining.Commands.setXp;
import net.goldmc.cosmicmining.Config.Config;
import net.goldmc.cosmicmining.Database.InitSql;
import net.goldmc.cosmicmining.Database.MySqlDatabase;
import net.goldmc.cosmicmining.Leveling.XpBoosters.XpBoosterRightClick;
import net.goldmc.cosmicmining.Listeners.BreakingEvents.*;
import net.goldmc.cosmicmining.Utilites.BlockBreakHolder;
import net.goldmc.cosmicmining.Utilites.CosmicExpansion;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import net.goldmc.cosmicmining.Listeners.OnJoin;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

public final class CosmicMining extends JavaPlugin {

    Boolean Data;

    public static Object getInstance() {
        return null;
    }

    public void setGameRules() {
        getServer().getWorld("world").setGameRuleValue("doEntityDrops", "false");
        getServer().getWorld("world").setGameRuleValue("doMobLoot", "false");
        getServer().getWorld("world").setGameRuleValue("doMobSpawning", "false");
        getServer().getWorld("world").setGameRuleValue("doTileDrops", "false");
        getServer().getWorld("world").setGameRuleValue("mobGreifing", "false");
    }


    public void createListeners() {
        Bukkit.getPluginManager().registerEvents(new OnJoin(), this);
        Bukkit.getPluginManager().registerEvents(new OnOreBlockBreak(), this);
        Bukkit.getPluginManager().registerEvents(new OnPlayerInteract(), this);
        Bukkit.getPluginManager().registerEvents(new OnPlayerInteractWithOre(), this);
        Bukkit.getPluginManager().registerEvents(new XpBoosterRightClick(), this);
    }
    boolean checkPapi() {
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            /*
             * We register the EventListener here, when PlaceholderAPI is installed.
             * Since all events are in the main class (this class), we simply use "this"
             */
            return true;
        } else {
            /*
             * We inform about the fact that PlaceholderAPI isn't installed and then
             * disable this plugin to prevent issues.
             */
            getLogger().severe("PlaceholderAPI is not installed, disabling plugin!");
            Bukkit.getPluginManager().disablePlugin(this);
            return false;
        }
    }

    public void onLoad() {
        PacketEvents.setAPI(SpigotPacketEventsBuilder.build(this));
        //Are all listeners read only?
        PacketEvents.getAPI().getSettings().readOnlyListeners(true)
                .checkForUpdates(true)
                .bStats(true);
        PacketEvents.getAPI().load();
    }

    @Override
    public void onEnable() {
        try {
            Config.createConfig();
            Config.createLevels();
            Config.createXpBoosters();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //add plugin to blockbreakholders
        new BlockBreakHolder(this);
        createListeners();
        //setGameRules();
        System.out.println("CosmicMining Started up");
        getCommand("setxp").setExecutor(new setXp());
        getCommand("setlevel").setExecutor(new setLevel());
        getCommand("xpbooster").setExecutor(new CreateXpBooster());
        if(checkPapi()) {
            new CosmicExpansion(this).register();
        }
        new MySqlDatabase();
        PacketEvents.getAPI().init();
    }

    @Override
    public void onDisable() {
        InitSql.getSource().close();
        System.out.println("Shutting down database");
    }
}
