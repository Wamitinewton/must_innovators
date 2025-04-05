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
package com.newton.core.sharedBus

import kotlinx.coroutines.channels.*
import kotlinx.coroutines.flow.*
import timber.log.*
import javax.inject.*

@Singleton
class TestimonialsEventBus
@Inject
constructor() {
    private val _testimonialsUpdate =
        MutableSharedFlow<Unit>(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val testimonialUpdate = _testimonialsUpdate.asSharedFlow()

    suspend fun notifyTestimonialUpdate() {
        Timber.d("Updating flow....................WAIT UP KINDLY")
        _testimonialsUpdate.emit(Unit)
    }
}
