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
