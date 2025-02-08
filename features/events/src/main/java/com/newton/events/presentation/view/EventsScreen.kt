package com.newton.events.presentation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.newton.events.domain.models.Event
import com.newton.events.presentation.events.CategoryEvent
import com.newton.events.presentation.view.composables.EventCard
import com.newton.events.presentation.viewmodel.CategoryViewModel
import com.newton.events.presentation.viewmodel.EventViewModel

@Composable
fun EventsScreen(
    modifier: Modifier = Modifier,
    categoryViewModel: CategoryViewModel,
    eventViewModel: EventViewModel
) {
    val categoryState by categoryViewModel.state.collectAsState()
    val eventState by eventViewModel.eventState.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) { paddingValue ->
        Box(
            modifier = Modifier.padding(
                top = paddingValue.calculateTopPadding(),
                bottom = paddingValue.calculateBottomPadding(),
            ),
        ) {
            CustomAppBar(
                categoryState.selectedCategory,
                categoryState.categories,
                onCategorySelected = {
                    categoryViewModel.onEvent(CategoryEvent.OnCategorySelected(it))
                },
            )
            LazyColumn(
                modifier = Modifier.fillMaxSize(), contentPadding = PaddingValues(bottom = 64.dp)
            ) {
                itemsIndexed(eventState.events) { index, event ->
                    EventCard(event) {
                    //TODO()
                    }
                    if (index == eventState.events.lastIndex && !eventState.isLoading) {
                        LaunchedEffect(key1 = eventState.events) {
                            eventViewModel.loadMoreEvents()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CustomAppBar(
    selectedCategory: String, categories: List<String>, onCategorySelected: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = {}) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Menu",
                )
            }
            Text("Events Screen", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            IconButton(onClick = { }) {
                Box {
                    Icon(
                        imageVector = Icons.Default.Notifications,
                        contentDescription = "Notifications",
                    )
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(Color.Red)
                            .align(Alignment.TopEnd)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        TextField(value = "",
            onValueChange = {},
            placeholder = { Text("Search", color = Color.Gray) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search Icon",
                )
            },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Outlined.CheckCircle,
                    contentDescription = "Filter",
                    tint = Color.Gray
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF2A2A3A), RoundedCornerShape(16.dp))
                .clip(RoundedCornerShape(16.dp))
        )

        Spacer(modifier = Modifier.height(12.dp))
        LazyRow(modifier = Modifier.fillMaxWidth()) {
            items(categories) { category ->
                val isSelected = category == selectedCategory
                Box(modifier = Modifier
                    .padding(end = 8.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(if (isSelected) Color(0xFF8257E5) else Color(0xFF3A3A4A))
                    .clickable { onCategorySelected(category) }
                    .padding(horizontal = 12.dp, vertical = 8.dp)) {
                    Text(text = category, fontSize = 14.sp)
                }
            }
        }
    }
}
