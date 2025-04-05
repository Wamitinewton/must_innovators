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
