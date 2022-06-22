package net.goldmc.cosmicmining.Utilites;

import dev.dejvokep.boostedyaml.YamlDocument;
import me.clip.placeholderapi.PlaceholderAPI;
import net.goldmc.cosmicmining.CosmicMining;
import net.goldmc.cosmicmining.Leveling.XpFunctions;
import org.apache.commons.lang.math.IntRange;
import org.bukkit.OfflinePlayer;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;
import java.text.DecimalFormat;

import static java.lang.Math.pow;
import static java.lang.Math.round;

public class CosmicExpansion extends PlaceholderExpansion {
    YamlDocument levels = net.goldmc.cosmicmining.Config.Config.getLevels();
    IntRange range1 = new IntRange(1, 10);
    IntRange range2 = new IntRange(11, 30);
    IntRange range3 = new IntRange(31, 55);
    IntRange range4 = new IntRange(56, 90);
    IntRange range5 = new IntRange(91, 100);

    private final CosmicMining plugin;

    @Override
    public boolean canRegister() {
        return true;
    }

    public CosmicExpansion(CosmicMining plugin) {
        this.plugin = plugin;
    }

    @NotNull
    @Override
    public String getAuthor() {
        return "super_cool_spy";
    }

    @NotNull
    @Override
    public String getIdentifier() {
        return "cosmicmining";
    }

    @NotNull
    @Override
    public String getVersion() {
        return "0.1.0";
    }

    @Override
    public boolean persist() {
        return true; // This is required or else PlaceholderAPI will unregister the Expansion on reload
    }

    @Override
    public String onRequest(OfflinePlayer player, String params) {
        if(params.equalsIgnoreCase("level")) {
            YamlDocument levels = net.goldmc.cosmicmining.Config.Config.getLevels();
            return String.valueOf(levels.getInt("Levels." + player.getUniqueId().toString() + ".level"));
        }
        if(params.equalsIgnoreCase("xp")) {
            YamlDocument levels = net.goldmc.cosmicmining.Config.Config.getLevels();
            int xp = levels.getInt("Levels." + player.getUniqueId().toString() + ".xp");
            DecimalFormat formatter = new DecimalFormat("#,###.00");
            xp = Integer.parseInt(formatter.format(xp));
            return String.valueOf(xp);
        }
        if(params.equalsIgnoreCase("level_remaining_xp")) {
            YamlDocument levels = net.goldmc.cosmicmining.Config.Config.getLevels();
            int xp = levels.getInt("Levels." + player.getUniqueId().toString() + ".xp");
            int level = levels.getInt("Levels." + player.getUniqueId().toString() + ".level");
            BigInteger remainingXp = BigInteger.valueOf(-22222);
            if(range1.containsInteger(level)) {
                remainingXp = BigInteger.valueOf((long) (5 * (pow(level, 2)) + (50L * level) + 100) - xp);
            } else if(range2.containsInteger(level)) {
                remainingXp = BigInteger.valueOf((long) (5 * (pow(level, 2.5)) + (50L * level) + 100) - xp);
            } else if(range3.containsInteger(level)) {
                remainingXp = BigInteger.valueOf((long) (6 * (pow(level, 3)) + (50L * level) + 100) - xp);
            } else if(range4.containsInteger(level)) {
                remainingXp = BigInteger.valueOf((long) (7 * (pow(level, 3.5)) + (50L * level) + 100) - xp);
            } else if(range5.containsInteger(level)) {
                remainingXp = BigInteger.valueOf((long) (1 * (pow(level, 4)) + (50L * level) + 100) - xp);
            }
            String remainingxp;
            int amount = Integer.parseInt(String.valueOf(remainingXp));
            DecimalFormat formatter = new DecimalFormat("#,###.00");
            remainingxp = formatter.format(amount);
            remainingxp = remainingxp.replace(".00", "");
            return remainingxp;
        }
        if(params.equalsIgnoreCase("level_percentage")) {
            YamlDocument levels = net.goldmc.cosmicmining.Config.Config.getLevels();
            int xp = levels.getInt("Levels." + player.getUniqueId().toString() + ".xp");
            int level = levels.getInt("Levels." + player.getUniqueId().toString() + ".level");
            double theXp = -22222;
            if(range1.containsInteger(level)) {
                theXp = xp /  (5 * (pow(level, 2)) + (50L * level) + 100);
            } else if(range2.containsInteger(level)) {
                theXp = xp /  (5 * (pow(level, 2.5)) + (50L * level) + 100);
            } else if(range3.containsInteger(level)) {
                theXp = xp /  (6 * (pow(level, 3)) + (50L * level) + 100);
            } else if(range4.containsInteger(level)) {
                theXp =  xp /  (7 * (pow(level, 3.5)) + (50L * level) + 100);
            } else if(range5.containsInteger(level)) {
                theXp =  xp /  (1 * (pow(level, 4)) + (50L * level) + 100);
            }
            theXp = round(theXp * 100);
            int theeXp = (int) theXp;
            return String.valueOf(theeXp);
        }


        return null; // Placeholder is unknown by the Expansion
    }
}