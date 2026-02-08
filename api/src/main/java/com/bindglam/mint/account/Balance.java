package com.bindglam.mint.account;

import com.bindglam.mint.Mint;
import com.bindglam.mint.account.log.TransactionLogger;
import com.bindglam.mint.currency.Currency;

import java.math.BigDecimal;

/**
 * An interface for account's balance
 *
 * @author bindglam
 */
public interface Balance {
    TransactionLogger logger();

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
        return get(Mint.instance().currencyManager().defaultCurrency());
    }

    /**
     * Modify the balance of the account in the default currency
     *
     * @param value value
     * @param operation operation
     */
    default Operation.Result modify(BigDecimal value, Operation operation) {
        return modify(Mint.instance().currencyManager().defaultCurrency(), value, operation);
    }
}
