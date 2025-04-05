package com.newton.database

import com.newton.database.db.*
import kotlinx.coroutines.*
import javax.inject.*

class DbCleaner
@Inject
constructor(
    private val appDatabase: AppDatabase
) {
    suspend fun clearAllTables() {
        withContext(Dispatchers.IO) {
            appDatabase.clearAllTables()
        }
    }
}
