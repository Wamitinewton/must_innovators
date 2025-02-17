package com.newton.account.presentation.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun AccountScreen(modifier: Modifier = Modifier) {
    Scaffold(modifier = Modifier.fillMaxSize()){ values ->
        Box(modifier = Modifier.padding(top = values.calculateTopPadding(), bottom = values.calculateBottomPadding())){
            Text(text = "Account Screen")
        }
    }
}