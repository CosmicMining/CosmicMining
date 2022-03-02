package net.goldmc.cosmicmining.Leveling;

import net.goldmc.cosmicmining.Database.LoadPlayerData;

import java.util.UUID;

import static java.lang.Float.parseFloat;
import static java.lang.Math.*;

public class XpFunctions {
    public float calculateXp(UUID u) {
        LoadPlayerData loadPlayerData = new LoadPlayerData();
        float xp = 0.0f;
        int level1xp = 28;
        int[] playerData = loadPlayerData.loadPlayerData(u);
        float power = (float) pow(playerData[0], 2);

        float amountOfXpToNextLevel = (float) (3000000 * playerData[0] - 158);
        float percentage = ( playerData[1] / amountOfXpToNextLevel);
        System.out.printf(String.valueOf(percentage));
        return percentage;
    }
}
