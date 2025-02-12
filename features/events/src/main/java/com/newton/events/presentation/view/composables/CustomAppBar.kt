package com.newton.events.presentation.view.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.newton.events.presentation.events.EventsEvent


@Composable
fun CustomAppBar(
    selectedCategory: String,
    categories: List<String>,
    searchInput: String,
    onEvent: (EventsEvent) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
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
        Box (modifier = Modifier.padding(horizontal = 8.dp)) {
            TextField(
                value = searchInput,
                onValueChange = {
                    onEvent(EventsEvent.SearchInputChanged(it))
                },
                placeholder = { Text("Search") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Icon",
                    )
                },
                singleLine = true,
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.CheckCircle,
                        contentDescription = "Filter",
                        tint = Color.Gray
                    )
                },
                colors = TextFieldDefaults.colors().copy(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.secondary, RoundedCornerShape(16.dp))
                    .clip(RoundedCornerShape(16.dp))
            )
        }
        Spacer(Modifier.height(2.dp))
        LazyRow(modifier = Modifier.fillMaxWidth()) {
            items(categories) { category ->
                val isSelected = category == selectedCategory
                Box(modifier = Modifier
                    .padding(end = 8.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(if (isSelected) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.onSecondary)
                    .clickable {
                        onEvent(EventsEvent.OnCategorySelected(category))
                    }
                    .padding(horizontal = 12.dp, vertical = 8.dp)) {
                    Text(text = category, fontSize = 14.sp)
                }
            }
        }
    }
}
