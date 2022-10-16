package net.goldmc.cosmicmining.Utilites;

import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.block.implementation.Section;
import net.goldmc.cosmicmining.Config.Config;
import net.goldmc.cosmicmining.Database.MySqlDatabase;
import net.goldmc.cosmicmining.Listeners.BreakingEvents.OnOreBlockBreak;
import org.apache.commons.lang.math.IntRange;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.IOException;
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

    private void tickDown(Player p) {
        if(Config.getXpBoosters().contains(p.getUniqueId() + ".duration")) {
            //make bukkit runnable
            Bukkit.getScheduler().scheduleSyncRepeatingTask(Bukkit.getPluginManager().getPlugin("CosmicMining"), () -> new BukkitRunnable() {
                @Override
                public void run() {
                    if (p.isOnline()) {
                        if (Config.getTheConfig().getBoolean("MySql.use")) {
                            new MySqlDatabase().updatePlayerData(p.getUniqueId(), 0, 0);
                        } else {
                            if(Config.getXpBoosters().contains(p.getUniqueId() + ".duration")) {
                                YamlDocument xpboosters = Config.getXpBoosters();
                                Section s = xpboosters.getSection(p.getUniqueId().toString());
                                if (s.getLong("duration") <= 0) {
                                    Config.getXpBoosters().remove(p.getUniqueId().toString());
                                    try {
                                        Config.setXpBoosters(xpboosters);
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }
                                    p.sendMessage("§cYour xp booster has expired.");
                                    cancel();
                                }
                                s.set("duration", s.getInt("duration") - 1);
                                try {
                                    Config.setXpBoosters(xpboosters);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }
                    }
                }
            }.runTaskAsynchronously(Bukkit.getPluginManager().getPlugin("CosmicMining")), 0, 20);
        }
    }

    public void registerXpBooster(Player p, double multiplier, int minutes) {
        if(Config.getTheConfig().getBoolean("MySql.use")) {
            new MySqlDatabase().registerXpBooster(p, multiplier, minutes * 60L + 1);
        } else {
            YamlDocument xpBoosters = Config.getXpBoosters();
            xpBoosters.set(p.getUniqueId().toString() + ".multiplier", multiplier);
            xpBoosters.set(p.getUniqueId().toString() + ".duration", minutes * 60 + 1);
            try {
                Config.setXpBoosters(xpBoosters);
            } catch (Exception e) {
                e.printStackTrace();
            }
            tickDown(p);
        }
    }

    public static Integer canBreak(Player p, Block b) {
        Map<String, Integer> hm = OnOreBlockBreak.getOreMap();
        Integer blockLevel = hm.get(getBlockType(b));

        if(!p.hasPermission("cosmicmining.minearea")) {
            return null;
        }

        PlayerData playerData = new PlayerData(p.getUniqueId());
        boolean canBreak = playerData.canBreakBlock(blockLevel);
        if(canBreak) {
            return blockLevel;
        }

        return null;
    }

    public static String getBlockType(Block b) {
        if (b.getType() == Material.GLOWING_REDSTONE_ORE) {
            return "REDSTONE";
        }

        return b.getType().toString().split("_")[0];
    }

    public static boolean isOreOrBlock(Block b) {
        if (b.getType() == Material.GLOWING_REDSTONE_ORE) {
            return true;
        }
        return b.getType().toString().split("_")[1].equals("ORE") || b.getType().toString().split("_")[1].equals("BLOCK");
    }
}
