package net.goldmc.cosmicmining.Database;

import net.goldmc.cosmicmining.Config.Config;
import net.goldmc.cosmicmining.CosmicMining;
import org.apache.commons.lang.math.IntRange;

import java.util.UUID;

public class LoadPlayerData {
    public int[] loadPlayerData(UUID uuid) {
        Config config = new Config();
        int level = (int) config.getLevels().get("Levels." + uuid.toString() + ".level");
        int xp = (int) config.getLevels().get("Levels." + uuid.toString() + ".xp");
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
    public boolean canBreakBlock(UUID uuid, int blockLevel) {
        int[] data = loadPlayerData(uuid);
        int breaklevel = data[2];
        switch (breaklevel) {
            case 1:
                return blockLevel <= 1;
            case 2:
                return blockLevel <= 2;
            case 3:
                return blockLevel <= 3;
            case 4:
                return blockLevel <= 4;
            case 5:
                return blockLevel <= 5;
            case 6:
                return blockLevel <= 6;
            case 7:
                return blockLevel <= 7;
            default:
                return false;
        }
    }
}
