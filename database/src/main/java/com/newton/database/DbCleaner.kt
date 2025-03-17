package com.newton.database

import com.newton.database.db.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DbCleaner @Inject constructor(
    private val appDatabase: AppDatabase
) {
    suspend fun clearAllTables() {
        withContext(Dispatchers.IO) {
            appDatabase.clearAllTables()
        }
    }
}