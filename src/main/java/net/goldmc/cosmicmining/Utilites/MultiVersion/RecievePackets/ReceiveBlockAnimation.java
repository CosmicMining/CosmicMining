/*
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

public class ReceiveBlockAnimation {
    public ReceiveBlockAnimation(CosmicMining plugin) {
        ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
        PacketAdapter packet = new PacketAdapter(plugin, ListenerPriority.HIGHEST, PacketType.Play.Server.BLOCK_BREAK_ANIMATION) {
            @Override
            public void onPacketSending(PacketEvent event) {
                PacketContainer packet = event.getPacket();
                int destroyStage = packet.getIntegers().read(1);
                if(destroyStage == 0) {
                    event.setCancelled(true);
                    event.setPacket(null);
                }
            }
        };
        protocolManager.addPacketListener(packet);
    }
}
 */