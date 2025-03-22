package com.newton.account.presentation.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.newton.account.presentation.composables.account.AccountDrawerContent
import com.newton.account.presentation.composables.account.EducationSection
import com.newton.account.presentation.composables.account.FeedbackSelectionBottomSheet
import com.newton.account.presentation.composables.account.ProfileSection
import com.newton.account.presentation.composables.account.ProjectsSection
import com.newton.account.presentation.composables.account.SkillsSection
import com.newton.account.presentation.composables.account.SocialMediaSection
import com.newton.account.presentation.composables.account.TechStacksSection
import com.newton.account.presentation.composables.account.UserInfoSection
import com.newton.account.presentation.events.LogoutEvent
import com.newton.account.presentation.events.LogoutNavigationEvent
import com.newton.account.presentation.viewmodel.AccountManagementViewModel
import com.newton.account.presentation.viewmodel.UpdateAccountViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountScreen(
    onMyEventsClick: () -> Unit,
    onBugReportClick: () -> Unit,
    onGeneralFeedbackClick: () -> Unit,
    onDeleteAccount: () -> Unit,
    onUpdateProfile: () -> Unit,
    onLogoutClicked: () -> Unit,
    accountViewModel: UpdateAccountViewModel,
    accountManagementViewModel: AccountManagementViewModel
) {
    val accountUiState by accountViewModel.accountState.collectAsState()
    val logoutState by accountManagementViewModel.logoutState.collectAsState()

    val user = accountUiState.userData

    LaunchedEffect(key1 = true) {
        accountManagementViewModel.navigateAfterLogout.collect { event ->
            when (event) {
                LogoutNavigationEvent.NavigateToLogin -> {
                    onLogoutClicked()
                }
            }
        }
    }

    if (logoutState.isLoading) {
        com.newton.common_ui.ui.LoadingDialog()
    }

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scrollBehaviour = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    val coroutine = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }

    val bottomSheetState = rememberModalBottomSheetState()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            AccountDrawerContent(
                drawerState = drawerState,
                onMyEventsClick = onMyEventsClick,
                onDeleteAccountClicked = onDeleteAccount,
                onFeedbackClicked = {
                    coroutine.launch {
                        drawerState.close()
                        showBottomSheet = true
                    }
                },
                onLogoutClicked = {
                    accountManagementViewModel.onLogoutEvent(LogoutEvent.Logout)
                }
            )
        }
    ) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .nestedScroll(scrollBehaviour.nestedScrollConnection),
            topBar = {
                LargeTopAppBar(
                    title = {
                        Text(
                            "My Account",
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            coroutine.launch {
                                drawerState.open()
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Filled.Menu,
                                contentDescription = "Menu"
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = { onUpdateProfile() }) {
                            Icon(
                                imageVector = Icons.Outlined.Edit,
                                contentDescription = "Edit Profile"
                            )
                        }
                    },
                    scrollBehavior = scrollBehaviour
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { onUpdateProfile() },
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ) {
                    Icon(Icons.Outlined.Edit, contentDescription = "Edit profile")
                }
            }
        ) { innerPadding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    if (user != null) {
                        ProfileSection(user = user)
                    }
                }

                item {
                    if (user != null) {
                        UserInfoSection(user = user)
                    }
                }

                item {
                    if (user?.projects != null) {
                        ProjectsSection(projects = user.projects)
                    }
                }

                item {
                    if (user?.skills != null) {
                        SkillsSection(skills = user.skills)
                    }
                }

                item {
                    if (user?.tech_stacks != null) {
                        TechStacksSection(techStacks = user.tech_stacks)
                    }
                }

                item {
                    if (user != null) {
                        EducationSection(user = user)
                    }
                }

                item {
                    if (user?.social_media != null) {
                        SocialMediaSection(socialMedia = user.social_media)
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(80.dp))
                }
            }
        }
    }

    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false },
            sheetState = bottomSheetState,
            containerColor = MaterialTheme.colorScheme.surface,
            shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
            tonalElevation = 8.dp
        ) {
            FeedbackSelectionBottomSheet(
                onSelectBugReport = {
                    coroutine.launch {
                        bottomSheetState.hide()
                        onBugReportClick()
                    }
                },
                onSelectGeneralFeedback = {
                    coroutine.launch {
                        bottomSheetState.hide()
                        onGeneralFeedbackClick()
                    }
                },
                onDismiss = {}
            )
        }
    }
}