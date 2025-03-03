package com.newton.communities.presentation.view.about_us.composables

import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.newton.common_ui.composables.animation.shimmerEffect

@Composable
fun CommunityCardShimmer() {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    modifier = Modifier.size(48.dp),
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)
                ) {
                    Box(modifier = Modifier.shimmerEffect())
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Box(
                        modifier = Modifier
                            .size(width = 120.dp, height = 16.dp)
                            .shimmerEffect()
                            .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f))
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Box(
                        modifier = Modifier
                            .size(width = 80.dp, height = 12.dp)
                            .shimmerEffect()
                            .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f))
                    )
                }
            }

            Column(modifier = Modifier.padding(16.dp)) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(12.dp)
                        .shimmerEffect()
                        .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f))
                )

                Spacer(modifier = Modifier.height(8.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(12.dp)
                        .shimmerEffect()
                        .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f))
                )

                Spacer(modifier = Modifier.height(12.dp))

                Box(
                    modifier = Modifier
                        .align(Alignment.End)
                        .size(width = 100.dp, height = 36.dp)
                        .shimmerEffect()
                        .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f))
                )
            }
        }
    }
}

@Composable
fun CommunityCardShimmerList(
    itemCount: Int = 5,
    contentPadding: PaddingValues = PaddingValues(bottom = 16.dp)
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        contentPadding = contentPadding
    ) {
        itemsIndexed(List(itemCount) { it }) { _, _ ->
            CommunityCardShimmer(
            )
        }
    }
}
