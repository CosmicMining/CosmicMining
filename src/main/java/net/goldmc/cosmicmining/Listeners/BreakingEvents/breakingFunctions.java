package net.goldmc.cosmicmining.Listeners.BreakingEvents;

import net.goldmc.cosmicmining.Database.loadPlayerData;
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

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;

public class breakingFunctions {
    public void startRunnable(int y, Block b, String[] split) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if(y!=5) {
                    b.setType(Material.getMaterial(split[0] + "_ORE"));
                } else {
                    b.setType(Material.getMaterial(split[0] + "_BLOCK"));
                }
            }
        }.runTaskLater(Bukkit.getPluginManager().getPlugin("CosmicMining"), 40);
    }
    public void blockChecks(Player p,String finalOrigblock, Block b, int y, String[] split, int x, int blocklevel, boolean isoreblock) {
        Map<Integer, String> hm
                = new HashMap<Integer, String>();
        hm.put(1, "COAL");
        hm.put(2, "IRON_INGOT");
        hm.put(3, "DYE");
        hm.put(4, "REDSTONE");
        hm.put(5, "GOLD_INGOT");
        hm.put(6, "DIAMOND");
        hm.put(7, "EMERALD");
        boolean canBreak = loadPlayerData.canBreak(p.getUniqueId(), blocklevel);
        ItemStack item;
        if(!(isoreblock)) {
            if(canBreak) {
                item = new ItemStack(Material.getMaterial(finalOrigblock));
                item.setAmount(x);
                p.getInventory().addItem(item);
                b.setType(Material.STONE);
                startRunnable(y, b, split);
            } else {
                p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 9999999,255, false, false), true);
                b.setType(Material.getMaterial(finalOrigblock));
            }
        } else {
            if(canBreak) {
                for(Map.Entry<Integer, String> entry: hm.entrySet()) {
                    // if give value is equal to value from entry
                    // print the corresponding key
                    if(entry.getKey() == blocklevel) {
                        if(blocklevel != 3) {
                            item = new ItemStack(Material.getMaterial(entry.getValue()));
                            item.setAmount(x);
                            p.getInventory().addItem(item);
                            b.setType(Material.STONE);
                            startRunnable(y, b, split);
                            break;
                        } else {
                            Dye l = new Dye();
                            l.setColor(DyeColor.BLUE);
                            item = l.toItemStack(x);
                            item.setAmount(x);
                            p.getInventory().addItem(item);
                            b.setType(Material.STONE);
                        }
                    }
                }
            } else {
                p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 9999999,255, false, false), true);
                b.setType(Material.getMaterial(finalOrigblock));
            }
        }
    }
}
