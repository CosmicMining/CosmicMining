package net.goldmc.cosmicmining.Config;

import dev.dejvokep.boostedyaml.YamlDocument;
import net.goldmc.cosmicmining.CosmicMining;

import java.io.File;
import java.io.IOException;

public class Config {
    static CosmicMining plugin = (CosmicMining) CosmicMining.getPlugin(CosmicMining.class);
    static YamlDocument config;
    static YamlDocument levels;
    static YamlDocument xpboosters;
    public static void createConfig() throws IOException {
        config = YamlDocument.create(new File(plugin.getDataFolder(), "config.yml"), plugin.getResource("config.yml"));
    }

    public static void createXpBoosters() throws IOException {
        xpboosters = YamlDocument.create(new File(plugin.getDataFolder(), "xpboosters.yml"));
    }

    public static void createLevels() throws IOException {
        levels = YamlDocument.create(new File(plugin.getDataFolder(), "levels.yml"), plugin.getResource("levels.yml"));
    }

    public static YamlDocument getTheConfig() {
        try {
            config.reload();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return config;
    }

    public static YamlDocument getLevels() {
        try {
            levels.reload();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return levels;
    }

    public static YamlDocument getXpBoosters() {
        try {
            xpboosters.reload();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return xpboosters;
    }

    public static void setConfig(YamlDocument config) throws IOException {
        Config.config = config;
        config.save();
        config.reload();
    }

    public static void setLevels(YamlDocument levels) throws IOException {
        levels.save();
        levels.reload();
        Config.levels = levels;
    }

    public static void setXpBoosters(YamlDocument xpboosters) throws IOException {
        Config.xpboosters = xpboosters;
        Config.xpboosters.save();
        Config.xpboosters.reload();
    }
}
