package com.newton.communities.presentation.view.about_us

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.newton.common_ui.ui.ErrorScreen
import com.newton.common_ui.ui.LoadingIndicator
import com.newton.communities.presentation.state.ExecutiveUiState
import com.newton.communities.presentation.view.about_us.composables.ExecutiveCard
import com.newton.communities.presentation.view.about_us.composables.ExecutiveCardShimmer
import com.newton.core.domain.models.about_us.Executive

@Composable
fun ExecutivesSection(
    uiState: ExecutiveUiState,

    ) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Meet the talented individuals who lead our technology initiatives",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
        when (uiState) {
            is ExecutiveUiState.Loading -> {
                ExecutiveCardShimmer()
            }

            is ExecutiveUiState.Success -> {
                val executives = uiState.executives
                ExecutiveListSection(executives = executives)
            }

            is ExecutiveUiState.Error -> {
                val errorMessage = uiState.message
                ErrorScreen(
                    message = errorMessage,
                    onRetry = { }
                )
            }
        }
    }
}

@Composable
fun ExecutiveListSection(executives: List<Executive>) {
    var visibleItems by remember { mutableStateOf(false) }

    androidx.compose.runtime.LaunchedEffect(executives) {
        visibleItems = true
    }
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        itemsIndexed(executives) { index, executive ->
            AnimatedVisibility(
                visible = visibleItems,
                enter = fadeIn(
                    animationSpec = tween(
                        durationMillis = 300,
                        delayMillis = 100 * index
                    )
                )
            ) {
                ExecutiveCard(
                    executive = executive,
                    modifier = Modifier
                )
            }
        }
    }
}

