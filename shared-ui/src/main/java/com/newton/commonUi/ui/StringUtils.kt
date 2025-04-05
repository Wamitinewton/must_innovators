package com.newton.commonUi.ui

import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

fun String.toCustomRequestBody(mediaType: String = "text/plain"): RequestBody {
    return this.toRequestBody(mediaType.toMediaTypeOrNull())
}
