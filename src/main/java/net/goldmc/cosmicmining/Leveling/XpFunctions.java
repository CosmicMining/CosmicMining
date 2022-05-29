package net.goldmc.cosmicmining.Leveling;

import net.goldmc.cosmicmining.Config.Config;
import net.goldmc.cosmicmining.Database.LoadPlayerData;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Dye;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import static java.lang.Float.parseFloat;
import static java.lang.Math.*;
import static net.goldmc.cosmicmining.Config.Config.getCustomConfig3;

public class XpFunctions {
    public float calculateXp(UUID u) {
        LoadPlayerData loadPlayerData = new LoadPlayerData();
        float xp = 0.0f;
        int level1xp = 28;
        int[] playerData = loadPlayerData.loadPlayerData(u);
        float power = (float) pow(playerData[0], 2);

        float amountOfXpToNextLevel = (float) (3000000 * playerData[0] - 158);
        float percentage = ( playerData[1] / amountOfXpToNextLevel);
        System.out.printf(String.valueOf(percentage));
        return percentage;
    }


    public int giveXpForOre(UUID u, String blocklevel) {
        FileConfiguration Config = net.goldmc.cosmicmining.Config.Config.getCustomConfig1();

        Map<String, Integer> hm
                = new HashMap<>();
        hm.put("COAL", 1);
        hm.put("IRON", Config.getInt("XpPerBlock.Iron"));
        hm.put("LAPIS", Config.getInt("XpPerBlock.Lapis"));
        hm.put("REDSTONE", Config.getInt("XpPerBlock.Redstone"));
        hm.put("GOLD", Config.getInt("XpPerBlock.Gold"));
        hm.put("DIAMOND", Config.getInt("XpPerBlock.Diamond"));
        hm.put("EMERALD", Config.getInt("XpPerBlock.Emerald"));




        LoadPlayerData loadPlayerData = new LoadPlayerData();
        int xp = net.goldmc.cosmicmining.Config.Config.getCustomConfig3().getInt("Levels." + Bukkit.getPlayer(u) + ".xp");
        for(Map.Entry<String , Integer> entry: hm.entrySet()) {
            // if give value is equal to value from entry
            // print the corresponding key
            if(Objects.equals(entry.getKey(), blocklevel)) {
                int sum = xp + entry.getValue();
                net.goldmc.cosmicmining.Config.Config.getCustomConfig3().set("Levels." + u.toString() + ".xp", sum);
                net.goldmc.cosmicmining.Config.Config.saveConfig3();
                return sum;
            }
        }
        return -1;
    }
}
