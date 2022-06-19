package net.goldmc.cosmicmining.Leveling;

import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.route.Route;
import net.goldmc.cosmicmining.Utilites.PlayerData;
import org.apache.commons.lang.math.IntRange;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import static java.lang.Math.*;

public class XpFunctions {
    public float calculateXp(UUID u) {
        //TODO: Make levels system work
        PlayerData loadPlayerData = new PlayerData();
        int level1xp = 28;
        int[] playerData = loadPlayerData.loadPlayerData(u);
        float xp = playerData[1];
        float level = playerData[0];
        //1-10
        float oneten = (float) (5 * (pow(level, 2)) + (50 * level) + 100);
        //11-30
        float eleventhirty = (float) (5 * (pow(level, 2.5)) + (50 * level) + 100);
        //31-55
        float thirtyone55 = (float) (6 * (pow(level, 3)) + (50 * level) + 100);
        //56-90
        float fifty6ninety = (float) (7 * (pow(level, 3.5)) + (50 * level) + 100);
        BigDecimal b = new BigDecimal(fifty6ninety);
        //91-100
        float ninetyone100 = (float) (1 * (pow(level, 4)) + (50 * level) + 100);
        BigDecimal b1 = new BigDecimal(ninetyone100);
        //
        return ninetyone100;
    }


    public void giveXpForOre(UUID u, String blocklevel) {
        YamlDocument Config = net.goldmc.cosmicmining.Config.Config.getTheConfig();
        Map<String, Integer> hm
                = new HashMap<>();
        hm.put("COAL", Config.getInt(Route.from("XpPerBlock", "Coal")));
        hm.put("IRON", Config.getInt("XpPerBlock.Iron"));
        hm.put("LAPIS", Config.getInt("XpPerBlock.Lapis"));
        hm.put("REDSTONE", Config.getInt("XpPerBlock.Redstone"));
        hm.put("GLOWING", Config.getInt("XpPerBlock.Redstone"));
        hm.put("GOLD", Config.getInt("XpPerBlock.Gold"));
        hm.put("DIAMOND", Config.getInt("XpPerBlock.Diamond"));
        hm.put("EMERALD", Config.getInt("XpPerBlock.Emerald"));
        calculateXp(u);


        PlayerData playerData = new PlayerData();
        YamlDocument levels = net.goldmc.cosmicmining.Config.Config.getLevels();
        int xp = levels.getInt("Levels." + u.toString() + ".xp");
        for(Map.Entry<String , Integer> entry: hm.entrySet()) {
            // if give value is equal to value from entry
            // print the corresponding key
            if(Objects.equals(entry.getKey(), blocklevel)) {
                int sum = xp + entry.getValue();
                levels.set("Levels." + u + ".xp", sum);
                try {
                    net.goldmc.cosmicmining.Config.Config.setLevels(levels);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return;
            }
       }
    }
}
