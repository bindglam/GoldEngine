package com.bindglam.mint.utils

import com.bindglam.mint.manager.LanguageManager

fun lang(key: String, vararg args: Any) = LanguageManager.lang().get(key, *args)