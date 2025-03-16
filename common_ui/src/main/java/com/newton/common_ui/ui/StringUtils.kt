package com.newton.common_ui.ui

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

fun String.toCustomRequestBody(mediaType: String = "text/plain"): RequestBody {
    return this.toRequestBody(mediaType.toMediaTypeOrNull())
}
