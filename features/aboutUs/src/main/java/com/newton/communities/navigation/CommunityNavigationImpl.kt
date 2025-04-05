package com.newton.communities.navigation

import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.*
import androidx.navigation.*
import androidx.navigation.compose.*
import com.newton.communities.presentation.view.aboutUs.*
import com.newton.communities.presentation.view.communityDetails.*
import com.newton.communities.presentation.viewModel.*
import com.newton.navigation.*

class CommunityNavigationImpl : CommunityNavigationApi {
    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navHostController: NavHostController
    ) {
        navGraphBuilder.navigation(
            route = NavigationSubGraphRoutes.AboutUs.route,
            startDestination = NavigationRoutes.AboutUsRoute.routes
        ) {
            composable(route = NavigationRoutes.AboutUsRoute.routes) {
                val communityViewModel = hiltViewModel<CommunitiesViewModel>()
                val parentEntry =
                    remember(it) {
                        navHostController.getBackStackEntry(NavigationSubGraphRoutes.AboutUs.route)
                    }
                val aboutUsSharedViewModel = hiltViewModel<AboutUsSharedViewModel>(parentEntry)
                val executiveViewModel = hiltViewModel<ExecutiveViewModel>()
                val clubBioViewModel = hiltViewModel<ClubBioViewModel>()
                AboutUsScreen(
                    communitiesViewModel = communityViewModel,
                    onCommunityDetailsClick = { community ->
                        aboutUsSharedViewModel.selectCommunity(community)
                        navHostController.navigate(NavigationRoutes.CommunitiesDetailsRoute.routes)
                    },
                    executiveViewModel = executiveViewModel,
                    clubBioViewModel = clubBioViewModel
                )
            }

            composable(
                route = NavigationRoutes.CommunitiesDetailsRoute.routes
            ) {
                val parentEntry =
                    remember(it) {
                        navHostController.getBackStackEntry(NavigationSubGraphRoutes.AboutUs.route)
                    }
                val aboutUsSharedViewModel = hiltViewModel<AboutUsSharedViewModel>(parentEntry)
                CommunityDetailsScreen(
                    onBackPressed = {
                        navHostController.navigateUp()
                    },
                    onShareClick = {},
                    onJoinCommunity = {},
                    aboutUsSharedViewModel = aboutUsSharedViewModel
                )
            }
        }
    }
}
