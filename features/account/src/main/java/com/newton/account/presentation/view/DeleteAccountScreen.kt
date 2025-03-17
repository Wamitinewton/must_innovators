package com.newton.account.presentation.view


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.newton.account.presentation.composables.account.DeleteAccountConfirmationDialog
import com.newton.account.presentation.composables.account.DeleteConsequenceCard
import com.newton.account.presentation.events.DeleteAccountEvent
import com.newton.account.presentation.events.DeleteAccountNavigationEvent
import com.newton.account.presentation.viewmodel.AccountManagementViewModel
import com.newton.common_ui.composables.DefaultScaffold

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeleteAccountScreen(
    onNavigateBack: () -> Unit,
    onNavigateToAccountDeleted: () -> Unit,
    viewModel: AccountManagementViewModel
) {
    val uiState by viewModel.deleteAccountState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    var showConfirmationDialog by remember { mutableStateOf(false) }

    val warningIconSize by animateFloatAsState(
        targetValue = if (showConfirmationDialog) 1.2f else 1f,
        label = "warning icon animation",
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )

    LaunchedEffect(key1 = true) {
        viewModel.navigateToAccountDeleted.collect { event ->
            when (event) {
                DeleteAccountNavigationEvent.NavigateToAccountDeleted -> {
                    onNavigateToAccountDeleted()
                }
            }
        }
    }

    LaunchedEffect(uiState.errorMessage) {
        uiState.errorMessage?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.onDeleteAccountEvent(DeleteAccountEvent.ClearError)
        }
    }

    if (showConfirmationDialog) {
        DeleteAccountConfirmationDialog(
            onConfirm = {
                showConfirmationDialog = false
                viewModel.onDeleteAccountEvent(DeleteAccountEvent.DeleteAccount)
            },
            onDismiss = { showConfirmationDialog = false }
        )
    }

    DefaultScaffold(
        snackbarHostState = snackbarHostState,
        isLoading = uiState.isLoading,
        showOrbitals = true,
        topBar = {
            TopAppBar(
                title = { Text("Delete Account") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Navigate back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.1f),
                    titleContentColor = MaterialTheme.colorScheme.error
                )
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .padding(vertical = 24.dp)
                    .size(80.dp * warningIconSize)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.errorContainer),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.Warning,
                    contentDescription = "Warning",
                    tint = MaterialTheme.colorScheme.error,
                    modifier = Modifier.size(48.dp * warningIconSize)
                )
            }

            Text(
                text = "Delete Your Account",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.error
            )

            Text(
                text = "We're sorry to see you go. Before you proceed, please understand what happens when you delete your account.",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            DeleteConsequenceCard(
                title = "Your Content Will Be Removed",
                description = "All your blogs, comments, and community contributions will be permanently deleted and cannot be recovered.",
                modifier = Modifier.fillMaxWidth()
            )

            DeleteConsequenceCard(
                title = "Community Memberships Lost",
                description = "You will lose access to all communities you've joined, including any moderator or admin roles.",
                modifier = Modifier.fillMaxWidth()
            )

            DeleteConsequenceCard(
                title = "Personal Information",
                description = "Your profile, personal data, and preferences will be permanently erased from our system.",
                modifier = Modifier.fillMaxWidth()
            )

            DeleteConsequenceCard(
                title = "Can't Undo This Action",
                description = "Once your account is deleted, this action cannot be reversed. You'll need to create a new account if you wish to return.",
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { showConfirmationDialog = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error,
                    contentColor = MaterialTheme.colorScheme.onError
                ),
                enabled = !uiState.isLoading
            ) {
                AnimatedVisibility(
                    visible = !uiState.isLoading,
                    enter = fadeIn() + expandVertically(),
                    exit = fadeOut() + slideOutVertically()
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = null
                        )
                        Text(
                            text = "Delete My Account",
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }

                AnimatedVisibility(
                    visible = uiState.isLoading,
                    enter = fadeIn() + expandVertically(),
                    exit = fadeOut() + slideOutVertically()
                ) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.onError,
                        modifier = Modifier.size(24.dp),
                        strokeWidth = 2.dp
                    )
                }
            }

            TextButton(
                onClick = onNavigateBack,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                enabled = !uiState.isLoading
            ) {
                Text(
                    text = "Cancel",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.titleMedium
                )
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}