package com.newton.common_ui.ui

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.StarRate
import androidx.compose.material.icons.outlined.StarRate
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp

@Composable
fun RatingBarInput(
    modifier: Modifier = Modifier,
    currentRating: Int,
    onRatingChanged: (Int) -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center
    ) {
        for (i in 1..5) {
            val isSelected = i <= currentRating
            val interactionSource = remember { MutableInteractionSource() }
            val isHovered by interactionSource.collectIsPressedAsState()

            val iconAlpha by animateFloatAsState(
                targetValue = when {
                    isSelected -> 1f
                    isHovered -> 0.7f
                    else -> 0.5f
                },
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                ),
                label = "starAlpha$i"
            )

            val iconScale by animateFloatAsState(
                targetValue = when {
                    isSelected -> 1.2f
                    isHovered -> 1.1f
                    else -> 1.0f
                },
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                ),
                label = "starScale$i"
            )

            Box(
                modifier = Modifier
                    .size(48.dp)
                    .scale(iconScale)
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null,
                        onClick = { onRatingChanged(i) }
                    ) ,
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (isSelected) Icons.Filled.StarRate else Icons.Outlined.StarRate,
                    contentDescription = "Star $i",
                    tint = MaterialTheme.colorScheme.primary.copy(alpha = iconAlpha),
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }
}

@Composable
 fun RatingBar(
    rating: Int,
    maxRating: Int = 5,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        repeat(maxRating) { index ->
            Icon(
                imageVector = if (index < rating) Icons.Filled.StarRate else Icons.Outlined.StarRate,
                contentDescription = null,
                tint = if (index < rating) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                },
                modifier = Modifier
                    .size(24.dp)
                    .padding(end = 4.dp)
            )
        }

        Text(
            text = "$rating/$maxRating",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}