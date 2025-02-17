package com.newton.meruinnovators.navigation

import com.newton.account.navigation.AccountNavigationApi
import com.newton.auth.navigation.AuthNavigationApi
import com.newton.blogs.navigation.BlogsNavigationApi
import com.newton.events.navigation.EventsNavigationApi
import com.newton.home.navigation.HomeNavigationApi

data class NavigationSubGraphs(
    val authNavigationApi: AuthNavigationApi,
    val eventNavigationApi: EventsNavigationApi,
    val homeNavigationApi: HomeNavigationApi,
    val blogsNavigationApi: BlogsNavigationApi,
    val accountNavigationApi: AccountNavigationApi,
)
