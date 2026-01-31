package com.bindglam.goldengine.manager;

import org.jetbrains.annotations.NotNull;

public interface Managerial {
    default void start(@NotNull Context context) {
    }

    default void end(@NotNull Context context) {
    }
}
