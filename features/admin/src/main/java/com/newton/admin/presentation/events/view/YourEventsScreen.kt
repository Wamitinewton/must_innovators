package com.newton.admin.presentation.events.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.newton.common_ui.composables.MeruInnovatorsAppBar
import com.newton.common_ui.ui.CustomButton
import com.newton.core.navigation.NavigationRoutes

@Composable
fun YourEventsScreen(navController: NavController) {
    Scaffold(
        topBar = {
            MeruInnovatorsAppBar(title = "Your Events", actions = {
                CustomButton(
                    onClick = {
                        navController.navigate(NavigationRoutes.AddEvent.routes)
                    },
                    modifier = Modifier.padding(8.dp),
                    content =  {
                        Icon(Icons.Filled.Add, contentDescription = "Add Event")
                    }
                )
            })
        }
    ) {
        Box(modifier = Modifier.fillMaxSize().padding(it)){

        }
    }
}