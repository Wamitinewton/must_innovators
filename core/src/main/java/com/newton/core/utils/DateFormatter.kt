package com.newton.core.utils

import java.time.*
import java.time.format.*

fun formatDateTime(dateString: String): String {
    val instant = Instant.parse(dateString)
    val formatter =
        DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy 'at' h:mm a")
            .withZone(ZoneId.systemDefault())
    return formatter.format(instant)
}
