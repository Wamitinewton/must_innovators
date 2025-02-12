package com.newton.meruinnovators.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.newton.auth.data.work_manager.scheduleTokenRefreshWork
import com.newton.meruinnovators.navigation.MeruInnovatorsNavigation
import com.newton.meruinnovators.navigation.NavigationSubGraphs
import com.newton.meruinnovators.ui.theme.ThemeUtils.MeruinnovatorsTheme
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
            MeruinnovatorsTheme {
               Surface(modifier = Modifier.safeContentPadding()) {
                   MeruInnovatorsNavigation(navigationSubGraphs = navigationSubGraphs)
               }
            }
        }
    }
}
