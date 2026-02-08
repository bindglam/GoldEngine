package com.bindglam.mint.account.log;

import com.bindglam.mint.account.Operation;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record Log(
        LocalDateTime timestamp,
        Operation operation,
        Operation.Result result,
        BigDecimal value
) {
}
