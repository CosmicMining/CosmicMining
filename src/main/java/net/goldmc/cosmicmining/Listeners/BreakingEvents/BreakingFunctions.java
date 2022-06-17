package net.goldmc.cosmicmining.Listeners.BreakingEvents;

import net.goldmc.cosmicmining.Database.LoadPlayerData;
import net.goldmc.cosmicmining.Leveling.XpFunctions;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Dye;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;


public class BreakingFunctions {


    public void startRunnable(int y, Block b, String[] split) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if(y!=5) {
                    if(Objects.equals(split[0], "GLOWING")) {
                        b.setType(Material.getMaterial(split[1] + "_ORE"));
                    } else {
                        b.setType(Material.getMaterial(split[0] + "_ORE"));
                    }
                } else {
                    if(Objects.equals(split[0], "GLOWING")) {
                        b.setType(Material.getMaterial(split[1] + "_BLOCK"));
                    } else {
                        b.setType(Material.getMaterial(split[0] + "_BLOCK"));
                    }
                }
            }
        }.runTaskLater(Bukkit.getPluginManager().getPlugin("CosmicMining"), 40);
    }
    public void blockChecks(Player p, String finalOrigblock, Block b, int blocklevel, boolean isoreblock) {
        LoadPlayerData loadPlayerData = new LoadPlayerData();
        XpFunctions xpFunctions = new XpFunctions();
        Map<String, Integer> hm
                = new HashMap<String, Integer>();
        hm.put("COAL", 1);
        hm.put("IRON", 2);
        hm.put("LAPIS", 3);
        hm.put("REDSTONE", 4);
        hm.put("GLOWING", 4);
        hm.put("GOLD", 5);
        hm.put("DIAMOND", 6);
        hm.put("EMERALD", 7);
        int y = ThreadLocalRandom.current().nextInt(0, 10);
        int x = ThreadLocalRandom.current().nextInt(1, 3);
        String[] split = finalOrigblock.split("_", 0);
        boolean canBreak = loadPlayerData.canBreak(p.getUniqueId(), blocklevel);
        if(canBreak) {
            if(isoreblock) {
                for(Map.Entry<String , Integer> entry: hm.entrySet()) {
                    // if give value is equal to value from entry
                    // print the corresponding key
                    if(entry.getValue() == blocklevel) {
                        if(blocklevel != 3) {
                            if(!(Objects.equals(entry.getKey(), "GOLD") || Objects.equals(entry.getKey(), "IRON"))) {
                                ItemStack item;
                                item = new ItemStack(Material.getMaterial(entry.getKey()));
                                item.setAmount(x);
                                p.getInventory().addItem(item);
                            } else {
                                ItemStack item;
                                item = new ItemStack(Material.getMaterial(entry.getKey() + "_INGOT"));
                                item.setAmount(x);
                                p.getInventory().addItem(item);
                            }
                            b.setType(Material.STONE);
                            xpFunctions.giveXpForOre(p.getUniqueId(), split[0]);
                            startRunnable(y, b, split);
                            break;
                        } else {
                            ItemStack item;
                            Dye l = new Dye();
                            l.setColor(DyeColor.BLUE);
                            item = l.toItemStack(x);
                            item.setAmount(x);
                            p.getInventory().addItem(item);
                            b.setType(Material.STONE);
                            xpFunctions.giveXpForOre(p.getUniqueId(), split[0]);
                            startRunnable(y, b, split);
                            break;
                        }
                    }
                }
            } else {
                ItemStack item;
                b.setType(Material.STONE);
                if(Objects.equals(split[0], "GLOWING")) {
                    item = new ItemStack(Material.REDSTONE_ORE);
                } else {
                    item = new ItemStack(Material.getMaterial(finalOrigblock));
                }
                item.setAmount(x);
                p.getInventory().addItem(item);
                xpFunctions.giveXpForOre(p.getUniqueId(), split[0]);
                startRunnable(y, b, split);
            }
        } else {
            p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 9999999,255, false, false), true);
            b.setType(Material.getMaterial(finalOrigblock));
        }
    }
}
