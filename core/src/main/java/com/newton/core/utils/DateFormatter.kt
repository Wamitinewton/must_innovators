/**
 * Copyright (c) 2025 Meru Science Innovators Club
 *
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of Meru Science Innovators Club.
 * You shall not disclose such confidential information and shall use it only in accordance
 * with the terms of the license agreement you entered into with Meru Science Innovators Club.
 *
 * Unauthorized copying of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 *
 * NO WARRANTY: This software is provided "as is" without warranty of any kind,
 * either express or implied, including but not limited to the implied warranties
 * of merchantability and fitness for a particular purpose.
 */
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
