package com.bindglam.goldengine.language

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage

class LanguageComponent(private val raw: String) {
    fun component(vararg args: Any): Component {
        var argIdx = 0

        var result = raw
        while (result.contains("%s")) {
            result = result.replaceFirst("%s".toRegex(), args[argIdx++].toString())
        }
        return MiniMessage.miniMessage().deserialize(result)
    }
}