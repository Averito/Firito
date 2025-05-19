package com.averito.firito.data.managers

import android.content.Context
import android.net.Uri
import com.averito.firito.core.utils.AppLogger
import com.averito.firito.data.database.AppDatabaseHolder
import com.averito.firito.di.qualifiers.DefaultAppLogger
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class DatabaseBackupManager @Inject constructor(
    @ApplicationContext private val context: Context,
    @DefaultAppLogger private val defaultAppLogger: AppLogger
) {
    private val name = "DatabaseBackupManager"
    private val dbName = "firito_db"

    fun exportDatabaseToUri(uri: Uri): Boolean {
        return try {
            defaultAppLogger.debug("$name: exportDatabaseToUri")
            val dbFile = context.getDatabasePath(dbName)

            AppDatabaseHolder.close()

            context.contentResolver.openOutputStream(uri)?.use { output ->
                dbFile.inputStream().use { input -> input.copyTo(output) }
            }

            return true
        } catch (error: Exception) {
            defaultAppLogger.error("$name: exportDatabaseToUri: ${error.message}")
            return false
        }
    }

    fun importDatabaseFromUri(uri: Uri): Boolean {
        return try {
            defaultAppLogger.debug("$name: importDatabaseFromUri")
            val dbFile = context.getDatabasePath(dbName)

            AppDatabaseHolder.close()

            context.contentResolver.openInputStream(uri)?.use { input ->
                dbFile.outputStream().use { output ->
                    input.copyTo(output)
                }
            }

            return true
        } catch (error: Exception) {
            defaultAppLogger.error("$name: importDatabaseFromUri: ${error.message}")
            return false
        }
    }
}
