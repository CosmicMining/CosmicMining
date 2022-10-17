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
import java.util.concurrent.ThreadLocalRandom;


public class BreakingFunctions {
    private final HashMap<String, Integer> blocks = OnOreBlockBreak.getOreMap();
    private final Integer blocklevel;
    private final Block b;
    private final Player p;
    private boolean isOreBlock;
    private final String originalBlockType;
    private final int y = ThreadLocalRandom.current().nextInt(0, 10);
    private final int x = ThreadLocalRandom.current().nextInt(1, 3);
    private final XpFunctions xp;
    private final Material blockMaterial;

    public BreakingFunctions(int blockLevel, Block b, Player player, boolean isOreBlock, String theBlock) {
        this.blocklevel = blockLevel;
        this.b = b;
        this.p = player;
        this.isOreBlock = isOreBlock;
        this.originalBlockType = theBlock;
        this.blockMaterial = b.getType();
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
    /**
     * Checks if the block is a mineable block then runs all the functions to turn it into stone then back to the ore
     */
    public void runMiningBlock() {
        PlayerData playerData = new PlayerData(p.getUniqueId());
        boolean canBreak = playerData.canBreakBlock(blocklevel);

        //Check if the user can break the block
        if (!canBreak) {
            b.setType(Material.getMaterial(originalBlockType));
            return;
        }


        //If it is an ore block then run the oreblock function
        if(isOreBlock) {
            oreBlock();
        } else {
            //If it is an ore then run the ore function
            ore();
        }
    }

    private void ore() {
        ItemStack item;
        b.setType(Material.STONE);
        if(blockMaterial.toString().contains("GLOWING")) {
            item = new ItemStack(Material.REDSTONE_ORE);
        } else {
            item = new ItemStack(Material.getMaterial(originalBlockType));
        }
        item.setAmount(x);
        p.getInventory().addItem(item);
    }

    private void oreBlock() {
        //Get the blocks break level
        Integer blockLevel = PlayerData.canBreak(p, b);

        //If the block level is null then return, it is not a minable block
        if (blockLevel == null) {
            b.setType(Material.getMaterial(originalBlockType));
            return;
        }

        //Check for lapis blocks since lapis is a dye
        if (b.getType() == Material.LAPIS_BLOCK) {
            ItemStack item;
            Dye l = new Dye();
            l.setColor(DyeColor.BLUE);
            item = l.toItemStack(x);
            item.setAmount(x);
            p.getInventory().addItem(item);
            setStoneGiveXp();
            return;
        }

        //Check for ingots since they are not just the name
        if (b.getType().toString().contains("GOLD") || b.getType().toString().contains("IRON")) {
            ItemStack item;
            item = new ItemStack(Material.getMaterial(PlayerData.getBlockType(b) + "_INGOT"));
            item.setAmount(x);
            p.getInventory().addItem(item);
        } else {
            //If it is not a lapis block or an ingot then just give the name
            ItemStack item;
            item = new ItemStack(Material.getMaterial(PlayerData.getBlockType(b)));
            item.setAmount(x);
            p.getInventory().addItem(item);
        }


        setStoneGiveXp();
    }

    public void setOreBlock(boolean oreBlock) {
        isOreBlock = oreBlock;
    }

    /**
     * Sets the block to stone and gives the player xp
     * This will start a runnable which will turn the block back to stone in the config's set amount of seconds
     */
    private void setStoneGiveXp() {
        b.setType(Material.STONE);
        xp.giveXpForOre(p.getUniqueId(), PlayerData.getStringBlockType(originalBlockType));
        startRunnable();
        new Scoreboards(p.getUniqueId()).prisonsMiningScoreboard();
    }
}
