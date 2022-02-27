package net.goldmc.cosmicmining.Listeners.BreakingEvents;

import net.goldmc.cosmicmining.Database.loadPlayerData;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

public class onPlayerInteractWithOre implements Listener {
    @EventHandler
    public void playerInteractEventwithOre(PlayerInteractEvent e) {



        if(e.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
            if(e.getPlayer().hasPermission("cosmicmining.minearea")) {
                Player p = e.getPlayer();
                Block b = e.getClickedBlock();
                Block blocksave = e.getClickedBlock();
                Random random = new Random();

                String origblock = b.getType().toString();
                int x = random.nextInt(3);
                if(b.getType()== Material.COAL_ORE || b.getType()==Material.IRON_ORE || b.getType()==Material.LAPIS_ORE || b.getType()==Material.REDSTONE_ORE || b.getType()==Material.GOLD_ORE || b.getType()==Material.DIAMOND_ORE || b.getType()==Material.EMERALD_ORE || b.getType()==Material.COAL_BLOCK || b.getType()==Material.IRON_BLOCK || b.getType()==Material.LAPIS_BLOCK || b.getType()==Material.REDSTONE_BLOCK || b.getType()==Material.GOLD_BLOCK || b.getType()==Material.DIAMOND_BLOCK || b.getType()==Material.EMERALD_BLOCK) {
                    String finalOrigblock = origblock;
                    String[] split = finalOrigblock.split("_", 0);
                    String block = split[0];
                    switch (block) {
                        case "COAL":
                            boolean canBreak = loadPlayerData.canBreak(p.getUniqueId(), 1);
                            if(canBreak) {
                                p.removePotionEffect(PotionEffectType.SLOW_DIGGING);
                            }
                            break;
                        case "IRON":
                            canBreak = loadPlayerData.canBreak(p.getUniqueId(), 2);
                            if(canBreak) {
                                p.removePotionEffect(PotionEffectType.SLOW_DIGGING);
                            }
                            break;
                        case "LAPIS":
                            canBreak = loadPlayerData.canBreak(p.getUniqueId(), 3);
                            if(canBreak) {
                                p.removePotionEffect(PotionEffectType.SLOW_DIGGING);
                            }
                            break;
                        case "REDSTONE":
                            canBreak = loadPlayerData.canBreak(p.getUniqueId(), 4);
                            if(canBreak) {
                                p.removePotionEffect(PotionEffectType.SLOW_DIGGING);
                            }
                            break;
                        case "GOLD":
                            canBreak = loadPlayerData.canBreak(p.getUniqueId(), 5);
                            if(canBreak) {
                                p.removePotionEffect(PotionEffectType.SLOW_DIGGING);
                            }
                            break;
                        case "DIAMOND":
                            canBreak = loadPlayerData.canBreak(p.getUniqueId(), 6);
                            if(canBreak) {
                                p.removePotionEffect(PotionEffectType.SLOW_DIGGING);
                            } else {
                                break;
                            }
                        case "EMERALD":
                            canBreak = loadPlayerData.canBreak(p.getUniqueId(), 7);
                            if(canBreak) {
                                p.removePotionEffect(PotionEffectType.SLOW_DIGGING);
                            }
                            break;
                        default:
                            System.out.println("Error");
                    }
                }
            }
        }
    }
}
