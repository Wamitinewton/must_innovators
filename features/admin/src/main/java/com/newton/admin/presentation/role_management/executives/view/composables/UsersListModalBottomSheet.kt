package com.newton.admin.presentation.role_management.executives.view.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.newton.admin.data.mappers.User
import com.newton.common_ui.composables.OopsError
import com.newton.common_ui.composables.animation.custom_animations.ShimmerWithFade

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UsersListModalBottomSheet(
    users: List<User>,
    onUserSelected: (User) -> Unit,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onSearchClicked: () -> Unit,
    isLoading: Boolean,
    errorMessage:String?,
    onErrorRetry:()->Unit = {},
    onDismiss:()->Unit
) {
    val modalBottomSheetState = rememberModalBottomSheetState()
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        sheetState = modalBottomSheetState,
    ) {
        UserSelectionBottomSheet(
            users = users,
            onUserSelected = onUserSelected,
            searchQuery = searchQuery,
            onSearchQueryChange = onSearchQueryChange,
            onSearchClicked = onSearchClicked,
            isLoading = isLoading,
            hasError = errorMessage,
            onErrorRetry = onErrorRetry,
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
        modifier = Modifier
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
            },

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
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
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
            modifier = Modifier
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
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        color = Color.Transparent
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ShimmerWithFade(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)

            ) {
                Box(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3F))
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                ShimmerWithFade(
                    modifier = Modifier
                        .height(12.dp)
                        .fillMaxWidth(.6f)
                        .clip(RoundedCornerShape(4.dp))
                ) {
                    Box(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3F))
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                ShimmerWithFade(
                    modifier = Modifier
                        .height(12.dp)
                        .fillMaxWidth(.4f)
                        .clip(RoundedCornerShape(4.dp))
                ) {
                    Box(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3F))
                    )
                }
            }
        }
    }
}