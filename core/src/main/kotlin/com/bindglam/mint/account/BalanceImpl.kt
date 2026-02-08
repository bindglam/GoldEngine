package com.bindglam.mint.account

import com.alibaba.fastjson2.JSONObject
import com.bindglam.mint.Mint
import com.bindglam.mint.account.log.Log
import com.bindglam.mint.account.log.TransactionLoggerImpl
import com.bindglam.mint.currency.Currency
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.concurrent.ConcurrentHashMap
import kotlin.collections.set

class BalanceImpl : Balance {
    private val map = ConcurrentHashMap<String, BigDecimal>()

    private val logger = TransactionLoggerImpl()

    init {
        Mint.instance().currencyManager().registry().entries().forEach { currency ->
            map[currency.id] = BigDecimal.ZERO
        }
    }

    fun fromJson(json: JSONObject) {
        Mint.instance().currencyManager().registry().entries().forEach { currency ->
            map[currency.id] = json.getBigDecimal(currency.id) ?: BigDecimal.ZERO
        }
    }

    fun toJson(): JSONObject {
        return JSONObject().also {
            Mint.instance().currencyManager().registry().entries().forEach { currency ->
                it[currency.id] = get(currency)
            }
        }
    }

    override fun get(currency: Currency): BigDecimal = map[currency.id] ?: BigDecimal.ZERO
    override fun modify(currency: Currency, value: BigDecimal, operation: Operation): Operation.Result {
        val result = operation.operate(get(currency), value)

        if(result.isSuccess) {
            map[currency.id] = result.result

            logger.log(Log(LocalDateTime.now(), operation, result, value))
        }

        return result
    }

    override fun logger() = logger
}