package com.newton.core.utils

import com.newton.core.enums.ErrorType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException
import kotlin.coroutines.cancellation.CancellationException


fun <T> safeApiCall(
    apiCall: suspend () -> T
): Flow<Resource<T>> = flow {
    try {
        emit(Resource.Loading(true))
        val response = apiCall()
        emit(Resource.Success(data = response))
    } catch (e: SocketTimeoutException) {
        emit(Resource.Error(message = "Request timed out. Please try again."))
    } catch (e: HttpException) {
        val errorMessage = handleError(e)
        emit(Resource.Error(message = errorMessage))
    } catch (e: IOException) {
        emit(Resource.Error(message = "Network error: Please check your internet connection"))
    } catch (e: Exception) {
        emit(Resource.Error(message = "An unexpected error occurred: ${e.message ?: "Unknown error"}"))
    } finally {
        emit(Resource.Loading(isLoading = false))
    }
}


private fun handleError(error: HttpException, message: String? = null): String {
    return when (val code = error.code()) {
        400 -> "Invalid request: Please check your input"
        401 -> "Unauthorized: Please log in again"
        403 -> "Access denied. ${message ?: ""}"
        404 -> "Resource not found"
        in 500..599 -> "Server error: Please try again later"
        else -> "Network error (Code: $code): ${error.message()}"
    }
}


fun <T> safeApiCall(
    apiCall: suspend () -> T,
    errorHandler: (Throwable) -> String = { it.localizedMessage ?: "Unknown error occurred" }
): Flow<ResourceMigration<T>> = flow {
    emit(ResourceMigration.Loading(true))

    try {
        val response = apiCall()
        emit(ResourceMigration.Success(data = response))
    } catch (e: CancellationException) {
        emit(ResourceMigration.Loading(false))
        throw e
    } catch (e: SocketTimeoutException) {
        emit(ResourceMigration.Error(message = "Request timed out. Please try again.", errorType = ErrorType.TIMEOUT))
    } catch (e: TimeoutException) {
        emit(ResourceMigration.Error(message = "Request timed out. Please try again.", errorType = ErrorType.TIMEOUT))
    } catch (e: ConnectException) {
        emit(ResourceMigration.Error(message = "Couldn't connect to server. Please check your internet connection.", errorType = ErrorType.CONNECTIVITY))
    } catch (e: UnknownHostException) {
        emit(ResourceMigration.Error(message = "Couldn't find the server. Please check your internet connection.", errorType = ErrorType.CONNECTIVITY))
    } catch (e: HttpException) {
        val errorResponse = handleHttpError(e)
        emit(ResourceMigration.Error(
            message = errorResponse.message,
            errorType = errorResponse.errorType,
            httpCode = e.code()
        ))
    } catch (e: IOException) {
        emit(ResourceMigration.Error(message = "Network error: ${e.message ?: "Please check your internet connection"}", errorType = ErrorType.IO))
    } catch (e: Exception) {
        val errorMessage = errorHandler(e)
        emit(ResourceMigration.Error(message = "An unexpected error occurred: $errorMessage", errorType = ErrorType.UNKNOWN))
    } finally {
        emit(ResourceMigration.Loading(false))
    }
}.catch { e ->
    if (e is CancellationException) {
        throw e
    } else {
        emit(ResourceMigration.Error(message = "Fatal error: ${e.message ?: "Unknown error"}", errorType = ErrorType.FATAL))
        emit(ResourceMigration.Loading(false))
    }
}

/**
 * Processes HTTP errors and returns appropriate error messages
 */
private fun handleHttpError(error: HttpException): ErrorResponse {
    return when (val code = error.code()) {
        400 -> ErrorResponse("Invalid request: Please check your input", ErrorType.CLIENT_ERROR)
        401 -> ErrorResponse("Unauthorized: Please log in again", ErrorType.AUTHENTICATION)
        403 -> ErrorResponse("Access denied: You don't have permission to access this resource", ErrorType.AUTHORIZATION)
        404 -> ErrorResponse("Resource not found", ErrorType.NOT_FOUND)
        408 -> ErrorResponse("Request timeout: Please try again", ErrorType.TIMEOUT)
        409 -> ErrorResponse("Conflict: The request couldn't be completed due to a conflict", ErrorType.CONFLICT)
        422 -> ErrorResponse("Validation failed: Please check your input", ErrorType.VALIDATION)
        429 -> ErrorResponse("Too many requests: Please try again later", ErrorType.RATE_LIMIT)
        in 500..599 -> ErrorResponse("Server error: Please try again later", ErrorType.SERVER_ERROR)
        else -> ErrorResponse("Network error (Code: $code): ${error.message()}", ErrorType.HTTP_ERROR)
    }
}

/**
 * Error response model for HTTP errors
 */
data class ErrorResponse(
    val message: String,
    val errorType: ErrorType
)




