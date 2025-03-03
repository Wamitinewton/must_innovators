package com.newton.account.presentation.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Bookmark
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.newton.account.presentation.composables.AccountDrawerContent
import com.newton.account.presentation.composables.BlogCard
import com.newton.account.presentation.composables.CommunitiesSection
import com.newton.account.presentation.composables.ProfileSection
import com.newton.account.presentation.composables.SectionHeader
import com.newton.account.presentation.composables.UserInfoSection
import com.newton.auth.presentation.login.view_model.GetUserDataViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountScreen(
    onMyEventsClick: () -> Unit,
    getUserDataViewModel: GetUserDataViewModel = hiltViewModel()
) {
    val getUserUiState by getUserDataViewModel.getUserDataState.collectAsState()

    val user = getUserUiState.userData

    val communities = remember {
        listOf(
            Community(
                id = "1",
                name = "Android Developers",
                membersCount = 5234,
                color = Color(0xFF1A73E8)
            ),
            Community(
                id = "2",
                name = "UI/UX Design",
                membersCount = 3187,
                color = Color(0xFF4285F4)
            ),
            Community(
                id = "3",
                name = "Kotlin Enthusiasts",
                membersCount = 2954,
                color = Color(0xFF7B1FA2)
            ),
            Community(
                id = "4",
                name = "CS Study Group",
                membersCount = 1876,
                color = Color(0xFF0F9D58)
            )
        )
    }

    val blogs = remember {
        listOf(
            Blog(
                id = "1",
                title = "Getting Started with Jetpack Compose",
                preview = "Learn how to build modern Android UIs with Jetpack Compose, Google's modern toolkit for building native UI...",
                date = "2 days ago",
                readTime = 5,
                likes = 128
            ),
            Blog(
                id = "2",
                title = "Material 3 Design System Explained",
                preview = "Dive deep into Material 3, Google's latest design system with dynamic color and improved accessibility...",
                date = "1 week ago",
                readTime = 7,
                likes = 246
            ),
            Blog(
                id = "3",
                title = "Building Animations in Compose",
                preview = "Learn how to create beautiful animations in your Android applications using Jetpack Compose...",
                date = "2 weeks ago",
                readTime = 6,
                likes = 189
            )
        )
    }

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scrollBehaviour = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    val coroutine = rememberCoroutineScope()


    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            AccountDrawerContent(
                drawerState = drawerState,
                onMyEventsClick = onMyEventsClick
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
                        IconButton(onClick = {}) {
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
                    onClick = {},
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ) {
                    Icon(Icons.Outlined.Edit, contentDescription = "Create new blog")
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
                        ProfileSection(
                            user = user,
                        )
                    }
                }

                item {
                    if (user != null) {
                        UserInfoSection(user = user)
                    }
                }

                item {
                    SectionHeader(
                        title = "My Communities",
                        icon = Icons.Filled.Groups,
                        count = communities.size
                    )

                    AnimatedVisibility(
                        visible = true,
                        enter = fadeIn() + slideInVertically { it / 2 }
                    ) {
                        CommunitiesSection(communities = communities)
                    }
                }

                item {
                    SectionHeader(
                        title = "My Blogs",
                        icon = Icons.Outlined.Bookmark,
                        count = blogs.size,
                        showSeeAll = true,
                        onSeeAllClick = {

                        }
                    )
                }

                items(blogs) { blog ->
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                    BlogCard(
                        blog = blog,
                        modifier = Modifier.animateItem(
                            placementSpec = spring(
                                dampingRatio = Spring.DampingRatioMediumBouncy,
                                stiffness = Spring.StiffnessLow
                            )
                        )
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(60.dp))
                }
            }
        }
    }
}