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
package com.newton.sharedprefs.viewModel

import androidx.lifecycle.*
import com.newton.core.enums.*
import com.newton.sharedprefs.domain.*
import dagger.hilt.android.lifecycle.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.*

@HiltViewModel
class ThemeViewModel @Inject constructor(
    private val prefsRepository: PrefsRepository
) : ViewModel() {

    private val _themeState = MutableStateFlow(
        ThemeUiState(
            themeMode = getInitialThemeMode()
        )
    )

    val themeState: StateFlow<ThemeUiState> = _themeState.asStateFlow()

    init {
        viewModelScope.launch {
            prefsRepository.observeThemeMode().collect { prefs ->
                val themeMode = when {
                    prefs.systemThemeEnabled -> ThemeMode.SYSTEM
                    prefs.darkThemeEnabled -> ThemeMode.DARK
                    else -> ThemeMode.LIGHT
                }
                _themeState.value = ThemeUiState(themeMode)
            }
        }
    }



    /**
     * Handles theme-related events
     */
    fun handleEvent(event: ThemeEvent) {
        when (event) {
            is ThemeEvent.ToggleTheme -> {
                val currentMode = _themeState.value.themeMode
                val newMode = when (currentMode) {
                    ThemeMode.DARK -> ThemeMode.LIGHT
                    ThemeMode.LIGHT -> ThemeMode.SYSTEM
                    ThemeMode.SYSTEM -> ThemeMode.DARK
                }
                setThemeMode(newMode)
            }
            is ThemeEvent.SetThemeMode -> {
                setThemeMode(event.themeMode)
            }
        }
    }


    private fun updateThemeState() {
        val isDarkTheme = prefsRepository.isDarkThemeEnabled()
        val isSystemTheme = prefsRepository.isSystemThemeEnabled()

        val themeMode = when {
            isSystemTheme -> ThemeMode.SYSTEM
            isDarkTheme -> ThemeMode.DARK
            else -> ThemeMode.LIGHT
        }

        _themeState.value = ThemeUiState(themeMode = themeMode)
    }

    /**
     * Initializes the theme mode based on saved preferences
     */
    private fun getInitialThemeMode(): ThemeMode {
        return if (prefsRepository.isDarkThemeEnabled()) {
            ThemeMode.DARK
        } else {
            ThemeMode.LIGHT
        }
    }

    private fun setThemeMode(themeMode: ThemeMode) {
        viewModelScope.launch {
            when (themeMode) {
                ThemeMode.LIGHT -> {
                    prefsRepository.setSystemThemeEnabled(false)
                    prefsRepository.setDarkThemeEnabled(false)
                }
                ThemeMode.DARK -> {
                    prefsRepository.setSystemThemeEnabled(false)
                    prefsRepository.setDarkThemeEnabled(true)
                }
                ThemeMode.SYSTEM -> {
                    prefsRepository.setSystemThemeEnabled(true)
                }
            }
        }
    }


}



sealed class ThemeEvent {
    data object ToggleTheme : ThemeEvent()
    data class SetThemeMode(val themeMode: ThemeMode) : ThemeEvent()
}


data class ThemeUiState(
    val themeMode: ThemeMode = ThemeMode.LIGHT
)
