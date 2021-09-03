package com.github.purofle.mindustryantiaddiction

import arc.util.CommandHandler
import arc.util.Log
import mindustry.gen.Player
import mindustry.mod.Plugin

class AntiAddiction: Plugin() {
    override fun init() {
        Log.info("欢迎使用防沉迷Mod")
    }

    override fun registerClientCommands(handler: CommandHandler) {
        handler.register<Player>("auth", "[name] [idCard]", "验证") { arg, _ ->
            if ( arg.size == 2) {
                val id = arg[2]
            }
        }
    }
}