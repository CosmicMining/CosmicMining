package net.goldmc.cosmicmining.Listeners.BreakingEvents;

import net.goldmc.cosmicmining.Database.loadPlayerData;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

public class onBlockBreakRewrite implements Listener {
    // When you break the block version of an ore

    @EventHandler
    public void playerBlockBreakEvent(BlockBreakEvent e) throws InterruptedException {
        Player p = e.getPlayer();
        Block b = e.getBlock();
        Block blocksave = e.getBlock();
        Random random = new Random();
        int y = random.nextInt(10);
        breakingFunctions runnable = new breakingFunctions();
        String origblock = b.getType().toString();
        int x = random.nextInt(3);


        if(p.hasPermission("cosmicmining.minearea")) {
            if(b.getType()==Material.COAL_BLOCK || b.getType()==Material.IRON_BLOCK || b.getType()==Material.LAPIS_BLOCK || b.getType()==Material.REDSTONE_BLOCK || b.getType()==Material.GOLD_BLOCK || b.getType()==Material.DIAMOND_BLOCK || b.getType()==Material.EMERALD_BLOCK) {
                String finalOrigblock = origblock;
                String[] split = finalOrigblock.split("_", 0);
                String block = split[0];
                if (x == 0) {
                    x = 1;
                }

                Map<String, Integer> hm
                        = new HashMap<String, Integer>();
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
                        runnable.blockChecks(p, finalOrigblock, b, y, split, x, entry.getValue(), true);
                        p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 9999999, 255, false, false), true);
                        e.setCancelled(true);
                        break;
                    }
                }
            }
        }
    }
}
