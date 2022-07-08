package net.goldmc.cosmicmining.Utilites.MultiVersion.BlockBreakPackets;

import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.PacketPlayOutBlockBreakAnimation;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.Random;
import java.util.stream.IntStream;

public class BlockBreakPacket_v1_8_R3 implements BlockBreakPacket {
    @Override
    public void sendBlockBreakPacket(Location Loc, Byte DestroyStage, Player player) {
        int id = Loc.getBlockX() - Loc.getBlockY() + Loc.getBlockZ();
        BlockPosition blockPosition = new BlockPosition(Loc.getBlockX(), Loc.getBlockY(), Loc.getBlockZ());
        PacketPlayOutBlockBreakAnimation fixpacket = new PacketPlayOutBlockBreakAnimation(id, blockPosition, (byte) -1);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(fixpacket);
        PacketPlayOutBlockBreakAnimation packet = new PacketPlayOutBlockBreakAnimation(id, blockPosition, DestroyStage);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
    }
}
