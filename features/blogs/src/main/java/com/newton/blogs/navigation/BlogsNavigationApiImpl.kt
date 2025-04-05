package com.newton.blogs.navigation

import androidx.navigation.*
import androidx.navigation.compose.*
import com.newton.blogs.presentation.view.*
import com.newton.navigation.*

class BlogsNavigationApiImpl : BlogsNavigationApi {
    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navHostController: NavHostController
    ) {
        navGraphBuilder.navigation(
            route = NavigationSubGraphRoutes.Blogs.route,
            startDestination = NavigationRoutes.BlogsRoute.routes
        ) {
            composable(route = NavigationRoutes.BlogsRoute.routes) {
                BlogsScreen()
            }
        }
    }
}
