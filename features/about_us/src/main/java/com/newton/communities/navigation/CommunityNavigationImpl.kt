package com.newton.communities.navigation

import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.newton.communities.presentation.view.about_us.AboutUsScreen
import com.newton.communities.presentation.view.community_details.CommunityDetailsScreen
import com.newton.communities.presentation.view_model.AboutUsSharedViewModel
import com.newton.communities.presentation.view_model.CommunitiesViewModel
import com.newton.core.navigation.NavigationRoutes
import com.newton.core.navigation.NavigationSubGraphRoutes

class CommunityNavigationImpl: CommunityNavigationApi {
    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navHostController: NavHostController
    ) {
        navGraphBuilder.navigation(
            route = NavigationSubGraphRoutes.Communities.route,
            startDestination = NavigationRoutes.AboutUsRoute.routes
        ) {
            composable(route = NavigationRoutes.AboutUsRoute.routes) {
                val communityViewModel =  hiltViewModel<CommunitiesViewModel>()
                val parentEntry = remember(it) {
                    navHostController.getBackStackEntry(NavigationSubGraphRoutes.Communities.route)
                }
                val aboutUsSharedViewModel = hiltViewModel<AboutUsSharedViewModel>(parentEntry)
                AboutUsScreen(
                    onNavigateToDetails = { communityDetails ->
                        aboutUsSharedViewModel.selectCommunity(communityDetails)
                        navHostController.navigate(NavigationRoutes.CommunitiesDetailsRoute.routes)
                    },
                    communitiesViewModel = communityViewModel,
                )
            }

            composable(route = NavigationRoutes.CommunitiesDetailsRoute.routes) {
                val parentEntry = remember(it) {
                    navHostController.getBackStackEntry(NavigationSubGraphRoutes.Communities.route)
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