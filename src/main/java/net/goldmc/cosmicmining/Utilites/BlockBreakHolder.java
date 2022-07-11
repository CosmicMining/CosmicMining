package net.goldmc.cosmicmining.Utilites;

import net.goldmc.cosmicmining.CosmicMining;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDamageEvent;

import java.util.HashMap;
import java.util.UUID;

public class BlockBreakHolder implements Listener {
    private UUID uuid = null;
    private static CosmicMining plugin;
    private final HashMap<Location, Integer> blockBreakList = new HashMap<>();

    public BlockBreakHolder(CosmicMining pl) {
        plugin = pl;
    }
    public BlockBreakHolder(UUID u) {
        uuid = u;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }
    public HashMap<Location, Integer> getBlocks() {
        return blockBreakList;
    }

    public int getBlock(Location loc) {
        return blockBreakList.get(loc);
    }
    public void addBlock(Player player, int damageID, int x, int y, int z) {
        Location loc = new Location(player.getWorld(), x, y, z);
        PacketSender.sendBlockBreakPacket(loc, (byte) damageID, player);
        blockBreakList.put(loc, damageID);
    }

    public void setBlock(Player player, int damageID, int x, int y, int z) {
        Location loc = new Location(player.getWorld(), x, y, z);
        PacketSender.sendBlockBreakPacket(loc, (byte) damageID, player);
        blockBreakList.replace(loc, damageID);
    }

    public void removeBlock(Player player, int id, int data, int x, int y, int z) {
        Location loc = new Location(Bukkit.getPlayer(uuid).getWorld(), x, y, z);
        blockBreakList.remove(loc);
    }

    public void removeAllBlocks() {
        blockBreakList.clear();
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockBreak(BlockDamageEvent event) {
        if(event.getPlayer().getUniqueId() == uuid) {
            Block b = event.getBlock();
            if(blockBreakList.containsKey(event.getBlock().getLocation())) {
                setBlock(event.getPlayer(), getBlock(b.getLocation()), b.getX(), b.getY(), b.getZ());
                event.setCancelled(true);
                PacketSender.sendBlockBreakPacket(b.getLocation(), (byte) getBlock(b.getLocation()), event.getPlayer());
            }
        }
    }
}
