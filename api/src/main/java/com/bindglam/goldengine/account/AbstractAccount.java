package com.bindglam.goldengine.account;

import com.bindglam.goldengine.GoldEngine;
import com.bindglam.goldengine.currency.Currency;
import com.bindglam.goldengine.manager.AccountManager;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public abstract class AbstractAccount implements Account {
    private final UUID holder;

    private final Map<String, BigDecimal> balance = new HashMap<>();
    private boolean isJustCreated;

    public AbstractAccount(UUID holder) {
        this.holder = holder;

        load();
    }

    private void load() {
        GoldEngine.instance().database().getConnection((connection) -> {
            try(PreparedStatement statement = connection.prepareStatement("SELECT * FROM " + AccountManager.ACCOUNTS_TABLE_NAME + " WHERE holder = ?")) {
                statement.setString(1, this.holder.toString());

                ResultSet result = statement.executeQuery();
                if(result.next()) {
                    for (Currency currency : GoldEngine.instance().currencyManager().registry().entries()) {
                        this.balance.put(currency.id(), result.getBigDecimal(currency.id()));
                    }

                    this.isJustCreated = false;
                } else {
                    for (Currency currency : GoldEngine.instance().currencyManager().registry().entries()) {
                        this.balance.put(currency.id(), BigDecimal.ZERO);
                    }

                    this.isJustCreated = true;
                }
            }
        });
    }

    @ApiStatus.Internal
    public void save() {
        List<Currency> currencies = GoldEngine.instance().currencyManager().registry().entries().stream().toList();

        GoldEngine.instance().database().getConnection((connection) -> {
            if (this.isJustCreated) {
                StringBuilder balanceNames = new StringBuilder();
                for (int i = 0; i < currencies.size(); i++) {
                    balanceNames.append(currencies.get(i).id());
                    if(i < currencies.size()-1)
                        balanceNames.append(", ");
                }

                try (PreparedStatement statement = connection.prepareStatement("INSERT INTO " + AccountManager.ACCOUNTS_TABLE_NAME + " (holder, " + balanceNames + ") VALUES (?, ?)")) {
                    statement.setString(1, this.holder.toString());
                    for (int i = 0; i < currencies.size(); i++) {
                        statement.setBigDecimal(2 + i, this.balance(currencies.get(i)));
                    }

                    statement.executeUpdate();
                }
            } else {
                StringBuilder balanceFields = new StringBuilder();
                for (int i = 0; i < currencies.size(); i++) {
                    balanceFields.append(currencies.get(i).id()).append(" = ?");
                    if(i < currencies.size()-1)
                        balanceFields.append(", ");
                }

                try (PreparedStatement statement = connection.prepareStatement("UPDATE " + AccountManager.ACCOUNTS_TABLE_NAME + " SET " + balanceFields + " WHERE holder = ?")) {
                    for (int i = 0; i < currencies.size(); i++) {
                        statement.setBigDecimal(1 + i, this.balance(currencies.get(i)));
                    }
                    statement.setString(currencies.size() + 1, this.holder.toString());

                    statement.executeUpdate();
                }
            }
        });
    }

    @Override
    public @NotNull UUID holder() {
        return this.holder;
    }

    @Override
    public BigDecimal balance(Currency currency) {
        return this.balance.getOrDefault(currency.id(), BigDecimal.ZERO);
    }

    @Override
    public void balance(Currency currency, BigDecimal balance) {
        this.balance.put(currency.id(), balance);
    }

    @Override
    public boolean modifyBalance(Currency currency, BigDecimal amount, Operation operation) {
        Operation.Result result = operation.operate(this.balance(currency), amount);
        if(result.isFailed())
            return false;
        this.balance(currency, result.result());
        return true;
    }

    @Override
    public void close() {
        save();
    }
}
