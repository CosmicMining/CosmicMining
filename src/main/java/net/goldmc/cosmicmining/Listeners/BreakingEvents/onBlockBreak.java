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
                        boolean canBreak = loadPlayerData.canBreak(p.getUniqueId(), 1);
                        ItemStack item = new ItemStack(Material.COAL);
                        if(canBreak) {
                            item = new ItemStack(Material.COAL);
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
                            item = new ItemStack(Material.IRON_INGOT);
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
                            Dye l = new Dye();
                            l.setColor(DyeColor.BLUE);
                            item = l.toItemStack(x);
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
                        e.setCancelled(true);
                        canBreak = loadPlayerData.canBreak(p.getUniqueId(), 4);
                        if(canBreak) {
                            e.setCancelled(true);
                            item = new ItemStack(Material.REDSTONE);
                            item.setAmount(x);
                            p.getInventory().addItem(item);
                            b.setType(Material.STONE);
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    if(y!=5) {
                                        b.setType(Material.REDSTONE_ORE);
                                        e.setCancelled(true);
                                    } else {
                                        b.setType(Material.REDSTONE_BLOCK);
                                        e.setCancelled(true);
                                    }
                                }
                            }.runTaskLater(Bukkit.getPluginManager().getPlugin("CosmicMining"), 40);
                        } else {
                            p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 9999999,255, false, false), true);
                            b.setType(Material.getMaterial(finalOrigblock));
                            e.setCancelled(true);
                        }
                        break;
                    case "GOLD":
                        canBreak = loadPlayerData.canBreak(p.getUniqueId(), 5);
                        if(canBreak) {
                            item = new ItemStack(Material.GOLD_INGOT);
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
                            item = new ItemStack(Material.DIAMOND);
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
                            item = new ItemStack(Material.EMERALD);
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
                        System.out.println("NOT FOUND!");
                }

                p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 9999999,255, false, false), true);
                e.setCancelled(true);
            }
        }
    }





}
