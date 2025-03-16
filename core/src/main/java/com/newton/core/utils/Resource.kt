package com.newton.core.utils

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