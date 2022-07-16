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

    Map<String, Integer> hm
            = new HashMap<String, Integer>();


    @EventHandler
    public void playerInteractEventwithOre(PlayerInteractEvent e) {

        if (e.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
            if (e.getPlayer().hasPermission("cosmicmining.minearea.mine")) {
                Block b = e.getClickedBlock();
                if (b.getType() == Material.COAL_ORE || b.getType() == Material.IRON_ORE || b.getType() == Material.LAPIS_ORE || b.getType()==Material.REDSTONE_ORE || b.getType()==Material.GLOWING_REDSTONE_ORE || b.getType() == Material.GOLD_ORE || b.getType() == Material.DIAMOND_ORE || b.getType() == Material.EMERALD_ORE || b.getType() == Material.COAL_BLOCK || b.getType() == Material.IRON_BLOCK || b.getType() == Material.LAPIS_BLOCK || b.getType() == Material.REDSTONE_BLOCK || b.getType() == Material.GOLD_BLOCK || b.getType() == Material.DIAMOND_BLOCK || b.getType() == Material.EMERALD_BLOCK) {
                    Player p = e.getPlayer();Random random = new Random();
                    String finalOrigblock = b.getType().toString();
                    String[] split = finalOrigblock.split("_", 0);
                    OnOreBlockBreak.oreandblocksmap(hm);
                    for (Map.Entry<String, Integer> entry : hm.entrySet()) {
                        // if give value is equal to value from entry
                        // print the corresponding key
                        if (Objects.equals(entry.getKey(), split[0])) {
                            PlayerData playerData = new PlayerData();
                            boolean canBreak = playerData.canBreakBlock(p.getUniqueId(), entry.getValue());
                            if(canBreak) {
                                //TODO: allow in new block break system
                            }
                            break;
                        }
                    }
                }
            }
        }
    }
}
