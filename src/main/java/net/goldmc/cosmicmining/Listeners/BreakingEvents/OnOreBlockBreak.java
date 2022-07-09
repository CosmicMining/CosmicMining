package net.goldmc.cosmicmining.Listeners.BreakingEvents;

import net.goldmc.cosmicmining.Leveling.enums;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.*;

public class OnOreBlockBreak implements Listener {
    ArrayList<enums.oreleveling> ores = new ArrayList<>(Arrays.asList(enums.oreleveling.values()));
    ArrayList<enums.blockleveling> blocks = new ArrayList<>(Arrays.asList(enums.blockleveling.values()));
    BreakingFunctions runnable = new BreakingFunctions();

    Map<String, Integer> hm
            = new HashMap<String, Integer>();

    @EventHandler
    public void playerOreBlockBreakEvent(BlockBreakEvent e)  {
        Block b = e.getBlock();
        Player p = e.getPlayer();

        if(p.hasPermission("cosmicmining.minearea.mine")) {
            //Gets all ore types from enum
            String finalOrigblock = b.getType().toString();
            String[] split = finalOrigblock.split("_", 0);
            if(Objects.equals(split[1], "BLOCK")) {
                for(enums.blockleveling block : blocks) {
                    if(b.getType() == block.getBlock()) {
                        oreandblocksmap(hm);
                        for (Map.Entry<String, Integer> entry : hm.entrySet()) {
                            // if give value is equal to value from entry
                            // print the corresponding key
                            if (Objects.equals(entry.getKey(), split[0])) {
                                runnable.blockChecks(p, finalOrigblock, b, entry.getValue(), true);
                                p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 9999999, 255, false, false), true);
                                e.setCancelled(true);
                                break;
                            }
                        }
                    }
                }
            } else {
                //for every ore type in enum
                for (enums.oreleveling ore : ores) {
                    //if the block is equal to the ore type
                    if (b.getType() == ore.getOre()) {
                        oreandblocksmap(hm);
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
                        break;
                    }

                }
            }
        }
    }

    static void oreandblocksmap(Map<String, Integer> hm) {
        hm.put("COAL", 1);
        hm.put("IRON", 2);
        hm.put("LAPIS", 3);
        hm.put("REDSTONE", 4);
        hm.put("GLOWING", 4);
        hm.put("GOLD", 5);
        hm.put("DIAMOND", 6);
        hm.put("EMERALD", 7);
    }

}
