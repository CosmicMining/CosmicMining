package net.goldmc.cosmicmining.Config;

import net.goldmc.cosmicmining.CosmicMining;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Config {
    static CosmicMining plugin = (CosmicMining) CosmicMining.getPlugin(CosmicMining.class);
    public static File customConfigFile1;
    private static FileConfiguration customConfig1;
    public static File customConfigFile2;
    /*     */   public static FileConfiguration getCustomConfig1() {
        /*  19 */     return customConfig1;
        /*     */   }
    private static FileConfiguration customConfig2; public static File customConfigFile3; private static FileConfiguration customConfig3;
    public static void createCustomConfig1() {
        /*  23 */     customConfigFile1 = new File(plugin.getDataFolder(), "config.yml");
        /*  24 */     if (!customConfigFile1.exists()) {
            /*  25 */       customConfigFile1.getParentFile().mkdirs();
            /*  26 */       plugin.saveResource("config.yml", false);
            /*     */     }
        /*     */
        /*  29 */     customConfig1 = (FileConfiguration)new YamlConfiguration();
        /*     */
        /*     */     try {
            /*  32 */       customConfig1.load(customConfigFile1);
            /*  33 */     } catch (IOException |org.bukkit.configuration.InvalidConfigurationException e) {
            /*  34 */       e.printStackTrace();
            /*     */     }
        /*     */   }
    /*     */
    /*     */
    /*     */
    /*     */   public static FileConfiguration getCustomConfig2() {
        /*  42 */     return customConfig2;
        /*     */   }
    /*     */   public static void createCustomConfig2() {
        /*  46 */     customConfigFile2 = new File(plugin.getDataFolder(), "uuid.yml");
        /*  47 */     if (!customConfigFile2.exists()) {
            /*  48 */       customConfigFile2.getParentFile().mkdirs();
            /*  49 */       plugin.saveResource("uuid.yml", false);
            /*     */     }
        /*     */
        /*  52 */     customConfig2 = (FileConfiguration)new YamlConfiguration();
        /*     */
        /*     */     try {
            /*  55 */       customConfig2.load(customConfigFile2);
            /*  56 */     } catch (IOException|org.bukkit.configuration.InvalidConfigurationException e) {
            /*  57 */       e.printStackTrace();
            /*     */     }
        /*     */   }

    public static FileConfiguration getCustomConfig3() {
        /*  42 */     return customConfig3;
        /*     */   }
    public static void createCustomConfig3() {
        /*  46 */     customConfigFile3 = new File(plugin.getDataFolder(), "levels.yml");
        /*  47 */     if (!customConfigFile3.exists()) {
            /*  48 */       customConfigFile3.getParentFile().mkdirs();
            /*  49 */       plugin.saveResource("levels.yml", false);
            /*     */     }
        /*     */
        /*  52 */     customConfig3 = (FileConfiguration)new YamlConfiguration();
        /*     */
        /*     */     try {
            /*  55 */       customConfig3.load(customConfigFile3);
            /*  56 */     } catch (IOException|org.bukkit.configuration.InvalidConfigurationException e) {
            /*  57 */       e.printStackTrace();
            /*     */     }
        /*     */   }
    /*     */
    /*     */
    /*     */
    /*     */
    /*     *
    /*     */

public static void saveConfig3() {
    File file1 = new File("plugins//CosmicMining//levels.yml");
    try {
        getCustomConfig3().save(file1);
    } catch (IOException ee) {
        ee.printStackTrace();
    }
}
 public static void saveConfig1() {
        /*  85 */     File file = new File("plugins//CosmicMining//config.yml");
        /*     */     try {
            /*  87 */       getCustomConfig1().save(file);
            /*  88 */     } catch (IOException ee) {
            /*  89 */       ee.printStackTrace();
            /*     */     }
        /*     */   }
    public static void saveConfig2() {
    File file = new File("plugins//CosmicMining//uuid.yml");
      try {
         getCustomConfig2().save(file);
         } catch (IOException ee) {
        ee.printStackTrace();
       }

}
}
