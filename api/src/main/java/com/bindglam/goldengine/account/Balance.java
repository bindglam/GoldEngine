package com.bindglam.goldengine.account;

import com.bindglam.goldengine.GoldEngine;
import com.bindglam.goldengine.currency.Currency;

import java.math.BigDecimal;

/**
 * An interface for account's balance
 *
 * @author bindglam
 */
public interface Balance {
    /**
     * Get the balance of the account in the given currency
     *
     * @param currency currency
     */
    BigDecimal get(Currency currency);

    /**
     * Modify the balance of the account in the given currency
     *
     * @param currency currency
     * @param value value
     * @param operation operation
     */
    Operation.Result modify(Currency currency, BigDecimal value, Operation operation);

    /**
     * Get the balance of the account in the default currency
     */
    default BigDecimal get() {
        return get(GoldEngine.instance().currencyManager().defaultCurrency());
    }

    /**
     * Modify the balance of the account in the default currency
     *
     * @param value value
     * @param operation operation
     */
    default Operation.Result modify(BigDecimal value, Operation operation) {
        return modify(GoldEngine.instance().currencyManager().defaultCurrency(), value, operation);
    }
}
