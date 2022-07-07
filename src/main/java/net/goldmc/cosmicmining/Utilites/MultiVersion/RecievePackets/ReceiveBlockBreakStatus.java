package net.goldmc.cosmicmining.Utilites.MultiVersion.RecievePackets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.EnumWrappers;
import net.goldmc.cosmicmining.CosmicMining;
import org.bukkit.Bukkit;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ReceiveBlockBreakStatus {
    public ReceiveBlockBreakStatus(CosmicMining plugin) {
        ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
        PacketAdapter packet = new PacketAdapter(plugin, ListenerPriority.HIGHEST, PacketType.Play.Client.BLOCK_DIG) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                PacketContainer packet = event.getPacket();
                EnumWrappers.PlayerDigType Status = packet.getPlayerDigTypes().read(0);
                if(Status == EnumWrappers.PlayerDigType.ABORT_DESTROY_BLOCK || Status == EnumWrappers.PlayerDigType.STOP_DESTROY_BLOCK) {
                    //run task synchronously
                    Bukkit.getScheduler().runTask(plugin, () -> {
                        event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 9999999,255, false, false), true);
                    });
                }
            }
        };
        protocolManager.addPacketListener(packet);
    }
}
