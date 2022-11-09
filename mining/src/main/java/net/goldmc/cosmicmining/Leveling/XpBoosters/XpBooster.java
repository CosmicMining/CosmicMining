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
        NBTItem nbtItem = new NBTItem(new ItemStack(Material.EMERALD));
        nbtItem.setBoolean("xpBooster", true);
        nbtItem.setDouble("multiplier", multiplier);
        nbtItem.setInteger("duration", minutes);
        nbtItem.setString("uuid", UUID.randomUUID().toString());

        ItemStack item = nbtItem.getItem();
        ItemMeta meta = item.getItemMeta();
        List<String> lore = new ArrayList<>();

        meta.setDisplayName(ChatColor.GREEN + String.valueOf(multiplier) + "x Xp Booster");

        lore.add(ChatColor.GRAY + "Duration: " + minutes + " minutes");
        lore.add(ChatColor.GRAY + "Right click to use");

        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        item.setItemMeta(meta);
        item.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
        return item;
    }
}
