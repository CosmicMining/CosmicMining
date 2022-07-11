package net.goldmc.cosmicmining.Leveling;

import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.route.Route;
import net.goldmc.cosmicmining.Config.Config;
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
        IntRange range1 = new IntRange(1, 10);
        IntRange range2 = new IntRange(11, 30);
        IntRange range3 = new IntRange(31, 55);
        IntRange range4 = new IntRange(56, 90);
        IntRange range5 = new IntRange(91, 100);
        if (range1.containsInteger(level)) {
            return oneten;
        } else if (range2.containsInteger(level)) {
            return eleventhirty;
        } else if (range3.containsInteger(level)) {
            return thirtyone55;
        } else if (range4.containsInteger(level)) {
            return fifty6ninety;
        } else if (range5.containsInteger(level)) {
            return ninetyone100;
        } else {
            return 0;
        }
    }


    public void giveXpForOre(UUID u, String blocklevel) {
        YamlDocument config = net.goldmc.cosmicmining.Config.Config.getTheConfig();
        Map<String, Integer> hm
                = new HashMap<>();
        hm.put("COAL", config.getInt(Route.from("XpPerBlock", "Coal")));
        hm.put("IRON", config.getInt("XpPerBlock.Iron"));
        hm.put("LAPIS", config.getInt("XpPerBlock.Lapis"));
        hm.put("REDSTONE", config.getInt("XpPerBlock.Redstone"));
        hm.put("GLOWING", config.getInt("XpPerBlock.Redstone"));
        hm.put("GOLD", config.getInt("XpPerBlock.Gold"));
        hm.put("DIAMOND", config.getInt("XpPerBlock.Diamond"));
        hm.put("EMERALD", config.getInt("XpPerBlock.Emerald"));
        calculateXp(u);


        PlayerData playerData = new PlayerData();
        YamlDocument levels = net.goldmc.cosmicmining.Config.Config.getLevels();
        int xp = levels.getInt("Levels." + u.toString() + ".xp");
        for(Map.Entry<String , Integer> entry: hm.entrySet()) {
            // if give value is equal to value from entry
            // print the corresponding key
            if(Objects.equals(entry.getKey(), blocklevel)) {
                Double multiplier = new PlayerData().getXpMultiplier(u);
                double sum = (xp + entry.getValue() * multiplier);
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
    public void checkLevelUp(UUID u) {
        YamlDocument levels = net.goldmc.cosmicmining.Config.Config.getLevels();
        int xp = levels.getInt("Levels." + u.toString() + ".xp");
        int level = levels.getInt("Levels." + u.toString() + ".level");
        if(xp >= calculateXp(u)) {
            int newlevel = level;
            //loop to 100
            while (xp >= calculateXp(u)) {
                if(xp < calculateXp(u)) {
                    YamlDocument levels1 = net.goldmc.cosmicmining.Config.Config.getLevels();
                    levels1.set("Levels." + u.toString() + ".level", newlevel);
                    try {
                        net.goldmc.cosmicmining.Config.Config.setLevels(levels1);
                        Config.getLevels().reload();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                } else {
                    newlevel++;
                    levels.set("Levels." + u.toString() + ".level", newlevel);
                    try {
                        net.goldmc.cosmicmining.Config.Config.setLevels(levels);
                        Config.getLevels().reload();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
