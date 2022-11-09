package net.goldmc.cosmicmining.Listeners;

import net.goldmc.cosmicmining.dataconnectors.LevelingUtils;
import net.goldmc.cosmicmining.Utilites.Scoreboards;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

public class OnJoin implements Listener {
    @EventHandler
    public static void joinEvent(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        UUID u = p.getUniqueId();
        try {
            boolean miningUUID = LevelingUtils.getInstance().getLevel(u) != null;
            if(!miningUUID) {
                LevelingUtils.getInstance().setLevel(u, 1);
                LevelingUtils.getInstance().setXp(u, 0L);
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        new Scoreboards(u).prisonsMiningScoreboard();
    }
}
