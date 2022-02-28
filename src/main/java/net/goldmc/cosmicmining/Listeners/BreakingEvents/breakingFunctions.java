package net.goldmc.cosmicmining.Listeners.BreakingEvents;

import net.goldmc.cosmicmining.Database.loadPlayerData;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

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
    public void blockChecks(Player p,String finalOrigblock, Block b, int y, String[] split, int x, int blocklevel) {
        boolean canBreak = loadPlayerData.canBreak(p.getUniqueId(), blocklevel);
        ItemStack item;
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
    }
}
