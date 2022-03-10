package net.goldmc.cosmicmining.Leveling;

import net.goldmc.cosmicmining.Config.Config;
import net.goldmc.cosmicmining.Database.LoadPlayerData;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static java.lang.Float.parseFloat;
import static java.lang.Math.*;

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

    public int giveXpForOre(Player p) {
        FileConfiguration Config = net.goldmc.cosmicmining.Config.Config.getCustomConfig1();

        Map<String, Integer> hm
                = new HashMap<String, Integer>();
        hm.put("COAL", Config.getInt("XpPerBlock.Coal"));
        hm.put("IRON", Config.getInt("XpPerBlock.Iron"));
        hm.put("LAPIS", Config.getInt("XpPerBlock.Lapis"));
        hm.put("REDSTONE", Config.getInt("XpPerBlock.Redstone"));
        hm.put("GOLD", Config.getInt("XpPerBlock.Gold"));
        hm.put("DIAMOND", Config.getInt("XpPerBlock.Diamond"));
        hm.put("EMERALD", Config.getInt("XpPerBlock.Emerald"));
        return -1;
    }
}
