package net.goldmc.cosmicmining.dataconnectors;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import static net.goldmc.cosmicmining.dataconnectors.Config.getTheConfig;

public class SqlLeveling extends LevelingUtils {
    private static final AtomicInteger connectionCounter = new AtomicInteger(0);
    private static final SqlLeveling instance = new SqlLeveling();
    private static final HikariDataSource dataSource = createNewSource();

    public static SqlLeveling getInstance() {
        return instance;
    }

    @Override
    public void addXp(UUID uuid, Long xp) throws SQLException {
        dataSource.getConnection().prepareStatement("UPDATE cosmicmining SET xp = xp + " + xp + " WHERE uuid = '" + uuid.toString() + "'").execute();
    }

    @Override
    public void removeXp(UUID uuid, Long xp) throws IOException, SQLException {
        dataSource.getConnection().prepareStatement("UPDATE cosmicmining SET xp = xp - " + xp + " WHERE uuid = '" + uuid.toString() + "'").execute();
    }

    @Override
    public void setXp(UUID uuid, Long xp) throws IOException, SQLException {
        dataSource.getConnection().prepareStatement("UPDATE cosmicmining SET xp = " + xp + " WHERE uuid = '" + uuid.toString() + "'").execute();
    }

    @Override
    public Float getXp(UUID uuid) {
        try {
            return dataSource.getConnection().prepareStatement("SELECT xp FROM cosmicmining WHERE uuid = '" + uuid.toString() + "'").executeQuery().getFloat("xp");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void addLevel(UUID uuid, Long level) throws IOException, SQLException {
        dataSource.getConnection().prepareStatement("UPDATE cosmicmining SET level = level + " + level + " WHERE uuid = '" + uuid.toString() + "'").execute();
    }

    @Override
    public void removeLevel(UUID uuid, Long level) throws IOException, SQLException {
        dataSource.getConnection().prepareStatement("UPDATE cosmicmining SET level = level - " + level + " WHERE uuid = '" + uuid.toString() + "'").execute();
    }

    @Override
    public void setLevel(UUID uuid, Integer level) throws IOException, SQLException {
        dataSource.getConnection().prepareStatement("UPDATE cosmicmining SET level = " + level + " WHERE uuid = '" + uuid.toString() + "'").execute();
    }

    @Override
    public Integer getLevel(UUID uuid) {
        try {
            return dataSource.getConnection().prepareStatement("SELECT level FROM cosmicmining WHERE uuid = '" + uuid.toString() + "'").executeQuery().getInt("level");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void removeXpBooster(UUID uuid) throws IOException, SQLException {
        dataSource.getConnection().prepareStatement("UPDATE cosmicmining SET xpbooster = 0, xpboostermultiplier = 0, xpboostertime = 0 WHERE uuid = '" + uuid.toString() + "'").execute();
    }

    @Override
    public void setXpBooster(UUID uuid, Double multiplier, Long time) throws IOException, SQLException {
        dataSource.getConnection().prepareStatement("UPDATE cosmicmining SET xpbooster = 1, xpboostermultiplier = " + multiplier + ", xpboostertime = " + time + " WHERE uuid = '" + uuid.toString() + "'").execute();
    }

    @Override
    public Double getXpBoosterMultiplier(UUID uuid) {
        try {
            return dataSource.getConnection().prepareStatement("SELECT xpboostermultiplier FROM cosmicmining WHERE uuid = '" + uuid.toString() + "'").executeQuery().getDouble("xpboostermultiplier");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Long getXpBoosterDuration(UUID uuid) {
        try {
            return dataSource.getConnection().prepareStatement("SELECT xpboostertime FROM cosmicmining WHERE uuid = '" + uuid.toString() + "'").executeQuery().getLong("xpboostertime");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean hasXpBooster(UUID uuid) {
        try {
            return dataSource.getConnection().prepareStatement("SELECT xpbooster FROM cosmicmining WHERE uuid = '" + uuid.toString() + "'").executeQuery().getBoolean("xpbooster");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    private static HikariDataSource createNewSource() {
        if (Config.getTheConfig().getBoolean("MySql.use")) {
            return null;
        }


        final HikariConfig hikari = new HikariConfig();
        hikari.setConnectionTimeout(100000);
        hikari.setLeakDetectionThreshold(100000);

        // ensure we use unicode (this calls #setProperties, a hack for the mariadb driver)
        hikari.addDataSourceProperty("properties", "useUnicode=true;characterEncoding=utf8");

        hikari.setPoolName("cosmic-sql-" + connectionCounter.getAndIncrement());
        hikari.setJdbcUrl("jdbc:mysql://" + getTheConfig().getString("MySql.address") + ":" + getTheConfig().getInt("MySql.port") + "/" + getTheConfig().getString("MySql.database"));
        hikari.setMaximumPoolSize((Runtime.getRuntime().availableProcessors() * 2) + 1);
        if ((Runtime.getRuntime().availableProcessors() * 2) + 1 > 10) {
            hikari.setMinimumIdle((Runtime.getRuntime().availableProcessors() * 2) - 1);
        } else {
            hikari.setMinimumIdle(10);
        }

        hikari.setMaxLifetime(0);

        hikari.addDataSourceProperty("serverName", getTheConfig().getString("MySql.address"));
        hikari.addDataSourceProperty("port", getTheConfig().getInt("MySql.port"));
        hikari.addDataSourceProperty("databaseName", getTheConfig().getString("MySql.database"));

        hikari.setUsername(getTheConfig().getString("MySql.username"));
        hikari.setPassword(getTheConfig().getString("MySql.password"));


        return new HikariDataSource(hikari);
    }
}
