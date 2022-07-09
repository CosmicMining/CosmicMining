package net.goldmc.cosmicmining;


import net.goldmc.cosmicmining.Commands.setLevel;
import net.goldmc.cosmicmining.Commands.setXp;
import net.goldmc.cosmicmining.Config.Config;
import net.goldmc.cosmicmining.Database.MySqlDatabase;
import net.goldmc.cosmicmining.Listeners.BreakingEvents.*;
import net.goldmc.cosmicmining.Utilites.BlockBreakHolder;
import net.goldmc.cosmicmining.Utilites.CosmicExpansion;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import net.goldmc.cosmicmining.Listeners.OnJoin;

import java.io.IOException;

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


    @Override
    public void onEnable() {
        //add plugin to blockbreakholders
        new BlockBreakHolder(this);
        createListeners();
        try {
            Config.createConfig();
            Config.createLevels();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //setGameRules();
        //TODO: Add config back
        System.out.println("CosmicMining Started up");
        this.getCommand("setxp").setExecutor(new setXp());
        this.getCommand("setlevel").setExecutor(new setLevel());
        if(checkPapi()) {
            new CosmicExpansion(this).register();
        }
        MySqlDatabase mySqlDatabase = new MySqlDatabase();
    }

    @Override
    public void onDisable() {
        System.out.println("Shutting down database");
    }
}
