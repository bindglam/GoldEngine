package com.bindglam.mint.manager

import com.bindglam.mint.account.Account
import com.bindglam.mint.account.AccountImpl
import com.bindglam.mint.account.log.TransactionLoggerImpl
import com.bindglam.mint.utils.Constants
import java.util.*

object AccountManagerImpl : AccountManager {
    const val ACCOUNTS_TABLE_NAME = "${Constants.PLUGIN_ID}_accounts"
    const val LOGS_TABLE_NAME = "${Constants.PLUGIN_ID}_logs"

    override fun start(context: Context) {
        context.plugin().database().getConnection { connection ->
            AccountImpl.createTable(connection)
            TransactionLoggerImpl.createTable(connection)
        }
    }

    override fun end(context: Context) {
    }

    override fun getAccount(uuid: UUID): Account {
        return AccountImpl(uuid)
    }
}