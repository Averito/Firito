package com.averito.firito.ui.shared.app_navigation

import java.time.LocalDate

sealed class AppNavGraphRoutes {
    object Main {
        const val route = "main"
        const val title = "Главная"
    }

    object MainFood {
        const val route = "main/{id}"
        val title = "Редактирование еды"

        fun getRoute(id: Int): String {
            return route.replace("{id}", id.toString())
        }
        fun getRoute(id: Long): String {
            return route.replace("{id}", id.toString())
        }
    }

    object Settings {
        const val route = "settings"
        const val title = "Настройки"
    }

    object Journal {
        const val route = "journal"
        const val title = "Журнал"
    }

    object JournalDetails {
        const val route = "journal/{date}"
        const val title = "Подробности"

        fun getRoute(date: Long): String {
            return route.replace("{date}", date.toString())
        }
    }
}