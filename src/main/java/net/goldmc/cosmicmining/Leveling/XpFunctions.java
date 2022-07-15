package net.goldmc.cosmicmining.Leveling;

import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.route.Route;
import net.goldmc.cosmicmining.Config.Config;
import net.goldmc.cosmicmining.CosmicMining;
import net.goldmc.cosmicmining.Utilites.PlayerData;
import org.apache.commons.lang.math.IntRange;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import static java.lang.Math.pow;

public class XpFunctions {
    private final UUID u;
    public XpFunctions(UUID u) {
        this.u = u;
    }


    public float calculateXp() {
        final float[] formula = {0f};

        final PlayerData loadPlayerData = new PlayerData();
        final float[] playerData = loadPlayerData.loadPlayerData(u);
        assert playerData != null;
        final float level = playerData[0];
        //1-10
        final float oneten = (float) (5 * (pow(level, 2)) + (50 * level) + 100);
        //11-30
        final float eleventhirty = (float) (5 * (pow(level, 2.5)) + (50 * level) + 100);
        //31-55
        float thirtyone55 = (float) (6 * (pow(level, 3)) + (50 * level) + 100);
        //56-90
        float fifty6ninety = (float) (7 * (pow(level, 3.5)) + (50 * level) + 100);
        //91-100
        float ninetyone100 = (float) (1 * (pow(level, 4)) + (50 * level) + 100);
        IntRange range1 = new IntRange(1, 10);
        IntRange range2 = new IntRange(11, 30);
        IntRange range3 = new IntRange(31, 55);
        IntRange range4 = new IntRange(56, 90);
        IntRange range5 = new IntRange(91, 100);
        if (range1.containsInteger(level)) {
            formula[0] = oneten;
        } else if (range2.containsInteger(level)) {
            formula[0] = eleventhirty;
        } else if (range3.containsInteger(level)) {
            formula[0] = thirtyone55;
        } else if (range4.containsInteger(level)) {
            formula[0] = fifty6ninety;
        } else if (range5.containsInteger(level)) {
            formula[0] = ninetyone100;
        } else {
            //Failsafe otherwise crashes the server if a player has a level above 100
            formula[0] = Float.MAX_VALUE - 20;
        }
        return formula[0];
    }


    public void giveXpForOre(UUID u, String blocklevel) {
        new BukkitRunnable() {
            @Override
            public void run() {
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
                calculateXp();


                PlayerData playerData = new PlayerData();
                YamlDocument levels = net.goldmc.cosmicmining.Config.Config.getLevels();
                Float xp = levels.getFloat("Levels." + u.toString() + ".xp");
                for (Map.Entry<String, Integer> entry : hm.entrySet()) {
                    // if give value is equal to value from entry
                    // print the corresponding key
                    if (Objects.equals(entry.getKey(), blocklevel)) {
                        Double multiplier = playerData.getXpMultiplier(u);
                        double sum = (xp + entry.getValue() * multiplier);
                        levels.set("Levels." + u + ".xp", (float) sum);
                        try {
                            net.goldmc.cosmicmining.Config.Config.setLevels(levels);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return;
                    }
                }
            }
        }.runTaskAsynchronously(CosmicMining.getPlugin(CosmicMining.class));
    }

    public void checkLevelUp() {

        YamlDocument levels = net.goldmc.cosmicmining.Config.Config.getLevels();
        Float xp = levels.getFloat("Levels." + u.toString() + ".xp");
        int level = levels.getInt("Levels." + u + ".level");
        if (xp >= calculateXp()) {
            int newlevel = level;
            //loop to 100
            while (xp >= calculateXp()) {
                if (xp < calculateXp()) {
                    YamlDocument levels1 = net.goldmc.cosmicmining.Config.Config.getLevels();
                    levels1.set("Levels." + u + ".level", newlevel);
                    try {
                        net.goldmc.cosmicmining.Config.Config.setLevels(levels1);
                        Config.getLevels().reload();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                } else {
                    newlevel++;
                    levels.set("Levels." + u + ".level", newlevel);
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
