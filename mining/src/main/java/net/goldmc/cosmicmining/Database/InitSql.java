package net.goldmc.cosmicmining.Database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static net.goldmc.cosmicmining.dataconnectors.Config.getTheConfig;

public class InitSql {
    private static final AtomicInteger POOL_COUNTER = new AtomicInteger(0);

    private static HikariDataSource source = null;
    private static HikariDataSource longSource = null;

    public InitSql() {
        newConnection();
    }

    public static HikariDataSource newConnection() {
        HikariConfig hikari = createNewSource();
        hikari.setConnectionTimeout(100000);
        hikari.setLeakDetectionThreshold(100000);

        // ensure we use unicode (this calls #setProperties, a hack for the mariadb driver)
        hikari.addDataSourceProperty("properties", "useUnicode=true;characterEncoding=utf8");

        source = new HikariDataSource(hikari);
        return source;
    }

    public static HikariDataSource newLongConnection() {
         createNewSource();
        HikariConfig hikari = createNewSource();
        hikari.setConnectionTimeout(TimeUnit.MINUTES.toMillis(1000));
        hikari.setLeakDetectionThreshold(100000);

        // ensure we use unicode (this calls #setProperties, a hack for the mariadb driver)
        hikari.addDataSourceProperty("properties", "useUnicode=true;characterEncoding=utf8");

        longSource = new HikariDataSource(hikari);
        return longSource;
    }

    private static HikariConfig createNewSource() {
        Properties properties = new Properties();
        properties.setProperty("useConfigs", "maxPerformance");

        final HikariConfig hikari = new HikariConfig(properties);

        hikari.setPoolName("cosmic-sql-" + POOL_COUNTER.getAndIncrement());
        hikari.setJdbcUrl("jdbc:mysql://" + getTheConfig().getString("MySql.address") + ":" + getTheConfig().getInt("MySql.port") + "/" + getTheConfig().getString("MySql.database"));
        hikari.setMaximumPoolSize((Runtime.getRuntime().availableProcessors() * 2) + 1);
        hikari.setMinimumIdle(10);
        hikari.setMaxLifetime(0);

        hikari.addDataSourceProperty("serverName", getTheConfig().getString("MySql.address"));
        hikari.addDataSourceProperty("port", getTheConfig().getInt("MySql.port"));
        hikari.addDataSourceProperty("databaseName", getTheConfig().getString("MySql.database"));

        hikari.setUsername(getTheConfig().getString("MySql.username"));
        hikari.setPassword(getTheConfig().getString("MySql.password"));

        return hikari;
    }

    public static HikariDataSource getSource() {
        return source;
    }
}
