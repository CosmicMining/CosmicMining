package net.goldmc.cosmicmining.Config;

import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.dvs.versioning.BasicVersioning;
import dev.dejvokep.boostedyaml.settings.dumper.DumperSettings;
import dev.dejvokep.boostedyaml.settings.general.GeneralSettings;
import dev.dejvokep.boostedyaml.settings.loader.LoaderSettings;
import dev.dejvokep.boostedyaml.settings.updater.UpdaterSettings;
import net.goldmc.cosmicmining.CosmicMining;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Config {
    static CosmicMining plugin = (CosmicMining) CosmicMining.getPlugin(CosmicMining.class);
    static YamlDocument config;
    public static void createConfig() throws IOException {
        config = YamlDocument.create(new File(plugin.getDataFolder(), "config.yml"), plugin.getResource("config.yml"));
    }

    public static YamlDocument getTheConfig() {
        try {
            config.reload();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return config;
    }

    public static void setConfig(YamlDocument config) throws IOException {
        Config.config = config;
        config.save();
        config.reload();
    }
    static YamlDocument levels;

    public static void createLevels() throws IOException {
        levels = YamlDocument.create(new File(plugin.getDataFolder(), "levels.yml"), plugin.getResource("levels.yml"));
    }

    public static YamlDocument getLevels() {
        try {
            levels.reload();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return levels;
    }

    public static void setLevels(YamlDocument levels) throws IOException {
        levels.save();
        levels.reload();
        Config.levels = levels;
    }
}
