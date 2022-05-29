package net.goldmc.cosmicmining.Listeners.BreakingEvents;

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

public class OnOreBlockBreak implements Listener {

    Map<String, Integer> hm
            = new HashMap<String, Integer>();

    @EventHandler
    public void playerOreBlockBreakEvent(BlockBreakEvent e)  {
        Block b = e.getBlock();
        Player p = e.getPlayer();

        if(p.hasPermission("cosmicmining.minearea")) {
            if(b.getType()== Material.COAL_ORE || b.getType()==Material.IRON_ORE || b.getType()==Material.LAPIS_ORE ||  b.getType()==Material.REDSTONE_ORE || b.getType()==Material.GLOWING_REDSTONE_ORE || b.getType()==Material.GOLD_ORE || b.getType()==Material.DIAMOND_ORE || b.getType()==Material.EMERALD_ORE) {
                BreakingFunctions runnable = new BreakingFunctions();
                Random random = new Random();
                int y = random.nextInt(10);
                int x = random.nextInt(3);
                String origblock = b.getType().toString();
                String finalOrigblock = origblock;
                String[] split = finalOrigblock.split("_", 0);
                String block = split[0];
                if (x == 0) {
                    x = 1;
                }


                hm.put("COAL", 1);
                hm.put("IRON", 2);
                hm.put("LAPIS", 3);
                hm.put("REDSTONE", 4);
                hm.put("GLOWING", 4);
                hm.put("GOLD", 5);
                hm.put("DIAMOND", 6);
                hm.put("EMERALD", 7);
                for (Map.Entry<String, Integer> entry : hm.entrySet()) {
                    // if give value is equal to value from entry
                    // print the corresponding key
                    if (Objects.equals(entry.getKey(), split[0])) {
                        runnable.blockChecks(p, finalOrigblock, b, entry.getValue(), false);
                        p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 9999999, 255, false, false), true);
                        e.setCancelled(true);
                        break;
                    }
                }
            }
        }
    }

}
