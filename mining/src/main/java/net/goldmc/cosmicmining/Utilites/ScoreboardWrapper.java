package net.goldmc.cosmicmining.Utilites;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.util.ArrayList;
import java.util.List;

/**
 * ScoreboardWrapper is a class that wraps Bukkit Scoreboard API
 * and makes your life easier.
 */
public class ScoreboardWrapper {

    public static final int MAX_LINES = 16;

    private final Scoreboard scoreboard;
    private final Objective objective;

    private final List<String> modifies = new ArrayList<>(MAX_LINES);

    /**
     * Instantiates a new ScoreboardWrapper with a default title.
     */
    public ScoreboardWrapper(String title) {
        scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        objective = scoreboard.registerNewObjective(title, "dummy");
        objective.setDisplayName(title);
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
    }

    /**
     * Sets the scoreboard title.
     */
    public void setTitle(String title) {
        objective.setDisplayName(title);
    }

    /**
     * Modifies the line with §r strings in the way to add
     * a line equal to another.
     */
    private String getLineCoded(String line) {
        String result = line;
        while (modifies.contains(result))
            result += ChatColor.RESET;
        return result.substring(0, Math.min(40, result.length()));
    }

    /**
     * Adds a new line to the scoreboard. Throw an error if the lines count are higher than 16.
     */
    public void addLine(String line) {
        if (modifies.size() > MAX_LINES)
            throw new IndexOutOfBoundsException("You cannot add more than 16 lines.");
        String modified = getLineCoded(line);
        modifies.add(modified);
        objective.getScore(modified).setScore(-(modifies.size()));
    }

    /**
     * Adds a blank space to the scoreboard.
     */
    public void addBlankSpace() {
        addLine(" ");
    }

    /**
     * Sets a scoreboard line to an exact index (between 0 and 15).
     */
    public void setLine(int index, String line) {
        if (index < 0 || index >= MAX_LINES)
            throw new IndexOutOfBoundsException("The index cannot be negative or higher than 15.");
        String oldModified = modifies.get(index);
        scoreboard.resetScores(oldModified);
        String modified = getLineCoded(line);
        modifies.set(index, modified);
        objective.getScore(modified).setScore(-(index + 1));
    }

    /**
     * Gets the Bukkit Scoreboard.
     */
    public Scoreboard getScoreboard() {
        return scoreboard;
    }

    /**
     * Just for debug.
     */
    @Override
    public String toString() {
        String out = "";
        int i = 0;
        for (String string : modifies)
            out += -(i + 1) + ")-> " + string + ";\n";
        return out;
    }
}
