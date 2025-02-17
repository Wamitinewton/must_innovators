package com.newton.meruinnovators.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.newton.auth.data.work_manager.scheduleTokenRefreshWork
import com.newton.meruinnovators.navigation.NavigationSubGraphs
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var navigationSubGraphs: NavigationSubGraphs



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        scheduleTokenRefreshWork(applicationContext)
        setContent {
            RootScreen(navigationSubGraphs)
        }
    }

}
