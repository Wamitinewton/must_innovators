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
package com.newton.sharedprefs.di

import android.content.*
import com.newton.sharedprefs.data.*
import com.newton.sharedprefs.domain.*
import com.newton.sharedprefs.prefsManager.*
import dagger.*
import dagger.hilt.*
import dagger.hilt.android.qualifiers.*
import dagger.hilt.components.*
import javax.inject.*

@Module
@InstallIn(SingletonComponent::class)
object PrefsModule {

    @Provides
    @Singleton
    fun providePrefsManager(
        @ApplicationContext context: Context
    ): PreferenceManager {
        return PreferenceManager(context)
    }

    @Provides
    @Singleton
    fun providePrefsRepository(
        preferenceManager: PreferenceManager
    ): PrefsRepository {
        return PrefsRepositoryImpl(preferenceManager)
    }
}
