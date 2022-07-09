package net.goldmc.cosmicmining.Database;


import com.ptsmods.mysqlw.table.ColumnDefault;
import com.ptsmods.mysqlw.table.ColumnType;
import com.ptsmods.mysqlw.table.TablePreset;
import dev.dejvokep.boostedyaml.YamlDocument;
import net.goldmc.cosmicmining.Utilites.PlayerData;

import java.sql.*;
import java.util.UUID;

import static net.goldmc.cosmicmining.Config.Config.getTheConfig;

public class Data {
    public static void createTable() {
        YamlDocument config = getTheConfig();
        TablePreset.create(config.get("Mysql.table-prefix").toString() + "mining")
                .putColumn("uuid", ColumnType.VARCHAR.struct()
                .setUnique(true)
                .setPrimary(true)
                .configure(sup -> sup.apply(32))
                .setNullAllowed(false))
            .putColumn("level", ColumnType.INT.struct()
                .setDefault(ColumnDefault.def(1))
                .setNullAllowed(false))
            .putColumn("xp", ColumnType.BIGINT.struct()
                .setDefault(ColumnDefault.def(0))
                .setNullAllowed(false))
            .putColumn("xpmultiplier", ColumnType.DOUBLE.struct()
                 .setDefault(ColumnDefault.def(1.0))
                 .setNullAllowed(false));
    }
}
