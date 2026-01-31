package com.bindglam.goldengine.manager

import com.bindglam.goldengine.GoldEngineConfiguration
import com.bindglam.goldengine.GoldEnginePlugin
import com.bindglam.goldengine.utils.plugin

class ContextImpl(private val plugin: GoldEnginePlugin) : Context {
    override fun plugin() = this.plugin
    override fun config(): GoldEngineConfiguration = this.plugin.config()
    override fun logger() = this.plugin.plugin().logger
}