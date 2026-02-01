package com.bindglam.goldengine.utils

import com.bindglam.goldengine.manager.LanguageManager

fun lang(key: String, vararg args: Any) = LanguageManager.lang().get(key, *args)