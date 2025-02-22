package com.newton.database.di

import android.app.Application
import androidx.room.Room
import com.newton.database.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)

object AppDatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(app: Application): AppDatabase {
        return Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            "meru_innovators.db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideUserDao(db: AppDatabase) = db.userDao

    @Provides
    @Singleton
    fun provideEventDao(db: AppDatabase) = db.eventDao

    @Provides
    @Singleton
    fun provideTicketDao(db: AppDatabase) = db.ticketDao
}