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
package com.newton.meruinnovators.navigation

import com.newton.account.navigation.*
import com.newton.admin.navigation.*
import com.newton.auth.navigation.*
import com.newton.blogs.navigation.*
import com.newton.communities.navigation.*
import com.newton.events.navigation.*
import com.newton.feedback.navigation.*
import com.newton.home.navigation.*
import com.newton.settings.navigation.*

data class NavigationSubGraphs(
    val authNavigationApi: AuthNavigationApi,
    val eventNavigationApi: EventsNavigationApi,
    val homeNavigationApi: HomeNavigationApi,
    val blogsNavigationApi: BlogsNavigationApi,
    val accountNavigationApi: AccountNavigationApi,
    val adminNavigationApi: AdminNavigationApi,
    val aboutUsNavigationApi: AboutUsNavigationApi,
    val feedbackNavigationApi: FeedbackNavigationApi,
    val settingNavigationApi: SettingNavigationApi,
    val communityNavigationApi: CommunityNavigationApi
)
