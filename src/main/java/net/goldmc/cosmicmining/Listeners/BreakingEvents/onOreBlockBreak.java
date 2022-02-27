package net.goldmc.cosmicmining.Listeners.BreakingEvents;

import net.goldmc.cosmicmining.Database.loadPlayerData;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class onOreBlockBreak implements Listener {
    @EventHandler
    public void playerOreBlockBreakEvent(BlockBreakEvent e) throws InterruptedException {
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
                        boolean canBreak = loadPlayerData.canBreak(p.getUniqueId(), 1);
                        ItemStack item;
                        if(canBreak) {
                            item = new ItemStack(Material.getMaterial(finalOrigblock));
                            item.setAmount(x);
                            p.getInventory().addItem(item);
                            b.setType(Material.STONE);

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
                        } else {
                            p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 9999999,255, false, false), true);
                            b.setType(Material.getMaterial(finalOrigblock));
                        }
                        break;
                    case "IRON":
                        canBreak = loadPlayerData.canBreak(p.getUniqueId(), 2);
                        if(canBreak) {
                            item = new ItemStack(Material.getMaterial(finalOrigblock));
                            item.setAmount(x);
                            p.getInventory().addItem(item);
                            b.setType(Material.STONE);

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
                        } else {
                            p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 9999999,255, false, false), true);
                            b.setType(Material.getMaterial(finalOrigblock));
                        }
                        break;
                    case "LAPIS":
                        canBreak = loadPlayerData.canBreak(p.getUniqueId(), 3);
                        if(canBreak) {
                            item = new ItemStack(Material.getMaterial(finalOrigblock));
                            item.setAmount(x);
                            p.getInventory().addItem(item);
                            b.setType(Material.STONE);

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
                        } else {
                            p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 9999999,255, false, false), true);
                            b.setType(Material.getMaterial(finalOrigblock));
                        }
                        break;
                    case "REDSTONE":
                        canBreak = loadPlayerData.canBreak(p.getUniqueId(), 4);
                        if(canBreak) {
                            e.setCancelled(true);
                            item = new ItemStack(Material.REDSTONE_ORE);
                            item.setAmount(x);
                            p.getInventory().addItem(item);
                            b.setType(Material.STONE);
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    if(y!=5) {
                                        b.setType(Material.REDSTONE_ORE);
                                    } else {
                                        b.setType(Material.REDSTONE_BLOCK);
                                    }
                                }
                            }.runTaskLater(Bukkit.getPluginManager().getPlugin("CosmicMining"), 40);
                            e.setCancelled(true);
                        } else {
                            p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 9999999,255, false, false), true);
                            b.setType(Material.getMaterial(finalOrigblock));
                            e.setCancelled(true);
                        }
                        break;
                    case "GOLD":
                        canBreak = loadPlayerData.canBreak(p.getUniqueId(), 5);
                        if(canBreak) {
                            item = new ItemStack(Material.getMaterial(finalOrigblock));
                            item.setAmount(x);
                            p.getInventory().addItem(item);
                            b.setType(Material.STONE);

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
                        } else {
                            p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 9999999,255, false, false), true);
                            b.setType(Material.getMaterial(finalOrigblock));
                        }
                        break;
                    case "DIAMOND":
                        canBreak = loadPlayerData.canBreak(p.getUniqueId(), 6);
                        if(canBreak) {
                            item = new ItemStack(Material.getMaterial(finalOrigblock));
                            item.setAmount(x);
                            p.getInventory().addItem(item);
                            b.setType(Material.STONE);

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
                        } else {
                            p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 9999999,255, false, false), true);
                            b.setType(Material.getMaterial(finalOrigblock));
                        }
                        break;
                    case "EMERALD":
                        canBreak = loadPlayerData.canBreak(p.getUniqueId(), 7);
                        if(canBreak) {
                            item = new ItemStack(Material.getMaterial(finalOrigblock));
                            item.setAmount(x);
                            p.getInventory().addItem(item);
                            b.setType(Material.STONE);

                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    if(y!=5) {
                                        b.setType(Material.getMaterial(split[0] + "_ORE"));
                                    } else {
                                        p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 9999999,255, false, false), true);
                                        b.setType(Material.getMaterial(split[0] + "_BLOCK"));
                                    }
                                }
                            }.runTaskLater(Bukkit.getPluginManager().getPlugin("CosmicMining"), 40);
                            p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 9999999,255, false, false), true);

                        } else {
                            b.setType(Material.getMaterial(finalOrigblock));
                        }
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
