package com.github.purofle.mindustryantiaddiction

import arc.Events
import arc.util.CommandHandler
import arc.util.Log
import mindustry.game.EventType
import mindustry.gen.Call
import mindustry.gen.Player
import mindustry.mod.Plugin

class AntiAddiction: Plugin() {
    private val data = HashMap<String, List<Boolean>>()
    override fun init() {
        Log.info("欢迎使用防沉迷Mod")
        Events.on(EventType.PlayerJoin::class.java) {
            if (!data.containsKey(it.player.uuid())) {
                data[it.player.uuid()] = listOf(false, false)
            }
        }
    }

    override fun registerClientCommands(handler: CommandHandler) {
        handler.register<Player>("auth", "[name] [idCard]", "验证(请先阅读用户协议:https://github.com/purofle/MindustryAntiAddiction/blob/main/PrivacyAgreement.md)") { arg, player ->
            if (arg.size == 2) {
                val id = arg[1]
                if (data.containsKey(player.uuid())) {
                    Call.sendMessage("您已实名","validate", player)
                } else {
                    val validate = ValidateIdCard.validate(id)
                    data[player.uuid()] = validate
                    Log.info(validate)
                    if (validate[0]) {
                        Call.sendMessage("实名成功!", "validate", player)
                        if (!validate[1]) {
                            Call.sendMessage(
                                "实名成功!您仅能在周五、周六、周日和法定节假日的 20 时至 21 时， 游戏1 小时.",
                                "validate",
                                player
                            )
                        }
                    } else {
                        data.remove(player.uuid())
                        Call.sendMessage("请检查您的身份证号或姓名是否输入错误", "validate", player)
                    }
                }
            }
        }
    }

    override fun registerServerCommands(handler: CommandHandler) {
        handler.register("info", "查询信息") {
            Log.info("格式:\ndata              玩家UUID[是否实名,是否满18岁]")
            Log.info(data)
        }
    }
}