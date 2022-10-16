package net.goldmc.cosmicmining.Listeners.BreakingEvents;

import net.goldmc.cosmicmining.Utilites.PlayerData;
import net.goldmc.cosmicmining.Leveling.XpFunctions;
import net.goldmc.cosmicmining.Utilites.Scoreboards;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Dye;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;


public class BreakingFunctions {
    private final HashMap<String, Integer> blocks = OnOreBlockBreak.getOreMap();
    private final Integer blocklevel;
    private final Block b;
    private final Player p;
    private boolean isOreBlock;
    private final String finalOrigblock;
    private final int y = ThreadLocalRandom.current().nextInt(0, 10);
    private final int x = ThreadLocalRandom.current().nextInt(1, 3);
    private final XpFunctions xp;

    public BreakingFunctions(int blocklevel, Block b, Player player, boolean isOreBlock, String finalOrigblock) {
        this.blocklevel = blocklevel;
        this.b = b;
        this.p = player;
        this.isOreBlock = isOreBlock;
        this.finalOrigblock = finalOrigblock;
        xp = new XpFunctions(p.getUniqueId());
    }

    public BreakingFunctions(int blocklevel, Block b, Player player, String finalOrigblock) {
        this.blocklevel = blocklevel;
        this.b = b;
        this.p = player;
        this.finalOrigblock = finalOrigblock;
        xp = new XpFunctions(p.getUniqueId());
    }


    public void startRunnable() {
        new BukkitRunnable() {
            @Override
            public void run() {
                if(y!=5) {
                    b.setType(Material.getMaterial(PlayerData.getBlockType(b) + "_ORE"));
                } else {
                    b.setType(Material.getMaterial(PlayerData.getBlockType(b) + "_BLOCK"));
                }
            }
        }.runTaskLater(Bukkit.getPluginManager().getPlugin("CosmicMining"), 40);
    }
    public void blockChecks() {
        PlayerData playerData = new PlayerData(p.getUniqueId());

        String[] split = finalOrigblock.split("_");
        boolean canBreak = playerData.canBreakBlock(blocklevel);

        if (!canBreak) {
            b.setType(Material.getMaterial(finalOrigblock));
            return;
        }

        if(isOreBlock) {
            oreBlock();
            return;
        }

        ItemStack item;
        b.setType(Material.STONE);
        if(Objects.equals(split[0], "GLOWING")) {
            item = new ItemStack(Material.REDSTONE_ORE);
        } else {
            item = new ItemStack(Material.getMaterial(finalOrigblock));
        }
        item.setAmount(x);
        p.getInventory().addItem(item);
        xp.giveXpForOre(p.getUniqueId(), split[0]);
        startRunnable();
        new Scoreboards(p.getUniqueId()).prisonsMiningScoreboard();
    }

    private void oreBlock() {
        for(Map.Entry<String , Integer> entry: blocks.entrySet()) {
            // if you give value is equal to value from entry
            // print the corresponding key
            if(Objects.equals(entry.getValue(), blocklevel)) {
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
                } else {
                    ItemStack item;
                    Dye l = new Dye();
                    l.setColor(DyeColor.BLUE);
                    item = l.toItemStack(x);
                    item.setAmount(x);
                    p.getInventory().addItem(item);
                }
                b.setType(Material.STONE);
                xp.giveXpForOre(p.getUniqueId(), PlayerData.getBlockType(b));
                startRunnable();
                new Scoreboards(p.getUniqueId()).prisonsMiningScoreboard();
                break;
            }
        }
    }

    public void setOreBlock(boolean oreBlock) {
        isOreBlock = oreBlock;
    }
}
