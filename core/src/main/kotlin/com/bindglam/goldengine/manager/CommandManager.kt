package com.bindglam.goldengine.manager

import com.bindglam.goldengine.GoldEngine
import com.bindglam.goldengine.account.Operation
import com.bindglam.goldengine.utils.formatCurrency
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
import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import java.math.BigDecimal

object CommandManager : Managerial {
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
                                    sender.sendMessage(lang("command_money_balance_get", target.name ?: "Unknown", formatCurrency(account.balance())))
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
                                    sender.sendMessage(lang("command_money_balance_get", target.name ?: "Unknown", formatCurrency(account.balance())))
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
                                    sender.sendMessage(lang("command_money_balance_get", target.name ?: "Unknown", formatCurrency(account.balance())))
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
                                    sender.sendMessage(lang("command_money_balance_get", target.name ?: "Unknown", formatCurrency(account.balance())))
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

                                player.sendMessage(lang("command_money_send_sender", target.name ?: "Unknown", formatCurrency(account.balance())))
                                target.player?.sendMessage(lang("command_money_send_target", player.name, formatCurrency(targetAccount.balance())))
                            } }
                        } }
                    }),
                CommandAPICommand("자랑")
                    .executesPlayer(PlayerCommandExecutor { player, _ ->
                        if(!GoldEngine.instance().config().features.boast.enabled.value()) {
                            player.sendMessage(lang("error_inavailable_feature"))
                            return@PlayerCommandExecutor
                        }

                        AccountManagerImpl.getAccount(player.uniqueId).thenAccept { account -> account.use {
                            val cost = BigDecimal.valueOf(GoldEngine.instance().config().features.boast.cost.value())

                            if(account.balance() < cost) {
                                player.sendMessage(lang("error_insufficient_money_with_cost", formatCurrency(cost)))
                                return@thenAccept
                            }

                            Bukkit.broadcast(lang("command_money_boast_broadcast", player.name, formatCurrency(account.balance())))

                            account.modifyBalance(cost, Operation.SUBTRACT)

                            player.sendMessage(lang("command_money_boast_sender", formatCurrency(account.balance())))
                        } }
                    })
            )
            .executesPlayer(PlayerCommandExecutor { player, _ ->
                AccountManagerImpl.getAccount(player.uniqueId).thenAccept { account ->
                    player.sendMessage(lang("command_money", formatCurrency(account.balance())))
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