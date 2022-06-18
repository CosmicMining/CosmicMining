package net.goldmc.cosmicmining.Leveling;

import dev.dejvokep.boostedyaml.YamlDocument;
import net.goldmc.cosmicmining.Config.Config;
import net.goldmc.cosmicmining.Database.LoadPlayerData;
import org.bukkit.Bukkit;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import static java.lang.Math.*;

public class XpFunctions {
    Config config = new Config();
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


    public void giveXpForOre(UUID u, String blocklevel) {
        System.out.println("giveXpForOre");
        Config config = new Config();
        YamlDocument Config = config.getTheConfig();

        Map<String, Integer> hm
                = new HashMap<>();
        hm.put("COAL", 1);
        hm.put("IRON", Config.getInt("XpPerBlock.Iron"));
        hm.put("LAPIS", Config.getInt("XpPerBlock.Lapis"));
        hm.put("REDSTONE", Config.getInt("XpPerBlock.Redstone"));
        hm.put("GLOWING", Config.getInt("XpPerBlock.Redstone"));
        hm.put("GOLD", Config.getInt("XpPerBlock.Gold"));
        hm.put("DIAMOND", Config.getInt("XpPerBlock.Diamond"));
        hm.put("EMERALD", Config.getInt("XpPerBlock.Emerald"));




        LoadPlayerData loadPlayerData = new LoadPlayerData();
        YamlDocument levels = config.getLevels();
        int xp = levels.getInt("Levels." + Bukkit.getPlayer(u) + ".xp");
        for(Map.Entry<String , Integer> entry: hm.entrySet()) {
            // if give value is equal to value from entry
            // print the corresponding key
            if(Objects.equals(entry.getKey(), blocklevel)) {
                int sum = xp + entry.getValue();
                System.out.println(xp);
                levels.set("Levels." + u.toString() + ".xp", sum);
                try {
                    config.setLevels(levels);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return;
            }
       }
        //return -1;
    }
}
