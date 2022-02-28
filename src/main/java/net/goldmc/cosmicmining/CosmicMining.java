package net.goldmc.cosmicmining;


import net.goldmc.cosmicmining.Commands.setLevel;
import net.goldmc.cosmicmining.Commands.setXp;
import net.goldmc.cosmicmining.Config.Config;
import net.goldmc.cosmicmining.Listeners.BreakingEvents.*;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import net.goldmc.cosmicmining.Listeners.onJoin;

public final class CosmicMining extends JavaPlugin {

    public FileConfiguration config = this.getConfig();

    Boolean Data;

    public void setGameRules() {
        getServer().getWorld("world").setGameRuleValue("doEntityDrops", "false");
        getServer().getWorld("world").setGameRuleValue("doMobLoot", "false");
        getServer().getWorld("world").setGameRuleValue("doMobSpawning", "false");
        getServer().getWorld("world").setGameRuleValue("doTileDrops", "false");
        getServer().getWorld("world").setGameRuleValue("mobGreifing", "false");
    }

    public void createListeners() {
        Bukkit.getPluginManager().registerEvents(new onJoin(), this);
        Bukkit.getPluginManager().registerEvents(new onOreBlockBreak(), this);
        Bukkit.getPluginManager().registerEvents(new onBlockBreak(), this);
        Bukkit.getPluginManager().registerEvents(new onPlayerInteract(), this);
        Bukkit.getPluginManager().registerEvents(new onPlayerInteractWithOreRewrite(), this);
    }



    @Override
    public void onEnable() {
        setGameRules();
        Config.createCustomConfig1();
        Config.createCustomConfig2();
        Config.createCustomConfig3();
        System.out.println("CosmicMining Started up");
        this.getCommand("givexp").setExecutor(new setXp());
        this.getCommand("setlevel").setExecutor(new setLevel());
        createListeners();
    }

    @Override
    public void onDisable() {
        System.out.println("Shutting down database");
        net.goldmc.cosmicmining.Database.Data.shutdownDatabase();
    }
}
