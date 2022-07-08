package net.goldmc.cosmicmining.Utilites.MultiVersion.BlockBreakPackets;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public interface BlockBreakPacket {
    public void sendBlockBreakPacket(Location Location, Byte DestroyStage, Player player);
}
