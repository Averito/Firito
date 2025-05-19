package com.averito.firito.ui.screens.settings

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.averito.firito.data.managers.DatabaseBackupManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsScreenViewModel @Inject constructor(
    private val databaseBackupManager: DatabaseBackupManager
) : ViewModel() {
    fun exportToUri(uri: Uri): Boolean {
        return databaseBackupManager.exportDatabaseToUri(uri)
    }

    fun importFromUri(uri: Uri): Boolean {
        return databaseBackupManager.importDatabaseFromUri(uri)
    }
}