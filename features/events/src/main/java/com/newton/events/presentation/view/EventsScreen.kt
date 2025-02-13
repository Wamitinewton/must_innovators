package com.newton.events.presentation.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.newton.events.domain.models.Event
import com.newton.events.presentation.view.composables.CustomAppBar
import com.newton.events.presentation.view.composables.EventCard
import com.newton.events.presentation.viewmodel.EventViewModel

@Composable
fun EventsScreen(
    modifier: Modifier = Modifier,
    eventViewModel: EventViewModel
) {
    val eventState by eventViewModel.eventState.collectAsState()
    val searchState by eventViewModel.searchState.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CustomAppBar(
                eventState.selectedCategory,
                eventState.categories,
                searchState.searchInput,
                onEvent = eventViewModel::onEvent,
            )
        },


    ) { paddingValue ->
        Box(
            modifier = Modifier.padding(
                top = paddingValue.calculateTopPadding(),
                bottom = paddingValue.calculateBottomPadding(),
            )

        ) {

            LazyColumn(
                contentPadding = PaddingValues(bottom = 64.dp)
            ) {
                if (eventState.events.isEmpty()){
                    items(7){
                        EventCard(dummyEvent) { }
                    }
                }
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
val dummyEvent = Event(
    contactEmail = "joedoh@gmail.com",
    date = "Mon, 20th,2025",
    description = "Annual international technology conference featuring AI, blockchain, and quantum computing experts",
    id = 101,
    image = "https://images.pexels.com/photos/30632452/pexels-photo-30632452/free-photo-of-young-man-exploring-nature-by-a-serene-river.jpeg?auto=compress&cs=tinysrgb&w=600&lazy=load",
    isVirtual = false,
    location = "San Francisco Convention Center, 747 Howard St, San Francisco, CA",
    name =  "Global Tech Summit 2024",
    organizer = "Future Tech Foundation",
    title = "Global Tech Summit: Shaping Tomorrow's Innovations",
)
