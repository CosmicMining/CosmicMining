package net.goldmc.cosmicmining.Database;


import java.sql.*;
import java.util.UUID;

public class Data {




    public static void createDatabase() throws SQLException, InterruptedException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        String url = "jdbc:mysql://localhost:3306/";
        String dbName = "cosmicsandbox";
        String driver = "com.mysql.jdbc.Driver";
        String userName = "root";
        String password = "1272";

        Class.forName(driver).newInstance();
        Connection conn = DriverManager.getConnection("jdbc:mysql://" + "localhost" + ":" + "3306" + "/" + "cosmicsandbox" + "?autoReconnect=" + "true", "root", "1272");

        String DatabaseNAME = "cosmicsandbox";
        String TableNAME = "mining";
        Statement stmt = conn.createStatement();
        stmt.executeQuery("CREATE DATABASE IF NOT EXISTS "+  DatabaseNAME +";");
        stmt.executeQuery("CREATE TABLE IF NOT EXISTS `cosmicsandbox`.`" + TableNAME + "` (\n" +
                "  `uuid` CHAR(36) NOT NULL,\n" +
                "  `level` INT NOT NULL,\n" +
                "  `xp` BIGINT NOT NULL,\n" +
                "  PRIMARY KEY (`uuid`));");
        conn.close();
    }

    public static void shutdownDatabase() {
        System.out.println("Closed");
    }

    public static void setPlayerData(UUID uuid, int level, int xp) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cosmicsandbox" , "root", "1272");
        String DatabaseNAME = "cosmicsandbox";
        String TableNAME = "mining";

        Statement stmt = conn.createStatement();

        ResultSet result = null;
        result = stmt.executeQuery("SELECT EXISTS(SELECT * from " + TableNAME + " WHERE uuid=" + uuid.toString() + ");");

        if(!result.isBeforeFirst()){
            System.out.println("No Data Found"); //data not exist
            LoadPlayerData loadPlayerData = new LoadPlayerData();
            int[] data = loadPlayerData.loadPlayerData(uuid);
            stmt.executeQuery("INSERT INTO " + TableNAME + " " + "VALUES (" + uuid.toString() + ", " + level + ", " + xp + ")");
        }
            else{
                stmt.executeQuery("UPDATE " + TableNAME + " SET xp = " + xp + " WHERE uuid IS " + uuid.toString() + ";");
                stmt.executeQuery("UPDATE " + TableNAME + " SET level = " + level + " WHERE uuid IS " + uuid.toString() + ";");
        }
            conn.close();
    }
}
