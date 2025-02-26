package com.newton.admin.presentation.partners.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun AddPartnerScreen(modifier: Modifier = Modifier) {
    Scaffold {
        Box(modifier=Modifier.fillMaxSize().padding(it)){
            Text("Add Partners Screen")
        }
    }
}