package com.newton.communities.presentation.view_model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.newton.core.domain.models.about_us.Community
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AboutUsSharedViewModel
@Inject
constructor(

): ViewModel() {
    var selectedCommunity by mutableStateOf<Community?>(null)
        private set


    fun selectCommunity(community: Community) {
        selectedCommunity = community
    }

    fun clearSelectedCommunity() {
        selectedCommunity = null
    }
}