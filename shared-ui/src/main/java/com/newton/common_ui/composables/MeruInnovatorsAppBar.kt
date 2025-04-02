package com.newton.common_ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MeruInnovatorsAppBar(
    title: String,
    actions: @Composable() (RowScope.() -> Unit) = {},
) {
    TopAppBar(
        actions = {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp), content = actions, modifier = Modifier.padding(horizontal = 8.dp))
        },
        scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
        title = {
            Text(title, style = MaterialTheme.typography.headlineMedium)
        }

    )
}
