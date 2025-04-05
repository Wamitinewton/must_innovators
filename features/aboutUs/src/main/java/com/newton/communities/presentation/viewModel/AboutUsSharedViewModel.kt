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
package com.newton.communities.presentation.viewModel

import androidx.compose.runtime.*
import androidx.lifecycle.*
import com.newton.core.domain.models.aboutUs.*
import dagger.hilt.android.lifecycle.*
import javax.inject.*

@HiltViewModel
class AboutUsSharedViewModel
@Inject
constructor() : ViewModel() {
    var selectedCommunity by mutableStateOf<Community?>(null)
        private set

    fun selectCommunity(community: Community) {
        selectedCommunity = community
    }

    fun clearSelectedCommunity() {
        selectedCommunity = null
    }
}
