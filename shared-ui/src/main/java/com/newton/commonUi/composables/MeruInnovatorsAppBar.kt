package com.newton.commonUi.composables

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MeruInnovatorsAppBar(
    title: String,
    actions:
        @Composable()
        (RowScope.() -> Unit) = {}
) {
    TopAppBar(
        actions = {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                content = actions,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        },
        scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
        title = {
            Text(title, style = MaterialTheme.typography.headlineMedium)
        }
    )
}
