package net.goldmc.cosmicmining.Utilites;

import net.goldmc.cosmicmining.CosmicMining;
import net.goldmc.cosmicmining.dataconnectors.LevelingUtils;
import org.apache.commons.lang.math.IntRange;
import org.bukkit.OfflinePlayer;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;
import java.text.DecimalFormat;

import static java.lang.Math.*;

public class CosmicExpansion extends PlaceholderExpansion {
    LevelingUtils levelingUtils = LevelingUtils.getInstance();
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
        float xp = levelingUtils.getXp(player.getUniqueId());
        int level = levelingUtils.getLevel(player.getUniqueId());
        if(params.equalsIgnoreCase("level")) {
            return String.valueOf(levelingUtils.getLevel(player.getUniqueId())).replace(".0", "");
        }
        if(params.equalsIgnoreCase("xp")) {
            DecimalFormat formatter = new DecimalFormat("#,###.00");
            String xpstuff = formatter.format(xp);
            xpstuff = xpstuff.replace(".00", "");
            if(xpstuff.length() == 0) {
                xpstuff = "0";
            }
            return xpstuff;
        }
        double v = 5 * (pow(level, 2)) + (50L * level) + 100;
        double v1 = 5 * (pow(level, 2.5)) + (50L * level) + 100;
        double v2 = 6 * (pow(level, 3)) + (50L * level) + 100;
        double v3 = 7 * (pow(level, 3.5)) + (50L * level) + 100;
        double v4 = 1 * (pow(level, 4)) + (50L * level) + 100;
        if(params.equalsIgnoreCase("level_remaining_xp")) {
            BigInteger remainingXp = BigInteger.valueOf(-22222);
            if(range1.containsInteger(level)) {
                remainingXp = BigInteger.valueOf((long) ((long) v - xp));
            } else if(range2.containsInteger(level)) {
                remainingXp = BigInteger.valueOf((long) ((long) v1 - xp));
            } else if(range3.containsInteger(level)) {
                remainingXp = BigInteger.valueOf((long) ((long) v2 - xp));
            } else if(range4.containsInteger(level)) {
                remainingXp = BigInteger.valueOf((long) ((long) v3 - xp));
            } else if(range5.containsInteger(level)) {
                remainingXp = BigInteger.valueOf((long) ((long) v4 - xp));
            }
            String remainingxp;
            int amount = Integer.parseInt(String.valueOf(remainingXp));
            DecimalFormat formatter = new DecimalFormat("#,###.00");
            remainingxp = formatter.format(amount);
            remainingxp = remainingxp.replace(".00", "");
            return remainingxp;
        }
        if(params.equalsIgnoreCase("level_percentage")) {
            double theXp = -22222;
            if(range1.containsInteger(level)) {
                theXp = xp /  v;
            } else if(range2.containsInteger(level)) {
                theXp = xp /  v1;
            } else if(range3.containsInteger(level)) {
                theXp = xp /  v2;
            } else if(range4.containsInteger(level)) {
                theXp =  xp /  v3;
            } else if(range5.containsInteger(level)) {
                theXp =  xp /  v4;
            }
            theXp = round(theXp * 100);
            int theeXp = (int) theXp;
            return String.valueOf(theeXp);
        }


        return null; // Placeholder is unknown by the Expansion
    }
}