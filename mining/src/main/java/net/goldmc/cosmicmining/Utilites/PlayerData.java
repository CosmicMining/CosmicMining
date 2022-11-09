package net.goldmc.cosmicmining.Utilites;

import net.goldmc.cosmicmining.dataconnectors.LevelingUtils;
import net.goldmc.cosmicmining.dataconnectors.Config;
import net.goldmc.cosmicmining.Listeners.BreakingEvents.OnOreBlockBreak;
import org.apache.commons.lang.math.IntRange;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerData {
    private UUID uuid;
    private static final LevelingUtils leveling = LevelingUtils.getInstance();

    public PlayerData(UUID uuid) {
        this.uuid = uuid;
    }

    public PlayerData() {

    }

    public float[] loadPlayerData() {
        float level = leveling.getLevel(uuid);
        Float xp = leveling.getXp(uuid);
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
        if(leveling.getXpBoosterMultiplier(uuid) != null) {
            multiplier = leveling.getXpBoosterMultiplier(uuid);
        }
        return multiplier;
    }
    public boolean updatePlayerData(int level, long xp) {
        try {
            leveling.setLevel(uuid, level);
            leveling.setXp(uuid, xp);
        } catch (IOException | SQLException e) {
            return false;
        }
        return true;
    }

    private void tickDown(Player p) {
        if(leveling.getXpBoosterDuration(uuid) != null) {
            //make bukkit runnable
            Bukkit.getScheduler().scheduleSyncRepeatingTask(Bukkit.getPluginManager().getPlugin("CosmicMining"), () -> new BukkitRunnable() {
                @Override
                public void run() {
                    if (!p.isOnline()) {
                        cancel();
                        return;
                    }

                    if(leveling.getXpBoosterDuration(p.getUniqueId()) == null) {
                        cancel();
                        return;
                    }

                    if (leveling.getXpBoosterDuration(uuid) <= 0) {
                        Config.getXpBoosters().remove(p.getUniqueId().toString());
                        try {
                            leveling.removeXpBooster(uuid);
                        } catch (IOException | SQLException e) {
                            throw new RuntimeException(e);
                        }
                        p.sendMessage("§cYour xp booster has expired.");
                        cancel();
                        return;
                    }
                    try {
                        leveling.setXpBooster(uuid, leveling.getXpBoosterMultiplier(uuid), leveling.getXpBoosterDuration(uuid) - 1);
                    } catch (IOException | SQLException e) {
                        e.printStackTrace();
                    }
                }
            }.runTaskAsynchronously(Bukkit.getPluginManager().getPlugin("CosmicMining")), 0, 20);
        }
    }

    public void registerXpBooster(Player p, double multiplier, int minutes) {
        LevelingUtils leveling = LevelingUtils.getInstance();
        try {
            leveling.setXpBooster(p.getUniqueId(), multiplier, minutes * 60L + 1);
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }

        tickDown(p);
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

    public static String getStringBlockType(String b) {
        if (b.equals("GLOWING_REDSTONE_ORE")) {
            return "REDSTONE";
        }
        return b.split("_")[0];
    }

    public static boolean commandPlayerChecks(CommandSender sender, String[] args) {
        if (!(sender instanceof Player || sender instanceof ConsoleCommandSender)) {
            return false;
        }

        if(!(args.length == 2)) {
            return true;
        }

        if (Bukkit.getPlayer(UUID.fromString(args[0])) == null) {
            return true;
        }
        return false;
    }
}
