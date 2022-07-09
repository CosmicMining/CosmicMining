package net.goldmc.cosmicmining.Database;


import be.bendem.sqlstreams.util.SqlFunction;
import com.google.common.collect.ImmutableMap;
import com.mysql.jdbc.PreparedStatement;
import dev.dejvokep.boostedyaml.YamlDocument;
import me.lucko.helper.sql.DatabaseCredentials;
import me.lucko.helper.sql.plugin.HelperSql;

import java.util.Arrays;
import java.util.UUID;

import static net.goldmc.cosmicmining.Config.Config.getTheConfig;

public class MySqlDatabase {
    private static boolean triedToConnect = false;
    private static HelperSql helperSql;
    public MySqlDatabase() {
        connect();
        createTableIfUsed();
    }

    public void connect() {
        if(getTheConfig().getBoolean("MySql.use")) {
            YamlDocument config = getTheConfig();
            String host = getTheConfig().get("MySql.address").toString();
            int port = config.getInt("MySql.port");
            String name = config.getString("MySql.database");
            String username = config.getString("MySql.username");
            String password = config.getString("MySql.password");
            try {
                helperSql = new HelperSql(DatabaseCredentials.of(host, port, name, username, password));
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
            if(config.getBoolean("Mysql.use")) {
                try {
                    helperSql.getHikari().getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS `" + getTheConfig().getString("MySql.table-prefix").toString() + "_levels` (\n" +
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
                PreparedStatement updateData = (PreparedStatement) helperSql.getHikari().getConnection().prepareStatement("UPDATE "+ getTheConfig().getString("MySql.table-prefix") + "_levels set level=? and xp=? and xpmultiplier=? WHERE uuid=?");
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
