package com.bindglam.goldengine.utils

import com.bindglam.goldengine.GoldEngine

fun lang(key: String, vararg args: Any) = GoldEngine.instance().languageManager().lang().get(key, *args)