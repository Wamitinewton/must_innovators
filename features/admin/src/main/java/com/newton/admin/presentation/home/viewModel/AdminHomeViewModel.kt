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
package com.newton.admin.presentation.home.viewModel

import androidx.lifecycle.*
import com.newton.admin.presentation.home.events.*
import com.newton.admin.presentation.home.states.*
import kotlinx.coroutines.flow.*

class AdminHomeViewModel : ViewModel() {
    private val _adminState = MutableStateFlow(AdminHomeState())
    val adminState: StateFlow<AdminHomeState> = _adminState.asStateFlow()

    fun handleEvents(event: AdminHomeEvent) {
        when (event) {
            is AdminHomeEvent.Sheet -> _adminState.update { it.copy(isShowChoices = event.shown) }
        }
    }
}
