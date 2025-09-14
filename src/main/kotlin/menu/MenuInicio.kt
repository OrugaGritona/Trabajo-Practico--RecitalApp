package main.kotlin.menu

import main.kotlin.utils.Emojis

enum class MenuInicio(val option: Int, val description: String) {
    LOGIN(1, "${Emojis.CHECK} Login"),
    REGISTER(2, "${Emojis.NEW} Registrarse"),
    EXIT(0, "$ Salir");

    companion object {
        fun election (value: Int): MenuInicio? {
            return values().find { it.option == value }
        }
    }
}
