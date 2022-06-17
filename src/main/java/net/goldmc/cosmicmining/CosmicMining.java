package net.goldmc.cosmicmining;


import dev.dejvokep.boostedyaml.YamlDocument;
import net.goldmc.cosmicmining.Commands.setLevel;
import net.goldmc.cosmicmining.Commands.setXp;
import net.goldmc.cosmicmining.Listeners.BreakingEvents.*;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import net.goldmc.cosmicmining.Listeners.OnJoin;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.IOException;

public final class CosmicMining extends JavaPlugin {

    Boolean Data;

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
        Bukkit.getPluginManager().registerEvents(new OnBlockBreak(), this);
        Bukkit.getPluginManager().registerEvents(new OnPlayerInteract(), this);
        Bukkit.getPluginManager().registerEvents(new OnPlayerInteractWithOre(), this);
    }
    YamlDocument config;
    public void createConfig() throws IOException {
        YamlDocument config = YamlDocument.create(new File(getDataFolder(), "config.yml"), getResource("config.yml"));
    }

    public YamlDocument getTheConfig() {
        return config;
    }

    public void setConfig(YamlDocument config) {
        this.config = config;
    }
    YamlDocument levels;

    public void createLevels() throws IOException {
        YamlDocument levels = YamlDocument.create(new File(getDataFolder(), "levels.yml"), getResource("levels.yml"));
    }

    public YamlDocument getLevels() {
        return levels;
    }

    public void setLevels(YamlDocument levels) throws IOException {
        this.levels = levels;
        levels.save();
    }

    @Override
    public void onEnable() {
        try {
            createConfig();
            createLevels();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        setGameRules();
        //TODO: Add config back
        System.out.println("CosmicMining Started up");
        this.getCommand("setxp").setExecutor(new setXp());
        this.getCommand("setlevel").setExecutor(new setLevel());
        createListeners();
    }

    @Override
    public void onDisable() {
        System.out.println("Shutting down database");
        net.goldmc.cosmicmining.Database.Data.shutdownDatabase();
    }
}
