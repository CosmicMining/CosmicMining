package net.goldmc.cosmicmining.Listeners.BreakingEvents;

import net.goldmc.cosmicmining.Database.LoadPlayerData;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffectType;

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
            if (e.getPlayer().hasPermission("cosmicmining.minearea")) {
                Block b = e.getClickedBlock();
                if (b.getType() == Material.COAL_ORE || b.getType() == Material.IRON_ORE || b.getType() == Material.LAPIS_ORE || b.getType() == Material.REDSTONE_ORE || b.getType() == Material.GOLD_ORE || b.getType() == Material.DIAMOND_ORE || b.getType() == Material.EMERALD_ORE || b.getType() == Material.COAL_BLOCK || b.getType() == Material.IRON_BLOCK || b.getType() == Material.LAPIS_BLOCK || b.getType() == Material.REDSTONE_BLOCK || b.getType() == Material.GOLD_BLOCK || b.getType() == Material.DIAMOND_BLOCK || b.getType() == Material.EMERALD_BLOCK) {
                    Player p = e.getPlayer();Random random = new Random();
                    String origblock = b.getType().toString();
                    String finalOrigblock = origblock;
                    String[] split = finalOrigblock.split("_", 0);
                    hm.put("COAL", 1);
                    hm.put("IRON", 2);
                    hm.put("LAPIS", 3);
                    hm.put("REDSTONE", 4);
                    hm.put("GOLD", 5);
                    hm.put("DIAMOND", 6);
                    hm.put("EMERALD", 7);
                    for (Map.Entry<String, Integer> entry : hm.entrySet()) {
                        // if give value is equal to value from entry
                        // print the corresponding key
                        if (Objects.equals(entry.getKey(), split[0])) {
                            LoadPlayerData loadPlayerData = new LoadPlayerData();
                            boolean canBreak = loadPlayerData.canBreak(p.getUniqueId(), entry.getValue());
                            if(canBreak) {
                                p.removePotionEffect(PotionEffectType.SLOW_DIGGING);
                            }
                            break;
                        }
                    }
                }
            }
        }
    }
}
