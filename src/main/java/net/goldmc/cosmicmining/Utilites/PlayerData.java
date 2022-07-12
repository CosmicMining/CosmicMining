package net.goldmc.cosmicmining.Utilites;

import dev.dejvokep.boostedyaml.YamlDocument;
import net.goldmc.cosmicmining.Config.Config;
import net.goldmc.cosmicmining.Database.MySqlDatabase;
import org.apache.commons.lang.math.IntRange;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

import static org.bukkit.Bukkit.getOfflinePlayer;

public class PlayerData {
    private UUID uuid;

    public PlayerData(UUID uuid) {
        this.uuid = uuid;
    }

    public PlayerData() {

    }

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
    public double getXpMultiplier(UUID u) {
        Double multiplier = 1.0;
        if(!Config.getTheConfig().getBoolean("MySql.use")) {
            if(Config.getXpBoosters().get(u.toString() + ".multiplier") != null) {
                multiplier = Config.getXpBoosters().getDouble(u + ".multiplier");
            }
        }
        return multiplier;
    }
    public boolean updatePlayerData(UUID uuid, int level, long xp) {
        if(Config.getTheConfig().getBoolean("MySql.use")) {
            new MySqlDatabase().updatePlayerData(uuid, level, xp);
        } else {
            YamlDocument levels = Config.getLevels();
            levels.set("Levels." + uuid.toString() + ".level", level);
            levels.set("Levels." + uuid.toString() + ".xp", xp);
            try {
                Config.setLevels(levels);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    public void registerXpBooster(Player p, double multiplier, int minutes) {
        if(Config.getTheConfig().getBoolean("MySql.use")) {
            new MySqlDatabase().registerXpBooster(p, multiplier, minutes * 60L);
        } else {
            YamlDocument xpBoosters = Config.getXpBoosters();
            xpBoosters.set(p.getUniqueId().toString() + ".multiplier", multiplier);
            xpBoosters.set(p.getUniqueId().toString() + ".duration", minutes);
            try {
                Config.setXpBoosters(xpBoosters);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
