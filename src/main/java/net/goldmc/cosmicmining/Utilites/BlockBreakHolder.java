package net.goldmc.cosmicmining.Utilites;

import net.goldmc.cosmicmining.Utilites.MultiVersion.BlockBreakPackets.BlockBreakPacket;
import net.goldmc.cosmicmining.Utilites.MultiVersion.BlockBreakPackets.BlockBreakPacket_v1_8_R3;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

public class BlockBreakHolder {
    private final UUID uuid;
    private final HashMap<Location, Integer> blockBreakList = new HashMap<>();
    BlockBreakPacket blockBreakPacket;

    public BlockBreakHolder(UUID u) {
        uuid = u;
        if(Bukkit.getServer().getClass().getName().contains("v1_8_R3")) {
            blockBreakPacket = new BlockBreakPacket_v1_8_R3();
        }
    }
    public HashMap<Location, Integer> getBlocks() {
        return blockBreakList;
    }

    public int getBlock(Location loc) {
        return blockBreakList.get(loc);
    }
    public void addBlock(Player player, int damageID, int x, int y, int z) {
        Location loc = new Location(player.getWorld(), x, y, z);
        Random randI = new Random();
        int myRandInt = randI.nextInt(100000);
        blockBreakPacket.sendBlockBreakPacket(myRandInt, loc, (byte) damageID, player);
        blockBreakList.put(loc, damageID);
    }

    public void setBlock(Player player, int damageID, int x, int y, int z) {
        Location loc = new Location(player.getWorld(), x, y, z);
        blockBreakList.replace(loc, damageID);
    }

    public void removeBlock(Player player, int id, int data, int x, int y, int z) {
        Location loc = new Location(Bukkit.getPlayer(uuid).getWorld(), x, y, z);
        blockBreakList.remove(loc);
    }

    public void removeAllBlocks() {
        blockBreakList.clear();
    }
}
