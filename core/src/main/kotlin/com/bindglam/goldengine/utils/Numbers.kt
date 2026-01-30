package com.bindglam.goldengine.utils

import com.bindglam.goldengine.GoldEngine
import java.math.BigDecimal
import java.text.DecimalFormat

private val DECIMAL_FORMAT = DecimalFormat("###,###")

fun formatCurrency(balance: BigDecimal) = "${DECIMAL_FORMAT.format(balance)}${GoldEngine.instance().config().economy.currencyName.value()}"