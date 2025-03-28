package com.newton.core.sharedBus

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TestimonialsEventBus @Inject constructor() {
    private val _testimonialsUpdate = MutableSharedFlow<Unit>(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val testimonialUpdate = _testimonialsUpdate.asSharedFlow()

    suspend fun notifyTestimonialUpdate() {
        Timber.d("Updating flow....................WAIT UP KINDLY")
        _testimonialsUpdate.emit(Unit)
    }
}