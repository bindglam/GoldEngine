package com.bindglam.mint.test.utils

import com.bindglam.mint.Mint
import com.bindglam.mint.currency.Currency

fun won(): Currency = Mint.instance().currencyManager().registry().get("won").orElseThrow()