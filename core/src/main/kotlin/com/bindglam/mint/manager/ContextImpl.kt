package com.bindglam.mint.manager

import com.bindglam.mint.MintConfiguration
import com.bindglam.mint.MintPlugin
import com.bindglam.mint.utils.plugin

class ContextImpl(private val plugin: MintPlugin) : Context {
    override fun plugin() = this.plugin
    override fun config(): MintConfiguration = this.plugin.config()
    override fun logger() = this.plugin.plugin().logger
}