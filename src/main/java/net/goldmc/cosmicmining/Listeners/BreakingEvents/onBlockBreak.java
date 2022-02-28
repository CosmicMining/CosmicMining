package net.goldmc.cosmicmining.Listeners.BreakingEvents;

import net.goldmc.cosmicmining.Database.loadPlayerData;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Dye;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import sun.security.krb5.internal.KdcErrException;

import java.util.Random;

public class onBlockBreak implements Listener {
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
                if(x == 0) {x=1;}
                switch (block) {
                    case "COAL":
                        runnable.blockChecks(p, finalOrigblock, b, y, split, x, 1, true);
                        break;
                    case "IRON":
                        runnable.blockChecks(p, finalOrigblock, b, y, split, x, 2, true);
                        break;
                    case "LAPIS":
                        runnable.blockChecks(p, finalOrigblock, b, y, split, x, 3, true);
                        break;
                    case "REDSTONE":
                        runnable.blockChecks(p, finalOrigblock, b, y, split, x, 4, true);
                    case "GOLD":
                        runnable.blockChecks(p, finalOrigblock, b, y, split, x, 5, true);
                        break;
                    case "DIAMOND":
                        runnable.blockChecks(p, finalOrigblock, b, y, split, x, 6, true);
                        break;
                    case "EMERALD":
                        runnable.blockChecks(p, finalOrigblock, b, y, split, x, 7, true);
                        break;
                    default:
                        System.out.println("NOT FOUND!");
                }

                p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 9999999,255, false, false), true);
                e.setCancelled(true);
            }
        }
    }





}
