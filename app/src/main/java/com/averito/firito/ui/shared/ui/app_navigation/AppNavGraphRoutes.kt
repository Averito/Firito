package com.averito.firito.ui.shared.ui.app_navigation

sealed class AppNavGraphRoutes {
    object Main {
        const val ROUTE = "main"
        const val TITLE = "Главная"
    }

    object MainFood {
        const val ROUTE = "main/{id}"
        const val TITLE = "Редактирование еды"

        fun getRoute(id: Int): String {
            return ROUTE.replace("{id}", id.toString())
        }
        fun getRoute(id: Long): String {
            return ROUTE.replace("{id}", id.toString())
        }
    }

    object Settings {
        const val ROUTE = "settings"
        const val TITLE = "Настройки"
    }

    object Journal {
        const val ROUTE = "journal"
        const val TITLE = "Журнал"
    }

    object JournalDetails {
        const val ROUTE = "journal/{date}"
        const val TITLE = "Подробности"

        fun getRoute(date: Long): String {
            return ROUTE.replace("{date}", date.toString())
        }
    }

    object Statistics {
        const val ROUTE = "statistics"
        const val TITLE = "Статистика"
    }

    object StatisticsCategory {
        enum class CATEGORY {
            ACTIVITY,
            CALORIES
        }

        const val ROUTE = "statistics/{category}"
        const val TITLE = "Статистика {category}"

        fun getRoute(category: CATEGORY): String {
            return ROUTE.replace("{category}", category.name.lowercase())
        }

        fun getTitle(category: CATEGORY): String {
            val categoryStr = when (category) {
                CATEGORY.CALORIES -> "калорий"
                CATEGORY.ACTIVITY -> "активности"
            }
            return TITLE.replace("{category}", categoryStr)
        }
    }
}