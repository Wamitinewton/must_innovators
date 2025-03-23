package com.newton.admin.presentation.community.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.newton.admin.presentation.community.view.composable.CommunityCard
import com.newton.admin.presentation.community.viewmodels.UpdateCommunityViewModel
import com.newton.common_ui.composables.DefaultScaffold
import com.newton.common_ui.composables.OopsError
import com.newton.core.domain.models.about_us.Community
import com.newton.core.navigation.NavigationRoutes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminCommunityList(
    viewModel: UpdateCommunityViewModel,
    onCommunitySelected: (Community) -> Unit
) {
    val communityState by viewModel.updateCommunityState.collectAsState()
    DefaultScaffold(topBar = {
        TopAppBar(title = { Text("Select Community to update") })
    }) {
        if (communityState.isLoading) {
            Box(modifier = Modifier.fillMaxSize()){
                CircularProgressIndicator()
            }
        } else if (communityState.errorMessage != null) {
            OopsError(errorMessage = communityState.errorMessage!!)
        } else if (communityState.isSuccess) {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(communityState.communities, key = { it.id }) { community ->
                    CommunityCard(
                        community,
                        onSelectedCommunity = onCommunitySelected
                    )
                }
            }
        } else {
            OopsError(
                errorMessage = "Unknown error occurred"
            )
        }

    }

}