package com.bindglam.mint.database;

/**
 * Database interface
 *
 * @author bindglam
 */
public interface Database {
    void start();

    void stop();

    void getConnection(ConnectionConsumer consumer);
}
