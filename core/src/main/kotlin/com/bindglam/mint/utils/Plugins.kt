package com.bindglam.mint.utils

import com.bindglam.mint.Mint
import com.bindglam.mint.MintPlugin
import org.bukkit.plugin.java.JavaPlugin
import org.slf4j.Logger

fun logger(): Logger = Mint.instance().plugin().slF4JLogger

fun MintPlugin.plugin(): JavaPlugin = this as JavaPlugin