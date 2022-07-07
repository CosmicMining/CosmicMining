package net.goldmc.cosmicmining.Utilites.MultiVersion.ClientSideEffects;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public interface ClientSideEffects {
    public void sendClientSideEffect(Player player, PotionEffectType potionEffect, int amplifier, int duration);
}
