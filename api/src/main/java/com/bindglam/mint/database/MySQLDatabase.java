package com.bindglam.mint.database;

import com.bindglam.mint.MintConfiguration;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * An implementation of Database interface for MySQL
 *
 * @author bindglam
 */
public final class MySQLDatabase implements Database {
    private HikariDataSource dataSource;

    private final MintConfiguration config;

    public MySQLDatabase(MintConfiguration config) {
        this.config = config;
    }

    @Override
    public void start() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl("jdbc:mysql://" + config.database.mysql.url.value() + "/" + config.database.mysql.database.value());
        hikariConfig.setUsername(config.database.mysql.username.value());
        hikariConfig.setPassword(config.database.mysql.password.value());
        hikariConfig.setMaximumPoolSize(config.database.mysql.maxPoolSize.value());

        this.dataSource = new HikariDataSource(hikariConfig);
    }

    @Override
    public void stop() {
        this.dataSource.close();
    }

    @Override
    public void getConnection(ConnectionConsumer consumer) {
        try(Connection connection = dataSource.getConnection()) {
            consumer.accept(connection);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to proceed database connection", e);
        }
    }
}
