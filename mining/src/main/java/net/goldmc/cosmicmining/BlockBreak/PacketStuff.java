package net.goldmc.cosmicmining.BlockBreak;

import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.PacketPlayOutBlockBreakAnimation;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.util.CraftMagicNumbers;
import org.bukkit.entity.Player;

public class PacketStuff {
    public void sendBreakPacket(int animation, Block block) {
        ((CraftServer) Bukkit.getServer()).getHandle().sendPacketNearby(null, block.getX(), block.getY(), block.getZ(), 120,
                ((CraftWorld) block.getWorld()).getHandle().dimension, new PacketPlayOutBlockBreakAnimation(getBlockEntityId(block), getBlockPosition(block), animation));
    }

    public void sendBreakBlock(Player player, Block block) {
        ((CraftPlayer) player).getHandle().playerInteractManager.breakBlock(getBlockPosition(block));
    }

    public void playBlockSound(Block block) {
        String soundName = CraftMagicNumbers.getBlock(block).stepSound.getBreakSound();
        soundName = soundName.toUpperCase().replaceAll("\\.", "_");

        block.getWorld().playSound(block.getLocation(), Sound.valueOf(soundName), 1, 1);
    }

    private BlockPosition getBlockPosition(Block block){
        return new BlockPosition(block.getX(), block.getY(), block.getZ());
    }

    private int getBlockEntityId(Block block){
        return ((block.getX() & 0xFFF) << 20 | (block.getZ() & 0xFFF) << 8) | (block.getY() & 0xFF);
    }
}
