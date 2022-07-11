package net.goldmc.cosmicmining.Leveling.XpBoosters;

import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class XpBooster {
    private final double multiplier;
    private final int minutes;
    public XpBooster(double multiplier, int durationInMinutes) {
        this.multiplier = multiplier;
        this.minutes = durationInMinutes;
    }

    public ItemStack getItem() {
        NBTItem nbti = new NBTItem(new ItemStack(Material.EMERALD));
        nbti.setBoolean("xpBooster", true);
        nbti.setDouble("multiplier", multiplier);
        nbti.setInteger("duration", minutes);
        nbti.setString("uuid", UUID.randomUUID().toString());
        ItemStack item = nbti.getItem();
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + String.valueOf(multiplier) + "x Xp Booster");
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "Duration: " + minutes + " minutes");
        lore.add(ChatColor.GRAY + "Right click to use");
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(meta);
        item.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
        return item;
    }
}
