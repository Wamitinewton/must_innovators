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
package com.newton.account.presentation.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.automirrored.outlined.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.input.nestedscroll.*
import androidx.compose.ui.text.style.*
import androidx.compose.ui.unit.*
import com.newton.account.presentation.composables.account.*
import com.newton.account.presentation.events.*
import com.newton.account.presentation.viewmodel.*
import com.newton.commonUi.ui.*
import kotlinx.coroutines.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountScreen(
    onMyEventsClick: () -> Unit,
    onBugReportClick: () -> Unit,
    onGeneralFeedbackClick: () -> Unit,
    onDeleteAccount: () -> Unit,
    onUpdateProfile: () -> Unit,
    onCreateTestimonial: () -> Unit,
    onLogoutClicked: () -> Unit,
    accountViewModel: UpdateAccountViewModel,
    onSettingsClicked: () -> Unit,
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
        LoadingDialog()
    }

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scrollBehaviour =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
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
                },
                onSettingsClicked = onSettingsClicked
            )
        }
    ) {
        CustomScaffold(
            modifier =
            Modifier
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
                    onClick = { onCreateTestimonial() },
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ) {
                    Icon(Icons.AutoMirrored.Outlined.NoteAdd, contentDescription = "Edit profile")
                }
            }
        ) {
            LazyColumn(
                modifier =
                Modifier
                    .fillMaxSize(),
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
