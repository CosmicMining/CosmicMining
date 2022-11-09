package net.goldmc.cosmicmining.Listeners.BreakingEvents;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class OnPlayerInteractWithOre implements Listener {





    @EventHandler
    public void playerInteractEventwithOre(PlayerInteractEvent e) {

        if (e.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
            if (e.getPlayer().hasPermission("cosmicmining.minearea.mine")) {

            }
        }
    }
}
