package com.bindglam.mint.account.log

import java.util.LinkedList

class TransactionLoggerImpl : TransactionLogger {
    private val logs = LinkedList<Log>()

    fun log(log: Log) {
        logs.add(log)
    }

    override fun logs() = logs
}