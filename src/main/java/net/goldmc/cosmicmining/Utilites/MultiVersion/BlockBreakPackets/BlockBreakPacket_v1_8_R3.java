package net.goldmc.cosmicmining.Utilites.MultiVersion.BlockBreakPackets;

import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.PacketPlayOutBlockBreakAnimation;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class BlockBreakPacket_v1_8_R3 implements BlockBreakPacket {
    @Override
    public void sendBlockBreakPacket(int EntityID, Location Loc, Byte DestroyStage, Player player) {
        String entityID = "73634" + EntityID;
        EntityID = Integer.parseInt(entityID);
        BlockPosition blockPosition = new BlockPosition(Loc.getBlockX(), Loc.getBlockY(), Loc.getBlockZ());
        PacketPlayOutBlockBreakAnimation packet = new PacketPlayOutBlockBreakAnimation(EntityID, blockPosition, DestroyStage);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
    }
}
