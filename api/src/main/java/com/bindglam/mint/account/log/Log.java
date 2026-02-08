package com.bindglam.mint.account.log;

import com.bindglam.mint.account.Operation;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public record Log(
        Timestamp timestamp,
        Operation operation,
        Operation.Result result,
        BigDecimal value
) {
}
