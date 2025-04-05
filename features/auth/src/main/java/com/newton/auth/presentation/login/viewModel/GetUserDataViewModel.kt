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
package com.newton.auth.presentation.login.viewModel

import androidx.lifecycle.*
import com.newton.auth.presentation.login.event.*
import com.newton.auth.presentation.login.state.*
import com.newton.core.domain.repositories.*
import dagger.hilt.android.lifecycle.*
import kotlinx.coroutines.flow.*
import javax.inject.*

@HiltViewModel
class GetUserDataViewModel
@Inject
constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val stateHolder =
        UserDataStateHolder(
            authRepository = authRepository,
            viewModelScope = viewModelScope
        )

    val getUserDataState: StateFlow<GetUserDataViewModelState> = stateHolder.state

    fun onEvent(event: GetUserDataEvent) {
        when (event) {
            GetUserDataEvent.GetUserEvent -> {
                stateHolder.getUserData()
            }
        }
    }
}
