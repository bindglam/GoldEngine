package com.bindglam.goldengine.account;

import com.bindglam.goldengine.GoldEngine;
import com.bindglam.goldengine.currency.Currency;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public interface Account extends AutoCloseable {
    @NotNull UUID holder();

    BigDecimal balance(Currency currency);

    void balance(Currency currency, BigDecimal balance);

    boolean modifyBalance(Currency currency, BigDecimal amount, Operation operation);

    @Deprecated
    default BigDecimal balance() {
        return balance(GoldEngine.instance().currencyManager().registry().get(Currency.WON).orElseThrow());
    }

    @Deprecated
    default void balance(BigDecimal balance) {
        balance(GoldEngine.instance().currencyManager().registry().get(Currency.WON).orElseThrow(), balance);
    }

    @Deprecated
    default boolean modifyBalance(BigDecimal amount, Operation operation) {
        return modifyBalance(GoldEngine.instance().currencyManager().registry().get(Currency.WON).orElseThrow(), amount, operation);
    }
}
