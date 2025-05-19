package com.averito.firito.ui.screens.journal

sealed class JournalTab(val title: String) {
    object Calendar : JournalTab("Календарь")
    object List : JournalTab("Список")

    companion object {
        val all = listOf(Calendar, List)
    }
}
