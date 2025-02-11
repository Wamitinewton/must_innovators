package com.newton.database.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.newton.database.dao.EventDao
import com.newton.database.db.EventDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object EventDatabaseModule {
    private const val DATABASE_NAME = "event_database"

    @Provides
    @Singleton
    fun provideEventDatabase(app: Application): EventDatabase {
        return Room.databaseBuilder(
            app,
            EventDatabase::class.java,
            DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }
    @Provides
    @Singleton
    fun provideEventDao(db:EventDatabase): EventDao{
        return db.eventDao()
    }
}