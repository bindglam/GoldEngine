package com.bindglam.mint.account;

import com.bindglam.mint.account.log.TransactionLogger;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * Account interface
 *
 * @author bindglam
 */
public interface Account extends AutoCloseable {
    void save();

    /**
     * Get the uuid of the holder
     */
    @NotNull UUID holder();

    /**
     * Get the account's balance
     */
    @NotNull Balance balance();

    @NotNull TransactionLogger logger();
}
