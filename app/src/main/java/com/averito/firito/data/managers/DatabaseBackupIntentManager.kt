package com.averito.firito.data.managers

import android.content.Intent
import com.averito.firito.core.utils.AppLogger
import com.averito.firito.di.qualifiers.DefaultAppLogger
import javax.inject.Inject

class DatabaseBackupIntentManager @Inject constructor(
    @DefaultAppLogger private val defaultAppLogger: AppLogger
) {
    private val name = "DatabaseBackupIntentManager"

    fun createExportIntent(): Intent {
        defaultAppLogger.debug("$name: createExportIntent")

        return Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "application/octet-stream"
            putExtra(Intent.EXTRA_TITLE, "firito_backup.db")
        }
    }

    fun createImportIntent(): Intent {
        defaultAppLogger.debug("$name: createImportIntent")

        return  Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "application/octet-stream"
        }
    }
}
