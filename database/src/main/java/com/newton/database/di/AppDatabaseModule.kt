/**
 * Copyright (c) 2025 Meru Science Innovators Club
 *
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of Meru Science Innovators Club.
 * You shall not disclose such confidential information and shall use it only in accordance
 * with the terms of the license agreement you entered into with Meru Science Innovators Club.
 *
 * Unauthorized copying of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 *
 * NO WARRANTY: This software is provided "as is" without warranty of any kind,
 * either express or implied, including but not limited to the implied warranties
 * of merchantability and fitness for a particular purpose.
 */
package com.newton.database.di

import android.app.*
import androidx.room.*
import com.newton.database.db.*
import dagger.*
import dagger.hilt.*
import dagger.hilt.components.*
import javax.inject.*

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

    @Provides
    @Singleton
    fun provideUserFeedbackDao(db: AppDatabase) = db.userFeedback

    @Provides
    @Singleton
    fun provideEventsFeedbackDao(db: AppDatabase) = db.eventsFeedback

    @Provides
    @Singleton
    fun providePartnersDao(db: AppDatabase) = db.partnersDao

    @Provides
    @Singleton
    fun provideClubBioDao(db: AppDatabase) = db.clubBioDao

    @Provides
    @Singleton
    fun provideTestimonialsDao(db: AppDatabase) = db.testimonialsDao
}
