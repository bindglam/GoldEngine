package com.bindglam.goldengine.manager

import com.bindglam.goldengine.GoldEngine
import com.bindglam.goldengine.account.Operation
import com.bindglam.goldengine.utils.lang
import com.bindglam.goldengine.utils.plugin
import dev.jorel.commandapi.CommandAPI
import dev.jorel.commandapi.CommandAPIBukkitConfig
import dev.jorel.commandapi.CommandAPICommand
import dev.jorel.commandapi.CommandPermission
import dev.jorel.commandapi.arguments.DoubleArgument
import dev.jorel.commandapi.arguments.OfflinePlayerArgument
import dev.jorel.commandapi.executors.CommandExecutor
import dev.jorel.commandapi.executors.PlayerCommandExecutor
import org.bukkit.OfflinePlayer
import java.math.BigDecimal
import java.text.DecimalFormat

object CommandManager : Managerial {
    private val decimalFormat = DecimalFormat("###,###")

    override fun start() {
        CommandAPI.onLoad(CommandAPIBukkitConfig(GoldEngine.instance().plugin()))

        CommandAPICommand("goldengine")
            .withPermission(CommandPermission.OP)
            .withSubcommands(
                CommandAPICommand("balance")
                    .withSubcommands(
                        CommandAPICommand("get")
                            .withArguments(OfflinePlayerArgument("target"))
                            .executes(CommandExecutor { sender, args ->
                                val target = args["target"] as OfflinePlayer

                                AccountManagerImpl.getAccount(target.uniqueId).thenAccept { account ->
                                    sender.sendMessage(lang("command_money_balance_get", target.name ?: "Unknown", "${decimalFormat.format(account.balance())}${GoldEngine.instance().config().economy.currencyName.value()}"))
                                    account.close()
                                }
                            }),
                        CommandAPICommand("set")
                            .withArguments(OfflinePlayerArgument("target"), DoubleArgument("amount"))
                            .executes(CommandExecutor { sender, args ->
                                val target = args["target"] as OfflinePlayer
                                val amount = args["amount"] as Double

                                AccountManagerImpl.getAccount(target.uniqueId).thenAccept { account ->
                                    account.balance(BigDecimal.valueOf(amount))
                                    sender.sendMessage(lang("command_money_balance_get", target.name ?: "Unknown", "${decimalFormat.format(account.balance())}${GoldEngine.instance().config().economy.currencyName.value()}"))
                                    account.close()
                                }
                            }),
                        CommandAPICommand("add")
                            .withArguments(OfflinePlayerArgument("target"), DoubleArgument("amount"))
                            .executes(CommandExecutor { sender, args ->
                                val target = args["target"] as OfflinePlayer
                                val amount = args["amount"] as Double

                                AccountManagerImpl.getAccount(target.uniqueId).thenAccept { account ->
                                    account.modifyBalance(BigDecimal.valueOf(amount), Operation.ADD)
                                    sender.sendMessage(lang("command_money_balance_get", target.name ?: "Unknown", "${decimalFormat.format(account.balance())}${GoldEngine.instance().config().economy.currencyName.value()}"))
                                    account.close()
                                }
                            }),
                        CommandAPICommand("subtract")
                            .withArguments(OfflinePlayerArgument("target"), DoubleArgument("amount"))
                            .executes(CommandExecutor { sender, args ->
                                val target = args["target"] as OfflinePlayer
                                val amount = args["amount"] as Double

                                AccountManagerImpl.getAccount(target.uniqueId).thenAccept { account ->
                                    account.modifyBalance(BigDecimal.valueOf(amount), Operation.SUBTRACT)
                                    sender.sendMessage(lang("command_money_balance_get", target.name ?: "Unknown", "${decimalFormat.format(account.balance())}${GoldEngine.instance().config().economy.currencyName.value()}"))
                                    account.close()
                                }
                            })
                    )
            )
            .register()

        CommandAPICommand("돈")
            .withSubcommands(
                CommandAPICommand("보내기")
                    .withArguments(OfflinePlayerArgument("받는플레이어"), DoubleArgument("돈"))
                    .executesPlayer(PlayerCommandExecutor { player, args ->
                        val target = args["받는플레이어"] as OfflinePlayer
                        val amount = BigDecimal.valueOf(args["돈"] as Double)

                        AccountManagerImpl.getAccount(player.uniqueId).thenAccept { account -> account.use {
                            AccountManagerImpl.getAccount(target.uniqueId).thenAccept { targetAccount -> targetAccount.use {
                                if(account.balance() < amount) {
                                    player.sendMessage(lang("error_insufficient_money"))
                                    return@thenAccept
                                }
                                targetAccount.modifyBalance(amount, Operation.ADD)
                                account.modifyBalance(amount, Operation.SUBTRACT)

                                player.sendMessage(lang("command_money_send_sender", target.name ?: "Unknown", "${decimalFormat.format(account.balance())}${GoldEngine.instance().config().economy.currencyName.value()}"))
                                target.player?.sendMessage(lang("command_money_send_target", player.name, "${decimalFormat.format(targetAccount.balance())}${GoldEngine.instance().config().economy.currencyName.value()}"))
                            } }
                        } }
                    })
            )
            .executesPlayer(PlayerCommandExecutor { player, _ ->
                AccountManagerImpl.getAccount(player.uniqueId).thenAccept { account ->
                    player.sendMessage(lang("command_money", "${decimalFormat.format(account.balance())}${GoldEngine.instance().config().economy.currencyName.value()}"))
                    account.close()
                }
            })
            .register()

        CommandAPI.onEnable()
    }

    override fun end() {
        CommandAPI.onDisable()
    }
}