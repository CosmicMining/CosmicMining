package net.goldmc.cosmicmining.Listeners.BreakingEvents;

import net.goldmc.cosmicmining.Database.loadPlayerData;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

public class onPlayerInteractWithOre implements Listener {
    @EventHandler
    public void playerInteractEventwithOre(PlayerInteractEvent e) {


        if (e.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
            if (e.getPlayer().hasPermission("cosmicmining.minearea")) {
                Player p = e.getPlayer();
                Block b = e.getClickedBlock();
                Block blocksave = e.getClickedBlock();
                Random random = new Random();
                int y = random.nextInt(10);
                String origblock = b.getType().toString();
                int x = random.nextInt(3);
                breakingFunctions runnable = new breakingFunctions();
                if (b.getType() == Material.COAL_ORE || b.getType() == Material.IRON_ORE || b.getType() == Material.LAPIS_ORE || b.getType() == Material.REDSTONE_ORE || b.getType() == Material.GOLD_ORE || b.getType() == Material.DIAMOND_ORE || b.getType() == Material.EMERALD_ORE || b.getType() == Material.COAL_BLOCK || b.getType() == Material.IRON_BLOCK || b.getType() == Material.LAPIS_BLOCK || b.getType() == Material.REDSTONE_BLOCK || b.getType() == Material.GOLD_BLOCK || b.getType() == Material.DIAMOND_BLOCK || b.getType() == Material.EMERALD_BLOCK) {
                    String finalOrigblock = origblock;
                    Map<String, Integer> hm
                            = new HashMap<String, Integer>();
                    String[] split = finalOrigblock.split("_", 0);
                    String block = split[0];
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
