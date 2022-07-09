package net.goldmc.cosmicmining.Database;


import com.mysql.jdbc.PreparedStatement;
import com.zaxxer.hikari.HikariDataSource;
import dev.dejvokep.boostedyaml.YamlDocument;

import java.util.UUID;

import static net.goldmc.cosmicmining.Config.Config.getTheConfig;
import static net.goldmc.cosmicmining.Database.InitSql.getSource;

public class MySqlDatabase {
    private static boolean triedToConnect = false;
    private static HikariDataSource dataSource;
    public MySqlDatabase() {
        connect();
        createTableIfUsed();
    }

    public void connect() {
        if(getTheConfig().getBoolean("MySql.use")) {
            try {
                new InitSql();
                dataSource = getSource();
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
                    dataSource.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS `" + getTheConfig().getString("MySql.table-prefix").toString() + "_levels` (\n" +
                            "  `uuid` varchar(36) NOT NULL,\n" +
                            "  `level` INT NOT NULL DEFAULT '1',\n" +
                            "  `xp` BIGINT NOT NULL DEFAULT '0',\n" +
                            "  PRIMARY KEY (`uuid`)\n" +
                            ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;").execute();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void updatePlayerData(UUID uuid, int level, long xp, double xpmultiplier) {
        YamlDocument config = getTheConfig();
        if(config.getBoolean("MySql.use")) {
            try {
                PreparedStatement updateData = (PreparedStatement) dataSource.getConnection().prepareStatement("UPDATE "+ getTheConfig().getString("MySql.table-prefix") + "_levels set level=? and xp=? and xpmultiplier=? WHERE uuid=?");
                updateData.setInt(1, level);
                updateData.setLong(2, xp);
                updateData.setDouble(3, xpmultiplier);
                updateData.setString(4, uuid.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
