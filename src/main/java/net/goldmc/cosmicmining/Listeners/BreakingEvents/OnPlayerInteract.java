package net.goldmc.cosmicmining.Listeners.BreakingEvents;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.stream.Stream;

public class OnPlayerInteract implements Listener {
    @EventHandler
    public void playerInteractEvent(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        Block b = e.getClickedBlock();

        if(e.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
            if(p.hasPermission("cosmicmining.minearea.mine")) {
                if(Stream.of(Material.COAL_ORE, Material.IRON_ORE, Material.LAPIS_ORE, Material.REDSTONE_ORE, Material.GLOWING_REDSTONE_ORE, Material.GOLD_ORE, Material.DIAMOND_ORE, Material.EMERALD_ORE, Material.COAL_BLOCK, Material.IRON_BLOCK, Material.LAPIS_BLOCK, Material.REDSTONE_BLOCK, Material.GOLD_BLOCK, Material.DIAMOND_BLOCK, Material.EMERALD_BLOCK).noneMatch(material -> b.getType() == material)) {
                    p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 9999999,255, false, false), true);
                    e.setCancelled(true);
                }
            }
        }
    }


}
