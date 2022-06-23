package net.goldmc.cosmicmining.Utilites;

import dev.dejvokep.boostedyaml.YamlDocument;
import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.libs.kyori.adventure.platform.facet.Facet;
import net.goldmc.cosmicmining.Config.Config;
import net.goldmc.cosmicmining.CosmicMining;
import net.goldmc.cosmicmining.Leveling.XpFunctions;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.scoreboard.Scoreboard;

import java.text.NumberFormat;
import java.util.Objects;
import java.util.UUID;

public class Scoreboards {
    static YamlDocument config = Config.getTheConfig();
    static String convert(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }
    public static Scoreboard prisonsScoreboard(UUID uuid) {
        //Cosmic Prisons Scoreboard
        if(config.getBoolean("Scoreboards.Enabled") && Objects.equals(config.get("Scoreboards.Mode").toString(), "Cosmic")) {
            XpFunctions xpf = new XpFunctions();
            String servername;
            if(config.get("Scoreboards.ServerName") == null) {
                servername = "ExampleMC";
                config.set("Scoreboards.ServerName", "ExampleMC");
                try {
                    Config.setConfig(config);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                servername = config.get("Scoreboards.ServerName").toString();
            }
            xpf.checkLevelUp(uuid);
            int xptax = 1;
            ScoreboardWrapper wrapper = new ScoreboardWrapper("Prisons");
            wrapper.setTitle("§6§l" + servername +  "§7- §b§lPrisons"); //TODO: Add date to the title
            wrapper.addLine("§b§lAccount: §r" + Bukkit.getOfflinePlayer(uuid).getName());
            wrapper.addLine("");
            wrapper.addLine(ChatColor.translateAlternateColorCodes('&', "&6&lLevel"));
            String level = PlaceholderAPI.setPlaceholders((OfflinePlayer) Bukkit.getPlayer(uuid), "%cosmicmining_level%");
            String xp = PlaceholderAPI.setPlaceholders((OfflinePlayer) Bukkit.getPlayer(uuid), "%cosmicmining_xp%");
            level = ChatColor.translateAlternateColorCodes('&', "   &l" + level + " &r&7(" + xp + " XP)");
            wrapper.addLine(level);
            wrapper.addLine(convert("&e&lProgress"));
            int nextlevel = Integer.parseInt(PlaceholderAPI.setPlaceholders((OfflinePlayer) Bukkit.getPlayer(uuid), "%cosmicmining_level%")) + 1;
            wrapper.addLine(convert(PlaceholderAPI.setPlaceholders((OfflinePlayer) Bukkit.getPlayer(uuid), "   &7%cosmicmining_level_remaining_xp% (&a%cosmicmining_level_percentage%%&r&7) to &f" + nextlevel)));
            if (xptax != 0) {
                wrapper.addLine(convert("&c&lGuard XP Tax &c&l" + ChatColor.UNDERLINE + xptax + "%"));
            }
            if (Bukkit.getPlayer(uuid) != null) {
                Bukkit.getPlayer(uuid).setScoreboard(wrapper.getScoreboard());
            }
            return wrapper.getScoreboard();
        } else {
            return null;
        }
    }
}
