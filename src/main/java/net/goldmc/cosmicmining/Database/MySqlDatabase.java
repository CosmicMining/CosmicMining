package net.goldmc.cosmicmining.Database;


import com.mysql.jdbc.PreparedStatement;
import com.zaxxer.hikari.HikariDataSource;
import dev.dejvokep.boostedyaml.YamlDocument;
import org.bukkit.entity.Player;

import javax.persistence.TemporalType;
import java.sql.Date;
import java.sql.SQLException;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalUnit;
import java.util.Calendar;
import java.util.UUID;

import static net.goldmc.cosmicmining.Config.Config.getTheConfig;
import static net.goldmc.cosmicmining.Database.InitSql.getSource;
import static net.goldmc.cosmicmining.Database.InitSql.newConnection;

public class MySqlDatabase {
    private static boolean triedToConnect = false;
    private static HikariDataSource dataSource;
    public MySqlDatabase() {
        connect();
        createTableIfUsed();
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
        if(!triedToConnect) {
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
                            "  `booster` DOUBLE NOT NULL DEFAULT '0',\n" +
                            "  `time` DATE NOT NULL,\n" +
                            "  PRIMARY KEY (`uuid`)\n" +
                            ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;").execute();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
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
                Date dateMilis = new Date(System.currentTimeMillis());
                Instant dateInstant = dateMilis.toInstant().plusSeconds(time);
                dateInstant.atOffset(ZoneOffset.UTC);
                dateMilis.setTime(dateInstant.toEpochMilli());

                PreparedStatement updateData = (PreparedStatement) dataSource.getConnection().prepareStatement("INSERT INTO "+ getTheConfig().getString("MySql.table-prefix") + "_xpboosters set booster=? and time=? WHERE uuid=?");
                updateData.setDouble(1, booster);
                updateData.setDate(2, dateMilis);
                updateData.setString(3, p.getUniqueId().toString());
                updateData.execute();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
