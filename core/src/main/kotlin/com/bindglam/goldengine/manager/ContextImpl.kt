package com.bindglam.goldengine.manager

import com.bindglam.goldengine.GoldEngineConfiguration
import com.bindglam.goldengine.GoldEnginePlugin

class ContextImpl(private val plugin: GoldEnginePlugin) : Context {
    override fun plugin() = this.plugin

    override fun config(): GoldEngineConfiguration = this.plugin.config()
}