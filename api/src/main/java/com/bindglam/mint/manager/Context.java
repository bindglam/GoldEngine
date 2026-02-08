package com.bindglam.mint.manager;

import com.bindglam.mint.MintConfiguration;
import com.bindglam.mint.MintPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Logger;

/**
 * Context interface for managers
 *
 * @author bindglam
 */
public interface Context {
    @NotNull MintPlugin plugin();

    @NotNull MintConfiguration config();

    @NotNull Logger logger();
}
