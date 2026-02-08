package com.bindglam.mint.manager;

import com.bindglam.mint.currency.Currency;
import com.bindglam.mint.currency.CurrencyRegistry;
import org.jetbrains.annotations.NotNull;

/**
 * CurrencyManager interface
 *
 * @author bindglam
 */
public interface CurrencyManager extends Managerial, Reloadable {
    @NotNull CurrencyRegistry registry();

    @NotNull Currency defaultCurrency();
}
