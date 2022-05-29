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

public class OnPlayerInteract implements Listener {
    @EventHandler
    public void playerInteractEvent(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        Block b = e.getClickedBlock();

        if(e.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
            if(p.hasPermission("cosmicmining.minearea")) {
                if(!(b.getType()== Material.COAL_ORE || b.getType()==Material.IRON_ORE || b.getType()==Material.LAPIS_ORE || b.getType()==Material.REDSTONE_ORE || b.getType()==Material.GLOWING_REDSTONE_ORE || b.getType()==Material.GOLD_ORE || b.getType()==Material.DIAMOND_ORE || b.getType()==Material.EMERALD_ORE || b.getType()==Material.COAL_BLOCK || b.getType()==Material.IRON_BLOCK || b.getType()==Material.LAPIS_BLOCK || b.getType()==Material.REDSTONE_BLOCK || b.getType()==Material.GOLD_BLOCK || b.getType()==Material.DIAMOND_BLOCK || b.getType()==Material.EMERALD_BLOCK)) {
                    p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 9999999,255, false, false), true);
                    e.setCancelled(true);
                }
            }
        }
    }


}
