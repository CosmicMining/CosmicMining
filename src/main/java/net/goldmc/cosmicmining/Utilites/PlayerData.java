package net.goldmc.cosmicmining.Utilites;

import net.goldmc.cosmicmining.Config.Config;
import org.apache.commons.lang.math.IntRange;
import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.UUID;

import static org.bukkit.Bukkit.getOfflinePlayer;

public class PlayerData {
    public int[] loadPlayerData(UUID uuid) {
        int level = (int) Config.getLevels().get("Levels." + uuid.toString() + ".level");
        int xp = (int) Config.getLevels().get("Levels." + uuid.toString() + ".xp");
        return getInts(level, xp);
    }

    public static int[] getInts(int level, int xp) {
        int[] data = new int[3];
        data[0] = level;
        data[1] = xp;
        HashMap<IntRange, Integer> ranges = new HashMap<>();
        ranges.put(new IntRange(1, 9), 1);
        ranges.put(new IntRange(10, 29), 2);
        ranges.put(new IntRange(30, 49), 3);
        ranges.put(new IntRange(50, 69), 4);
        ranges.put(new IntRange(70, 89), 5);
        ranges.put(new IntRange(90, 99), 6);
        ranges.put(new IntRange(100, 100), 7);
        for (IntRange range : ranges.keySet()) {
            if (range.containsInteger(level)) {
                data[2] = ranges.get(range);
                break;
            }
        }
        return data;
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
