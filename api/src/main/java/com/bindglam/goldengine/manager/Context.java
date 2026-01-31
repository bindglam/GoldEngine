package com.bindglam.goldengine.manager;

import com.bindglam.goldengine.GoldEngineConfiguration;
import com.bindglam.goldengine.GoldEnginePlugin;
import org.jetbrains.annotations.NotNull;

public interface Context {
    @NotNull GoldEnginePlugin plugin();

    @NotNull GoldEngineConfiguration config();
}
