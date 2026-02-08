package com.bindglam.mint.account;

import com.bindglam.mint.Mint;
import com.bindglam.mint.account.log.TransactionLogger;
import com.bindglam.mint.currency.Currency;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * Account interface
 *
 * @author bindglam
 */
public interface Account {
    /**
     * Get the uuid of the holder
     */
    @NotNull UUID holder();

    @NotNull TransactionLogger logger();

    /**
     * Get the balance of the account in the given currency
     *
     * @param currency currency
     */
    CompletableFuture<BigDecimal> getBalance(Currency currency);

    /**
     * Modify the balance of the account in the given currency
     *
     * @param currency currency
     * @param value value
     * @param operation operation
     */
    CompletableFuture<Operation.Result> modifyBalance(Currency currency, BigDecimal value, Operation operation);

    /**
     * Get the balance of the account in the default currency
     */
    default CompletableFuture<BigDecimal> getBalance() {
        return getBalance(Mint.instance().currencyManager().defaultCurrency());
    }

    /**
     * Modify the balance of the account in the default currency
     *
     * @param value value
     * @param operation operation
     */
    default CompletableFuture<Operation.Result> modifyBalance(BigDecimal value, Operation operation) {
        return modifyBalance(Mint.instance().currencyManager().defaultCurrency(), value, operation);
    }
}
