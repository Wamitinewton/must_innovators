package com.newton.meruinnovators.navigation

import com.newton.account.navigation.*
import com.newton.admin.navigation.*
import com.newton.auth.navigation.*
import com.newton.blogs.navigation.*
import com.newton.communities.navigation.*
import com.newton.events.navigation.*
import com.newton.feedback.navigation.*
import com.newton.home.navigation.*

data class NavigationSubGraphs(
    val authNavigationApi: AuthNavigationApi,
    val eventNavigationApi: EventsNavigationApi,
    val homeNavigationApi: HomeNavigationApi,
    val blogsNavigationApi: BlogsNavigationApi,
    val accountNavigationApi: AccountNavigationApi,
    val adminNavigationApi: AdminNavigationApi,
    val communityNavigationApi: CommunityNavigationApi,
    val feedbackNavigationApi: FeedbackNavigationApi
)
