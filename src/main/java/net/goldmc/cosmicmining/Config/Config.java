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
    CosmicMining plugin = (CosmicMining) CosmicMining.getPlugin(CosmicMining.class);
    YamlDocument config;
    public void createConfig() throws IOException {
        config = YamlDocument.create(new File(plugin.getDataFolder(), "config.yml"), plugin.getResource("config.yml"));
    }

    public YamlDocument getTheConfig() {
        return config;
    }

    public void setConfig(YamlDocument config) throws IOException {
        this.config = config;
        config.save();
    }
    YamlDocument levels;

    public void createLevels() throws IOException {
        levels = YamlDocument.create(new File(plugin.getDataFolder(), "levels.yml"), plugin.getResource("levels.yml"));
    }

    public YamlDocument getLevels() {
        return levels;
    }

    public void setLevels(YamlDocument levels) throws IOException {
        this.levels = levels;
        levels.save();
    }
}
