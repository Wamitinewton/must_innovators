package com.newton.admin.presentation.home.views

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.newton.admin.presentation.home.views.composables.DonutChartComponentView

data class CommunityGroup(val name:String,val members:Int)
data class ActiveUserData(val time:String,val users:Int)
data class EventData(val community:String,val events:Int)
data class InteractionData(val day:String,val Intensity:Int)
@Composable
fun AdminHome(modifier: Modifier = Modifier) {
    // Sample data for demonstration
    val communityGroups = listOf(
        CommunityGroup("Sports", 120),
        CommunityGroup("Music", 80),
        CommunityGroup("Art", 150),
        CommunityGroup("Tech", 200)
    )

    val activeUsersData = listOf(
        ActiveUserData("10:00", 100),
        ActiveUserData("10:05", 150),
        ActiveUserData("10:10", 130),
        ActiveUserData("10:15", 170),
        ActiveUserData("10:20", 160)
    )

    val eventData = listOf(
        EventData("Sports", 5),
        EventData("Music", 3),
        EventData("Art", 7),
        EventData("Tech", 4)
    )

    val dailyInteractions = listOf(
        InteractionData("Mon", 50),
        InteractionData("Tue", 70),
        InteractionData("Wed", 60),
        InteractionData("Thu", 90),
        InteractionData("Fri", 80),
        InteractionData("Sat", 100),
        InteractionData("Sun", 40)
    )

    val weeklyInteractions = listOf(
        InteractionData("Week 1", 400),
        InteractionData("Week 2", 450),
        InteractionData("Week 3", 500),
        InteractionData("Week 4", 480)
    )
    Scaffold {
        Box(modifier = Modifier.fillMaxSize().padding(it)){
            Box(modifier = Modifier.fillMaxSize().align(Alignment.Center)){
                Text("Admin Dashboard Screen")
            }
        }
    }
}
