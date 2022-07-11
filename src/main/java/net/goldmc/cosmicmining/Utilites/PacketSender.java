package net.goldmc.cosmicmining.Utilites;

import com.avaje.ebeaninternal.server.cluster.Packet;
import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.util.Vector3i;
import com.github.retrooper.packetevents.wrapper.PacketWrapper;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerBlockBreakAnimation;
import net.goldmc.cosmicmining.CosmicMining;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class PacketSender {
    public static void sendBlockBreakPacket(Location Loc, Byte DestroyStage, Player player) {
        int id = Loc.getBlockX() - Loc.getBlockY() + Loc.getBlockZ();
        final WrapperPlayServerBlockBreakAnimation packet = new WrapperPlayServerBlockBreakAnimation(
                id,
                new Vector3i(Loc.getBlockX(), Loc.getBlockY(), Loc.getBlockZ()),
                DestroyStage
        );
        fixBlock(Loc, player);
        sendPacketAsyncSilent(player, packet);
    }

    public static void fixBlock(Location Loc, Player player) {
        int id = player.getEntityId();
        final WrapperPlayServerBlockBreakAnimation packet = new WrapperPlayServerBlockBreakAnimation(
                id,
                new Vector3i(Loc.getBlockX(), Loc.getBlockY(), Loc.getBlockZ()),
                (byte) -1
        );
        sendPacketAsyncSilent(player, packet);
    }

    public static void sendPacketAsyncSilent(Player player, PacketWrapper<?> packet) {
        Bukkit.getScheduler().runTaskAsynchronously(
                CosmicMining.getPlugin(CosmicMining.class),
                () -> PacketEvents.getAPI().getPlayerManager().sendPacketSilently(player, packet)
        );
    }

    public static void sendPacketAsync(Player player, PacketWrapper<?> packet) {
        Bukkit.getScheduler().runTaskAsynchronously(
                CosmicMining.getPlugin(CosmicMining.class),
                () -> PacketEvents.getAPI().getPlayerManager().sendPacket(player, packet)
        );
    }


}
