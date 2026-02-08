package com.bindglam.mint.database;

import com.bindglam.mint.MintConfiguration;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

/**
 * Database type enum
 *
 * @author bindglam
 */
public enum DatabaseType {
    SQLITE(SQLiteDatabase::new),
    MYSQL(MySQLDatabase::new);

    private final Function<MintConfiguration, Database> supplier;

    DatabaseType(Function<MintConfiguration, Database> supplier) {
        this.supplier = supplier;
    }

    public @NotNull Database create(MintConfiguration config) {
        return supplier.apply(config);
    }
}
