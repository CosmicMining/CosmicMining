package net.goldmc.cosmicmining.Leveling.XpBoosters;

import de.tr7zw.changeme.nbtapi.NBTItem;
import net.goldmc.cosmicmining.Config.Config;
import net.goldmc.cosmicmining.Utilites.PlayerData;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class XpBoosterRightClick implements Listener {
    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {
        if(!(event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK))) {
            return;
        }

        if(!event.getPlayer().getItemInHand().getType().equals(Material.EMERALD)) {
            return;
        }

        event.setCancelled(true);
        ItemStack xpBoosterItemStack = event.getPlayer().getItemInHand();
        NBTItem itemNBTData = new NBTItem(xpBoosterItemStack);
        if(!itemNBTData.getBoolean("xpBooster")) {
            return;
        }

        if(itemNBTData.getInteger("duration") <= 0) {
            return;
        }

        if(itemNBTData.getString("uuid") == null) {
            return;
        }

        if(Config.getXpBoosters().contains(event.getPlayer().getUniqueId().toString())) {
           return;
        }

        if(xpBoosterItemStack.getAmount() != 1) {
            event.getPlayer().sendMessage(ChatColor.GREEN + "You have used an Xp Booster");
            event.getPlayer().sendMessage( ChatColor.RED + "" + ChatColor.BOLD + "You had an illegally stacked booster that was removed from your inventory, the booster has still been added. This incident has been reported to the admins");
        } else {
            event.getPlayer().sendMessage(ChatColor.GREEN + "You have used an Xp Booster");
        }
        
        new PlayerData().registerXpBooster(event.getPlayer(), itemNBTData.getDouble("multiplier"), itemNBTData.getInteger("duration"));
        event.getPlayer().setItemInHand(new ItemStack(Material.AIR));
    }
}
