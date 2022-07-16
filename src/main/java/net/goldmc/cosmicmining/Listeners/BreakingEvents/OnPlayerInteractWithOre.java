package net.goldmc.cosmicmining.Listeners.BreakingEvents;

import net.goldmc.cosmicmining.Utilites.PlayerData;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

public class OnPlayerInteractWithOre implements Listener {





    @EventHandler
    public void playerInteractEventwithOre(PlayerInteractEvent e) {

        if (e.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
            if (e.getPlayer().hasPermission("cosmicmining.minearea.mine")) {

            }
        }
    }
}
