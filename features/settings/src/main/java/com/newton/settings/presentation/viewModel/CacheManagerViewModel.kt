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
package com.newton.settings.presentation.viewModel

import androidx.lifecycle.*
import com.newton.database.dbManager.*
import com.newton.settings.presentation.state.*
import dagger.hilt.android.lifecycle.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.*

@HiltViewModel
class CacheManagerViewModel @Inject constructor(
    private val cacheManager: CacheManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(CacheManagerUiState())
    val uiState: StateFlow<CacheManagerUiState> = _uiState.asStateFlow()

    init {
        refreshCacheSize()
    }

    fun refreshCacheSize() {
        viewModelScope.launch {
            val cacheSize = cacheManager.getCacheSize()
            _uiState.update { it.copy(cacheSize = cacheManager.formatSize(cacheSize)) }
        }
    }

    fun clearCache() {
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isCacheClearing = true) }

                val bytesCleared = cacheManager.clearAppCache()

                val clearingDetails = CacheClearingDetails(
                    totalBytesCleared = bytesCleared,
                    formattedSize = cacheManager.formatSize(bytesCleared)
                )

                _uiState.update {
                    it.copy(
                        isCacheClearing = false,
                        cacheClearingSuccess = true,
                        cacheSize = "0 bytes",
                        cacheClearingDetails = clearingDetails
                    )
                }

                kotlinx.coroutines.delay(2000)
                _uiState.update { it.copy(cacheClearingSuccess = false) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isCacheClearing = false) }
                e.printStackTrace()
            }
        }
    }

    fun resetCacheClearingSuccess() {
        _uiState.update { it.copy(cacheClearingSuccess = false) }
    }
}
