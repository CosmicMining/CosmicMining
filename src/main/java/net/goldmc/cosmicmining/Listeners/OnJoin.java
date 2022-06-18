package net.goldmc.cosmicmining.Listeners;

import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.route.Route;
import net.goldmc.cosmicmining.Config.Config;
import net.goldmc.cosmicmining.Leveling.XpFunctions;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;
import java.util.UUID;

public class OnJoin implements Listener {
    @EventHandler
    public static void joinEvent(PlayerJoinEvent e) throws IOException {
        Config config = new Config();
        Player p = e.getPlayer();
        UUID u = p.getUniqueId();
        YamlDocument levels;
        boolean miningUUID = false;
        try {
            miningUUID = config.getLevels().contains(Route.from("Levels", e.getPlayer().getUniqueId().toString()));
            if(!miningUUID) {
                levels = config.getLevels();
                System.out.println(levels.toString());
                levels.set("Levels." + e.getPlayer().getUniqueId().toString() + ".level", 1);
                levels.set("Levels." + e.getPlayer().getUniqueId().toString() + ".xp", 0);
                config.setLevels(levels);
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        /*
        Config.getCustomConfig2().set("Players." + e.getPlayer().getUniqueId() + ".Username", p.getName());
        Config.saveConfig2();
        boolean miningUUID = Config.getCustomConfig3().contains("Levels." + e.getPlayer().getUniqueId().toString());
        if(!(miningUUID)) {
            Config.getCustomConfig3().set("Levels." + e.getPlayer().getUniqueId().toString() + ".level", 1);
            Config.getCustomConfig3().set("Levels." + e.getPlayer().getUniqueId().toString() + ".xp", 0);
            Config.saveConfig3();
        }
        int Level = Integer.parseInt(Config.getCustomConfig3().getString("Levels." + e.getPlayer().getUniqueId().toString()+ ".level"));
        p.setLevel(Level);
        //net.goldmc.cosmicmining.Database.Data.setPlayerData(u, Config.getCustomConfig3().getInt("Levels." + e.getPlayer().getUniqueId().toString() + ".level"), Config.getCustomConfig3().getInt("Levels." + e.getPlayer().getUniqueId().toString() + ".xp"));
        float xp = xpFunctions.calculateXp(u);
        p.setExp(xp);

         */
    }
}
