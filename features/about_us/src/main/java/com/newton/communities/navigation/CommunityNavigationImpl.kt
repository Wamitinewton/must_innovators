package com.newton.communities.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.newton.communities.presentation.view.AboutUsScreen
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
                AboutUsScreen(
                    onNavigateToDetails = {

                    },
                    communitiesViewModel = communityViewModel
                )
            }
        }
    }
}