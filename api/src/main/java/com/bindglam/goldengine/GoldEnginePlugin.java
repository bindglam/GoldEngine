package com.bindglam.goldengine;

import com.bindglam.goldengine.database.Database;
import com.bindglam.goldengine.manager.AccountManager;
import com.bindglam.goldengine.manager.CurrencyManager;
import com.bindglam.goldengine.manager.LanguageManager;

public interface GoldEnginePlugin {
    GoldEngineConfiguration config();

    Database database();

    LanguageManager languageManager();

    AccountManager accountManager();

    CurrencyManager currencyManager();
}
