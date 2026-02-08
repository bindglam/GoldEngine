package com.bindglam.mint.manager

import com.bindglam.mint.account.AbstractAccount
import com.bindglam.mint.account.Account
import com.bindglam.mint.account.OfflineAccount
import com.bindglam.mint.account.OfflineAccountImpl
import com.bindglam.mint.account.OnlineAccount
import com.bindglam.mint.account.OnlineAccountImpl
import com.bindglam.mint.account.log.TransactionLoggerImpl
import com.bindglam.mint.utils.Constants
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.util.*
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ConcurrentHashMap

object AccountManagerImpl : AccountManager {
    const val ACCOUNTS_TABLE_NAME = "${Constants.PLUGIN_ID}_accounts"
    const val LOGS_TABLE_NAME = "${Constants.PLUGIN_ID}_logs"

    private val onlineAccounts = ConcurrentHashMap<UUID, OnlineAccount>()

    override fun start(context: Context) {
        context.plugin().database().getConnection { connection ->
            AbstractAccount.createTable(connection)
            TransactionLoggerImpl.createTable(connection)
        }

        Bukkit.getOnlinePlayers().forEach { loadOnlineAccount(it) }
    }

    override fun end(context: Context) {
        Bukkit.getOnlinePlayers().forEach { disposeOnlineAccount(it) }
    }

    fun loadOnlineAccount(player: Player) {
        CompletableFuture.runAsync {
            onlineAccounts[player.uniqueId] = OnlineAccountImpl(player.uniqueId)
        }
    }

    fun disposeOnlineAccount(player: Player) {
        CompletableFuture.runAsync {
            onlineAccounts.remove(player.uniqueId)?.save()
        }
    }

    override fun getAccount(uuid: UUID): CompletableFuture<out Account> {
        return if(onlineAccounts.containsKey(uuid))
            CompletableFuture.completedFuture(getOnlineAccount(uuid)!!)
        else
            getOfflineAccount(uuid)
    }

    override fun getOnlineAccount(uuid: UUID): OnlineAccount? = onlineAccounts[uuid]

    override fun getOfflineAccount(uuid: UUID): CompletableFuture<OfflineAccount> =
        CompletableFuture.supplyAsync { OfflineAccountImpl(uuid) }
}