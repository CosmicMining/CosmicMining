package net.goldmc.cosmicmining.Utilites;

import dev.dejvokep.boostedyaml.YamlDocument;
import net.goldmc.cosmicmining.Config.Config;
import net.goldmc.cosmicmining.Database.MySqlDatabase;
import net.goldmc.cosmicmining.Listeners.BreakingEvents.OnOreBlockBreak;
import org.apache.commons.lang.math.IntRange;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import static org.bukkit.Bukkit.getOfflinePlayer;

public class PlayerData {
    private UUID uuid;

    public PlayerData(UUID uuid) {
        this.uuid = uuid;
    }

    public PlayerData() {

    }

    public float[] loadPlayerData() {
        float level = Config.getLevels().getFloat("Levels." + uuid.toString() + ".level");
        Float xp = Config.getLevels().getFloat("Levels." + uuid.toString() + ".xp");
        float[] data = new float[3];
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

    public boolean canBreakBlock(int blockLevel) {
        float[] data = loadPlayerData();
        int breaklevel = (int) data[2];
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
    public double getXpMultiplier() {
        Double multiplier = 1.0;
        if(!Config.getTheConfig().getBoolean("MySql.use")) {
            if(Config.getXpBoosters().get(uuid.toString() + ".multiplier") != null) {
                multiplier = Config.getXpBoosters().getDouble(uuid + ".multiplier");
            }
        }
        return multiplier;
    }
    public boolean updatePlayerData(int level, long xp) {
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

    private static Map<String, Integer> hm
            = new HashMap<String, Integer>();
    public static Object[] canBreak(Player p, Block b) {
        Object[] data = new Object[3];
        if(p.hasPermission("cosmicmining.minearea")) {
            if (b.getType() == Material.COAL_ORE || b.getType() == Material.IRON_ORE || b.getType() == Material.LAPIS_ORE || b.getType()==Material.REDSTONE_ORE || b.getType()==Material.GLOWING_REDSTONE_ORE || b.getType() == Material.GOLD_ORE || b.getType() == Material.DIAMOND_ORE || b.getType() == Material.EMERALD_ORE || b.getType() == Material.COAL_BLOCK || b.getType() == Material.IRON_BLOCK || b.getType() == Material.LAPIS_BLOCK || b.getType() == Material.REDSTONE_BLOCK || b.getType() == Material.GOLD_BLOCK || b.getType() == Material.DIAMOND_BLOCK || b.getType() == Material.EMERALD_BLOCK) {
                String finalOrigblock = b.getType().toString();
                String[] split = finalOrigblock.split("_", 0);
                OnOreBlockBreak.oreandblocksmap(hm);
                for (Map.Entry<String, Integer> entry : hm.entrySet()) {
                    if (Objects.equals(entry.getKey(), split[0])) {
                        PlayerData playerData = new PlayerData(p.getUniqueId());
                        boolean canBreak = playerData.canBreakBlock(entry.getValue());
                        if(canBreak) {
                            data[0] = true;
                            data[1] = finalOrigblock;
                            data[2] = entry.getValue();
                            return data;
                        }
                        break;
                    }
                }
            }
        }
        data[0] = false;
        return data;
    }
}
