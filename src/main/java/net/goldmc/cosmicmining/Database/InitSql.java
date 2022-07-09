package net.goldmc.cosmicmining.Database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static net.goldmc.cosmicmining.Config.Config.getTheConfig;

public class InitSql {
    private static final AtomicInteger POOL_COUNTER = new AtomicInteger(0);

    private static final Properties PROPERTIES;
    private static final String DATA_SOURCE_CLASS = "com.mysql.jdbc.Driver";

    // https://github.com/brettwooldridge/HikariCP/wiki/About-Pool-Sizing
    private static final int MAXIMUM_POOL_SIZE = (Runtime.getRuntime().availableProcessors() * 2) + 1;
    private static final int MINIMUM_IDLE = 10;

    private static final long MAX_LIFETIME = TimeUnit.MINUTES.toMillis(30); // 30 Minutes
    private static final long CONNECTION_TIMEOUT = TimeUnit.SECONDS.toMillis(10); // 10 seconds
    private static final long LEAK_DETECTION_THRESHOLD = TimeUnit.SECONDS.toMillis(10); // 10 seconds

    static {
        PROPERTIES = new Properties();

        // http://assets.en.oreilly.com/1/event/21/Connector_J%20Performance%20Gems%20Presentation.pdf
        PROPERTIES.setProperty("useConfigs", "maxPerformance");
    }

    private static HikariDataSource source = null;

    public InitSql() {
        final HikariConfig hikari = new HikariConfig();

        hikari.setPoolName("helper-sql-" + POOL_COUNTER.getAndIncrement());

        hikari.addDataSourceProperty("serverName", getTheConfig().getString("MySql.address"));
        hikari.addDataSourceProperty("port", getTheConfig().getInt("MySql.port"));
        hikari.addDataSourceProperty("databaseName", getTheConfig().getString("MySql.database"));

        hikari.setUsername(getTheConfig().getString("MySql.username"));
        hikari.setPassword(getTheConfig().getString("MySql.password"));

        hikari.setDataSourceProperties(PROPERTIES);
        hikari.setJdbcUrl("jdbc:mysql://" + getTheConfig().getString("MySql.address") + ":" + getTheConfig().getInt("MySql.port") + "/" + getTheConfig().getString("MySql.database"));

        hikari.setMaximumPoolSize(MAXIMUM_POOL_SIZE);
        hikari.setMinimumIdle(MINIMUM_IDLE);

        hikari.setMaxLifetime(MAX_LIFETIME);
        hikari.setConnectionTimeout(CONNECTION_TIMEOUT);
        hikari.setLeakDetectionThreshold(LEAK_DETECTION_THRESHOLD);

        // ensure we use unicode (this calls #setProperties, a hack for the mariadb driver)
        hikari.addDataSourceProperty("properties", "useUnicode=true;characterEncoding=utf8");

        source = new HikariDataSource(hikari);
    }

    public static HikariDataSource getSource() {
        return source;
    }
}
