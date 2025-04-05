package com.newton.events.presentation.view.eventDetails

import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.automirrored.filled.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.*
import androidx.compose.ui.platform.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.unit.*
import coil3.compose.*
import coil3.request.*
import com.newton.commonUi.composables.*
import com.newton.commonUi.ui.*
import com.newton.core.utils.*
import com.newton.events.presentation.states.*
import com.newton.events.presentation.viewmodel.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventDetailsScreen(
    viewModel: EventsSharedViewModel,
    onBackPressed: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()
    var isImageExpanded by remember { mutableStateOf(false) }

    DisposableEffect(Unit) {
        onDispose {
            viewModel.clearSelectedEvent()
        }
    }

    DefaultScaffold(
        topBar = {
            TopAppBar(
                title = { Text("Event Details") },
                navigationIcon = {
                    IconButton(onClick = onBackPressed) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors =
                TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) {
        when (uiState) {
            is EventDetailsState.Error -> {
                ErrorScreen(
                    titleText = "Failed to load EVENT DETAILS",
                    message = (uiState as EventDetailsState.Error).message,
                    onRetry = {}
                )
            }

            EventDetailsState.Initial -> {
                LoadingIndicator()
            }

            is EventDetailsState.Success -> {
                val event = (uiState as EventDetailsState.Success).event
                Column(
                    modifier =
                    Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState)
                ) {
                    Box(
                        modifier =
                        Modifier
                            .fillMaxWidth()
                            .height(if (isImageExpanded) 500.dp else 250.dp)
                            .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
                            .animateContentSize()
                    ) {
                        AsyncImage(
                            model =
                            ImageRequest.Builder(LocalContext.current)
                                .data(event.imageUrl)
                                .crossfade(true)
                                .build(),
                            contentDescription = "Event Image",
                            modifier =
                            Modifier
                                .fillMaxSize()
                                .clickable { isImageExpanded = !isImageExpanded }
                                .graphicsLayer {
                                    clip = true
                                    shape =
                                        RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp)
                                },
                            contentScale = ContentScale.Crop
                        )

                        Box(
                            modifier =
                            Modifier
                                .fillMaxSize()
                                .background(
                                    Brush.verticalGradient(
                                        colors =
                                        listOf(
                                            Color.Transparent,
                                            Color.Black.copy(alpha = 0.7f)
                                        )
                                    )
                                )
                        )

                        Column(
                            modifier =
                            Modifier
                                .align(Alignment.BottomStart)
                                .padding(16.dp)
                        ) {
                            Text(
                                text = event.name,
                                style = MaterialTheme.typography.headlineMedium,
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )

                            Text(
                                text = event.category,
                                style = MaterialTheme.typography.titleMedium,
                                color = Color.White.copy(alpha = 0.8f)
                            )
                        }
                    }

                    Column(
                        modifier =
                        Modifier
                            .padding(16.dp)
                            .fillMaxWidth()
                    ) {
                        EventDetailCard(
                            icon = Icons.Default.DateRange,
                            title = "Date & Time",
                            content = formatDateTime(event.date)
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        EventDetailCard(
                            icon = if (event.isVirtual) Icons.Default.Home else Icons.Default.LocationOn,
                            title = if (event.isVirtual) "Virtual Event" else "Location",
                            content = event.location
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors =
                            CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surfaceVariant
                            )
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp)
                            ) {
                                Text(
                                    text = "About the event",
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = event.description,
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        EventDetailCard(
                            icon = Icons.Default.Person,
                            title = "Organizer",
                            content = event.organizer
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        EventDetailCard(
                            icon = Icons.Default.Email,
                            title = "Contact",
                            content = event.contactEmail
                        )
                    }
                }
            }
        }
    }
}
