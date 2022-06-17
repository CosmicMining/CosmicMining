package net.goldmc.cosmicmining.Database;

import net.goldmc.cosmicmining.Config.Config;
import net.goldmc.cosmicmining.CosmicMining;
import org.apache.commons.lang.math.IntRange;

import java.util.UUID;

public class LoadPlayerData {
    public int[] loadPlayerData(UUID uuid) {
        CosmicMining plugin = (CosmicMining) CosmicMining.getPlugin(CosmicMining.class);
        int level = plugin.getLevels().getInt("Levels." + uuid.toString() + ".level");
        int xp = plugin.getLevels().getInt("Levels." + uuid.toString() + ".xp");
        int[] data = new int[3];
        data[0] = level;
        data[1] = xp;

        IntRange range1 = new IntRange(1, 9);
        IntRange range2 = new IntRange(10, 29);
        IntRange range3 = new IntRange(30, 49);
        IntRange range4 = new IntRange(50, 69);
        IntRange range5 = new IntRange(70, 89);
        IntRange range6 = new IntRange(90, 99);

        if (range1.containsInteger(level)) {
            data[2] = 1;
            return data;
        } else if (range2.containsInteger(level)) {
            data[2] = 2;
            return data;
        } else if (range3.containsInteger(level)) {
            data[2] = 3;
            return data;
        } else if (range4.containsInteger(level)) {
            data[2] = 4;
            return data;
        } else if (range5.containsInteger(level)) {
            data[2] = 5;
            return data;
        } else if (range6.containsInteger(level)) {
            data[2] = 6;
            return data;
        }else if (level >= 100) {
            data[2] = 7;
            return data;
        } else {
            data[2] = -1;
            return data;
        }
    }
    public boolean canBreak(UUID uuid, int blockLevel) {
        boolean canBreak;
        int[] data = loadPlayerData(uuid);
        int breaklevel = data[2];
        switch (breaklevel) {
            case 1:
                if(blockLevel <= 1) {
                    canBreak = true;
                    return true;
                }
                else {
                    canBreak = false; return canBreak;
                }
            case 2:
                if(blockLevel <= 2) {
                    canBreak = true;
                    return true;
                }
                else {
                    canBreak = false;
                    return false;
                }
            case 3:
                if(blockLevel <= 3) {
                    canBreak = true;
                    return true;
                }
                else {
                   return false;
                }
            case 4:
                if(blockLevel <= 4) {
                    canBreak = true;
                    return true;
                } else {
                    canBreak = false;
                    return false;
                }
            case 5:
                if(blockLevel <= 5) {
                    canBreak = true;
                    return true;
                } else {
                    canBreak = false;
                    return false;
                }
            case 6:
                if(blockLevel <= 6) {
                    canBreak = true;
                    return true;
                } else {
                    canBreak = false;
                    return false;
                }
            case 7:
                if(blockLevel <= 7) {
                    canBreak = true;
                    return true;
                }
                else {
                    canBreak = false;
                    return false;
                }
            default:
                return false;
        }
    }
}
