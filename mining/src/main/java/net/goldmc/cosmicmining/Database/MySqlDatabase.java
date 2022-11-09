package net.goldmc.cosmicmining.Database;


import com.mysql.jdbc.PreparedStatement;
import com.zaxxer.hikari.HikariDataSource;
import dev.dejvokep.boostedyaml.YamlDocument;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.UUID;

import static net.goldmc.cosmicmining.dataconnectors.Config.getTheConfig;
import static net.goldmc.cosmicmining.Database.InitSql.*;

public class MySqlDatabase {
    private Long time = 0L;
    private static boolean triedToConnect = false;
    private static HikariDataSource dataSource;
    private static HikariDataSource dataSourceLong;
    private int runnable;
    public MySqlDatabase() {
        connect();
        createTableIfUsed();
    }

    public void getNewLongDataSource() {
        if(dataSourceLong != null) {
            return;
        }
        dataSourceLong = newLongConnection();
    }

    public void getNewDataSource() throws SQLException {
        if(dataSource != null) {
            dataSource.getConnection().close();
        }
        dataSource = newConnection();
    }

    public void connect() {
        if(getTheConfig().getBoolean("MySql.use")) {
            try {
                getNewDataSource();
                System.out.println("[CosmicMining] Connected to database!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public void createTableIfUsed() {
        if(triedToConnect) {
           return;
        }
        triedToConnect = true;
        YamlDocument config = getTheConfig();
        if(config.getBoolean("MySql.use")) {
            try {
                getNewDataSource();
                dataSource.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS `" + getTheConfig().getString("MySql.table-prefix").toString() + "_levels` (\n" +
                        "  `uuid` varchar(36) NOT NULL,\n" +
                        "  `level` INT NOT NULL DEFAULT '1',\n" +
                        "  `xp` BIGINT NOT NULL DEFAULT '0',\n" +
                        "  PRIMARY KEY (`uuid`)\n" +
                        ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;").execute();
                getNewDataSource();
                //create xp booster table
                dataSource.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS `" + getTheConfig().getString("MySql.table-prefix").toString() + "_xpboosters` (\n" +
                        "  `uuid` varchar(36) NOT NULL,\n" +
                        "  `booster` DOUBLE NOT NULL DEFAULT '1.0',\n" +
                        "  `time` LONG NOT NULL,\n" +
                        "  PRIMARY KEY (`uuid`)\n" +
                        ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;").execute();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void subtractOne() {
        time--;
    }

    public void updatePlayerData(UUID uuid, int level, long xp) {
        YamlDocument config = getTheConfig();
        if(config.getBoolean("MySql.use")) {
            try {
                getNewDataSource();
                PreparedStatement updateData = (PreparedStatement) dataSource.getConnection().prepareStatement("UPDATE "+ getTheConfig().getString("MySql.table-prefix") + "_levels set level=? and xp=? WHERE uuid=?");
                updateData.setInt(1, level);
                updateData.setLong(2, xp);
                updateData.setString(4, uuid.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void registerXpBooster(Player p, double booster, long time) {
        YamlDocument config = getTheConfig();
        if(config.getBoolean("MySql.use")) {
            try {
                getNewDataSource();
                java.sql.PreparedStatement updateData =  dataSource.getConnection().prepareStatement("UPDATE " + getTheConfig().getString("MySql.table-prefix") + "_xpboosters set booster=? and time=? WHERE uuid=?");
                updateData.setDouble(1, booster);
                updateData.setLong(2, time);
                updateData.setString(3, String.valueOf(p.getUniqueId()));
                updateData.execute();
                this.time = time;
                tickPlayer(p.getUniqueId());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void cancel() {
        if(runnable != 0) {
            Bukkit.getScheduler().cancelTask(runnable);
            runnable = -1;
        }
    }

    private void tickPlayer(UUID u) {
        getNewLongDataSource();
        runnable = Bukkit.getScheduler().scheduleSyncRepeatingTask(Bukkit.getPluginManager().getPlugin("CosmicMining"), () -> Bukkit.getScheduler().runTaskAsynchronously(Bukkit.getPluginManager().getPlugin("CosmicMining"), () -> {
            subtractOne();
            YamlDocument config = getTheConfig();
            if(config.getBoolean("MySql.use")) {
                if(!(time <= 0)) {
                    try {
                        java.sql.PreparedStatement updateData = dataSourceLong.getConnection().prepareStatement("UPDATE "+ getTheConfig().getString("MySql.table-prefix") + "_xpboosters set time=? WHERE uuid=?");
                        updateData.setLong(1, time);
                        updateData.setString(2, u.toString());
                        updateData.execute();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        PreparedStatement updateData = (PreparedStatement) dataSourceLong.getConnection().prepareStatement("UPDATE "+ getTheConfig().getString("MySql.table-prefix") + "_xpboosters set booster=? WHERE uuid=?");
                        updateData.setDouble(1, 1.0);
                        updateData.setString(2, u.toString());
                        updateData.execute();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    cancel();
                }
            }
        }), 0L, 20L);
    }
}
