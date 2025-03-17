package com.newton.database

import com.newton.database.db.AppDatabase
import javax.inject.Inject

class DbCleaner @Inject constructor(
    private val appDatabase: AppDatabase
) {
    fun clearAllTables(){
        appDatabase.clearAllTables()
    }
}