package net.goldmc.cosmicmining.dataconnectors;

import java.io.IOException;
import java.sql.SQLException;
import java.util.UUID;

public abstract class LevelingUtils {
    protected LevelingUtils() {
    }

    public abstract void addXp(UUID uuid, Long xp) throws IOException, SQLException;

    public abstract void removeXp(UUID uuid, Long xp) throws IOException, SQLException;

    public abstract void setXp(UUID uuid, Long xp) throws IOException, SQLException;
    public abstract Float getXp(UUID uuid);
    public abstract void addLevel(UUID uuid, Long level) throws IOException, SQLException;
    public abstract void removeLevel(UUID uuid, Long level) throws IOException, SQLException;
    public abstract void setLevel(UUID uuid, Integer level) throws IOException, SQLException;
    public abstract Integer getLevel(UUID uuid);

    public abstract void removeXpBooster(UUID uuid) throws IOException, SQLException;
    public abstract void setXpBooster(UUID uuid, Double multiplier, Long time) throws IOException, SQLException;
    public abstract Double getXpBoosterMultiplier(UUID uuid);
    public abstract Long getXpBoosterDuration(UUID uuid);
    public abstract boolean hasXpBooster(UUID uuid);

    public static LevelingUtils getInstance() {
        if (Config.getTheConfig().getBoolean("MySql.use")) {
            return SqlLeveling.getInstance();
        } else {
            return YamlLeveling.getInstance();
        }
    }
}
