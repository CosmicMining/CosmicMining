package net.goldmc.cosmicmining.BlockBreak.utls;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Utls {

    public static void addSlowDig(Player player, int duration){
        if(player.hasPotionEffect(PotionEffectType.SLOW_DIGGING)) removeSlowDig(player);
        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, duration, -1, false, false), true);
    }

    public static void removeSlowDig(Player player){
        player.removePotionEffect(PotionEffectType.SLOW_DIGGING);
    }
}
