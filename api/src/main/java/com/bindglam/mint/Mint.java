package com.bindglam.mint;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

/**
 * GoldEnginePlugin interface provider
 *
 * @author bindglam
 */
public final class Mint {
    private static MintPlugin instance;

    private Mint() {
        throw new RuntimeException();
    }

    public static @NotNull MintPlugin instance() {
        if(Mint.instance == null)
            throw new IllegalStateException("Not initialized");
        return Mint.instance;
    }

    public static @NotNull MintConfiguration config() {
        return instance().config();
    }

    @ApiStatus.Internal
    static void registerInstance(@NotNull MintPlugin instance) {
        if(Mint.instance != null)
            throw new IllegalStateException("Already initialized");
        Mint.instance = instance;
    }
}
