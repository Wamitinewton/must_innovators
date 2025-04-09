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
package com.newton.sharedprefs.domain

import kotlinx.coroutines.flow.*

interface PrefsRepository {
    fun getUserOnboardingStatus(): Boolean
    fun setUserOnboardingStatus(completed: Boolean)
    fun hasCompletedPreferences(): Boolean
    fun setPreferencesCompleted(completed: Boolean)

    fun isDarkThemeEnabled(): Boolean
    fun setDarkThemeEnabled(enabled: Boolean)
    fun observeThemeMode(): Flow<ThemePreferences>
    fun isSystemThemeEnabled(): Boolean
    fun setSystemThemeEnabled(enabled: Boolean)

    fun isVerificationPending(): Boolean
    fun setVerificationPending(pending: Boolean)

    fun getUserEmail(): String
    fun setUserEmail(email: String)
    fun clearUserEmail()

    fun wasSignupSuccessful(): Boolean
    fun setSignupSuccessful(isSuccessful: Boolean)
}
