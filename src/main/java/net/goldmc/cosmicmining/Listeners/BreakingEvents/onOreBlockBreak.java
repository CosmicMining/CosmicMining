package net.goldmc.cosmicmining.Listeners.BreakingEvents;

import net.goldmc.cosmicmining.Database.loadPlayerData;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

public class onOreBlockBreak implements Listener {
    @EventHandler
    public void playerOreBlockBreakEvent(BlockBreakEvent e)  {
        breakingFunctions runnable = new breakingFunctions();
        Player p = e.getPlayer();
        Block b = e.getBlock();
        Block blocksave = e.getBlock();
        Random random = new Random();
        int y = random.nextInt(10);
        int x = random.nextInt(3);
        String origblock = b.getType().toString();

        if(p.hasPermission("cosmicmining.minearea")) {
            if(b.getType()== Material.COAL_ORE || b.getType()==Material.IRON_ORE || b.getType()==Material.LAPIS_ORE ||  b.getType()==Material.REDSTONE_ORE || b.getType()==Material.GOLD_ORE || b.getType()==Material.DIAMOND_ORE || b.getType()==Material.EMERALD_ORE) {
                String finalOrigblock = origblock;
                String[] split = finalOrigblock.split("_", 0);
                String block = split[0];
                if(x == 0) {x=1;}

                switch (block) {
                    case "COAL":
                        runnable.blockChecks(p, finalOrigblock, b, y, split, x, 1);
                        break;
                    case "IRON":
                        runnable.blockChecks(p, finalOrigblock, b, y, split, x, 2);
                        break;
                    case "LAPIS":
                        runnable.blockChecks(p, finalOrigblock, b, y, split, x, 3);
                        break;
                    case "REDSTONE":
                        runnable.blockChecks(p, finalOrigblock, b, y, split, x, 4);
                    case "GOLD":
                        runnable.blockChecks(p, finalOrigblock, b, y, split, x, 5);
                        break;
                    case "DIAMOND":
                        runnable.blockChecks(p, finalOrigblock, b, y, split, x, 6);
                        break;
                    case "EMERALD":
                        runnable.blockChecks(p, finalOrigblock, b, y, split, x, 7);
                        break;
                    default:
                        e.setCancelled(true);
                        System.out.println("NOT FOUND!");
                }

                p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 9999999,255, false, false), true);
                e.setCancelled(true);
            }
        }
    }

}
