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
package com.newton.sharedprefs.data

import com.newton.sharedprefs.domain.*
import com.newton.sharedprefs.prefsManager.*
import kotlinx.coroutines.flow.*
import javax.inject.*

@Singleton
class PrefsRepositoryImpl @Inject constructor(
    private val preferenceManager: PreferenceManager
) : PrefsRepository {

    override fun getUserOnboardingStatus(): Boolean {
        return preferenceManager.getBoolean(PrefsConstants.KEY_USER_ONBOARDED)
    }

    override fun setUserOnboardingStatus(completed: Boolean) {
        preferenceManager.putBoolean(PrefsConstants.KEY_USER_ONBOARDED, completed)
    }

    override fun hasCompletedPreferences(): Boolean {
        return preferenceManager.getBoolean(PrefsConstants.KEY_PREFERENCES_COMPLETED, false)
    }

    override fun setPreferencesCompleted(completed: Boolean) {
        preferenceManager.putBoolean(PrefsConstants.KEY_PREFERENCES_COMPLETED, completed)
    }

    override fun isDarkThemeEnabled(): Boolean {
        return preferenceManager.getBoolean(PrefsConstants.KEY_THEME_MODE, false)
    }

    override fun setDarkThemeEnabled(enabled: Boolean) {
        preferenceManager.putBoolean(PrefsConstants.KEY_THEME_MODE, enabled)
    }

    override fun observeThemeMode(): Flow<ThemePreferences> {
        val themeModeFlow = preferenceManager.observeBooleanPreference(PrefsConstants.KEY_THEME_MODE)
        val systemThemeFlow = preferenceManager.observeBooleanPreference(PrefsConstants.KEY_SYSTEM_THEME)

        return combine(themeModeFlow, systemThemeFlow) { dark, system ->
            ThemePreferences(darkThemeEnabled = dark, systemThemeEnabled = system)
        }
    }

    override fun isSystemThemeEnabled(): Boolean {
        return preferenceManager.getBoolean(PrefsConstants.KEY_SYSTEM_THEME, false)
    }

    override fun setSystemThemeEnabled(enabled: Boolean) {
        preferenceManager.putBoolean(PrefsConstants.KEY_SYSTEM_THEME, enabled)
    }

    override fun isVerificationPending(): Boolean {
        return preferenceManager.getBoolean(PrefsConstants.KEY_VERIFICATION_PENDING, false)
    }

    override fun setVerificationPending(pending: Boolean) {
        preferenceManager.putBoolean(PrefsConstants.KEY_VERIFICATION_PENDING, pending)
    }

    override fun getUserEmail(): String {
        return preferenceManager.getString(PrefsConstants.KEY_USER_EMAIL)
    }

    override fun setUserEmail(email: String) {
        preferenceManager.putString(PrefsConstants.KEY_USER_EMAIL, email)
    }

    override fun clearUserEmail() {
        preferenceManager.putString(PrefsConstants.KEY_USER_EMAIL, "")
    }

    override fun wasSignupSuccessful(): Boolean {
        return preferenceManager.getBoolean(PrefsConstants.KEY_SIGNUP_SUCCESSFUL, false)
    }

    override fun setSignupSuccessful(isSuccessful: Boolean) {
        preferenceManager.putBoolean(PrefsConstants.KEY_SIGNUP_SUCCESSFUL, isSuccessful)
    }
}
