package com.newton.admin.presentation.events.view.management.composables.feedback

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.unit.*

@Composable
fun RatingBar(
    rating: Float,
    maxRating: Int = 5,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        repeat(maxRating) { index ->
            val starFill =
                when {
                    index < rating.toInt() -> 1f
                    index == rating.toInt() && rating % 1 > 0 -> rating % 1
                    else -> 0f
                }

            Box(modifier = Modifier.padding(horizontal = 2.dp)) {
                Icon(
                    imageVector = Icons.Default.StarBorder,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
                )

                if (starFill > 0) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.clipToBounds()
                    )
                }
            }
        }
    }
}
