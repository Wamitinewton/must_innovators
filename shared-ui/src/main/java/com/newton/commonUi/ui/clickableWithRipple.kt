package com.newton.commonUi.ui

import androidx.compose.foundation.clickable
import androidx.compose.runtime.*
import androidx.compose.ui.*

@Composable
fun Modifier.clickableWithRipple(onClick: () -> Unit): Modifier {
    return this
        .clickable(
            interactionSource = remember { androidx.compose.foundation.interaction.MutableInteractionSource() },
            indication = androidx.compose.material.ripple.rememberRipple(bounded = true),
            onClick = onClick
        )
}
