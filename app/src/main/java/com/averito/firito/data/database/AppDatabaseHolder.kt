package com.averito.firito.data.database

import javax.inject.Singleton

@Singleton
object AppDatabaseHolder {
    var db: AppDatabase? = null

    fun close() {
        db?.close()
        db = null
    }
}
