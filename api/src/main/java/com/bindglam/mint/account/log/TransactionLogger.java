package com.bindglam.mint.account.log;

import org.jetbrains.annotations.Unmodifiable;

import java.util.List;

public interface TransactionLogger {
    @Unmodifiable List<Log> logs();
}
