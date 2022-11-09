package net.goldmc.cosmicmining.dataconnectors;

import java.io.IOException;
import java.util.UUID;

public class YamlLeveling extends LevelingUtils {
    private static final YamlLeveling instance = new YamlLeveling();

    public static YamlLeveling getInstance() {
        return instance;
    }

    @Override
    public void addXp(UUID uuid, Long xp) throws IOException {
        Config.getLevels().set("Levels." + uuid.toString() + ".xp", getXp(uuid) + xp);
        Config.getLevels().save();
    }

    @Override
    public void removeXp(UUID uuid, Long xp) throws IOException {
        Config.getLevels().set("Levels." + uuid.toString() + ".xp", getXp(uuid) - xp);
        Config.getLevels().save();
    }

    @Override
    public void setXp(UUID uuid, Long xp) throws IOException {
        Config.getLevels().set("Levels." + uuid.toString() + ".xp", xp);
        Config.getLevels().save();
    }

    @Override
    public Float getXp(UUID uuid) {
        return Config.getLevels().getFloat("Levels." + uuid.toString() + ".xp");
    }

    @Override
    public void addLevel(UUID uuid, Long level) throws IOException {
        Config.getLevels().set("Levels." + uuid.toString() + ".level", getLevel(uuid) + level);
        Config.getLevels().save();
    }

    @Override
    public void removeLevel(UUID uuid, Long level) throws IOException {
        Config.getLevels().set("Levels." + uuid.toString() + ".level", getLevel(uuid) - level);
        Config.getLevels().save();
    }

    @Override
    public void setLevel(UUID uuid, Integer level) throws IOException {
        Config.getLevels().set("Levels." + uuid.toString() + ".level", level);
        Config.getLevels().save();
    }

    @Override
    public Integer getLevel(UUID uuid) {
        return Config.getLevels().getInt("Levels." + uuid.toString() + ".level");
    }

    @Override
    public void removeXpBooster(UUID uuid) throws IOException {
        Config.getXpBoosters().remove(uuid.toString());
        Config.getXpBoosters().save();
    }

    @Override
    public void setXpBooster(UUID uuid, Double multiplier, Long time) throws IOException {
        Config.getXpBoosters().set(uuid.toString() + ".multiplier", multiplier);
        Config.getXpBoosters().set(uuid + ".duration", time);
        Config.getXpBoosters().save();
    }


    @Override
    public Double getXpBoosterMultiplier(UUID uuid) {
        return Config.getXpBoosters().getDouble(uuid.toString() + ".multiplier");
    }

    @Override
    public Long getXpBoosterDuration(UUID uuid) {
        return Config.getXpBoosters().getLong(uuid.toString() + ".duration");
    }

    @Override
    public boolean hasXpBooster(UUID uuid) {
        return Config.getXpBoosters().contains(uuid.toString());
    }
}
