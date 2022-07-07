package net.goldmc.cosmicmining.Utilites.MultiVersion.ClientSideEffects;

import net.minecraft.server.v1_8_R3.MobEffect;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityEffect;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ClientSideEffects_v1_8_R3 implements ClientSideEffects {
    @Override
    public void sendClientSideEffect(Player player, PotionEffectType potionEffect, int amplifier, int duration) {
        MobEffect mobEffect = new MobEffect(potionEffect.getId(), duration, amplifier, true, false);
        PacketPlayOutEntityEffect packet = new PacketPlayOutEntityEffect(player.getEntityId(), mobEffect);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
    }
}
