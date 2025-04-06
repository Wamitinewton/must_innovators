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
package com.newton.admin.presentation.community.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.*
import com.newton.admin.presentation.community.view.composable.*
import com.newton.admin.presentation.community.viewmodels.*
import com.newton.commonUi.composables.*
import com.newton.network.domain.models.aboutUs.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminCommunityList(
    viewModel: UpdateCommunityViewModel,
    onCommunitySelected: (Community) -> Unit
) {
    val communityState by viewModel.updateCommunityState.collectAsState()
    DefaultScaffold(topBar = {
        TopAppBar(title = { Text("Select Community to update") })
    }, isLoading = communityState.isLoading) {
        when {
            communityState.errorMessage != null -> OopsError(errorMessage = communityState.errorMessage!!)
            communityState.communities.isNotEmpty() -> {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(communityState.communities, key = { it.id }) { community ->
                        CommunityCard(
                            community,
                            onSelectedCommunity = { onCommunitySelected(community) }
                        )
                    }
                }
            }

            else -> OopsError(errorMessage = "Their is an error occurred when fetching communitues")
        }
    }
}
