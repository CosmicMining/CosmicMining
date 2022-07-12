package net.goldmc.cosmicmining.Listeners;

import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.route.Route;
import net.goldmc.cosmicmining.Config.Config;
import net.goldmc.cosmicmining.Leveling.XpFunctions;
import net.goldmc.cosmicmining.Utilites.BlockBreakHolder;
import net.goldmc.cosmicmining.Utilites.BlockBreakHoldersHashMap;
import net.goldmc.cosmicmining.Utilites.Scoreboards;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.Scoreboard;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;
import java.util.UUID;

public class OnJoin implements Listener {
    @EventHandler
    public static void joinEvent(PlayerJoinEvent e) throws IOException {
        Player p = e.getPlayer();
        UUID u = p.getUniqueId();
        BlockBreakHoldersHashMap.registerBlockHolder(u, new BlockBreakHolder(u));
        YamlDocument levels;
        boolean miningUUID = false;
        try {
            miningUUID = Config.getLevels().contains(Route.from("Levels", e.getPlayer().getUniqueId().toString()));
            if(!miningUUID) {
                levels = Config.getLevels();
                levels.set("Levels." + e.getPlayer().getUniqueId().toString() + ".level", 1);
                levels.set("Levels." + e.getPlayer().getUniqueId().toString() + ".xp", 0);
                Config.setLevels(levels);
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        e.getPlayer().setScoreboard(Scoreboards.prisonsScoreboard(e.getPlayer().getUniqueId()));
    }
}
