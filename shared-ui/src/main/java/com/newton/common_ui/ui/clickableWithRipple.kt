package com.newton.common_ui.ui

import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

@Composable
fun Modifier.clickableWithRipple(onClick: () -> Unit): Modifier {
    return this
        .clickable(
            interactionSource = remember { androidx.compose.foundation.interaction.MutableInteractionSource() },
            indication = androidx.compose.material.ripple.rememberRipple(bounded = true),
            onClick = onClick
        )
}