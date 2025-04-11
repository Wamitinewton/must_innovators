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
package com.newton.admin.presentation.roleManagement.executives.view.composables

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.unit.*
import com.newton.admin.data.mappers.*
import com.newton.commonUi.animations.*
import com.newton.commonUi.ui.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UsersListModalBottomSheet(
    users: List<User>,
    onUserSelected: (User) -> Unit,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onSearchClicked: () -> Unit,
    isLoading: Boolean,
    errorMessage: String?,
    onErrorRetry: () -> Unit = {},
    onDismiss: () -> Unit
) {
    val modalBottomSheetState = rememberModalBottomSheetState()
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        sheetState = modalBottomSheetState
    ) {
        UserSelectionBottomSheet(
            users = users,
            onUserSelected = onUserSelected,
            searchQuery = searchQuery,
            onSearchQueryChange = onSearchQueryChange,
            onSearchClicked = onSearchClicked,
            isLoading = isLoading,
            hasError = errorMessage,
            onErrorRetry = onErrorRetry
        )
    }
}

@Composable
fun UserSelectionBottomSheet(
    users: List<User>,
    onUserSelected: (User) -> Unit,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onSearchClicked: () -> Unit,
    isLoading: Boolean,
    hasError: String?,
    onErrorRetry: () -> Unit
) {
    Column(
        modifier =
        Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Select User",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        OutlinedTextField(
            value = searchQuery,
            onValueChange = onSearchQueryChange,
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Search user here...", fontWeight = FontWeight.Light) },
            trailingIcon = {
                IconButton(onClick = onSearchClicked) {
                    Icon(Icons.Default.Search, contentDescription = "position")
                }
            }
        )

        LazyColumn {
            if (isLoading) {
                items(8) {
                    UsersShimmer()
                }
            } else if (hasError != null) {
                item {
                    OopsError(errorMessage = hasError, showButton = true, onClick = onErrorRetry)
                }
            } else {
                items(users) { user ->
                    UserListItem(
                        user = user,
                        onClick = { onUserSelected(user) }
                    )
                }
            }
        }
        Spacer(Modifier.height(20.dp))
    }
}

@Composable
fun UserListItem(
    user: User,
    onClick: () -> Unit
) {
    Row(
        modifier =
        Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier =
            Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary)
                .border(1.dp, MaterialTheme.colorScheme.primary, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = user.name.take(1),
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }

        Column(
            modifier =
            Modifier
                .padding(start = 16.dp)
                .weight(1f)
        ) {
            Text(
                text = user.name,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = user.email,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
        }
    }
}

@Composable
fun UsersShimmer() {
    Surface(
        modifier =
        Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        color = Color.Transparent
    ) {
        Row(
            modifier =
            Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ShimmerWithFade(
                modifier =
                Modifier
                    .size(48.dp)
                    .clip(CircleShape)
            ) {
                Box(
                    modifier =
                    Modifier
                        .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3F))
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                ShimmerWithFade(
                    modifier =
                    Modifier
                        .height(12.dp)
                        .fillMaxWidth(.6f)
                        .clip(RoundedCornerShape(4.dp))
                ) {
                    Box(
                        modifier =
                        Modifier
                            .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3F))
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                ShimmerWithFade(
                    modifier =
                    Modifier
                        .height(12.dp)
                        .fillMaxWidth(.4f)
                        .clip(RoundedCornerShape(4.dp))
                ) {
                    Box(
                        modifier =
                        Modifier
                            .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3F))
                    )
                }
            }
        }
    }
}
