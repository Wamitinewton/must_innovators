package com.newton.navigation

import androidx.navigation.*

interface NavigationApi {
    fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navHostController: NavHostController
    )
}
