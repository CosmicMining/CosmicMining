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
import net.goldmc.cosmicmining.Utilites.MultiVersion.ClientSideEffects.ClientSideEffects;
import net.goldmc.cosmicmining.Utilites.MultiVersion.ClientSideEffects.ClientSideEffects_v1_8_R3;
import org.bukkit.Bukkit;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.lang.reflect.Field;

public class ReceiveRemoveEntityEffect {
    public ReceiveRemoveEntityEffect(CosmicMining plugin) {
        ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
        PacketAdapter packet = new PacketAdapter(plugin, ListenerPriority.HIGHEST, PacketType.Play.Server.REMOVE_ENTITY_EFFECT) {
            @Override
            public void onPacketSending(PacketEvent event) {
                PacketContainer packet = event.getPacket();
                //run task synchronously
                int effectid = packet.getIntegers().read(1);
                if(effectid == 4) {
                    event.setCancelled(true);
                    //send packet to client
                    System.out.println("[CosmicMining] Removing effect 4");

                }
            }
        };
        protocolManager.addPacketListener(packet);
    }
}
*/