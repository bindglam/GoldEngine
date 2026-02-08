package com.bindglam.mint.compatibility

interface Compatibility {
    val requiredPlugin: String

    fun start()

    fun end()
}