package com.bindglam.mint.manager;

import org.jetbrains.annotations.NotNull;

/**
 * Reloadable manager interface
 *
 * @author bindglam
 */
public interface Reloadable {
    void reload(@NotNull Context context);
}
