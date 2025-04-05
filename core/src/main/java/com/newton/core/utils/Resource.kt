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

import com.newton.core.enums.*

sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    class Loading<T>(val isLoading: Boolean = true) : Resource<T>(null)

    class Success<T>(data: T?) : Resource<T>(data)

    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)
}

/**
 * A sealed class representing different states of a network request.
 *
 * @param T The type of data being delivered
 * @param data The data payload (if any)
 * @param message Optional message (usually for errors)
 * @param errorType Classification of the error type
 * @param httpCode The HTTP status code (if applicable)
 */
sealed class ResourceMigration<T>(
    val data: T? = null,
    val message: String? = null,
    val errorType: ErrorType? = null,
    val httpCode: Int? = null
) {
    class Loading<T>(val isLoading: Boolean = true) : ResourceMigration<T>(null)

    class Success<T>(data: T?) : ResourceMigration<T>(data)

    class Error<T>(
        message: String,
        errorType: ErrorType = ErrorType.UNKNOWN,
        data: T? = null,
        httpCode: Int? = null
    ) : ResourceMigration<T>(data, message, errorType, httpCode)

    /**
     * Helper function to handle the resource state
     */
    inline fun handle(
        onLoading: (Boolean) -> Unit = {},
        onSuccess: (T?) -> Unit = {},
        onError: (String?, ErrorType?, Int?) -> Unit = { _, _, _ -> }
    ) {
        when (this) {
            is Loading -> onLoading(isLoading)
            is Success -> onSuccess(data)
            is Error -> onError(message, errorType, httpCode)
        }
    }
}
