package com.bindglam.mint.compatibility.vault

import com.bindglam.mint.Mint
import com.bindglam.mint.compatibility.Compatibility
import com.bindglam.mint.utils.logger
import com.bindglam.mint.utils.plugin
import net.milkbowl.vault.economy.Economy
import org.bukkit.Bukkit
import org.bukkit.plugin.ServicePriority

object VaultCompatibility : Compatibility {
    override val requiredPlugin = "Vault"

    override fun start() {
        logger().info("Vault hooked!")

        Bukkit.getServicesManager().register(Economy::class.java, VaultEconomy, Mint.instance().plugin(), ServicePriority.Normal)
    }

    override fun end() {
        Bukkit.getServicesManager().unregister(VaultEconomy)
    }
}